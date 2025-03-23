package com.yjkj.framework.rbac.userInfo.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.yjkj.framework.base.pager.Pager;
import com.yjkj.framework.rbac.roleInfo.model.RoleInfo;
import com.yjkj.framework.rbac.roleInfo.service.impl.RoleInfoServiceImpl;
import com.yjkj.framework.rbac.roleInfo.service.inte.RoleInfoService;
import com.yjkj.framework.rbac.userInfo.model.UserInfo;
import com.yjkj.framework.rbac.userInfo.service.impl.UserInfoServiceImpl;
import com.yjkj.framework.rbac.userInfo.service.inte.UserInfoService;



@Controller
@RequestMapping("/user")
public class UserInfoController {
	@Autowired
	private UserInfoService uis = new UserInfoServiceImpl();
	
	@Autowired
	private RoleInfoService ris = new RoleInfoServiceImpl();
	
	@RequestMapping("/login")
	public String login(UserInfo user,Model model,HttpSession session){
		try{	
			
			UserInfo userInfo = this.uis.login(user,session);
			session.setAttribute("Admin", userInfo);
			return "redirect:../request/index";
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("messageLogin", e.getMessage());
			model.addAttribute("loginName", user.getLoginName());
			model.addAttribute("loginPassword", user.getLoginPassword());
			return "jsp/Login";
		}
		
	}
	@RequestMapping("/loginF")
	public String loginF(UserInfo user,Model model,HttpSession session){
		try{	
			
			UserInfo userInfo = this.uis.login(user,session);
			session.setAttribute("User", userInfo);
			return "redirect:../request/welcome";
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("messageLogin", e.getMessage());
			model.addAttribute("loginName", user.getLoginName());
			model.addAttribute("loginPassword", user.getLoginPassword());
			return "redirect:../request/welcome";
		}
		
	}
	@RequestMapping("/logout")
	public String logout(HttpSession session){
			session.removeAttribute("Admin");
			return "jsp/Login";
	}
	@RequestMapping("/logoutF")
	public String logoutF(HttpSession session){
			session.removeAttribute("User");
			return "jsp/foreground/Welcome";
	}
	@RequestMapping("/logoutAdmin")
	public String logoutAdmin(HttpSession session){
			session.removeAttribute("Admin");
			return "jsp/LoginAdmin";
	}
	@RequestMapping("/userAdd")
	public String userAdd(UserInfo user,MultipartFile[] file,Model model,HttpServletRequest request){
			try{
				this.uis.userAdd(user,file,request);
				return "jsp/Success";
			}catch (RuntimeException e) {
				model.addAttribute("message", e.getMessage());
				return "jsp/Failed";
			}
	}
	@RequestMapping("/userAddF")
	public String userAddF(UserInfo user,MultipartFile[] file,Model model,HttpServletRequest request){
			try{
				this.uis.userAdd(user,file,request);
				return "jsp/foreground/Welcome";
			}catch (RuntimeException e) {
				model.addAttribute("message", e.getMessage());
				return "jsp/Failed";
			}
	}
	@RequestMapping("/userUpdate")
	public String userUpdate(UserInfo user,MultipartFile[] file,Model model,HttpServletRequest request){
			try{
				this.uis.userUpdate(user,file,request);
				return "jsp/Success";
			}catch (RuntimeException e) {
				model.addAttribute("message", e.getMessage());
				return "jsp/Failed";
			}
	}
	@RequestMapping("/userUpdateF")
	public String userUpdateF(UserInfo user,MultipartFile[] file,Model model,HttpServletRequest request){
			try{
				this.uis.userUpdate(user,file,request);
				return "jsp/foreground/Welcome";
			}catch (RuntimeException e) {
				e.printStackTrace();
				model.addAttribute("message", e.getMessage());
				return "jsp/Failed";
			}
	}
	@RequestMapping("/userDelete")
	public String userDelete(String id,Model model){
			try{
				this.uis.userDelete(id);
				return "jsp/Success";
			}catch (RuntimeException e) {
				model.addAttribute("message", e.getMessage());
				return "jsp/Failed";
			}
	}
	@RequestMapping("/queryOneFore")
	public String queryOneFore(String id,Model model){
		try{
			UserInfo user = this.uis.queryOne(id);
			List<RoleInfo> list = this.ris.query();
			model.addAttribute("role", list);
			model.addAttribute("user", user);
			return "jsp/foreground/information";
		}catch (RuntimeException e) {
			model.addAttribute("messageLogin", e.getMessage());
			return "redirect:query";
		}
	}
	
	@RequestMapping("/queryOne")
	public String queryOne(String id,Model model){
		try{
			UserInfo user = this.uis.queryOne(id);
			List<RoleInfo> list = this.ris.query();
			model.addAttribute("role", list);
			model.addAttribute("user", user);
			return "jsp/User";
		}catch (RuntimeException e) {
			model.addAttribute("messageLogin", e.getMessage());
			return "redirect:query";
		}
	}
	
	@RequestMapping("/queryOwn")
	public String queryOwn(String id,Model model,HttpSession session){
		try{
			
			UserInfo user = (UserInfo) session.getAttribute("User");
			List<RoleInfo> list = this.ris.query();
			model.addAttribute("role", list);
			model.addAttribute("user", user);
			return "jsp/User";
		}catch (RuntimeException e) {
			model.addAttribute("messageLogin", e.getMessage());
			return "redirect:query";
		}
	}
	
	@RequestMapping("/query")
	public String query(Pager pager,UserInfo user,Model model){
		try{
		model.addAttribute("pager", this.uis.Query(pager, user));
		}catch (RuntimeException e) {
			model.addAttribute("message", e.getMessage());
		}
		return "jsp/UserTable";
	}
	
	@RequestMapping("/employeeQuery")
	public String employeeQuery(Pager pager,UserInfo user,Model model){
		try{
		user.setRoleId("2");
		model.addAttribute("pager", this.uis.Query(pager, user));
		model.addAttribute("user", user);
		}catch (RuntimeException e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			model.addAttribute("message", e.getMessage());
		}
		return "jsp/EmployeeTable";
	}
	@RequestMapping("/employeeQueryFore")
	public String employeeQueryFore(Pager pager,UserInfo user,Model model){
		try{
			user.setRoleId("2");
		model.addAttribute("pager", this.uis.Query(pager, user));
		}catch (RuntimeException e) {
			model.addAttribute("message", e.getMessage());
		}
		return "jsp/foreground/EmployeeTable";
	}
	@RequestMapping("/employeeQueryOneFore")
	public String employeeQueryOneFore(String id,Model model){
		try{
			UserInfo user = this.uis.queryOne(id);
			List<RoleInfo> list = this.ris.query();
			model.addAttribute("role", list);
			model.addAttribute("user", user);
			return "jsp/foreground/Employee";
		}catch (RuntimeException e) {
			model.addAttribute("messageLogin", e.getMessage());
			return "redirect:query";
		}
	}
	
	@RequestMapping("/masterQuery")
	public String masterQuery(Pager pager,UserInfo user,Model model){
		try{
			user.setRoleId("3");
			model.addAttribute("user", user);
		model.addAttribute("pager", this.uis.Query(pager, user));
		}catch (RuntimeException e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			model.addAttribute("message", e.getMessage());
		}
		return "jsp/MasterTable";
	}
	
	@RequestMapping("/masterQueryFore")
	public String masterQueryFore(Pager pager,UserInfo user,Model model){
		try{
			user.setRoleId("3");
		model.addAttribute("pager", this.uis.Query(pager, user));
		}catch (RuntimeException e) {
			model.addAttribute("message", e.getMessage());
		}
		return "jsp/foreground/MasterTable";
	}
	@RequestMapping("/masterQueryOneFore")
	public String masterQueryOneFore(String id,Model model){
		try{
			UserInfo user = this.uis.queryOne(id);
			List<RoleInfo> list = this.ris.query();
			model.addAttribute("role", list);
			model.addAttribute("user", user);
			return "jsp/foreground/Master";
		}catch (RuntimeException e) {
			model.addAttribute("messageLogin", e.getMessage());
			return "redirect:query";
		}
	}
	
	@RequestMapping(path="/exportAllEmployee")
    public void exportAllEmployee(HttpServletRequest request,UserInfo user,Model model,HttpServletResponse response,HttpSession session) {
		try {
		this.uis.exportAllEmployee(request,user, response,session);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(path="/exportAllMaster")
    public void exportAllMaster(HttpServletRequest request,UserInfo user,Model model,HttpServletResponse response,HttpSession session) {
		try {
		this.uis.exportAllMaster(request,user, response,session);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
