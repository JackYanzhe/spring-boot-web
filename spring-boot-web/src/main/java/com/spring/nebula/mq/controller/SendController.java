package com.spring.nebula.mq.controller;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.nebula.mq.config.RabbitMQConfig;
import com.spring.nebula.mq.send.MessageClient;
import com.spring.nebula.mq.send.Send;

/**
 * 测试RabbitMQ发送消息的Controller
 * @author Raye
 *
 */
@RestController
@RequestMapping("/mq")
public class SendController{
	 public static final String EXCHANGE_A = "my-mq-exchange_A";
	 public static final String QUEUE_A = "QUEUE_A";
	 public static final String ROUTINGKEY_A = "spring-boot-routingKey_A";
	 @Autowired
     private Send sender;
	/**
	 * 向消息队列1中发送消息
	 * @param msg
	 * @return
	 */
	@RequestMapping("sendMsg")
	public String send1(String msg){
		for (int i = 0; i < 1; i++) {
			msg=msg+"老铁们，发送消息："+i;
			MessageClient messageClient = new MessageClient(msg, RabbitMQConfig.FANOUT_EXCHANGE);
			sender.sendFanoutMsg(messageClient);
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
		
		return "成功发送信息";
	}
	
	/**
	 * 消息的回调，主要是实现RabbitTemplate.ConfirmCallback接口
	 * 注意，消息回调只能代表消息发送成功，不能代表消息被成功处理
	 */
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		System.out.println(" 回调id:" + correlationData);
		if (ack) {
			System.out.println("消息成功消费");
		} else {
			System.out.println("消息消费失败:" + cause+"\n重新发送");
			
		}
	}
}
