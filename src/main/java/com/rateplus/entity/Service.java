package com.rateplus.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity(name="service")
@Table
public class Service implements Serializable{
	
	private static final long serialVersionUID = 6289704171242246484L;

	@Id
	String serviceId;
	
	String userId;
	
	@NotNull
	String type;
	
	@NotNull
	Date purchasedTime;
	
	Date expiredTime;
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getPurchasedTime() {
		return purchasedTime;
	}
	public void setPurchasedTime(Date purchasedTime) {
		this.purchasedTime = purchasedTime;
	}
	public Date getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}
}
