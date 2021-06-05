package com.dualwings.sales.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dualwings.common.domain.entity.CommonResult;
import com.dualwings.common.utils.SnowFlake;
import com.dualwings.sales.domain.entity.SalesInfo;
import com.dualwings.sales.service.SalesInfoService;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(tags="销售成员批量导入")
@RequestMapping("sales")
public class SalesImportExcelController {
	private static Logger log=Logger.getLogger(SalesImportExcelController.class);
	@Autowired
    private SalesInfoService salesInfoService;
	@Transactional
	@ApiOperation(value="销售成员批量导入",notes="销售成员批量导入")
	@ApiImplicitParam(value = "excel文件路径",name ="fileNm" )
	@RequestMapping("ImportExcel")
	public CommonResult HandleImportExcel(@RequestParam(required = false)String fileNm) {
		CommonResult resp=new CommonResult();
		try {
//			String fileNm="";
			fileNm="D://dy/salesInfo.xlsx";
			if(StrUtil.isBlank(fileNm)) {
				resp.setCode(5000);
				resp.setMessage("未上传文件");
				return resp;
			}
			String acct_id="";// 获取当前登录人
	        String typeNm = fileNm.split("\\.")[1];// 获取文件的后缀,文件格式
	        String webPath = "";
	        String fileDir = File.separator;
	        File excelFile = new File(webPath + fileDir + fileNm);
	        List<Object> errList = new ArrayList<>();// 格式错误行
	        List<Object> errParentList = new ArrayList<>();// 上级编号是否存在
	        List<Object> errObjList = new ArrayList<>();// 是否已存在
	        List<String> phoneList = new ArrayList<>();// 手机号
	        List<Object> duplicateList = new ArrayList<>();// 重复行
	        
	        if (excelFile.isFile() && excelFile.exists()) {
	        	Workbook wb = null;
				if ("xls".equals(typeNm)) {
					wb = new HSSFWorkbook(new FileInputStream(excelFile));
				} else {
					wb = new XSSFWorkbook(new FileInputStream(excelFile));
				}
				Sheet sheet = wb.getSheetAt(0);
				int firstRow = sheet.getFirstRowNum() + 1;// 获取第一行
				int lastRow = sheet.getLastRowNum();// 获取最后一行
				for (int i = firstRow; i <= lastRow; i++) {
					Row row = sheet.getRow(i);
					if (row != null) {
						phoneList.add(row.getCell(2).getStringCellValue());
						// 检验数据
						this.checkDataPre(row, i, errList, errParentList, phoneList, duplicateList,errObjList);
					} else {
						errList.add(i + 1);
					}
				}
				if (errList.size() > 0 || errParentList.size() > 0 || duplicateList.size() > 0|| errObjList.size()>0) {
					System.out.println("errList:"+errList.size());
					System.out.println("errParentList:"+errParentList.size());
					System.out.println("duplicateList:"+duplicateList.size());
					System.out.println("errObjList:"+errObjList.size());
					StringBuffer err = new StringBuffer();
					if (errList.size() > 0) {
						err.append("第");
						errList.forEach(ele -> {
							err.append(ele + "、");
						});
						err.delete(err.length() - 1, err.length()).append("行数据,格式有误；");
					}
					StringBuffer batchErr = new StringBuffer(); 
					if (errParentList.size() > 0) {
						batchErr.append("第");
						errParentList.forEach(ele -> {
							batchErr.append(ele + "、");
						});
						batchErr.delete(batchErr.length() - 1, batchErr.length()).append("行数据,数据有误,该数据的上级销售编号不存在,或销售等级无法配置下级销售");
					}
					StringBuffer duplicateErr = new StringBuffer(); 
					if (duplicateList.size() > 0) {
						duplicateErr.append("第");
						duplicateList.forEach(ele -> {
							duplicateErr.append(ele + "、");
						});
						duplicateErr.delete(duplicateErr.length() - 1, duplicateErr.length()).append("行数据,Excel表格中已存在相同的手机号");
					}
					StringBuffer errObjErr = new StringBuffer(); 
					if (errObjList.size() > 0) {
						errObjErr.append("第");
						errObjList.forEach(ele -> {
							errObjErr.append(ele + "、");
						});
						errObjErr.delete(errObjErr.length() - 1, errObjErr.length()).append("行数据,手机号错误,系统已存在该手机号绑定销售成员");
					}
					
					resp.setCode(500);
					log.error("HandleImportExcel-err-001--导入销售信息,错误信息:"+err.toString() + batchErr.toString() + duplicateErr.toString()+errObjErr.toString());
					resp.setMessage(err.toString() + batchErr.toString() + duplicateErr.toString()+errObjErr.toString() + "请核对，所有数据未导入");
					return resp;
				}
				List<SalesInfo> batchParams=new ArrayList<SalesInfo>();
				// 插入销售成员账号
				for (int i = firstRow; i <= lastRow; i++) {
					Row row = sheet.getRow(i);
					if(null!=row) {
						SalesInfo salesInfo=new SalesInfo();
						// 获取当前行每一列数据
						String indexRow=Convert.toStr(row.getCell(0));// 序号
						String mb_sale_nm=Convert.toStr(row.getCell(1));// 销售名称
						String mb_sale_phone=Convert.toStr(row.getCell(2));// 销售手机
						String mb_sale_pid=Convert.toStr(row.getCell(3));// 销售上级父编号
						Object remark=row.getCell(4);// 备注
						String salesId=SnowFlake.getSequence();
						String path=salesId;
						Integer lvl=2;
						if(!StrUtil.isBlank(mb_sale_pid)) {
							QueryWrapper qw=new QueryWrapper();
							qw.eq("mb_sale_id", mb_sale_pid);
							SalesInfo obj=salesInfoService.getOne(qw);
							String parPath=obj.getSalePath();// 上级编号路径
							path=parPath+"."+salesId;
							lvl=Integer.parseInt(obj.getSaleLvl())-1;
						}
						// 组装参数
						salesInfo.setMbSaleId(salesId);
						salesInfo.setSaleLvl(Convert.toStr(lvl));
						salesInfo.setMbSalePid(mb_sale_pid);
						salesInfo.setSalePath(path);
						salesInfo.setMbSaleNm(mb_sale_nm);
						salesInfo.setMbSalePhone(mb_sale_phone);
						salesInfo.setSaleStatus("0");
						salesInfo.setCrtAcct(acct_id);
						salesInfo.setCrtDt(DateUtil.formatDateTime(new Date()));
						salesInfo.setRemark(Convert.toStr(remark));
						batchParams.add(salesInfo);
						
					}else {
						continue;
					}
					
				}
				
				
				boolean flag=salesInfoService.saveBatch(batchParams);
				if(!flag) {
					resp.setCode(5000);
					resp.setMessage("导入异常,批量导入失败");
					log.error("HandleImportExcel-err-002:导入异常,批量导入失败");
					return resp;
				}
	        }else {
	        	resp.setCode(5000);
				resp.setMessage("文件不存在");
				log.error("HandleImportExcel-err-002:文件不存在");
				return resp;
	        }
			
		} catch (Exception e) {
			// TODO: handle exception
			resp.setCode(5000);
			resp.setMessage("导入异常"+e.getMessage());
			log.error("HandleImportExcel-err:导入异常",e);
			return resp;
		}
		
		
		return resp;
	}
	
	/**
	 * 检验数据
	 * @param row 当前行
	 * @param index 当前行号
	 * @param errList 检验是否数据错误
	 * @param errParentList 检验上级编号是否存在
	 * @param phoneList 所有手机
	 * @param duplicateList 重复行
	 */
	public void checkDataPre(Row row, int index,List<Object> errList, List<Object> errParentList,
			List<String> phoneList,List<Object> duplicateList,List<Object> errObjList) {
		boolean isErrData = false;
		try {
			String indexRow=Convert.toStr(row.getCell(0));// 序号
			String mb_sale_nm=Convert.toStr(row.getCell(1));// 销售名称
			String mb_sale_phone=Convert.toStr(row.getCell(2));// 销售手机
			String mb_sale_pid=Convert.toStr(row.getCell(3));// 销售上级父编号
			if(StrUtil.isBlank(indexRow)||StrUtil.isBlank(mb_sale_nm)||StrUtil.isBlank(mb_sale_phone)
					) {
				isErrData=true;
				log.error("HandleImportExcel-err-check:数据为空");
				
			}
			// 校验手机号
			Pattern pattern = Pattern.compile("\\d{11}");
			System.out.println("手机号："+mb_sale_phone);
			if (!pattern.matcher(mb_sale_phone).matches()) {
				// 手机号格式有误
				isErrData = true;
				log.error("HandleImportExcel-err-check:手机号格式有误");
			}
			if (isErrData) {
				errList.add(index + 1);
				return;
			}
			
			// 上级编号不为空时，校验是否存在异常
			if(!StrUtil.isBlank(mb_sale_pid)) {
				QueryWrapper qw=new QueryWrapper();
				qw.eq("mb_sale_id", mb_sale_pid);
				qw.gt("sale_lvl", 0);
				SalesInfo obj=salesInfoService.getOne(qw);
				if(null==obj) {
					errParentList.add(index+1);
				}
				
			}
			// 校验是否存在重复手机号
			int i=0;
			for (int j=0;j<phoneList.size();j++) {
				String str=phoneList.get(j);
				if(str.equals(mb_sale_phone)) {
					i++;
				}
			}
			if(i>1) {
				duplicateList.add(index+1);
			}
			
			// 检验是否已存在该手机号
			QueryWrapper phoneQw=new QueryWrapper();
			phoneQw.eq("mb_sale_phone", mb_sale_phone);
			SalesInfo obj=salesInfoService.getOne(phoneQw);
			if(null!=obj) {
				errObjList.add(index+1);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error("导入的数据格式有误", e);
			log.error("HandleImportExcel-err-check:导入的数据格式有误");
			errList.add(index + 1);
		}
		
	}
}
