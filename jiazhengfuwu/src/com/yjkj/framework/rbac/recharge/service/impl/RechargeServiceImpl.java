package com.yjkj.framework.rbac.recharge.service.impl;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yjkj.framework.base.basemodel.BaseService;
import com.yjkj.framework.base.pager.Pager;
import com.yjkj.framework.base.regular.Regular;
import com.yjkj.framework.base.regular.date.CreatDate;
import com.yjkj.framework.base.regular.date.OperaDate;
import com.yjkj.framework.rbac.recharge.model.Recharge;
import com.yjkj.framework.rbac.recharge.service.inte.RechargeService;
import com.yjkj.framework.rbac.userInfo.dao.UserInfoDao;
import com.yjkj.framework.rbac.userInfo.model.UserInfo;
import com.yjkj.framework.rbac.vip.model.Vip;
@Service("RechargeService")
public class RechargeServiceImpl extends BaseService implements RechargeService {

	@Autowired
	private UserInfoDao userInfoDao;
	
	@Override
	public Pager informationLoad(Pager pager,Recharge Recharge,HttpSession session) throws Exception{
		Map<String,Object> map = new HashMap<>();
		map.put("pager", pager);
		map.put("recharge", Recharge);
		List<Recharge> list = this.rechargeDao.informationLoad(map);
		pager.setDatas(list);
		pager.setTotalCount(this.count(Recharge));
		return pager;
	}
	
	@Override
	public Pager informationLoadFore(Pager pager,Recharge Recharge,HttpSession session) throws Exception{
		Map<String,Object> map = new HashMap<>();
		UserInfo userInfo = (UserInfo) session.getAttribute("User");
		Recharge.setUser_id(userInfo.getId());
		map.put("pager", pager);
		map.put("recharge", Recharge);
		List<Recharge> list = this.rechargeDao.informationLoad(map);
		pager.setDatas(list);
		pager.setTotalCount(this.count(Recharge));
		return pager;
	}

	@Override
	public List<Recharge> informationLoadAll() throws Exception {
		List<Recharge> list = this.rechargeDao.informationLoadAll();
		return list;
	}
	

	@Override
	public Integer count(Recharge Recharge) throws Exception {
		Integer count = this.rechargeDao.count(Recharge);
		return count;
	}

	@Override
	public Recharge informationLoadOne(Recharge Recharge) throws Exception {
			Recharge = this.rechargeDao.informationLoadOne(Recharge);
		return Recharge;
	}

	@Override
	public void informationDelete(Recharge Recharge) throws Exception {
			this.rechargeDao.informationDelete(Recharge);

	}

	@Override
	public void informationAdd(Recharge Recharge,HttpSession session) throws Exception {
			Recharge.setId(Regular.createId());
			Recharge.setCreatetime(CreatDate.getDate());
			UserInfo userInfo = this.userInfoDao.queryOne(Recharge.getUser_id());
			Vip vip = userInfo.getVip();
			if(Recharge.getRecharge_type().equals("1")) {
				Recharge.setMoney("10");
				if(vip.getStatus().equals("1")) {
					vip.setOrdertime(OperaDate.addDate(30, vip.getOrdertime()));
				}else {
				vip.setOrdertime(OperaDate.addDate(30, CreatDate.getDate()));
				}
				vip.setStatus("1");
				vip.setLevel("1");
			}
			if(Recharge.getRecharge_type().equals("2")) {
				Recharge.setMoney("25");
				if(vip.getStatus().equals("1")) {
					vip.setOrdertime(OperaDate.addDate(90, vip.getOrdertime()));
				}else {
				vip.setOrdertime(OperaDate.addDate(90, CreatDate.getDate()));
				}
				vip.setStatus("1");
				vip.setLevel("2");
			}
			if(Recharge.getRecharge_type().equals("3")) {
				Recharge.setMoney("50");
				if(vip.getStatus().equals("1")) {
					vip.setOrdertime(OperaDate.addDate(180, vip.getOrdertime()));
				}else {
				vip.setOrdertime(OperaDate.addDate(180, CreatDate.getDate()));
				}
				vip.setStatus("1");
				vip.setLevel("3");
			}
			if(Recharge.getRecharge_type().equals("4")) {
				Recharge.setMoney("102");
				if(vip.getStatus().equals("1")) {
					vip.setOrdertime(OperaDate.addDate(365, vip.getOrdertime()));
				}else {
				vip.setOrdertime(OperaDate.addDate(365, CreatDate.getDate()));
				}
				vip.setStatus("1");
				vip.setLevel("4");
			}
			this.vipDao.informationUpdate(vip);
			UserInfo user = (UserInfo) session.getAttribute("User");
			String userId = user.getId();
			session.removeAttribute("User");
			user = this.userInfoDao.queryOne(userId);
			session.setAttribute("User", user);
			this.rechargeDao.informationAdd(Recharge);

	}

	@Override
	public void informationUpade(Recharge Recharge) throws Exception {
			this.rechargeDao.informationUpdate(Recharge);

	}

	@Override
	public void exportAll(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception {
		 String  codedFileName = java.net.URLEncoder.encode("客户订单信息", "UTF-8");  
         response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls"); 
	List<Recharge> list = this.rechargeDao.informationLoadAll();
	Workbook workbook = new HSSFWorkbook();
	Sheet sheet = workbook.createSheet("收入信息");
	Row row = sheet.createRow(0);
	row.createCell(0).setCellValue("充值人");
	row.createCell(1).setCellValue("充值金额");
	row.createCell(2).setCellValue("充值时长");
	row.createCell(3).setCellValue("充值方式");
	row.createCell(4).setCellValue("充值时间");
	
	CellStyle cellStyle = workbook.createCellStyle();
	cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
	for (int i = 1; i <= list.size(); i++) {
		Recharge recharge = list.get(i - 1);
		row = sheet.createRow(i);
		row.createCell(0).setCellValue(recharge.getUserInfo().getUserName());
		row.createCell(1).setCellValue(recharge.getMoney());
		if (recharge.getRecharge_type().equals("1")) {
			row.createCell(2).setCellValue("一个月");
		}else if (recharge.getRecharge_type().equals("2")) {
			row.createCell(2).setCellValue("三个月");
		}else if (recharge.getRecharge_type().equals("3")) {
			row.createCell(2).setCellValue("半年");
		}else if (recharge.getRecharge_type().equals("4")) {
			row.createCell(2).setCellValue("一年");
		}else {
			row.createCell(2).setCellValue("获取信息错误");
		}
		if(recharge.getFlag().equals("1")) {
			row.createCell(3).setCellValue("支付宝");
		}else if(recharge.getFlag().equals("2")) {
			row.createCell(3).setCellValue("微信");
		}else {
			row.createCell(3).setCellValue("获取信息错误");
		}
		row.createCell(4).setCellValue(recharge.getCreatetime());
		
	}
	 OutputStream  fOut = response.getOutputStream();  
    workbook.write(fOut); 
    fOut.flush();  
    fOut.close(); 
		
	}

}
