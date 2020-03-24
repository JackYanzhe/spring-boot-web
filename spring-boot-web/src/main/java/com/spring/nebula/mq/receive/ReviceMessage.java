package com.spring.nebula.mq.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.google.gson.Gson;
import com.spring.nebula.api.entity.MailMqVo;
import com.spring.nebula.mq.config.RabbitMQConfig;
import com.spring.nebula.util.EmailUtil;
@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE_A)
public class ReviceMessage{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static Gson gson = new Gson();
	@Autowired
    private JavaMailSender mailSender;
	//发送邮件的模板引擎
    @Autowired
    private FreeMarkerConfigurer configurer;
	
    @RabbitHandler
    public void process(String content) {
         logger.info("接收到队列A当中的消息： " + content);
         String errorMsg="";
         int flag=0;
	     try {
	           MailMqVo mail = gson.fromJson(content, MailMqVo.class);
	           //发送邮件
	           EmailUtil.sendTemplateMail(mail, mailSender, configurer);
	           logger.info("邮件发送成功");
			} catch (Exception e) {
				logger.error("出现异常："+e.getMessage());
				if (!StringUtils.isEmpty(e.getMessage())) {
					errorMsg=e.getMessage();
				}else {
					errorMsg="异常信息为空值！！";
				}
				
				flag=1;
			}
         //如果邮件发送异常，则将发邮件至管理者
         try {
        	 if (0!=flag) {
     			//出现异常邮件发送失败
             	MailMqVo mailMqVo = new MailMqVo();
             	mailMqVo.setRoutingKeyMq(RabbitMQConfig.ROUTINGKEY_A);
          		mailMqVo.setExchangeMq(RabbitMQConfig.EXCHANGE_A);
          		mailMqVo.setMailFrom("532968876@qq.com");
          		mailMqVo.setMailTo("532968876@qq.com");
          		mailMqVo.setMailSubject("此为发送邮件出现异常时发送的邮件");
          		//mailMqVo.setMailText("闫哲先生，该邮件发送异常，相关信息："+content);
          	    //EmailUtil.sendSimpleEmail(mailMqVo, mailSender);
          		
          		mailMqVo.setTemplateName("emailTemple2.ftl");
         		mailMqVo.setUsername("Jack yan");
         		mailMqVo.setContent("该邮件发送异常，出现的异常信息为：<span style=color:red>"+errorMsg+"</span><br>邮件相关信息如下：<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>"+content+"</span>");
         		EmailUtil.sendTemplateMail(mailMqVo, mailSender, configurer);
         		logger.info("成功发送异常邮件！！！");
         		
     		}
		} catch (Exception e) {
			logger.error("发送异常邮件时出现异常信息:"+e.getMessage());
		}
         
         
    }


	
}
