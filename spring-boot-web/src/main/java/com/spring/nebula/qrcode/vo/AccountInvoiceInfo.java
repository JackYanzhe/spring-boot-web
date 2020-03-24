package com.spring.nebula.qrcode.vo;
/**
 * 发票对应公司账户信息
 * @author zhe.yan
 *
 */
public class AccountInvoiceInfo {

	private Integer id;
	/** 账户 */
	private String accountId;
	/** 平台 */
	private String platformEn;
	/** 公司名 */
	private String companyName;
	/** 公司地址 */
	private String companyAddress;
	/** 账户邮箱 */
	private String accountEmail;
	/** 站点 */
	private String sitenEn;
	/** vat注册人 */
	private String vatRegistrant;
	/** vat账户 */
	private String vatAccount;
	/** 是否展示公司信息 0 不 1展示 */
	private Integer isShowCompany;
	/** vat税率 */
	private Double vatTaxRate;
	/** 启用状态 0 不 1 启用 */
	private Integer enableStatus;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getPlatformEn() {
		return platformEn;
	}
	public void setPlatformEn(String platformEn) {
		this.platformEn = platformEn;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getAccountEmail() {
		return accountEmail;
	}
	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}
	public String getSitenEn() {
		return sitenEn;
	}
	public void setSitenEn(String sitenEn) {
		this.sitenEn = sitenEn;
	}
	public String getVatRegistrant() {
		return vatRegistrant;
	}
	public void setVatRegistrant(String vatRegistrant) {
		this.vatRegistrant = vatRegistrant;
	}
	public String getVatAccount() {
		return vatAccount;
	}
	public void setVatAccount(String vatAccount) {
		this.vatAccount = vatAccount;
	}
	public Integer getIsShowCompany() {
		return isShowCompany;
	}
	public void setIsShowCompany(Integer isShowCompany) {
		this.isShowCompany = isShowCompany;
	}
	public Double getVatTaxRate() {
		return vatTaxRate;
	}
	public void setVatTaxRate(Double vatTaxRate) {
		this.vatTaxRate = vatTaxRate;
	}
	public Integer getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}
	
	
	
	
	
}
