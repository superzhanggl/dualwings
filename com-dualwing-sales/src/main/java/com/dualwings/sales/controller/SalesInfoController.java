package com.dualwings.sales.controller;



import java.util.Date;
import java.util.List;

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
import com.dualwings.sales.dto.CommonResult;
import com.dualwings.sales.dto.QryDataInfo;
import com.dualwings.sales.po.SalesInfo;
import com.dualwings.sales.service.SalesInfoService;
import com.dualwings.sales.utils.SnowFlake;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
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
@RequestMapping("api")
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
    		@RequestParam(value="start",required = false) Integer start,
    		@RequestParam(value="limit",required = false) Integer limit,HttpServletRequest request
    		){
		System.out.println("**********方法体的*********"+request.getParameterValues("name")[0]);
    	IPage<SalesInfo> rows=null;
    	CommonResult result=null;
    		// 校验当前登录人是否关联了销售成员,获取当前销售人员的编号
    		String acct_sale_id="";
    		
    		
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
        	System.out.println("StrUtil.isBlank(saleLvl):"+StrUtil.isBlank(saleLvl));
//        	start=StrUtil.isBlank(StrUtil.toString(start))?0:start;
//        	limit=StrUtil.isBlank(StrUtil.toString(limit))?10:start;
        	rows=salesInfoService.page(new Page(0,10), queryWrapper);
        	result=new CommonResult();
        	
        	SalesInfo info=new SalesInfo();
        	QryDataInfo dto=new QryDataInfo();
        	BeanUtil.copyProperties(info,dto,"serialVersionUID");
        	dto.setFlag(true);
        	log.info("dto:"+rows.getRecords());
        	System.out.println("dto:"+dto.toString());
        	result.setData(rows);
        	
        	

		
    	
    	return result;
    }
    
    @RequestMapping("qryOne")
    public List qryOne() {
    	logger.info("销售的手机号不能为空，请重新输入！");
    	return salesInfoService.qryOne();
    }
    
    
    public static void main(String[] args) {
		System.out.println(Convert.toStr(""));
	}
    
}
