package zwt.charge.service;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zwt.charge.core.utils.BeanCopyUtils;
import zwt.charge.core.utils.DateUtils;
import zwt.charge.core.utils.JsonUtils;
import zwt.charge.bean.ChargeOriginalData;
import zwt.charge.domain.ChargeOriginalBak;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lilongzhou
 * @Description: 读取数据队列
 * @Date: Created in 下午3:32 2018/12/20
 */
@Component
@Slf4j
public class QueueJobService {

    @Autowired
    private BackupsService backupsService;

    @Autowired
    private CacheService cacheService;

    public boolean saveData(String data) throws Exception {
        long startTime = System.currentTimeMillis();

        ChargeOriginalData chargeOriginalData = JsonUtils.jsonToObject(data, ChargeOriginalData.class);
        //chargeOriginalData.setStartTime(DateTime.parse(chargeOriginalData.getStartTime(), dateTimeFormatter).toDate().toString());
        //chargeOriginalData.setEndTime(DateTime.parse(chargeOriginalData.getEndTime(), dateTimeFormatter).toDate().toString());

        //List<ChargeOriginalData> list = new ArrayList<>();
        //list.add(chargeOriginalData);

        long readEndTime = System.currentTimeMillis();
        log.info("readAndRechangeTime: {}", readEndTime - startTime);

        ChargeOriginalBak chargeOriginalBak = BeanCopyUtils.copy(chargeOriginalData, ChargeOriginalBak.class);
        chargeOriginalBak.setDeviceId(chargeOriginalData.getId());

        if (backupsService.backups(chargeOriginalBak)) {
            long backupsTime = System.currentTimeMillis();
            log.info("backupsTime: {}", backupsTime - readEndTime);
            // 缓存 对原始数据进行缓存
            cacheService.cacheDataList(chargeOriginalData);
            long cacheTime = System.currentTimeMillis();
            log.info("cacheTime: {}", cacheTime - backupsTime);
            return true;
        } else {
            // 做重试操作
            return false;
        }

    }

}
