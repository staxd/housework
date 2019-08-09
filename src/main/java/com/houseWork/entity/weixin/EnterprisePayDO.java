package com.houseWork.entity.weixin;

public class EnterprisePayDO {
	private Long userId;
	private String partner_trade_no;
	private Integer fee;
	private String payment_no;
	private String payment_time;
	private String openId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getPartner_trade_no() {
		return partner_trade_no;
	}
	public void setPartner_trade_no(String partner_trade_no) {
		this.partner_trade_no = partner_trade_no;
	}
	public String getPayment_no() {
		return payment_no;
	}
	public void setPayment_no(String payment_no) {
		this.payment_no = payment_no;
	}
	public String getPayment_time() {
		return payment_time;
	}
	public void setPayment_time(String payment_time) {
		this.payment_time = payment_time;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Integer getFee() {
		return fee;
	}
	public void setFee(Integer fee) {
		this.fee = fee;
	}
	
}
