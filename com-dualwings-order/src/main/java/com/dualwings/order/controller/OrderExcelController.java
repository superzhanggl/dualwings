package com.dualwings.order.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dualwings.common.domain.entity.CommonResult;
import com.dualwings.common.utils.SnowFlake;
import com.dualwings.order.domain.entity.OrderInfo;
import com.dualwings.order.domain.entity.OrderInfoDtl;
import com.dualwings.order.service.OrderInfoDtlService;
import com.dualwings.order.service.OrderInfoService;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "订单批量导入")
@RequestMapping("order")
public class OrderExcelController {
	private static Logger log=Logger.getLogger(OrderExcelController.class);
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private OrderInfoDtlService orderInfoDtlService;
	@Transactional
	@ApiOperation(value = "订单批量导入", notes = "订单批量导入")
	@ApiImplicitParam(value = "excel文件路径",name ="fileNm" )
	@RequestMapping("ImportExcel")
	public CommonResult HandleImportExcel(@RequestParam(required = false)String fileNm) {
		CommonResult resp=new CommonResult();
		try {
//			String fileNm="";
			fileNm = "D://dy/order.xlsx";
			if(StrUtil.isBlank(fileNm)) {
				resp.setCode(5000);
				resp.setMessage("未上传文件");
				return resp;
			}
			String acct_id="";// 获取当前登录人
			String importDt = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");// 创建时间
	        String typeNm = fileNm.split("\\.")[1];// 获取文件的后缀,文件格式
	        String webPath = "";
	        String fileDir = File.separator;
	        File excelFile = new File(webPath + fileDir + fileNm);
	        List<Object> errList = new ArrayList<>();// 格式错误行
			List<Object> errSalesList = new ArrayList<>();// 上级编号是否存在
	        List<Object> errObjList = new ArrayList<>();// 是否已存在
	        
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

						// 检验数据
						this.checkDataPre(row, i, errList, errSalesList, errObjList);
					} else {
						errList.add(i + 1);
					}
				}
				if (errList.size() > 0 || errSalesList.size() > 0
						|| errObjList.size() > 0) {
					System.out.println("errList:"+errList.size());
					System.out.println("errSalesList:" + errSalesList.size());
					System.out.println("errObjList:"+errObjList.size());
					StringBuffer err = new StringBuffer();
					if (errList.size() > 0) {
						err.append("第");
						errList.forEach(ele -> {
							err.append(ele + "、");
						});
						err.delete(err.length() - 1, err.length()).append("行数据,格式有误,请核对时间格式是否正确,是否存在未填必填项");
					}
					StringBuffer batchErr = new StringBuffer(); 
					if (errSalesList.size() > 0) {
						batchErr.append("第");
						errSalesList.forEach(ele -> {
							batchErr.append(ele + "、");
						});
						batchErr.delete(batchErr.length() - 1, batchErr.length())
								.append("行数据,数据有误,用户绑定的销售编号有误");
					}

					StringBuffer errObjErr = new StringBuffer(); 
					if (errObjList.size() > 0) {
						errObjErr.append("第");
						errObjList.forEach(ele -> {
							errObjErr.append(ele + "、");
						});
						errObjErr.delete(errObjErr.length() - 1, errObjErr.length()).append("行数据,系统不存在该用户信息");
					}
					
					resp.setCode(500);
					log.error("HandleImportExcel-err-001--导入订单信息有误,错误信息:" + err.toString() + batchErr.toString());
					resp.setMessage(err.toString() + batchErr.toString() + errObjErr.toString() + "请核对，所有数据未导入");
					return resp;
				}
				List<OrderInfo> batchParams = new ArrayList<OrderInfo>();
				List<OrderInfoDtl> batchDtlParams = new ArrayList<OrderInfoDtl>();
				// 插入订单信息
				for (int i = firstRow; i <= lastRow; i++) {
					Row row = sheet.getRow(i);
					if(null!=row) {
						OrderInfo orderInfo = new OrderInfo();
						// 获取当前行每一列数据
						String indexRow=Convert.toStr(row.getCell(0));// 序号
						String usr_id = Convert.toStr(row.getCell(1));// 用户编号
						String petrol_amount = Convert.toStr(row.getCell(2));// 加油量
						String order_dt = Convert.toStr(row.getCell(3));// 加油日期
						String sale_id = Convert.toStr(row.getCell(4));// 直属上级销售编号,可为空
						String crt_acct = Convert.toStr(row.getCell(5));// 数据记录员工号
						String crt_dt = Convert.toStr(row.getCell(6));// 数据时间
						String remark = Convert.toStr(row.getCell(7));// 备注
						String orderId = SnowFlake.getSequence();

						// 组装参数
						orderInfo.setOrderId(orderId);// 订单编号
						orderInfo.setUsrId(usr_id);// 用户编号
						orderInfo.setPetrolAmount(petrol_amount);// 石油量
						orderInfo.setBindSaleId(sale_id);// 销售编号
						orderInfo.setOrderDt(order_dt);// 订单日期
						orderInfo.setOrderStatus("0");// 订单状态,正常
						orderInfo.setPetrolStatus("0");// 加油状态,成功
						orderInfo.setSettleStatus("0");// 结算状态,待结算
						orderInfo.setRemark(remark);
						batchParams.add(orderInfo);

						// 组装订单详情
						OrderInfoDtl dtl = new OrderInfoDtl();
						dtl.setCrtAcct(crt_acct);// 数据记录者工号
						dtl.setCrtDt(crt_dt);// 数据记录时间
						dtl.setCrtType("2");// 创建方式，0-自主加油，1-系统导入,2-批量导入
						dtl.setOrderId(orderId);// 订单编号
						dtl.setImportDt(importDt);// 导入时间
						dtl.setImportAcct(acct_id);// 导入者
						batchDtlParams.add(dtl);
					}else {
						continue;
					}
					
				}
				
				
				boolean flag = orderInfoService.saveBatch(batchParams);
				if(!flag) {
					resp.setCode(5000);
					resp.setMessage("导入异常,批量导入失败");
					log.error("HandleImportExcel-err-002:导入异常,批量导入失败");
					return resp;
				}
				boolean flagDtl = orderInfoDtlService.saveBatch(batchDtlParams);
				if (!flagDtl) {
					resp.setCode(5000);
					resp.setMessage("导入异常,批量导入失败");
					log.error("HandleImportExcel-err-003:导入异常,批量导入失败");
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
	 * 
	 * @param row           当前行
	 * @param index         当前行号
	 * @param errList       检验是否数据错误
	 * @param errSalesList  检验上级编号是否存在
	 * @param errObjList    用户是否存在
	 */
	public void checkDataPre(Row row, int index,List<Object> errList, List<Object> errSalesList,
			List<Object> errObjList) {
		boolean isErrData = false;
		try {
			String indexRow=Convert.toStr(row.getCell(0));// 序号
			String usr_id = Convert.toStr(row.getCell(1));// 用户编号
			String petrol_amount = Convert.toStr(row.getCell(2));// 加油量
			String order_dt = Convert.toStr(row.getCell(3));// 加油日期
			String sale_id = Convert.toStr(row.getCell(4));// 直属上级销售编号,可为空
			String crt_dt = Convert.toStr(row.getCell(6));// 数据记录时间
			String crt_acct = Convert.toStr(row.getCell(5));// 数据记录者编号
			String remark = Convert.toStr(row.getCell(7));// 备注
			if (StrUtil.isBlank(indexRow) || StrUtil.isBlank(usr_id) || StrUtil.isBlank(petrol_amount)
					|| StrUtil.isBlank(order_dt) || StrUtil.isBlank(crt_dt) || StrUtil.isBlank(crt_acct)) {
				isErrData=true;
				log.error("HandleImportExcel-err-check:数据为空");
				
			}
			// 校验手机号
			Pattern pattern = Pattern.compile(
					"^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$");
			System.out.println("订单日期格式：" + order_dt);
			if (!pattern.matcher(order_dt).matches() || !pattern.matcher(crt_dt).matches()) {
				// 手机号格式有误
				isErrData = true;
				log.error("HandleImportExcel-err-check:加油日期格式或记录时间格式有误");
			}
			

			// 绑定销售成员不为空,校验销售成员
			if (!StrUtil.isBlank(sale_id)) {
				int count = orderInfoService.checkRelation(usr_id, sale_id);
				if (count <= 0) {
					isErrData = true;
					log.error("HandleImportExcel-err-check:销售成员与用户绑定关系有误");
				}
				int salesObj=orderInfoService.checkSaleInfo(sale_id);
				if (count <= 0) {
					errSalesList.add(index + 1);
					log.error("HandleImportExcel-err-check:销售成员信息不存在");
				}
			}
			if (isErrData) {
				errList.add(index + 1);
				return;
			}
			
			int obj = orderInfoService.checkMbAccount(usr_id);
			if (obj <= 0) {
				errObjList.add(index + 1);
				log.error("HandleImportExcel-err-check:用户信息不存在");
			}

		} catch (Exception e) {
			// TODO: handle exception
			log.error("导入的数据格式有误", e);
			log.error("HandleImportExcel-err-check:导入的数据格式有误");
			errList.add(index + 1);
		}
		
	}

	public static void main(String[] args) {
		Pattern pattern = Pattern.compile(
				"^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$");
		if (!pattern.matcher("2021-06-06  50:30:20").matches()) {
			// 手机号格式有误

			System.out.println("有误");
		} else {
			System.out.println("正确");
		}
	}
}
