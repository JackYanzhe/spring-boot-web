package com.spring.nebula.dingding.entity;

public class Message {

	private String error;
	private String msg;
	private Object data;
	
	
	public Message() {
		
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Message [error=" + error + ", msg=" + msg + ", data=" + data + "]";
	}
	
	
}
