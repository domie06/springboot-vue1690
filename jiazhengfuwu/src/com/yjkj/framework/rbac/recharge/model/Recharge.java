package com.yjkj.framework.rbac.recharge.model;

import com.yjkj.framework.base.basemodel.BaseModel;
import com.yjkj.framework.rbac.userInfo.model.UserInfo;

public class Recharge extends BaseModel {
	
	private String user_id;
	private String money;
	private String recharge_type;
	private UserInfo userInfo;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getRecharge_type() {
		return recharge_type;
	}
	public void setRecharge_type(String recharge_type) {
		this.recharge_type = recharge_type;
	}
	
	
	
}
