package com.spring.nebula.mq.send;

public class MessageClient {
      private String msgContent;
      private String routingKey;
      private String exchange;
      
	public MessageClient() {
		
	}
	public MessageClient(String msgContent, String exchange) {
		this.msgContent = msgContent;
		this.exchange = exchange;
	}
	
	public MessageClient(String msgContent, String routingKey, String exchange) {
		this.msgContent = msgContent;
		this.routingKey = routingKey;
		this.exchange = exchange;
	}
	
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getRoutingKey() {
		return routingKey;
	}
	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}
	
	
      
}
