package zwt.charge.service;

import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zwt.charge.core.Constant;
import zwt.charge.core.utils.BeanCopyUtils;
import zwt.charge.core.utils.DateUtils;
import zwt.charge.core.utils.JsonUtils;
import zwt.charge.core.utils.RedisKeyUtils;
import zwt.charge.core.utils.UuidUtils;
import zwt.charge.dao.ChargeConsumerRepository;
import zwt.charge.dao.ChargeDetailRepository;
import zwt.charge.bean.AudioDataBean;
import zwt.charge.bean.CacheDataBean;
import zwt.charge.domain.ChargeConsumer;
import zwt.charge.domain.ChargeDetail;
import zwt.charge.bean.DurationBean;
import zwt.charge.bean.VideoDataBean;
import zwt.charge.domain.QChargeConsumer;
import zwt.charge.service.device.DeviceService;
import zwt.charge.service.vo.AgencyChargeVO;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: lilongzhou
 * @Description: 持久化数据 用于数据归档操作
 * @Date: Created in 下午3:34 2018/12/20
 */
@Service
@Slf4j
public class PersistenceService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ChargeDetailRepository chargeDetailRepository;

    @Autowired
    private ChargeConsumerRepository chargeConsumerRepository;

    // 10min
    private final static Integer activedTime = 5;

    //private DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    /**
     *
     * -- 判断每一个deviceId的最后更新时间是不是"活跃的"，这里的活跃可以通过定义的时间来判断
     *
     * 1. 原始数据归档，读取、筛选、合并；
     *
     * 2. 计算消费的算法
     *
     * 3. 通过时长计算结果，归纳消费到db中
     *
     * 4. 清除内存中的数据
     *
     */
    public void checkActived() throws ParseException {
        Set<String> keys = cacheService.hashGetKeysByKey(RedisKeyUtils.consumptionKey());
        // 保持幂等性的字段
        String ticket = UuidUtils.generateOrder();
        for (String hkey : keys) {
            // 获取正在上课的课程id
            String coursing = cacheService.getDataCache(RedisKeyUtils.deviceCoursing(hkey).trim(), hkey);
            if (StringUtils.isEmpty(coursing)) {
                AgencyChargeVO agencyChargeVO = getAgencyInfo(hkey);
                agencyChargeVO.setTicket(ticket + agencyChargeVO.getCourseId());
                String agencyChargeJson = JsonUtils.toJsonObject(agencyChargeVO);
                cacheService.dataCache(RedisKeyUtils.deviceCoursing(hkey).trim(), hkey, agencyChargeJson);
            }
            String lastModifyTime = cacheService.getDataCache(RedisKeyUtils.consumptionKey(), hkey);
            //String date = DateUtils.timeStamp2Date(lastModifyTime);
            boolean flag = new DateTime(DateUtils.dateOriToDate(lastModifyTime)).plusMinutes(activedTime).toDate().before(DateTime.now().toDate());
            if (flag) {
                // 数据归档 -> 将连续时间进行合并操作
                keepMemoryData(hkey);
                // 清除key
                dataPersistenceClearKey(hkey);
                clearCoursingKey(RedisKeyUtils.deviceCoursing(hkey).trim(), hkey);
            }
        }
    }

    public void keepMemoryData(String deviceAlias) throws ParseException {
        //Set<String> keyList2 = cacheDataService.hashGetKeysByKey(RedisKeyUtils.consumptionKey());
        // 得到每一个设备的订阅设备信息
        List<String> values = cacheService.hashGetValuesByKey(RedisKeyUtils.deviceAliasKey(deviceAlias));

        // 查询该alias正在上课的课程和机构信息
        //AgencyChargeVO agencyChargeVO = getAgencyInfo(deviceAlias);
        String coursing = cacheService.getDataCache(RedisKeyUtils.deviceCoursing(deviceAlias).trim(), deviceAlias);
        AgencyChargeVO agencyChargeVO = JsonUtils.jsonToObject(coursing, AgencyChargeVO.class);

        AtomicLong courseConsumerDuration = new AtomicLong(0);
        AtomicLong courseConsumerDurationCharge = new AtomicLong(0);
        AtomicLong courseConsumerFlow = new AtomicLong(0);
        AtomicLong courseConsumerFlowCharge = new AtomicLong(0);
        AtomicLong courseConsumerConsumerMoney = new AtomicLong(0);

        for (String value : values) {
            // 获取每条redis存储数据数组
            List<String> deviceOriginDateList = JsonUtils.strToArray(value);
            List<String> deviceDataList = JsonUtils.strToArray(value);
            List<AudioDataBean> audioDataBeanList = new ArrayList<>();
            List<VideoDataBean> videoDataBeanList = new ArrayList<>();
            // 将这些数组转换成对象
            for(String deviceData : deviceDataList) {
                CacheDataBean cacheDataBean = JsonUtils.jsonToObject(deviceData, CacheDataBean.class);
                // 视频
                if (cacheDataBean.getStreamType().equalsIgnoreCase(Constant.VIDEO_TYPE.trim())) {
                    videoDataBeanList.add(BeanCopyUtils.copy(cacheDataBean, VideoDataBean.class));
                }
                // 音频
                if (cacheDataBean.getStreamType().equalsIgnoreCase(Constant.AUDIO_TYPE.trim())) {
                    audioDataBeanList.add(BeanCopyUtils.copy(cacheDataBean, AudioDataBean.class));
                }
            }
            log.info("audioDataBeanList: {}", audioDataBeanList);
            log.info("videoDataBeanList: {}", videoDataBeanList);



            List<DurationBean> durationBeanList = new ArrayList<>();
            if (videoDataBeanList.size() > 0) {
                // 对同一个订阅设备的音视频流进行时间段合并
                durationBeanList = timeSlotMerge(deviceAlias, videoDataBeanList.get(0).getStreamId(),
                                audioDataBeanList, videoDataBeanList);
            } else {
                if (audioDataBeanList.size() > 0) {
                    durationBeanList = timeSlotMerge(deviceAlias, audioDataBeanList.get(0).getStreamId(),
                            audioDataBeanList, videoDataBeanList);
                }
            }
            /*// 对同一个订阅设备的音视频流进行时间段合并
            List<DurationBean> durationBeanList =
                    timeSlotMerge(deviceAlias, audioDataBeanList.get(0).getStreamId(),
                            audioDataBeanList, videoDataBeanList);*/

            log.info("durationBeanList: {}", durationBeanList);
            // 合并时间段保存操作
            for (DurationBean durationBean : durationBeanList) {
                ChargeDetail chargeDetail = BeanCopyUtils.copy(durationBean, ChargeDetail.class);
                if (agencyChargeVO != null) {
                    if (agencyChargeVO.getAgencyId() != null) {
                        chargeDetail.setAgencyId(agencyChargeVO.getAgencyId());
                    }
                    if (agencyChargeVO.getCourseId() != null) {
                        chargeDetail.setCourseId(agencyChargeVO.getCourseId());
                    }
                    chargeDetail.setDeviceType(agencyChargeVO.getDeviceType());
                    chargeDetail.setTicket(agencyChargeVO.getTicket());
                }
                chargeDetail.setCreateTime(new Date());
                // 保存到ChargeDetail中
                chargeDetailRepository.save(chargeDetail);
            }

            List<DurationBean> durationConsumerList = new ArrayList<>();
            // 消费时长算法
            // 计算消费时长 将这段音视频的时间段合并,算消费金额
            if (videoDataBeanList.size() > 0) {
                durationConsumerList = durationConsumer(deviceAlias, videoDataBeanList.get(0).getStreamId(), durationBeanList);
            } else {
                if (audioDataBeanList.size() > 0) {
                    durationConsumerList = durationConsumer(deviceAlias, audioDataBeanList.get(0).getStreamId(), durationBeanList);
                }
            }
            /*List<DurationBean> durationConsumerList =
                    durationConsumer(deviceAlias, audioDataBeanList.get(0).getStreamId(), durationBeanList);*/
            log.info("durationConsumerList: {}", durationConsumerList);

            for (DurationBean durationBean : durationConsumerList) {
                ChargeDetail chargeDetail = BeanCopyUtils.copy(durationBean, ChargeDetail.class);
                if (agencyChargeVO != null) {
                    if (agencyChargeVO.getAgencyId() != null) {
                        chargeDetail.setAgencyId(agencyChargeVO.getAgencyId());
                    }
                    if (agencyChargeVO.getCourseId() != null) {
                        chargeDetail.setCourseId(agencyChargeVO.getCourseId());
                    }
                    if (agencyChargeVO.getPrice() > 0) {
                        // 消费金额 分为单位 这里的时长是秒为单位， 需将秒划分为分钟然后计算
                        long minute = getMinute(chargeDetail.getDurations());
                        long money = minute * agencyChargeVO.getPrice();
                        log.info("设备: " + deviceAlias + ", 本次消费: " + Double.valueOf(money) / 100 + "元");
                        chargeDetail.setDeviceStreamConsumerMoney(money);
                        // 更新缓存 缓存中的money 单位是分
                        DurationBean consumer = cacheKeepBalance(agencyChargeVO.getAgencyId(), money);
                        chargeDetail.setRechargeMoney(consumer.getRechargeMoney());
                        chargeDetail.setPresentMoney(consumer.getPresentMoney());
                        chargeDetail.setCreditMoney(consumer.getCreditMoney());
                        // 课程消费表数据
                        courseConsumerDuration.addAndGet(chargeDetail.getDurations());
                        courseConsumerDurationCharge.addAndGet(money);
                        courseConsumerFlow.addAndGet(chargeDetail.getFlowUsed());
                        courseConsumerFlowCharge.addAndGet(chargeDetail.getFlowConsumerMoney());
                        courseConsumerConsumerMoney.addAndGet(money + chargeDetail.getFlowConsumerMoney());
                    }
                    chargeDetail.setDeviceType(agencyChargeVO.getDeviceType());
                    chargeDetail.setCreateTime(new Date());
                    chargeDetail.setTicket(agencyChargeVO.getTicket());
                }
                // 保存到ChargeDetail中
                chargeDetailRepository.save(chargeDetail);
            }

            // 在缓存中去掉该数据 去掉缓存中这些已经归档的数据
            if (videoDataBeanList.size() > 0) {
                String cacheStr = cacheService.getDataCache(RedisKeyUtils.deviceAliasKey(deviceAlias).trim(),
                        RedisKeyUtils.deviceStreamKey(videoDataBeanList.get(0).getStreamId()).trim());
                List<String> data = JsonUtils.strToArray(cacheStr);
                data.removeAll(deviceOriginDateList);
                cacheService.dataCache(RedisKeyUtils.deviceAliasKey(deviceAlias).trim(),
                        RedisKeyUtils.deviceStreamKey(videoDataBeanList.get(0).getStreamId().trim()),
                        JsonUtils.toArray(data).toJSONString());
            } else {
                if (audioDataBeanList.size() > 0) {
                    String cacheStr = cacheService.getDataCache(RedisKeyUtils.deviceAliasKey(deviceAlias).trim(),
                            RedisKeyUtils.deviceStreamKey(audioDataBeanList.get(0).getStreamId()).trim());
                    List<String> data = JsonUtils.strToArray(cacheStr);
                    data.removeAll(deviceOriginDateList);
                    cacheService.dataCache(RedisKeyUtils.deviceAliasKey(deviceAlias).trim(),
                            RedisKeyUtils.deviceStreamKey(audioDataBeanList.get(0).getStreamId()).trim(),
                            JsonUtils.toArray(data).toJSONString());
                }
            }

        }

        // 聚合到课程消费记录表中
        ChargeConsumer chargeConsumer = new ChargeConsumer();
        if (agencyChargeVO != null) {
           chargeConsumer =
                    getByCourseIdAndTicket(agencyChargeVO.getAgencyId(), agencyChargeVO.getCourseId(), agencyChargeVO.getTicket());
        }
        if (chargeConsumer != null) {
            chargeConsumer.setDuration(courseConsumerDuration.get() + chargeConsumer.getDuration());
            chargeConsumer.setDurationCharge(courseConsumerDurationCharge.get() + chargeConsumer.getDurationCharge());
            chargeConsumer.setFlow(courseConsumerFlow.get() + chargeConsumer.getFlow());
            chargeConsumer.setFlowCharge(courseConsumerFlowCharge.get() + chargeConsumer.getFlowCharge());
            chargeConsumer.setConsumerMoney(courseConsumerConsumerMoney.get() + chargeConsumer.getConsumerMoney());
        } else {
            chargeConsumer.setDuration(courseConsumerDuration.get());
            chargeConsumer.setFlow(courseConsumerFlow.get());
            chargeConsumer.setDurationCharge(courseConsumerDurationCharge.get());
            chargeConsumer.setFlowCharge(courseConsumerFlowCharge.get());
            chargeConsumer.setConsumerMoney(courseConsumerConsumerMoney.get());
        }
        chargeConsumer.setAgencyId(agencyChargeVO.getAgencyId());
        chargeConsumer.setCourseId(agencyChargeVO.getCourseId());
        chargeConsumer.setTicket(agencyChargeVO.getTicket());
        chargeConsumer.setModifyTime(new Date());
        chargeConsumer.setCreateTime(new Date());
        chargeConsumer.setProductTime(new Date());
        chargeConsumerRepository.save(chargeConsumer);

    }

    private DurationBean cacheKeepBalance(Long agencyId, long money) {
        DurationBean durationBean = new DurationBean();
        AtomicLong balanceConsumer = new AtomicLong(0);
        AtomicLong presentConsumer = new AtomicLong(0);
        AtomicLong creditConsumer = new AtomicLong(0);
        AtomicLong atomicMoney = new AtomicLong(money);
        // 余额 空值处理
        Long agencyBalance = cacheService.getLongDataCache(RedisKeyUtils.chargeAgencyBalanceKey(agencyId),
                RedisKeyUtils.chargeAgencyhashKey(Constant.CHARGE_AGENCY_BALANCE).trim());
        // 赠送金额
        Long agencyPresent = cacheService.getLongDataCache(RedisKeyUtils.chargeAgencyBalanceKey(agencyId),
                RedisKeyUtils.chargeAgencyhashKey(Constant.CHARGE_AGENCY_PRESENT).trim());
        // 信用额度
        Long agencyCredit = cacheService.getLongDataCache(RedisKeyUtils.chargeAgencyBalanceKey(agencyId),
                RedisKeyUtils.chargeAgencyhashKey(Constant.CHARGE_AGENCY_CREDIT).trim());
        if (agencyBalance == null) {
            agencyBalance = 0L;
        }
        if (agencyPresent == null) {
            agencyPresent = 0L;
        }
        if (agencyCredit == null) {
            agencyCredit = 0L;
        }
        AtomicLong cacheMoney = new AtomicLong(agencyBalance + agencyPresent + agencyCredit);
        if (cacheMoney.get() > atomicMoney.get()) {
            // 1. 先扣赠送金额，再扣余额,最后扣除信用额度
            if (agencyPresent > 0) {
                if (agencyPresent > atomicMoney.get()) {
                    // 赠送金额大于当前消费的时候
                    cacheService.hashIncrBy(RedisKeyUtils.chargeAgencyBalanceKey(agencyId),
                            RedisKeyUtils.chargeAgencyhashKey(Constant.CHARGE_AGENCY_PRESENT).trim(), -atomicMoney.get());
                    presentConsumer.addAndGet(atomicMoney.get());
                    atomicMoney.set(0);
                } else {
                    // 赠送金额小于当前消费的时候
                    atomicMoney.addAndGet(-agencyPresent);
                    presentConsumer.addAndGet(agencyPresent);
                    cacheService.LongDataCache(RedisKeyUtils.chargeAgencyBalanceKey(agencyId),
                            RedisKeyUtils.chargeAgencyhashKey(Constant.CHARGE_AGENCY_PRESENT).trim(), 0);
                    if (agencyBalance > atomicMoney.get()) {
                        // 余额大于当前消费的时候
                        cacheService.hashIncrBy(RedisKeyUtils.chargeAgencyBalanceKey(agencyId),
                                RedisKeyUtils.chargeAgencyhashKey(Constant.CHARGE_AGENCY_BALANCE).trim(), -atomicMoney.get());
                        balanceConsumer.addAndGet(atomicMoney.get());
                        atomicMoney.set(0);
                    } else {
                        // 余额小于当前消费的时候
                        atomicMoney.addAndGet(-agencyBalance);
                        balanceConsumer.addAndGet(agencyBalance);
                        cacheService.LongDataCache(RedisKeyUtils.chargeAgencyBalanceKey(agencyId),
                                RedisKeyUtils.chargeAgencyhashKey(Constant.CHARGE_AGENCY_BALANCE), 0);
                        cacheService.hashIncrBy(RedisKeyUtils.chargeAgencyBalanceKey(agencyId),
                                RedisKeyUtils.chargeAgencyhashKey(Constant.CHARGE_AGENCY_CREDIT).trim(), -atomicMoney.get());
                        creditConsumer.addAndGet(atomicMoney.get());
                        atomicMoney.set(0);
                    }
                }
            } else {
                if (agencyBalance > atomicMoney.get()) {
                    // 余额大于当前消费的时候
                    cacheService.hashIncrBy(RedisKeyUtils.chargeAgencyBalanceKey(agencyId),
                            RedisKeyUtils.chargeAgencyhashKey(Constant.CHARGE_AGENCY_BALANCE).trim(), -atomicMoney.get());
                    balanceConsumer.addAndGet(atomicMoney.get());
                    atomicMoney.set(0);
                } else {
                    // 余额小于当前消费的时候
                    atomicMoney.addAndGet(-agencyBalance);
                    balanceConsumer.addAndGet(agencyBalance);
                    cacheService.LongDataCache(RedisKeyUtils.chargeAgencyBalanceKey(agencyId),
                            RedisKeyUtils.chargeAgencyhashKey(Constant.CHARGE_AGENCY_BALANCE), 0);
                    cacheService.hashIncrBy(RedisKeyUtils.chargeAgencyBalanceKey(agencyId),
                            RedisKeyUtils.chargeAgencyhashKey(Constant.CHARGE_AGENCY_CREDIT).trim(), -atomicMoney.get());
                    creditConsumer.addAndGet(atomicMoney.get());
                    atomicMoney.set(0);
                }
            }
        } else {
            cacheService.LongDataCache(RedisKeyUtils.chargeAgencyBalanceKey(agencyId),
                    RedisKeyUtils.chargeAgencyhashKey(Constant.CHARGE_AGENCY_BALANCE), -(atomicMoney.get() - cacheMoney.get()));
            cacheService.LongDataCache(RedisKeyUtils.chargeAgencyBalanceKey(agencyId),
                    RedisKeyUtils.chargeAgencyhashKey(Constant.CHARGE_AGENCY_PRESENT), 0);
            cacheService.LongDataCache(RedisKeyUtils.chargeAgencyBalanceKey(agencyId),
                    RedisKeyUtils.chargeAgencyhashKey(Constant.CHARGE_AGENCY_CREDIT), 0);
            balanceConsumer.addAndGet(atomicMoney.get() - agencyPresent - agencyCredit);
            presentConsumer.addAndGet(agencyPresent);
            presentConsumer.addAndGet(agencyCredit);
            atomicMoney.set(0);
        }
        durationBean.setRechargeMoney(balanceConsumer.get());
        durationBean.setPresentMoney(presentConsumer.get());
        durationBean.setCreditMoney(creditConsumer.get());
        return durationBean;
    }

    private long getMinute(long durations) {
        AtomicLong atomicMinute = new AtomicLong(durations / 60000);
        if ((durations % 60000) > 5000) {
            atomicMinute.addAndGet(1);
        }
        return atomicMinute.get();
    }

    /**
     * 对单个订阅设备的音视频流会进行时间段合并
     * @param deviceId 设备id
     * @param streamId 订阅设备id
     * @param audioDataBeanList 订阅设备音频使用时段
     * @param videoDataBeanList 订阅设备视频使用时段
     * @return 合并结果
     */
    private List<DurationBean> timeSlotMerge(String deviceId, String streamId, List<AudioDataBean> audioDataBeanList, List<VideoDataBean> videoDataBeanList) throws ParseException {

        List<DurationBean> durationBeanList = new ArrayList<>();
        /**音频数据合并**/

        // 根据时间进行排序
        audioDataBeanList.sort((o1, o2) -> {
            Date o1StartTime = null;
            Date o2StartTime = null;
            try {
                o1StartTime = DateUtils.dateOriToDate(o1.getStartTime());
                o2StartTime = DateUtils.dateOriToDate(o2.getStartTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (o1StartTime.before(o2StartTime)) {
                return -1;
            } else if (o1StartTime.after(o2StartTime)) {
                return 1;
            } else {
                return 0;
            }
        });
        if (audioDataBeanList.size() == 1) {
            DurationBean durationBean = new DurationBean();
            durationBean.setDeviceAlias(deviceId);
            durationBean.setDeviceStreamName(streamId);
            durationBean.setDeviceStreamType(Constant.AUDIO_TYPE.trim());
            durationBean.setStreamStartUsedTime(DateUtils.dateOriToDate(audioDataBeanList.get(0).getStartTime()));
            durationBean.setStreamEndUsedTime(DateUtils.dateOriToDate(audioDataBeanList.get(0).getEndTime()));
            durationBean.setFlowUsed(audioDataBeanList.get(0).getFlow());
            durationBeanList.add(durationBean);
        }
        if (audioDataBeanList.size() > 1) {
            Date startTime =  DateUtils.dateOriToDate(audioDataBeanList.get(0).getStartTime());
            Date endTime = DateUtils.dateOriToDate(audioDataBeanList.get(0).getEndTime());
            AtomicLong flow = new AtomicLong(audioDataBeanList.get(0).getFlow());
            // 片段合并
            for (int i = 0; i < audioDataBeanList.size() - 1; i++) {
                Date nextStartTime = DateUtils.dateOriToDate(audioDataBeanList.get(i + 1).getStartTime());
                Date nextEndTime = DateUtils.dateOriToDate(audioDataBeanList.get(i + 1).getEndTime());
                AtomicLong nextFlow = new AtomicLong(audioDataBeanList.get(i + 1).getFlow());
                if (endTime.equals(nextStartTime)) {
                    endTime = nextEndTime;
                    flow.addAndGet(nextFlow.get());
                } else {
                    DurationBean durationBean = new DurationBean();
                    durationBean.setDeviceAlias(deviceId);
                    durationBean.setDeviceStreamName(streamId);
                    durationBean.setDeviceStreamType(Constant.AUDIO_TYPE.trim());
                    durationBean.setStreamStartUsedTime(startTime);
                    durationBean.setStreamEndUsedTime(endTime);
                    durationBean.setFlowUsed(flow.get());
                    durationBeanList.add(durationBean);
                    startTime = nextStartTime;
                    endTime = nextEndTime;
                    flow = new AtomicLong(nextFlow.get());
                }
                if ((i + 1) == audioDataBeanList.size() - 1) {
                    DurationBean nextDurationBean = new DurationBean();
                    nextDurationBean.setDeviceAlias(deviceId);
                    nextDurationBean.setDeviceStreamName(streamId);
                    nextDurationBean.setDeviceStreamType(Constant.AUDIO_TYPE.trim());
                    nextDurationBean.setStreamStartUsedTime(startTime);
                    nextDurationBean.setStreamEndUsedTime(endTime);
                    nextDurationBean.setFlowUsed(flow.get());
                    durationBeanList.add(nextDurationBean);
                }
            }
        }


        /**视频数据合并**/
        videoDataBeanList.sort((o1, o2) -> {
            Date o1StartTime = null;
            Date o2StartTime = null;
            try {
                o1StartTime = DateUtils.dateOriToDate(o1.getStartTime());
                o2StartTime = DateUtils.dateOriToDate(o2.getStartTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (o1StartTime.before(o2StartTime)) {
                return -1;
            } else if (o1StartTime.after(o2StartTime)) {
                return 1;
            } else {
                return 0;
            }
        });

        if (videoDataBeanList.size() == 1) {
            DurationBean durationBean = new DurationBean();
            durationBean.setDeviceAlias(deviceId);
            durationBean.setDeviceStreamName(streamId);
            durationBean.setDeviceStreamType(Constant.VIDEO_TYPE.trim());
            durationBean.setStreamStartUsedTime(DateUtils.dateOriToDate(videoDataBeanList.get(0).getStartTime()));
            durationBean.setStreamEndUsedTime(DateUtils.dateOriToDate(videoDataBeanList.get(0).getEndTime()));
            durationBean.setFlowUsed(videoDataBeanList.get(0).getFlow());
            durationBeanList.add(durationBean);
        }
        if (videoDataBeanList.size() > 1) {
            Date videoStartTime = DateUtils.dateOriToDate(videoDataBeanList.get(0).getStartTime());
            Date videoEndTime = DateUtils.dateOriToDate(videoDataBeanList.get(0).getEndTime());
            AtomicLong videoFlow = new AtomicLong(videoDataBeanList.get(0).getFlow());
            // 片段合并
            for (int i = 0; i < videoDataBeanList.size() - 1; i++) {
                Date nextStartTime = DateUtils.dateOriToDate(videoDataBeanList.get(i + 1).getStartTime());
                Date nextEndTime = DateUtils.dateOriToDate(videoDataBeanList.get(i + 1).getEndTime());
                AtomicLong nextFlow = new AtomicLong(videoDataBeanList.get(i + 1).getFlow());
                if (videoEndTime.equals(nextStartTime)) {
                    videoEndTime = nextEndTime;
                    videoFlow.addAndGet(nextFlow.get());
                } else {
                    DurationBean durationBean = new DurationBean();
                    durationBean.setDeviceAlias(deviceId);
                    durationBean.setDeviceStreamName(streamId);
                    durationBean.setDeviceStreamType(Constant.VIDEO_TYPE.trim());
                    durationBean.setStreamStartUsedTime(videoStartTime);
                    durationBean.setStreamEndUsedTime(videoEndTime);
                    durationBean.setFlowUsed(videoFlow.get());
                    durationBeanList.add(durationBean);
                    videoStartTime = nextStartTime;
                    videoEndTime = nextEndTime;
                    videoFlow = new AtomicLong(nextFlow.get());
                }
                if ((i + 1) == videoDataBeanList.size() - 1) {
                    DurationBean nextDurationBean = new DurationBean();
                    nextDurationBean.setDeviceAlias(deviceId);
                    nextDurationBean.setDeviceStreamName(streamId);
                    nextDurationBean.setDeviceStreamType(Constant.VIDEO_TYPE.trim());
                    nextDurationBean.setStreamStartUsedTime(videoStartTime);
                    nextDurationBean.setStreamEndUsedTime(videoEndTime);
                    nextDurationBean.setFlowUsed(videoFlow.get());
                    durationBeanList.add(nextDurationBean);
                }
            }
        }

        return durationBeanList;
    }

    /**
     * 计算消费算法 -> 计算去重时间的总时长
     */
    public List<DurationBean> durationConsumer(String deviceId, String streamId, List<DurationBean> dateList) {
        List<DurationBean> durationBeanList = new ArrayList<>();

        // 1. 对时间进行排序
        dateList.sort((o1, o2) -> {
            Date o1StartTime = o1.getStreamStartUsedTime();
            Date o2StartTime = o2.getStreamStartUsedTime();
            if (o1StartTime.before(o2StartTime)) {
                return -1;
            } else if (o1StartTime.after(o2StartTime)) {
                return 1;
            } else {
                return 0;
            }
        });

        if (dateList.size() == 1) {
            DurationBean durationBean = new DurationBean();
            durationBean.setDeviceAlias(deviceId);
            durationBean.setDeviceStreamName(streamId);
            durationBean.setDeviceStreamType(Constant.COMBINE_TYPE.trim());
            durationBean.setStreamStartUsedTime(dateList.get(0).getStreamStartUsedTime());
            durationBean.setStreamEndUsedTime(dateList.get(0).getStreamEndUsedTime());
            durationBean.setDurations(dateList.get(0).getStreamEndUsedTime().getTime() - dateList.get(0).getStreamStartUsedTime().getTime());
            durationBean.setFlowUsed(dateList.get(0).getFlowUsed());
            durationBeanList.add(durationBean);
        }
        if (dateList.size() > 1) {
            // 2. 拍完序后,去重
            Date startTime = dateList.get(0).getStreamStartUsedTime();
            Date endTime = dateList.get(0).getStreamEndUsedTime();
            AtomicLong flow = new AtomicLong(dateList.get(0).getFlowUsed());
            for (int i = 0; i < dateList.size() - 1; i++) {
                Date nextStartTime = dateList.get(i + 1).getStreamStartUsedTime();
                Date nextEndTime = dateList.get(i + 1).getStreamEndUsedTime();
                AtomicLong nextFlow = new AtomicLong(dateList.get(i + 1).getFlowUsed());
                if (endTime.before(nextStartTime)) {
                    // 时间段不连续
                    // startTime - endTime 计算这段的时长;;; flow
                    DurationBean durationBean = new DurationBean();
                    durationBean.setDeviceAlias(deviceId);
                    durationBean.setDeviceStreamName(streamId);
                    durationBean.setDeviceStreamType(Constant.COMBINE_TYPE.trim());
                    durationBean.setStreamStartUsedTime(startTime);
                    durationBean.setStreamEndUsedTime(endTime);
                    durationBean.setDurations(endTime.getTime() - startTime.getTime());
                    durationBean.setFlowUsed(flow.get());
                    durationBeanList.add(durationBean);
                    startTime = nextStartTime;
                    endTime = nextEndTime;
                    flow = new AtomicLong(nextFlow.get());
                } else {
                    // 时间段是连续
                    /**
                     * 当前device结束时间小于下一个device结束时间，以下一个device结束时间为结束时间，
                     * 否则以当前device结束时间为结束时间；
                     * flow流量累加
                     */
                    if (endTime.before(nextEndTime)) {
                        endTime = nextEndTime;
                    }
                    // 累加
                    flow.addAndGet(nextFlow.get());
                }
                if ((i + 1) == dateList.size() - 1) {
                    DurationBean nextDurationBean = new DurationBean();
                    nextDurationBean.setDeviceAlias(deviceId);
                    nextDurationBean.setDeviceStreamName(streamId);
                    nextDurationBean.setDeviceStreamType(Constant.COMBINE_TYPE.trim());
                    nextDurationBean.setStreamStartUsedTime(startTime);
                    nextDurationBean.setStreamEndUsedTime(endTime);
                    nextDurationBean.setDurations(endTime.getTime() - startTime.getTime());
                    nextDurationBean.setFlowUsed(nextFlow.get());
                    durationBeanList.add(nextDurationBean);
                }
            }
        }
        return durationBeanList;// 返回计费时长
    }

    /**
     * 持久化数据除key的数据
     */
    private void dataPersistenceClearKey(String hKey) {
        cacheService.clearData(RedisKeyUtils.consumptionKey(), hKey);
    }

    private void clearCoursingKey(String key, String hKey) {
        cacheService.clearData(key, hKey);
    }

    /**
     * 根据设备id获取机构信息
     * @param deviceAlias 设备id
     * @return 机构信息
     */
    private AgencyChargeVO getAgencyInfo(String deviceAlias) {
        AgencyChargeVO agencyChargeVO  = deviceService.getInfoByAlias(deviceAlias);
        return agencyChargeVO;
    }

    /**
     * 根据课程id和ticket消费单号（相当于订单号）来查询课程信息
     * @param agencyId 机构id
     * @param courseId 课程id
     * @param ticket 相当于课程消费单号
     * @return
     */
    private ChargeConsumer getByCourseIdAndTicket(Long agencyId, Long courseId, String ticket) {
        ChargeConsumer chargeConsumer = new ChargeConsumer();
        if (agencyId != null && courseId != null && StringUtils.isNotEmpty(ticket)) {
            QChargeConsumer qchargeConsumer = QChargeConsumer.chargeConsumer;
            Predicate predicate = qchargeConsumer.agencyId.eq(agencyId)
                    .and(qchargeConsumer.courseId.eq(courseId))
                    .and(qchargeConsumer.ticket.eq(ticket));
            Optional<ChargeConsumer> optional = chargeConsumerRepository.findOne(predicate);
            if (optional.isPresent()) {
                chargeConsumer = optional.get();
            }
        }
        return chargeConsumer;
    }





}
