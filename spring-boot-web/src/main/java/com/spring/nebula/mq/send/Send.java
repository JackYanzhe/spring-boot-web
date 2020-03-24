package com.spring.nebula.mq.send;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.nebula.mq.config.RabbitMQConfig;


@Component
public class Send implements RabbitTemplate.ConfirmCallback{
	 
	 private RabbitTemplate rabbitTemplate;
	   /**  
	     * 构造方法注入  
	     */  
	    @Autowired
	    public Send(RabbitTemplate rabbitTemplate) {  
	        this.rabbitTemplate = rabbitTemplate;  
	        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容  
	    }
	    
	    /**
	     * 指定特定的交换机和rountingkey
	     * @param content
	     */
	    public void sendMsg(String content) {  
	        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());  
	        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
	        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_A, RabbitMQConfig.ROUTINGKEY_A, content, correlationId);
	    } 
	    
	    /**
	     * 同上
	     * @param message
	     */
	    public void sendMessage(MessageClient message) {  
	        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());  
	        rabbitTemplate.convertAndSend(message.getExchange(), message.getRoutingKey(), message.getMsgContent(), correlationId);  
	    } 
	    
	    /**
	     * 同上，但不会有回调
	     * @param message
	     */
	    public void sendMessages(MessageClient message){
		    rabbitTemplate.convertAndSend(message.getExchange(), message.getRoutingKey(), message.getMsgContent());
			   
	    }
	    
	    /**
	     * 广播的方式
	     * @param content
	     */
	    public void sendAll(String content) {
	        rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE,"", content);
	    }
	    
	    /**
	     * 广播的方式
	     * @param content
	     */
	    public void sendFanoutMsg(MessageClient message) {
	        rabbitTemplate.convertAndSend(message.getExchange(),"", message.getMsgContent());
	    }
	  
	  
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		  System.out.println(" 回调id:" + correlationData);  
	        if (ack) {  
	            System.out.println("消息成功消费");  
	        } else {  
	            System.out.println("消息消费失败:" + cause);  
	        }  
		
	}  
	  
}
