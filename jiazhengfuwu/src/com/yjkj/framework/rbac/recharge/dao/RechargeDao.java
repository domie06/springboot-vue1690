package com.yjkj.framework.rbac.recharge.dao;

import java.util.List;
import java.util.Map;

import com.yjkj.framework.rbac.recharge.model.Recharge;

public interface RechargeDao {

	public List<Recharge> informationLoad(Map<String,Object> map) throws Exception;
	
	public List<Recharge> informationLoadAll() throws Exception;
	
	public Integer count(Recharge Recharge) throws Exception;
	
	public Recharge informationLoadOne(Recharge Recharge) throws Exception;
	
	public void informationDelete(Recharge Recharge) throws Exception;
	
	public void informationAdd(Recharge Recharge) throws Exception;
	
	public void informationUpdate(Recharge Recharge) throws Exception;
	
}
