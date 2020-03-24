package com.spring.nebula.java.itextpdf.vo;
/**
 * 发票订单信息模板
 * @author zhe.yan
 *
 */

import java.util.List;

public class BillOrderTem {

	private String orderId;
	private String accountId;
	private String vATRegNumber;
	private String invoiceNo;
	private String invoiceDate;
	private String billTo;
	//private String address;
	private String address_1;
	private String address_2;
	private String address_3;
	private String phone;
	private String eMail;
	private String invoiceSubtotal;
	private String vATRate;
	private String invoiceTotal;
	private String postage;
	private String subtotal;
	private String vat;
	private String total;
	private List<BillOrderItemTem> billOrderItemTems;
	public String getvATRegNumber() {
		return vATRegNumber;
	}
	public void setvATRegNumber(String vATRegNumber) {
		this.vATRegNumber = vATRegNumber;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getBillTo() {
		return billTo;
	}
	public void setBillTo(String billTo) {
		this.billTo = billTo;
	}
	
	public String getAddress_1() {
		return address_1;
	}
	public void setAddress_1(String address_1) {
		this.address_1 = address_1;
	}
	public String getAddress_2() {
		return address_2;
	}
	public void setAddress_2(String address_2) {
		this.address_2 = address_2;
	}
	public String getAddress_3() {
		return address_3;
	}
	public void setAddress_3(String address_3) {
		this.address_3 = address_3;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getInvoiceSubtotal() {
		return invoiceSubtotal;
	}
	public void setInvoiceSubtotal(String invoiceSubtotal) {
		this.invoiceSubtotal = invoiceSubtotal;
	}
	public String getvATRate() {
		return vATRate;
	}
	public void setvATRate(String vATRate) {
		this.vATRate = vATRate;
	}
	public String getInvoiceTotal() {
		return invoiceTotal;
	}
	public void setInvoiceTotal(String invoiceTotal) {
		this.invoiceTotal = invoiceTotal;
	}
	public String getPostage() {
		return postage;
	}
	public void setPostage(String postage) {
		this.postage = postage;
	}
	public String getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
	public String getVat() {
		return vat;
	}
	public void setVat(String vat) {
		this.vat = vat;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public List<BillOrderItemTem> getBillOrderItemTems() {
		return billOrderItemTems;
	}
	public void setBillOrderItemTems(List<BillOrderItemTem> billOrderItemTems) {
		this.billOrderItemTems = billOrderItemTems;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	
	
	
	
	
}
