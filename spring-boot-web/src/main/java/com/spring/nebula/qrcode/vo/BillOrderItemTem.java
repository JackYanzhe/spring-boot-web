package com.spring.nebula.qrcode.vo;

/**
 * 发票子单信息模板
 * @author zhe.yan
 *
 */
public class BillOrderItemTem {

	private String description;
	private String quantity;
	private String unitPrice;
	private String amount;
	
	
	public BillOrderItemTem() {
		super();
	}
	public BillOrderItemTem(String description, String quantity, String unitPrice, String amount) {
		super();
		this.description = description;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
	
}
