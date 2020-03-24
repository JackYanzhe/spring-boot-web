package com.spring.nebula.dingding.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.spring.nebula.api.controller.ApiController;
import com.spring.nebula.api.entity.MailMqVo;
import com.spring.nebula.dingding.common.DingTalkConstant;
import com.spring.nebula.dingding.entity.User;
import com.spring.nebula.dingding.service.DingLoginService;
import com.spring.nebula.mq.config.RabbitMQConfig;
import com.spring.nebula.mq.send.MessageClient;
import com.spring.nebula.mq.send.Send;
import com.spring.nebula.util.ResultCode;

@RequestMapping("/dingUser")
@Controller
public class DingDingLoginController {

	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	@Autowired
	private DingLoginService dingLoginService;
	@Autowired
    private Send sender;
	
	@RequestMapping("/getLogin")
	public String getLogin() {
		System.out.println("*****************成功进入***********");
		return "index";
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse response,Model model) {
		logger.info("成功进入请求钉钉页1，生成二维码登录页面");
		String time = System.currentTimeMillis() + "";
        logger.info(time);
		StringBuilder stringBuilder = new StringBuilder();
		String result="";
		stringBuilder
				.append("https://oapi.dingtalk.com/connect/qrconnect?appid="+DingTalkConstant.APP_ID+"&")
				.append("response_type=code&scope=snsapi_login&state=")
				.append(time)
				.append("&redirect_uri=" + DingTalkConstant.CALL_BACK_URL);
		try {
			result = stringBuilder.toString();
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		
		return result;

	}
	
	/**
	 * 第三方授权免密登录
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/login3")
	public void login3(HttpServletRequest request, HttpServletResponse response,Model model) {
		logger.info("成功进入请求钉钉页1，生成二维码登录页面");
		String time = System.currentTimeMillis() + "";
		
		
        logger.info(time);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid="+DingTalkConstant.APP_ID+"&")
				.append("response_type=code&scope=snsapi_login&state=")
				.append(time)
				.append("&redirect_uri=" + DingTalkConstant.CALL_BACK_URL);
		try {
			response.sendRedirect(stringBuilder.toString());
		} catch (IOException e) {
			logger.info(e.getMessage());
		}

	}
	
	
	@RequestMapping(value="/callback", produces="text/html; charset=utf-8")
	@ResponseBody
	public String getUserInfo(HttpServletRequest request, HttpServletResponse response, Model model, String code, String state) {
		logger.info("成功进入请求钉钉用户信息页面,code=" + code+",登录时间戳："+state);
		User user = new User();
		JSONObject userJson = new JSONObject();
		String result = "";
		Gson gson = new Gson();
		try {
			userJson = dingLoginService.getDingLogin(code);
			if (null == userJson) {
				result = "登录失败，请重试，或您不是本公司员工不能进行登录操作";
				logger.info(result);
				return result;
			}
			logger.info(userJson.toJSONString());
			user = JSONObject.toJavaObject(userJson, User.class);
			logger.info(user.name + "," + user.mobile);
			
			logger.info(result);
			//发送邮件消息
			MailMqVo mailMqVo = new MailMqVo();
			mailMqVo.setRoutingKeyMq(RabbitMQConfig.ROUTINGKEY_A);
			mailMqVo.setExchangeMq(RabbitMQConfig.EXCHANGE_A);
			mailMqVo.setMailFrom("532968876@qq.com");
			mailMqVo.setMailTo(user.email);
			mailMqVo.setMailSubject("邮件：验证身份登录信息邮件");
			mailMqVo.setMailText("");
			mailMqVo.setTemplateName("emailTemple2.ftl");
			mailMqVo.setUsername(user.name);
			mailMqVo.setContent("已确认您的身份为该公司员工，成功登录！登录相关信息如下：<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+userJson.toJSONString());
			List<String> fileUrlList= new ArrayList<>();
			fileUrlList.add("http://47.100.29.15/group1/M00/00/00/rBO4UVvrlKuAB6DvAAD7WcgC1lU725.jpg");
			mailMqVo.setFileUrls(fileUrlList);
			//此时可以发送邮件
			MessageClient message = new MessageClient(gson.toJson(mailMqVo), mailMqVo.getRoutingKeyMq(), mailMqVo.getExchangeMq());
			sender.sendMessage(message);
			result = "邮件消息发送成功"+",欢迎你：" + user.name + ",您的电话为：" + user.mobile + ",userinfo:"+ userJson.toJSONString();
			
			
		} catch (Exception e) {
			
			logger.error("扫码登录时出现异常，异常信息如下："+e.getMessage());
			result ="登录失败，请确认您是否是本公司员工！！";
		}
		return result;

	}
	
	
	

	

   
	
}
