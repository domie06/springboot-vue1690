package com.yjkj.framework.rbac.recharge.web;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yjkj.framework.base.pager.Pager;
import com.yjkj.framework.rbac.recharge.model.Recharge;
import com.yjkj.framework.rbac.recharge.service.inte.RechargeService;

@Controller
@RequestMapping("/recharge")
public class RechargeController {
	
	@Autowired
	private RechargeService rechargeService;
	
	@RequestMapping("/informationLoad")
	public String informationLoad(Pager pager,Model model,Recharge Recharge,HttpSession session) {
		try {
		pager = this.rechargeService.informationLoad(pager,Recharge,session);
		model.addAttribute("rechargeList", pager);
		return "jsp/RechargeTable";
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			return "jsp/Failed";
		}
	}
	@RequestMapping("/informationLoadFore")
	public String informationLoadFore(Pager pager,Model model,Recharge Recharge,HttpSession session) {
		try {
		pager = this.rechargeService.informationLoadFore(pager,Recharge,session);
		model.addAttribute("rechargeList", pager);
		return "jsp/foreground/RechargeTable";
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			return "jsp/Failed";
		}
	}
	
	@RequestMapping("/informationLoadOne")
	public String informationLoadOne(Recharge Recharge,Model model,HttpSession session) {
		
		try {
			Recharge = this.rechargeService.informationLoadOne(Recharge);
			model.addAttribute("recharge", Recharge);
			return "jsp/foreground/Recharge";
		}catch (Exception e) {
			e.printStackTrace();
			return "jsp/Failed";
		}
		
	}
	
	@RequestMapping("/informationAdd")
	public String informationAdd(Recharge Recharge,Model model,HttpSession session) {
		
		try {
			
			this.rechargeService.informationAdd(Recharge,session);
			return "redirect:informationLoadFore";
		}catch (Exception e) {
			e.printStackTrace();
			return "jsp/Failed";
		}
		
	}
	
	@RequestMapping("/informationAddFore")
	public String informationAddFore(Recharge Recharge,Model model,HttpSession session) {
		
		try {
			
			this.rechargeService.informationAdd(Recharge,session);
			return "redirect:informationLoadFore";
		}catch (Exception e) {
			e.printStackTrace();
			return "jsp/Failed";
		}
		
	}
	
	@RequestMapping("/informationDelete")
	public String informationDelete(Recharge Recharge,Model model) {
		
		try {
			this.rechargeService.informationDelete(Recharge);
			return "jsp/Success";
		}catch (Exception e) {
			e.printStackTrace();
			return "jsp/Failed";
		}
		
	}
	
	@RequestMapping("/informationUpdate")
	public String informationUpdate(Recharge Recharge,Model model) {
		
		try {
			this.rechargeService.informationUpade(Recharge);
			return "jsp/Success";
		} catch (Exception e) {
			e.printStackTrace();
			return "jsp/Failed";
		}
	}
	
	@RequestMapping("/getTicket")
	public String getTicket(Recharge Recharge,Model model) {
		
		try {
			this.rechargeService.informationUpade(Recharge);
			return "redirect:informationLoadFore";
		} catch (Exception e) {
			e.printStackTrace();
			return "jsp/Failed";
		}
	}
	@RequestMapping("/backTicket")
	public String backTicket(Recharge Recharge,Model model) {
		
		try {
			this.rechargeService.informationUpade(Recharge);
			return "redirect:informationLoadFore";
		} catch (Exception e) {
			e.printStackTrace();
			return "jsp/Failed";
		}
	}
		@RequestMapping("/informationDeleteFore")
		public String informationDeleteFore(Recharge Recharge,Model model) {
			
			try {
				this.rechargeService.informationDelete(Recharge);
				return "redirect:informationLoadFore";
			}catch (Exception e) {
				e.printStackTrace();
				return "jsp/Failed";
			}
			
		}
		
		@RequestMapping("/informationUpdateFore")
		public String informationUpdateFore(Recharge Recharge,Model model) {
			
			try {
				this.rechargeService.informationUpade(Recharge);
				return "redirect:informationLoadFore";
			} catch (Exception e) {
				e.printStackTrace();
				return "jsp/Failed";
			}
		
	}
		
		@RequestMapping(path="/exportAll")
	    public void exportAll(HttpServletRequest request,Model model,HttpServletResponse response,HttpSession session) {
			try {
			this.rechargeService.exportAll(request, response,session);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	
}
