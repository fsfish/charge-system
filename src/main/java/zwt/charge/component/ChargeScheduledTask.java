package zwt.charge.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zwt.charge.service.PersistenceService;

import java.text.ParseException;

/**
 * @Author: lilongzhou
 * @Description: 定时任务操作
 * @Date: Created in 上午11:19 2019/1/23
 */
@Component
@Slf4j
public class ChargeScheduledTask {

    @Autowired
    private PersistenceService persistenceService;

    // 每1分钟检测一次 对上完课的设备进行数据归档
    @Scheduled(cron = "0 */1 * * * ?")
    public void checkAndPersistenceSchedule() {
        try {
            //System.out.println(1);
            persistenceService.checkActived();
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 1. 定时对账户余额、消费进行归档
     * 2. 定时发送余额不足的短信
     */


}
