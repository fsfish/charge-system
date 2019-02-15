package zwt.charge.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lilongzhou
 * @Description:
 * @Date: Created in 下午4:36 2018/12/27
 */
@Configuration
@Data
public class PropConfig {

    /***rabbitMQ配置信息***/
    @Value("${spring.rabbitmq.addresses}")
    private String address;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String mqRabbitVirtualHost;

    @Value("${spring.rabbitmq.listener.simple.concurrency}")
    private int concurrentConsumers;

    @Value("${spring.rabbitmq.listener.simple.acknowledge-mode}")
    private String acknowledgeMode;

    @Value("${spring.rabbitmq.listener.simple.max-concurrency}")
    private int maxConcurrentConsumers;

    @Value("${spring.rabbitmq.listener.simple.prefetch}")
    private int prefetchCount;

    @Value("${rabbit.queue}")
    private String notifyQueue;





}
