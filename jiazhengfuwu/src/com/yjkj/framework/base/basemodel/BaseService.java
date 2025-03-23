package com.yjkj.framework.base.basemodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yjkj.framework.rbac.about.dao.AboutDao;
import com.yjkj.framework.rbac.introduce.dao.IntroduceDao;
import com.yjkj.framework.rbac.notice.dao.NoticeDao;
import com.yjkj.framework.rbac.recharge.dao.RechargeDao;
import com.yjkj.framework.rbac.vip.dao.VipDao;

@Service
public class BaseService {
	
	
	
	@Autowired
	public AboutDao aboutDao;
	
	@Autowired
	public NoticeDao noticeDao;
	
	@Autowired
	public IntroduceDao introduceDao;
	
	@Autowired
	public RechargeDao rechargeDao;
	
	@Autowired
	public VipDao vipDao;
	

}
