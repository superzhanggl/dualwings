package com.dualwings.sales.controller;



import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dualwings.common.utils.SnowFlake;
import com.dualwings.sales.dto.CommonResult;
import com.dualwings.sales.po.SalesInfo;
import com.dualwings.sales.service.SalesInfoService;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
//@FeignClient
@Api(tags="销售管理")
@RequestMapping("sales")
public class SalesInfoController {
    // @Resource
    private RestTemplate restTemplate;
    @Autowired
    private SalesInfoService salesInfoService;
    private static Logger  logger=Logger.getLogger(SalesInfoController.class);
    
	@ApiOperation(value="添加销售成员",notes="添加销售成员")
    @RequestMapping("appendSalesMb")
    public boolean appendSalesMb(@RequestBody SalesInfo salesInfo){
    	try {
    		// 检验获取当前登录人
        	String acct_id="";
        	String mb_sale_pid=salesInfo.getMbSalePid();
        	String path="";
        	String sale_id=SnowFlake.getSequence();
        	
        	int lvl=2;
        	if(!StrUtil.isBlank(mb_sale_pid)) {
        		// 上级不为空时,检验是否存在上级编号
        		QueryWrapper queryWrapper =new QueryWrapper();
        		queryWrapper.eq("mb_sale_id", mb_sale_pid);
        		SalesInfo salesPone = salesInfoService.getOne(queryWrapper);
        		if(null==salesPone) {
        			System.out.println("上级销售不存在，请重新输入！");
        			return false;
        		}
        		path=salesPone.getSalePath()+"."+sale_id;
        		lvl=Integer.parseInt(salesPone.getSaleLvl())-1;// 等级必须低于上级一级
        		if(lvl<0) {
        			System.out.println("等级异常");
        			return false;
        		}
        	}else {
        		path=sale_id;
        		
        	}
        	
        	salesInfo.setMbSaleId(sale_id);
        	salesInfo.setSalePath(path);
        	salesInfo.setSaleLvl(Convert.toStr(lvl));
        	
        	if(StrUtil.isBlank(salesInfo.getMbSalePhone())) {
        		logger.info("销售的手机号不能为空，请重新输入！");
        		return false;
        	}
        	
        	
        	String crt_dt=DateUtil.formatDateTime(new Date());// 创建时间
        	String crt_acct=acct_id;
        	salesInfo.setCrtAcct(crt_acct);// 创建人
        	salesInfo.setSaleStatus("0");// 状态
        	salesInfo.setCrtDt(crt_dt);
        	salesInfoService.save(salesInfo);
        	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return false;
		}
    	
    	return true;
    }
    
	@ApiOperation(value="销售成员列表查询",notes="销售成员列表查询")
    @ApiImplicitParams(value={
    		 @ApiImplicitParam(value = "销售等级，0-初级，1-中级，2-高级",name ="saleLvl" ),	
    		 @ApiImplicitParam(value = "sales名称",name ="mbSaleNm" ),	
    		 @ApiImplicitParam(value = "sales手机",name ="mbSalePhone" ),	
    		 @ApiImplicitParam(value = "sale状态:0-正常，1-黑名单",name ="saleStatus" ),	
    		 @ApiImplicitParam(value = "上级销售编号",name ="mbSalePid" ),
    		 @ApiImplicitParam(value = "销售编号",name ="mbSaleId" ),
    		 @ApiImplicitParam(value = "查询起始页码",name ="start" ),
    		 @ApiImplicitParam(value = "查询数量",name ="limit" ),
    })
    @RequestMapping("salesInfoListQry")
    public CommonResult salesInfoQry(
    		@RequestParam(value="saleLvl",required = false) String saleLvl,
    		@RequestParam(value="mbSaleNm",required = false)  String mbSaleNm,
    		@RequestParam(value="mbSalePhone",required = false) String mbSalePhone,
    		@RequestParam(value="saleStatus",required = false) String saleStatus,
    		@RequestParam(value="mbSalePid",required = false) String mbSalePid,
    		@RequestParam(value="mbSaleId",required = false) String mbSaleId,
    		@RequestParam(value="start",required = false) String start,
    		@RequestParam(value="limit",required = false) String limit,HttpServletRequest request
    		){
    	IPage<SalesInfo> rows=null;
    	CommonResult result=new CommonResult();
    	
		try {
			// 校验当前登录人是否关联了销售成员,获取当前销售人员的编号
    		String acct_sale_id="";
    		int pageStart=StrUtil.isBlank(start)?0:NumberUtil.parseInt(start);
        	int pageLimit=StrUtil.isBlank(limit)?10:NumberUtil.parseInt(limit);
    		
    		QueryWrapper queryWrapper =new QueryWrapper();
        	queryWrapper
        	.eq(!StrUtil.isBlank(saleLvl), "sale_lvl", mbSaleId)
        	.like(!StrUtil.isBlank(mbSaleNm), "mb_sale_nm", mbSaleNm)
        	.like(!StrUtil.isBlank(mbSaleId), "mb_sale_id", mbSaleId)
        	.eq(!StrUtil.isBlank(saleStatus), "sale_status", saleStatus)
        	.like(!StrUtil.isBlank(mbSalePid), "sale_path", mbSalePid)
        	.orderByDesc("sale_lvl", "crt_dt");
        	// 关联了销售成员,只能查询当前销售成员的下级销售成员
        	if(!StrUtil.isBlank(acct_sale_id)) {
        		queryWrapper.like(StrUtil.isBlank(acct_sale_id), "sale_path", acct_sale_id);
        	}
        	rows=salesInfoService.page(new Page(pageStart,pageLimit), queryWrapper);
        	
        	QueryWrapper qw1 =new QueryWrapper();
        	qw1.ge(true, "sale_lvl", "1");
        	List<Map<String, Object>> listMaps = salesInfoService.listMaps(qw1);
        	
        	for (SalesInfo map : rows.getRecords()) {
				String pid=map.getMbSalePid();
				for (Map<String, Object> map2 : listMaps) {
					String saleId=Convert.toStr(map2.get("mb_sale_id"));
					if(!StrUtil.isBlank(saleId)&&saleId.equals(pid)) {
						map.setPmapMess(map2);
					}
				}
			}
        	result.setData(rows);
        	
		} catch (Exception e) {
			// TODO: handle exception
			result.setMessage(e.getMessage());
			log.info("查询异常",e);
		}
    	
    	return result;
    }
    
    @ApiOperation(value="编辑销售成员",notes="编辑销售成员")
    @RequestMapping("editSalesMb")
    public CommonResult editSalesMb(@RequestBody SalesInfo salesInfo){
    	CommonResult result=new CommonResult();
    	try {
    		// 检验获取当前登录人
        	String acct_id="";
        	String mb_sale_pid=salesInfo.getMbSalePid();
        	String path="";
        	String sale_id=salesInfo.getMbSaleId();
        	
        	int lvl=2;
        	if(StrUtil.isBlank(salesInfo.getMbSalePhone())) {
        		logger.info("销售的手机号不能为空，请重新输入！");
        		return result;
        	}
        	if(!StrUtil.isBlank(mb_sale_pid)) {
        		// 上级不为空时,检验是否存在上级编号
        		QueryWrapper queryWrapper =new QueryWrapper();
        		queryWrapper.eq("mb_sale_id", mb_sale_pid);
        		SalesInfo salesOne = salesInfoService.getOne(queryWrapper);
        		if(null==salesOne) {
        			log.info("上级销售不存在，请重新输入！");
        			result.setMessage("上级销售不存在，请重新输入");
        			result.setCode(500);
        			return result;
        		}
        		path=salesOne.getSalePath()+"."+sale_id;
        		lvl=Integer.parseInt(salesOne.getSaleLvl())-1;// 等级必须低于上级一级
        		if(lvl<0) {
        			log.info("修改异常，等级异常");
        			result.setMessage("修改异常，等级异常");
        			result.setCode(500);
        			return result;
        		}
        	}else {
        		path=sale_id;
        		
        	}
        	
        	salesInfo.setSalePath(path);
        	salesInfo.setSaleLvl(Convert.toStr(lvl));
        	
        	salesInfo.setMdfDt(DateUtil.formatDateTime(new Date()));// 修改时间
        	salesInfo.setMdfAcct(acct_id);// 修改人
        	
        	QueryWrapper qw =new QueryWrapper();
        	qw.eq("sale_id", sale_id);
        	salesInfoService.update(qw);
        	
		} catch (Exception e) {
			// TODO: handle exception
			result.setMessage(e.toString()+":修改异常");
			result.setCode(500);
			log.info("修改异常");
			
			return result;
		}
    	
    	return result;
    }
    
    @ApiOperation(value="获取销售详情",notes="获取销售详情")
    @RequestMapping("qrySalesInfoDtl")
    public CommonResult qrySalesInfoDtl(@RequestParam(value="salesId",required = true) String salesId){
    	CommonResult result=new CommonResult();
    	try {
    		
    		QueryWrapper qw=new QueryWrapper<>();
    		qw.eq("mb_sale_id", salesId);
    		Map<String,Object> map=salesInfoService.getMap(qw);
    		String pid=Convert.toStr(map.get("mb_sale_pid"));
    		if(!StrUtil.isBlank(pid)) {
    			QueryWrapper qw1=new QueryWrapper<>();
    			qw1.eq("mb_sale_id", pid);
    			Map<String,Object> map_p=salesInfoService.getMap(qw1);
    			map.put("pmapMess", map_p);
    		}
    		result.setData(map);
    		
		} catch (Exception e) {
			// TODO: handle exception
			result.setMessage(e.getMessage()+"获取详情异常");
			result.setCode(500);
			log.info("获取详情异常");
			
			return result;
		}
    	
    	return result;
    }
    
    
    @ApiOperation(value="删除销售",notes="删除销售")
    @RequestMapping("delSalesInfo")
    public CommonResult delSalesInfo(@RequestParam(value="salesId",required = true) String salesId){
    	CommonResult result=new CommonResult();
    	try {
    		// 获取当前登录人
    		String acct_id="";
    		QueryWrapper qw=new QueryWrapper<>();
    		qw.eq("mb_sale_id", salesId);
    		SalesInfo salesInfo=new SalesInfo();
    		salesInfo.setMdfDt(DateUtil.formatDateTime(new Date()));// 修改时间
        	salesInfo.setMdfAcct(acct_id);// 修改人
        	salesInfo.setSaleStatus("1");
    		salesInfoService.update(salesInfo, qw);
    		
		} catch (Exception e) {
			// TODO: handle exception
			result.setMessage(e.getMessage()+"删除异常");
			result.setCode(500);
			log.info("删除异常");
			
			return result;
		}
    	
    	return result;
    }
   
}
