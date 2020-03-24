package com.spring.nebula.api.entity;

import java.io.File;
import java.io.InputStream;
import java.util.List;
/**
 * 消息邮件Vo
 * @author zheyan.yan
 *
 */
public class MailMqVo {

	/**
	 * mail
	 */
	//邮件发送者
	private String mailFrom;
	//邮件接收者
	private String mailTo;
	//邮件主题
	private String mailSubject;
	//邮件内容
	private String mailText;
	//邮件模板名称
	private String templateName;
	//邮件接收者姓名
	private String username;
	//邮件接收者邮件核心内容信息
	private String content;
	//附件信息文件
	private List<File> files;
	//附件信息文件流
	private InputStream fileStream;
	
	//需上传的附件集合
	private List<String> fileUrls;
	/**
	 * mq
	 */
	//routingkey值
	private String routingKeyMq;
	//交换机名称
	private String exchangeMq;
	//队列名称
	private String queue;
	//需要发送的消息内容
	private String msgMq;
	public String getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	public String getMailTo() {
		return mailTo;
	}
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}
	public String getMailSubject() {
		return mailSubject;
	}
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}
	public String getMailText() {
		return mailText;
	}
	public void setMailText(String mailText) {
		this.mailText = mailText;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<File> getFiles() {
		return files;
	}
	public void setFiles(List<File> files) {
		this.files = files;
	}
	public InputStream getFileStream() {
		return fileStream;
	}
	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}
	public String getRoutingKeyMq() {
		return routingKeyMq;
	}
	public void setRoutingKeyMq(String routingKeyMq) {
		this.routingKeyMq = routingKeyMq;
	}
	public String getExchangeMq() {
		return exchangeMq;
	}
	public void setExchangeMq(String exchangeMq) {
		this.exchangeMq = exchangeMq;
	}
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	public String getMsgMq() {
		return msgMq;
	}
	public void setMsgMq(String msgMq) {
		this.msgMq = msgMq;
	}
	public List<String> getFileUrls() {
		return fileUrls;
	}
	public void setFileUrls(List<String> fileUrls) {
		this.fileUrls = fileUrls;
	}
	
	
}
