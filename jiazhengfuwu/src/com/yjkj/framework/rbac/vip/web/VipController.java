package com.yjkj.framework.rbac.vip.web;




import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yjkj.framework.base.pager.Pager;
import com.yjkj.framework.rbac.vip.model.Vip;
import com.yjkj.framework.rbac.vip.service.inte.VipService;

@Controller
@RequestMapping("/vip")
public class VipController {
	
	@Autowired
	private VipService vipService;
	
	@RequestMapping("/informationLoad")
	public String informationLoad(Pager pager,Model model,Vip Vip,HttpSession session) {
		try {
		pager = this.vipService.informationLoad(pager,Vip,session);
		model.addAttribute("vipList", pager);
		return "jsp/VipTable";
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			return "jsp/Failed";
		}
	}
	@RequestMapping("/informationLoadFore")
	public String informationLoadFore(Pager pager,Model model,Vip Vip,HttpSession session) {
		try {
		pager = this.vipService.informationLoadFore(pager,Vip,session);
		model.addAttribute("vipList", pager);
		return "jsp/foreground/VipTable";
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			return "jsp/Failed";
		}
	}
	
	@RequestMapping("/informationLoadOne")
	public String informationLoadOne(Vip Vip,Model model,HttpSession session) {
		
		try {
			Vip = this.vipService.informationLoadOne(Vip);
			model.addAttribute("vip", Vip);
			return "jsp/Vip";
		}catch (Exception e) {
			e.printStackTrace();
			return "jsp/Failed";
		}
		
	}
	
	@RequestMapping("/informationLoadOneFore")
	public String informationLoadOneFore(Vip Vip,Model model,HttpSession session) {
		
		try {
			Vip = this.vipService.informationLoadOne(Vip);
			model.addAttribute("vip", Vip);
			return "jsp/foreground/Vip";
		}catch (Exception e) {
			e.printStackTrace();
			return "jsp/Failed";
		}
		
	}
	
	@RequestMapping("/informationAdd")
	public String informationAdd(Vip Vip,Model model) {
		
		try {
			
			this.vipService.informationAdd(Vip);
			return "redirect:informationLoadFore";
		}catch (Exception e) {
			e.printStackTrace();
			return "jsp/Failed";
		}
		
	}
	
	@RequestMapping("/informationAddFore")
	public String informationAddFore(Vip Vip,Model model) {
		
		try {
			
			this.vipService.informationAdd(Vip);
			return "redirect:informationLoadFore";
		}catch (Exception e) {
			e.printStackTrace();
			return "jsp/Failed";
		}
		
	}
	
	@RequestMapping("/informationDelete")
	public String informationDelete(Vip Vip,Model model) {
		
		try {
			this.vipService.informationDelete(Vip);
			return "jsp/Success";
		}catch (Exception e) {
			e.printStackTrace();
			return "jsp/Failed";
		}
		
	}
	
	@RequestMapping("/informationUpdate")
	public String informationUpdate(Vip Vip,Model model) {
		
		try {
			this.vipService.informationUpade(Vip);
			return "jsp/Success";
		} catch (Exception e) {
			e.printStackTrace();
			return "jsp/Failed";
		}
	}
	
	@RequestMapping("/getTicket")
	public String getTicket(Vip Vip,Model model) {
		
		try {
			this.vipService.informationUpade(Vip);
			return "redirect:informationLoadFore";
		} catch (Exception e) {
			e.printStackTrace();
			return "jsp/Failed";
		}
	}
	@RequestMapping("/backTicket")
	public String backTicket(Vip Vip,Model model) {
		
		try {
			this.vipService.informationUpade(Vip);
			return "redirect:informationLoadFore";
		} catch (Exception e) {
			e.printStackTrace();
			return "jsp/Failed";
		}
	}
		@RequestMapping("/informationDeleteFore")
		public String informationDeleteFore(Vip Vip,Model model) {
			
			try {
				this.vipService.informationDelete(Vip);
				return "redirect:informationLoadFore";
			}catch (Exception e) {
				e.printStackTrace();
				return "jsp/Failed";
			}
			
		}
		
		@RequestMapping("/informationUpdateFore")
		public String informationUpdateFore(Vip Vip,Model model) {
			
			try {
				this.vipService.informationUpade(Vip);
				return "redirect:informationLoadFore";
			} catch (Exception e) {
				e.printStackTrace();
				return "jsp/Failed";
			}
		
	}
	
}
