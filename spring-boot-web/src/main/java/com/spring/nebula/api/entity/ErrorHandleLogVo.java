package com.spring.nebula.api.entity;

import java.util.Date;

public class ErrorHandleLogVo {

	private int id;
	private String fieldName;
	private String type;
	private String typeDetail;
	private String result;
	private Date date;
	private String memo;
	
	
	public ErrorHandleLogVo() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeDetail() {
		return typeDetail;
	}
	public void setTypeDetail(String typeDetail) {
		this.typeDetail = typeDetail;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Override
	public String toString() {
		return "ErrorHandleLogVo [id=" + id + ", fieldName=" + fieldName + ", type=" + type + ", typeDetail="
				+ typeDetail + ", result=" + result + ", date=" + date + ", memo=" + memo + "]";
	}
	
	
}
