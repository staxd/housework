package com.houseWork.entity.weixin;

public class UserRefundParam {
	private Integer type;
	private String orderId;
	private Double totalFee;
	private Double refundFee;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Double getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	public Double getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(Double refundFee) {
		this.refundFee = refundFee;
	}
	
}
