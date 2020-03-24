package com.spring.nebula.mq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.rabbitmq.client.Channel;

/**
Broker:它提供一种传输服务,它的角色就是维护一条从生产者到消费者的路线，保证数据能按照指定的方式进行传输, 
Exchange：消息交换机,它指定消息按什么规则,路由到哪个队列。 
Queue:消息的载体,每个消息都会被投到一个或多个队列。 
Binding:绑定，它的作用就是把exchange和queue按照路由规则绑定起来. 
Routing Key:路由关键字,exchange根据这个关键字进行消息投递。 
vhost:虚拟主机,一个broker里可以有多个vhost，用作不同用户的权限分离。 
Producer:消息生产者,就是投递消息的程序. 
Consumer:消息消费者,就是接受消息的程序. 
Channel:消息通道,在客户端的每个连接里,可建立多个channel.
*/
@Configuration
public class RabbitMQConfig {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
 
 
    public static final String EXCHANGE_A = "my-mq-exchange_A";
    public static final String EXCHANGE_B = "my-mq-exchange_B";
    //广播使用交换机
    public static final String FANOUT_EXCHANGE = "mq-fanout_exchange";
 
 
    public static final String QUEUE_A = "QUEUE_A";
    public static final String QUEUE_B = "QUEUE_B";
 
    public static final String ROUTINGKEY_A = "spring-boot-routingKey_A";
    public static final String ROUTINGKEY_B = "spring-boot-routingKey_B";
    
    public static final String QUEUE_C = "QUEUE_C";
    public static final String QUEUE_D = "QUEUE_D";
    
    public static final String QUEUE_F = "QUEUE_F";
    public static final String QUEUE_G = "QUEUE_G";
 
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        System.out.println(username+","+password);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }
 
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }
    
    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     HeadersExchange ：通过添加属性key-value匹配
     DirectExchange:按照routingkey分发到指定队列
     TopicExchange:多关键字匹配
     */
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE_A);
    }
    /**
     * 获取队列A
     * @return
     */
    @Bean
    public Queue QueueA() {
        return new Queue(QUEUE_A, true); //队列持久
    }
    
    @Bean
    public Queue QueueB() {
        return new Queue(QUEUE_B, true); //队列持久
    }
    
  
    
    //一个交换机可以绑定多个消息队列，也就是消息通过一个交换机，可以分发到不同的队列当中去。
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(QueueA()).to(defaultExchange()).with(ROUTINGKEY_A);
    }
    
    @Bean
    public Binding bindingB(){
        return BindingBuilder.bind(QueueB()).to(defaultExchange()).with(ROUTINGKEY_B);
    }
    
    
    
    /**
     * 广播时使用
     * @return
     */
    @Bean
    public Queue QueueC() {
        return new Queue(QUEUE_C, true); //队列持久
    }
    @Bean
    public Queue QueueD() {
        return new Queue(QUEUE_D, true); //队列持久
    }
   
    @Bean
    public Queue QueueF() {
        return new Queue(QUEUE_F, true); //队列持久
    }
    @Bean
    public Queue QueueG() {
        return new Queue(QUEUE_G, true); //队列持久
    }
    
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitMQConfig.FANOUT_EXCHANGE);
    }
    @Bean
    public Binding bindingExchangeC() {
        return BindingBuilder.bind(QueueC()).to(fanoutExchange());
    }
    @Bean
    public Binding bindingExchangeD() {
        return BindingBuilder.bind(QueueD()).to(fanoutExchange());
    }
   
    @Bean
    public Binding bindingExchangeF() {
        return BindingBuilder.bind(QueueF()).to(fanoutExchange());
    }
    @Bean
    public Binding bindingExchangeG() {
        return BindingBuilder.bind(QueueG()).to(fanoutExchange());
    }
    
    
    
    //配置监听
    @Bean  
    public SimpleMessageListenerContainer messageContainer() {  
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory()); 
        //绑定服务器的queue,绑定本机ip，作为标记进行注册
    	container.addQueueNames(QUEUE_B);
        container.setExposeListenerChannel(true);  
        container.setMaxConcurrentConsumers(10);  
        container.setConcurrentConsumers(10);  
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认  
        /*//启动时即向服务端发送消息进行注册
        Send send=new Send(rabbitTemplate());
		System.out.println("向服务器进行注册，注册信息为：");
		MessageClient messageClient=new MessageClient("此为客户机启动时注册信息",ROUTINGKEY_A,EXCHANGE_A );
		send.sendMessages(messageClient);*/
        
        container.setMessageListener(new ChannelAwareMessageListener() {

			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				 byte[] body = message.getBody();
	                //收到相应指令后进行后续操作
	                String orderMessage=new String(body);
	                logger.info("接收到的消息为："+orderMessage);
	                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费  
			}  
            
        });  
        return container;  
    }  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
