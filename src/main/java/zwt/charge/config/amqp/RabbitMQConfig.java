package zwt.charge.config.amqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zwt.charge.config.PropConfig;

/**
 * @Author: lilongzhou
 * @Description: rabbitMQ配置
 * @Date: Created in 下午1:58 2019/1/21
 */
@Configuration
@Slf4j
public class RabbitMQConfig {

    @Autowired
    PropConfig propConfig;

    /**
     * 配置连接信息
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setUsername(propConfig.getUsername());
        cachingConnectionFactory.setPassword(propConfig.getPassword());
        cachingConnectionFactory.setVirtualHost(propConfig.getMqRabbitVirtualHost());
        cachingConnectionFactory.setPublisherConfirms(true); // 必须设置
        // 该方法配置多个host，在当前连接host down掉的时候会自动去重连后面的host
        cachingConnectionFactory.setAddresses(propConfig.getAddress());
        return cachingConnectionFactory;
    }


    @Bean
    public SimpleRabbitListenerContainerFactory notifyContainer(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setPrefetchCount(propConfig.getPrefetchCount());
        factory.setMaxConcurrentConsumers(propConfig.getMaxConcurrentConsumers());
        factory.setConcurrentConsumers(propConfig.getConcurrentConsumers());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        configurer.configure(factory, connectionFactory);
        return factory;
    }




}
