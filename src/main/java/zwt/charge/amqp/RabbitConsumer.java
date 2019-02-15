package zwt.charge.amqp;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zwt.charge.service.QueueJobService;

/**
 * @Author: lilongzhou
 * @Description: rabbitMQ消费者
 * @Date: Created in 下午2:36 2019/1/21
 */
@Component
@Slf4j
public class RabbitConsumer {

    @Autowired
    private QueueJobService queueJobService;

    //@RabbitListener(queues = "${rabbit.queue}", containerFactory = "notifyContainer")
    @RabbitListener(queues = "${rabbit.queue}", containerFactory = "notifyContainer")
    public void receiver(Message message, Channel channel) throws Exception {
        String json = new String(message.getBody());
        try {
            if (StringUtils.isNotEmpty(json)) {
                handleJob(json, channel, message.getMessageProperties().getDeliveryTag());
            } else {
                return;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.info("json:{} throw exception!! ,reject json", json);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }

    }

    private void handleJob(String json, Channel channel, long deliveryTag) throws Exception {
        boolean flag = queueJobService.saveData(json);
        if (flag) {
            channel.basicAck(deliveryTag, false);
        }
    }

}
