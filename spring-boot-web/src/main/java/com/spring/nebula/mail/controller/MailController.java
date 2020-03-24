package com.spring.nebula.mail.controller;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@RestController
@RequestMapping("/mail")
public class MailController {
	@Autowired
	private JavaMailSender mailSender;
	
	//发送邮件的模板引擎
    @Autowired
    private FreeMarkerConfigurer configurer;
	
	@GetMapping("/sendSimpleEmail")
	public String sendSimpleEmail(){
		System.out.println("-----------开始发送邮件信息-------------");
		
		//建立邮件消息
		SimpleMailMessage mainMessage = new SimpleMailMessage();
		//发送者
		mainMessage.setFrom("532968876@qq.com");
		//接收者
		mainMessage.setTo("532968876@qq.com");
		//发送的标题
		mainMessage.setSubject("IT TEST");
		//发送的内容
		mainMessage.setText("嗨喽,小海燕，这是我的测试邮件！！！");
		
		mailSender.send(mainMessage);
		System.out.println("-----------***************------------");
		return "邮件发送成功";
	}
	
	@GetMapping("/sendAttachmentEmail")
	public String sendAttachmentEmail(){
		System.out.println("-----------开始发送邮件信息-------------");
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom("532968876@qq.com");
			//helper.setTo("491655684@qq.com");
			helper.setTo("532968876@qq.com");
			helper.setSubject("主题：IT测试-有附件");
			helper.setText("小燕，这是个有附件的邮件信息");
	 
			//可传file，也可传文件流
			File file = new File("E:\\图片测试\\001.png");
			FileSystemResource file2 = new FileSystemResource(new File("E:\\图片测试\\002.png"));
			helper.addAttachment("附件-1.jpg", file);
			helper.addAttachment("附件-2.jpg", file2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		mailSender.send(mimeMessage);
		System.out.println("-----------***************------------");
		return "邮件发送成功";
	}
	
	@GetMapping("/sendInlineMail")
	public String sendInlineMail(){
		System.out.println("-----------开始发送邮件信息-------------");
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		 try {
			    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			    helper.setFrom("532968876@qq.com");
				//helper.setTo("491655684@qq.com");
				helper.setTo("532968876@qq.com");
				helper.setSubject("主题：嵌入静态资源(附件+网页)");
				helper.setText("<html><body><h2>哈，小燕...</h2><img src=\"cid:meinv\" ><img src=\"cid:fashe\" ><img src=\"cid:feiwen\" >"
						+ "<img src=\"cid:gaoxiao\" ><img src=\"cid:gongzuo\" ></body></html>", true);
		 
				FileSystemResource file = new FileSystemResource(new File("E:\\图片测试\\meinv.gif"));
				FileSystemResource file2 = new FileSystemResource(new File("E:\\图片测试\\fashe.gif"));
				FileSystemResource file3 = new FileSystemResource(new File("E:\\图片测试\\feiwen.gif"));
				FileSystemResource file4 = new FileSystemResource(new File("E:\\图片测试\\gaoxiao.gif"));
				FileSystemResource file5 = new FileSystemResource(new File("E:\\图片测试\\gongzuo.gif"));
				helper.addInline("meinv", file);
				helper.addInline("fashe", file2);
				helper.addInline("feiwen", file3);
				helper.addInline("gaoxiao", file4);
				helper.addInline("gongzuo", file5);
				FileSystemResource file6 = new FileSystemResource(new File("E:\\图片测试\\odoo k32018.10.15.xlsx"));
				helper.addAttachment("附件-odoo k32018.10.15.xlsx", file6);
				
		} catch (Exception e) {
			   System.out.println(e.getMessage());
		}
		mailSender.send(mimeMessage);
		System.out.println("-----------***************------------");
		return "邮件发送成功";
	}
	
	@GetMapping("/sendTemplateMail")
	public String sendTemplateMail(){
		System.out.println("-----------开始发送邮件信息-------------");
		String templateName="emailTemple2.ftl";
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		 try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom("532968876@qq.com");
			//helper.setTo("491655684@qq.com");
			helper.setTo("532968876@qq.com");
			helper.setSubject("主题：重要模板邮件，注意查收!!");
			Map<String, Object> model = new HashMap<String, Object>();
	        model.put("username", "闫哲");
	        model.put("content", "这里是邮件的具体内容信息");
	            try {
	                Template template = configurer.getConfiguration().getTemplate(templateName);
	                try {
	                    String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
	                    helper.setText(text, true);
	                    mailSender.send(mimeMessage);
	                } catch (TemplateException e) {
	                    e.printStackTrace();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }

		System.out.println("-----------***************------------");
		return "邮件发送成功";
	}

}
