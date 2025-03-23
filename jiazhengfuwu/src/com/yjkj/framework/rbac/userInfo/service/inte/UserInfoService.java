package com.yjkj.framework.rbac.userInfo.service.inte;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.yjkj.framework.base.pager.Pager;
import com.yjkj.framework.rbac.userInfo.model.UserInfo;

public interface UserInfoService {

	public UserInfo login(UserInfo user,HttpSession session) throws Exception;
	
	public UserInfo queryOne(String id);

	public UserInfo queryRole(String roleId);
	
	public Pager Query(Pager pager,UserInfo user);
	
	public void userDelete(String id);
	
	public Integer queryCount(UserInfo userInfo);
	
	public void userAdd(
				UserInfo userInfo,MultipartFile[] file,HttpServletRequest request);
	
	public void userUpdate(
			UserInfo userInfo,MultipartFile[] file,HttpServletRequest request);
	
	public void exportAllEmployee(HttpServletRequest request,UserInfo user,HttpServletResponse response,HttpSession session) throws Exception;
	
	public void exportAllMaster(HttpServletRequest request,UserInfo user,HttpServletResponse response,HttpSession session) throws Exception;
	
}
