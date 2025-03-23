package com.yjkj.framework.rbac.vip.model;

import com.yjkj.framework.base.basemodel.BaseModel;

public class Vip extends BaseModel {
	
	private String level;
	private String ordertime;
	private String status;
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
