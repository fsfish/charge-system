package zwt.charge.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zwt.charge.amqp.RabbitProducer;
import zwt.charge.bean.ChargeOriginalData;
import zwt.charge.component.ChargeScheduledTask;
import zwt.charge.core.Constant;
import zwt.charge.service.CacheService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 16:00 2018/8/10
 */
@Controller
@Slf4j
public class IndexController {


    @Autowired
    private RabbitProducer producer;

    @Autowired
    private ChargeScheduledTask chargeScheduledTask;

    @Autowired
    private CacheService cacheService;

    @GetMapping(value = "/rabbit/send")
    public String send() {
        producer.send();
        return "{\"code\":0}";
    }

    @GetMapping(value = "/rabbit/task")
    public String task() {
        chargeScheduledTask.checkAndPersistenceSchedule();
        return "{\"code\":0}";
    }


}
