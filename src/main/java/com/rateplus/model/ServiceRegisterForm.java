package com.rateplus.model;

import java.util.Date;

public class ServiceRegisterForm {

	private String userId;
	private String serviceId;
	private String type;
	private Date expiredTime;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getServiceId() {
		return serviceId;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public Date getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}
	
}
