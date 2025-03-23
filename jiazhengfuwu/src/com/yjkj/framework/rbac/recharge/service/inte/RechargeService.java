package com.yjkj.framework.rbac.recharge.service.inte;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yjkj.framework.base.pager.Pager;
import com.yjkj.framework.rbac.recharge.model.Recharge;

public interface RechargeService {

	public Pager informationLoad(Pager pager,Recharge Recharge,HttpSession session) throws Exception;
	
	public Pager informationLoadFore(Pager pager,Recharge Recharge,HttpSession session) throws Exception;
	
	public List<Recharge> informationLoadAll() throws Exception;
	
	public Integer count(Recharge Recharge) throws Exception;
	
	public Recharge informationLoadOne(Recharge Recharge) throws Exception;
	
	public void informationDelete(Recharge Recharge) throws Exception;
	
	public void informationAdd(Recharge Recharge,HttpSession session) throws Exception;
	
	public void informationUpade(Recharge Recharge) throws Exception;
	
	public void exportAll(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception;
	
}
