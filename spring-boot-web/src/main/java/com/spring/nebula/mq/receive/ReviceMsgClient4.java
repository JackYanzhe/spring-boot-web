package com.spring.nebula.mq.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.spring.nebula.mq.config.RabbitMQConfig;
@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE_F)
public class ReviceMsgClient4{
private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @RabbitHandler
    public void process(String content) {
         logger.info("接收到队列F当中的消息： " + content);
         
         
    }
}
