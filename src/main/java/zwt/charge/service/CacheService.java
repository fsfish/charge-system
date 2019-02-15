package zwt.charge.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zwt.charge.core.utils.BeanCopyUtils;
import zwt.charge.core.utils.DateUtils;
import zwt.charge.core.utils.JsonUtils;
import zwt.charge.component.RedisComponent;
import zwt.charge.core.utils.RedisKeyUtils;
import zwt.charge.bean.CacheDataBean;
import zwt.charge.bean.ChargeOriginalData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Author: lilongzhou
 * @Description: 缓存数据
 * @Date: Created in 下午3:33 2018/12/20
 */
@Service
@Slf4j
public class CacheService {

    @Autowired
    private RedisComponent redisComponent;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    /**
     * 对数据进行缓存 同步操作
     * @param chargeOriginalData 需缓存的原始数据
     */
    public synchronized void cacheDataList(ChargeOriginalData chargeOriginalData) throws Exception {
        CacheDataBean cacheDataBean = BeanCopyUtils.copy(chargeOriginalData, CacheDataBean.class);
        cacheDataBean.setStartTime(DateTime.parse(chargeOriginalData.getStartTime(), dateTimeFormatter).toDate().toString());
        cacheDataBean.setEndTime(DateTime.parse(chargeOriginalData.getEndTime(), dateTimeFormatter).toDate().toString());

        String needStored = JsonUtils.toJsonObject(cacheDataBean);
        //Thread.sleep(100);
        String storedValue = getDataCache(RedisKeyUtils.deviceAliasKey(cacheDataBean.getId()).trim(),
                RedisKeyUtils.deviceStreamKey(cacheDataBean.getStreamId()).trim());

        Date lastModifyTime = DateUtils.dateOriToDate(getconsumptionDataCache(cacheDataBean.getId()));
        Date endTime = DateUtils.dateOriToDate(cacheDataBean.getEndTime());
        log.info("storedValue: {}", storedValue);
        if (StringUtils.isNotEmpty(storedValue)) {
            // 取出数据，对数据进行jsonarray操作然后再行插入
            //storedValue = getDataCache(cacheDataBean.getId(), cacheDataBean.getStreamId());
            List<String> jsonArray = JsonUtils.strToArray(storedValue);
            jsonArray.add(needStored);
            // 存储jsonarrays
            dataCache(RedisKeyUtils.deviceAliasKey(cacheDataBean.getId()).trim(),
                    RedisKeyUtils.deviceStreamKey(cacheDataBean.getStreamId()).trim(),
                    JsonUtils.toArray(jsonArray).toJSONString());
            if (lastModifyTime == null) {
                consumptionDataCache(cacheDataBean.getId(), cacheDataBean.getEndTime());
            } else {
                if (endTime != null && lastModifyTime.before(endTime)) {
                    consumptionDataCache(cacheDataBean.getId(), cacheDataBean.getEndTime());
                }
            }
        } else {
            // 直接插入数据
            List<String> storeValue = new ArrayList<>();
            storeValue.add(needStored);
            log.info("direct storedValue: {}", storedValue);
            dataCache(RedisKeyUtils.deviceAliasKey(cacheDataBean.getId()).trim(),
                    RedisKeyUtils.deviceStreamKey(cacheDataBean.getStreamId()).trim(),
                    JsonUtils.toArray(storeValue).toJSONString());
            if (lastModifyTime == null) {
                consumptionDataCache(cacheDataBean.getId(), cacheDataBean.getEndTime());
            } else {
                if (endTime != null && lastModifyTime.before(endTime)) {
                    consumptionDataCache(cacheDataBean.getId(), cacheDataBean.getEndTime());
                }
            }
        }

        String hasStored = getDataCache(RedisKeyUtils.deviceAliasKey(cacheDataBean.getId()).trim(),
                RedisKeyUtils.deviceStreamKey(cacheDataBean.getStreamId()).trim());
        log.info("hasStoredValue: {}", hasStored);

    }



    /*public void cacheDataList(List<ChargeOriginalData> list) {
        List<CacheDataBean> cacheDataList =
                list.stream().map(chargeOriginalData -> BeanCopyUtils.copy(chargeOriginalData, CacheDataBean.class)).collect(Collectors.toList());
        for (CacheDataBean cacheDataBean : cacheDataList) {
            String storedValue = getDataCache(RedisKeyUtils.deviceAliasKey(cacheDataBean.getId()).trim(),
                    RedisKeyUtils.deviceStreamKey(cacheDataBean.getStreamId()).trim());
            if (StringUtils.isNotEmpty(storedValue)) {
                // 取出数据，对数据进行jsonarray操作然后再行插入
                //storedValue = getDataCache(cacheDataBean.getId(), cacheDataBean.getStreamId());
                List<String> jsonArray = JsonUtils.strToArray(storedValue);
                jsonArray.add(JsonUtils.toJsonObject(cacheDataBean));
                // 存储jsonarrays
                dataCache(RedisKeyUtils.deviceAliasKey(cacheDataBean.getId()).trim(),
                        RedisKeyUtils.deviceStreamKey(cacheDataBean.getStreamId()).trim(),
                        JsonUtils.toArray(jsonArray).toJSONString());
                consumptionDataCache(cacheDataBean.getId(), cacheDataBean.getEndTime());
            } else {
                // 直接插入数据
                List<String> storeValue = new ArrayList<>();
                storeValue.add(JsonUtils.toJsonObject(cacheDataBean));
                dataCache(RedisKeyUtils.deviceAliasKey(cacheDataBean.getId()).trim(),
                        RedisKeyUtils.deviceStreamKey(cacheDataBean.getStreamId()).trim(),
                        JsonUtils.toArray(storeValue).toJSONString());
                consumptionDataCache(cacheDataBean.getId(), cacheDataBean.getEndTime());
            }
        }
    }*/

    // 存储原始数据
    public boolean dataCache(String key, String hKey, String value) {
        redisComponent.writeHash(key, hKey, value);
        return true;
    }

    // 存储计费中的deviceId
    public boolean consumptionDataCache(String hkey, String value) {
        redisComponent.writeHash(RedisKeyUtils.consumptionKey(), hkey, value);
        return true;
    }

    // 查询计费中的deviceId的数据
    public String getconsumptionDataCache(String hkey) {
        String lastModifyTime = redisComponent.loadHash(RedisKeyUtils.consumptionKey(), hkey);
        return lastModifyTime;
    }

    /**
     * 获取缓存数据
     * @param key key
     * @param hKey hkey
     * @return
     */
    public String getDataCache(String key, String hKey) {
        return redisComponent.loadHash(key, hKey);
    }


    public Long getLongDataCache(String key, String hKey) {
        return redisComponent.loadHashLong(key, hKey);
    }

    public void LongDataCache(String key, String hKey, long value) {
        redisComponent.writeHashLong(key, hKey, value);
    }

    public Long hashIncrBy(String key, String keyName, long value) {
        return redisComponent.hashIncrBy(key, keyName, value);
    }

    /**
     * 根据key获取数据
     * @param key key
     * @return
     */
    public Set<String> hashGetKeysByKey(String key) {
        return redisComponent.hashHKs(key);
    }

    public List<String> hashGetValuesByKey(String key) {
        return redisComponent.hashValues(key);
    }

    public void clearData(String key, String hkey) {
        redisComponent.clearByKey(key, hkey);
    }

}
