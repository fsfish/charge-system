package zwt.charge.amqp;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zwt.charge.core.Constant;
import zwt.charge.core.utils.DateUtils;
import zwt.charge.core.utils.JsonUtils;
import zwt.charge.bean.ProduceData;

import java.util.Date;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 下午3:25 2019/1/21
 */
@Component
@Slf4j
public class RabbitProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    final RabbitTemplate.ConfirmCallback callback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.out.println(" 回调id:" + correlationData);
            if (ack) {
                System.out.println("消息成功消费");
            } else {
                //失败则进行具体的后续操作:重试 或者补偿等手段
                System.out.println("消息消费失败:" + cause+"\n重新发送");
            }
        }
    };


    //@Scheduled(cron = "0 */1 * * * ?")
    public void send() {
        rabbitTemplate.setConfirmCallback(callback);
        Date date = new Date();
        for (int i = 0; i < 5; i++) {
            ProduceData produceData = new ProduceData();
            produceData.setId("duobei_t1");
            produceData.setStreamId("duobei_s2");
            produceData.setStreamType(Constant.AUDIO_TYPE.trim());
            if ( i == 0) {
                produceData.setStartTime((DateUtils.fromDate(date).replaceAll("[[\\s-:punct:]]","").substring(0, 12)) + "00");
                produceData.setEndTime((DateUtils.fromDate(date).replaceAll("[[\\s-:punct:]]","").substring(0, 12)) + (10 * i + 10));
            } else {
                produceData.setStartTime((DateUtils.fromDate(date).replaceAll("[[\\s-:punct:]]","").substring(0, 12)) + (10 * i));
                produceData.setEndTime((DateUtils.fromDate(date).replaceAll("[[\\s-:punct:]]","").substring(0, 12)) + (10 * i + 10));
            }
            produceData.setFlow(i);
            String json = JsonUtils.toJsonObject(produceData);
            System.out.println(json);
            rabbitTemplate.convertAndSend("zwt-queue2", json);
        }
    }

    public static void main(String[] args) {
        RabbitProducer rabbitProducer = new RabbitProducer();
        rabbitProducer.send();
    }



}
