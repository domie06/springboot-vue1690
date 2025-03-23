package com.yjkj.framework.rbac.userInfo.service.impl;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yjkj.framework.base.basemodel.BaseService;
import com.yjkj.framework.base.pager.Pager;
import com.yjkj.framework.base.regular.Regular;
import com.yjkj.framework.base.regular.date.CreatDate;
import com.yjkj.framework.base.regular.file.OperaFile;
import com.yjkj.framework.rbac.userInfo.dao.UserInfoDao;
import com.yjkj.framework.rbac.userInfo.model.UserInfo;
import com.yjkj.framework.rbac.userInfo.service.inte.UserInfoService;
import com.yjkj.framework.rbac.vip.model.Vip;


@Service
public class UserInfoServiceImpl extends BaseService implements UserInfoService{
	@Autowired
	private UserInfoDao userInfoDao;
	@Override
	public UserInfo login(UserInfo userInfo,HttpSession session) throws Exception{
		UserInfo userinfo = userInfoDao.loginUser(userInfo.getLoginName(), userInfo.getLoginPassword());
		if(userinfo==null){
			throw new RuntimeException("用户名或密码错误!");
		}
		
		Vip vip = userinfo.getVip();
			this.vipDao.updateStatus(vip);
		vip = this.vipDao.informationLoadOne(vip);
			userinfo.setVip(vip);
		return userinfo;
	}
	@Override
	public void userAdd(UserInfo userInfo,MultipartFile[] file,HttpServletRequest request) {
		try{
			Map<String, String> map = OperaFile.uploadFile(file, null, request);
			userInfo.setUrl(map.get("name"));
			userInfo.setId(Regular.createId());
			userInfo.setCreateDate(CreatDate.getDate());
			Vip vip = new Vip();
			vip.setId(Regular.createId());
			vip.setCreatetime(CreatDate.getDate());
			this.vipDao.informationAdd(vip);
			userInfo.setVip_id(vip.getId());
			this.userInfoDao.userAdd(userInfo);
		}catch (Exception e) {
			throw new RuntimeException("添加失败,原因为:"+e.getMessage());
		}
		
	}
	@Override
	public UserInfo queryOne(String id) {
		UserInfo user = this.userInfoDao.queryOne(id);
		return user;
	}
	@Override
	public UserInfo queryRole(String roleId) {
		UserInfo user = this.userInfoDao.queryRole(roleId);
		return user;
	}
	@Override
	public Pager Query(Pager pager, UserInfo user) {
		Map<String,Object> map = new HashMap<>();
		map.put("pager", pager);
		map.put("user", user);
		List<UserInfo> list = this.userInfoDao.Query(map);
		if(list==null||list.isEmpty()){
			throw new RuntimeException("暂时没有用户");
		}
		pager.setDatas(list);
		pager.setTotalCount(queryCount(user));
		return pager;
	}
	@Override
	public void userDelete(String id) {
		try{
			this.userInfoDao.userDelete(id);
		}catch (Exception e) {
			throw new RuntimeException("删除失败,原因为:"+e.getMessage());
		}
		
	}
	@Override
	public Integer queryCount(UserInfo userInfo) {
		Integer count = this.userInfoDao.queryCount(userInfo);
		return count;
	}
	@Override
	public void userUpdate(UserInfo userInfo,MultipartFile[] file,HttpServletRequest request) {
		try{
			if(!file[0].isEmpty()) {
				Map<String, String> map = OperaFile.uploadFile(file, null, request);
				userInfo.setUrl(map.get("name"));
			}
			this.userInfoDao.userUpdate(userInfo);
		}catch (Exception e) {
			throw new RuntimeException("修改失败,原因为:"+e.getMessage());
		}
		
	}
	
	@Override
	public void exportAllMaster(HttpServletRequest request, UserInfo userInfo,HttpServletResponse response,HttpSession session) throws Exception {
		 response.setContentType("application/vnd.ms-excel");  
		 

		 String  codedFileName = java.net.URLEncoder.encode("雇主表", "UTF-8");  
	         response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls"); 
	         List<UserInfo> userInfos = this.userInfoDao.queryAll(userInfo);
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("雇主");
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("姓名");
		row.createCell(1).setCellValue("年龄");
		row.createCell(2).setCellValue("性别");
		row.createCell(3).setCellValue("地址");
		row.createCell(4).setCellValue("电话");
		row.createCell(5).setCellValue("服务类型");
		row.createCell(6).setCellValue("会员等级");
		row.createCell(7).setCellValue("详细信息");
		row.createCell(8).setCellValue("图片信息");
		
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中  
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中  
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
		int i = 1;
		for (int j = 0; j < userInfos.size(); j++) {
			UserInfo user = userInfos.get(j);
			row = sheet.createRow(i);
			row.createCell(0).setCellValue(user.getUserName());
			row.createCell(1).setCellValue(user.getAge());
			if(user.getSex().equals("1")) {
				row.createCell(2).setCellValue("男");
			}else {
				row.createCell(2).setCellValue("女");
			}
			row.createCell(3).setCellValue(user.getAddress());
			row.createCell(4).setCellValue(user.getPhoneNo());
			row.createCell(5).setCellValue(user.getFlag());
			row.createCell(6).setCellValue(user.getVip().getLevel());
			row.createCell(7).setCellValue(user.getRemark());
			sheet.addMergedRegion(new CellRangeAddress(i,i+3,0,0));  
			sheet.addMergedRegion(new CellRangeAddress(i,i+3,1,1));  
			sheet.addMergedRegion(new CellRangeAddress(i,i+3,2,2));  
			sheet.addMergedRegion(new CellRangeAddress(i,i+3,3,3));  
			sheet.addMergedRegion(new CellRangeAddress(i,i+3,4,4));  
			sheet.addMergedRegion(new CellRangeAddress(i,i+3,5,5));  
			sheet.addMergedRegion(new CellRangeAddress(i,i+3,6,6));  
			sheet.addMergedRegion(new CellRangeAddress(i,i+3,7,7));  
			sheet.addMergedRegion(new CellRangeAddress(i,i+3,8,8));  
			byte[] bt = FileUtils.readFileToByteArray(new File(request.getServletContext().getRealPath("/WEB-INF/upload/"+user.getUrl())));
			System.out.println(request.getServletContext().getRealPath("/WEB-INF/upload/"+user.getUrl()));
			 int pictureIdx = workbook.addPicture(bt, Workbook.PICTURE_TYPE_PNG);
			 CreationHelper helper = workbook.getCreationHelper();
			 Drawing drawing = sheet.createDrawingPatriarch();
			 ClientAnchor anchor = helper.createClientAnchor();
			 anchor.setCol1(8); //图片开始列数
			 anchor.setRow1(i); //图片开始行数
			 anchor.setCol2(9); //图片结束列数
			 anchor.setRow2(i+4);//图片结束行数
			 drawing.createPicture(anchor, pictureIdx);
			 i = i + 4;
		}
		 OutputStream  fOut = response.getOutputStream();  
        workbook.write(fOut); 
        fOut.flush();  
        fOut.close(); 
		
	}
	@Override
	public void exportAllEmployee(HttpServletRequest request, UserInfo userInfo,HttpServletResponse response,HttpSession session) throws Exception {
		String  codedFileName = java.net.URLEncoder.encode("雇员表", "UTF-8");  
        response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls"); 
        List<UserInfo> userInfos = this.userInfoDao.queryAll(userInfo);
	Workbook workbook = new HSSFWorkbook();
	Sheet sheet = workbook.createSheet("雇员");
	Row row = sheet.createRow(0);
	row.createCell(0).setCellValue("姓名");
	row.createCell(1).setCellValue("年龄");
	row.createCell(2).setCellValue("性别");
	row.createCell(3).setCellValue("地址");
	row.createCell(4).setCellValue("电话");
	row.createCell(5).setCellValue("服务类型");
	row.createCell(6).setCellValue("会员等级");
	row.createCell(7).setCellValue("详细信息");
	row.createCell(8).setCellValue("图片信息");
	
	CellStyle cellStyle = workbook.createCellStyle();
	cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中  
	cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中  
	cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
	int i = 1;
	for (int j = 0; j < userInfos.size(); j++) {
		UserInfo user = userInfos.get(j);
		row = sheet.createRow(i);
		row.createCell(0).setCellValue(user.getUserName());
		row.createCell(1).setCellValue(user.getAge());
		if(user.getSex().equals("1")) {
			row.createCell(2).setCellValue("男");
		}else {
			row.createCell(2).setCellValue("女");
		}
		row.createCell(3).setCellValue(user.getAddress());
		row.createCell(4).setCellValue(user.getPhoneNo());
		row.createCell(5).setCellValue(user.getFlag());
		row.createCell(6).setCellValue(user.getVip().getLevel());
		row.createCell(7).setCellValue(user.getRemark());
		sheet.addMergedRegion(new CellRangeAddress(i,i+3,0,0));  
		sheet.addMergedRegion(new CellRangeAddress(i,i+3,1,1));  
		sheet.addMergedRegion(new CellRangeAddress(i,i+3,2,2));  
		sheet.addMergedRegion(new CellRangeAddress(i,i+3,3,3));  
		sheet.addMergedRegion(new CellRangeAddress(i,i+3,4,4));  
		sheet.addMergedRegion(new CellRangeAddress(i,i+3,5,5));  
		sheet.addMergedRegion(new CellRangeAddress(i,i+3,6,6));  
		sheet.addMergedRegion(new CellRangeAddress(i,i+3,7,7));  
		sheet.addMergedRegion(new CellRangeAddress(i,i+3,8,8));  
		byte[] bt = FileUtils.readFileToByteArray(new File(request.getServletContext().getRealPath("/WEB-INF/upload/"+user.getUrl())));
		System.out.println(request.getServletContext().getRealPath("/WEB-INF/upload/"+user.getUrl()));
		 int pictureIdx = workbook.addPicture(bt, Workbook.PICTURE_TYPE_PNG);
		 CreationHelper helper = workbook.getCreationHelper();
		 Drawing drawing = sheet.createDrawingPatriarch();
		 ClientAnchor anchor = helper.createClientAnchor();
		 anchor.setCol1(8); //图片开始列数
		 anchor.setRow1(i); //图片开始行数
		 anchor.setCol2(9); //图片结束列数
		 anchor.setRow2(i+4);//图片结束行数
		 drawing.createPicture(anchor, pictureIdx);
		 i = i + 4;
	}
	 OutputStream  fOut = response.getOutputStream();  
   workbook.write(fOut); 
   fOut.flush();  
   fOut.close(); 

}
}