package com.dualwings.sales.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.jboss.logging.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dualwings.common.domain.entity.CommonResult;
import com.dualwings.common.utils.SnowFlake;
import com.dualwings.sales.domain.entity.SalesSettleParams;
import com.dualwings.sales.service.SalesSettleParamsService;

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
@Api(tags = "销售管理结算参数管理")
@RequestMapping("sales")
public class SalesSettleParamsController {
	@Resource(name = "salesSettleParamsService")
	private SalesSettleParamsService sspService;

	private static Logger log = Logger.getLogger(SalesSettleParamsController.class);

	@ApiOperation(value = "查询历史参数记录", notes = "查询历史参数记录")
	@ApiImplicitParams(value = { @ApiImplicitParam(value = "参数等级", name = "paramsLvl"),
			@ApiImplicitParam(value = "参数类型", name = "paramType"),
			@ApiImplicitParam(value = "查询下标", name = "start"), @ApiImplicitParam(value = "查询下标", name = "limit") })
	@RequestMapping("qryhistoryParams")
	public CommonResult qryhistoryParams(@RequestParam(required = true) String paramsLvl,
			@RequestParam(required = true) String paramType, @RequestParam(required = false) String start,
			@RequestParam(required = false) String limit) {
		CommonResult resp = new CommonResult();
		try {
			int sqlStart = StrUtil.isBlank(start) ? 10 : Convert.toInt(start);
			int sqlLimit = StrUtil.isBlank(start) ? 0 : Convert.toInt(limit);
			QueryWrapper qw = new QueryWrapper();
			qw.eq("param_lvl", paramsLvl);// 等级
			qw.eq("param_type", paramType);// 类型
			IPage<SalesSettleParams> iPage = sspService.page(new Page(sqlStart, sqlLimit), qw);
			resp.setData(iPage);
		} catch (Exception e) {
			// TODO: handle exception
			resp.setMessage("查询数据异常" + e.getMessage());
			resp.setCode(500);
			log.info("查询数据异常", e);
		}

		return resp;
	}

	@ApiOperation(value = "查询参数列表", notes = "查询参数列表")
	@ApiImplicitParams(value = { @ApiImplicitParam(value = "参数等级", name = "paramsLvl"),
			@ApiImplicitParam(value = "参数类型", name = "paramType"), @ApiImplicitParam(value = "查询下标", name = "start"),
			@ApiImplicitParam(value = "查询下标", name = "limit") })
	@RequestMapping("qryParams")
	public CommonResult qryParams(@RequestParam(required = true) String paramsLvl,
			@RequestParam(required = true) String paramType, @RequestParam(required = false) String start,
			@RequestParam(required = false) String limit) {
		CommonResult resp = new CommonResult();
		try {
			int sqlStart = StrUtil.isBlank(start) ? 10 : Convert.toInt(start);
			int sqlLimit = StrUtil.isBlank(start) ? 0 : Convert.toInt(limit);
			QueryWrapper qw = new QueryWrapper();
			qw.eq(!StrUtil.isBlank(paramsLvl), "param_lvl", paramsLvl);// 等级
			qw.eq(!StrUtil.isBlank(paramType), "param_type", paramType);// 类型
			qw.eq("status", "0");// 状态，0-正常，1-弃用
			IPage<SalesSettleParams> iPage = sspService.page(new Page(sqlStart, sqlLimit), qw);
			resp.setData(iPage);
		} catch (Exception e) {
			// TODO: handle exception
			resp.setMessage("查询数据异常" + e.getMessage());
			resp.setCode(500);
			log.info("查询数据异常", e);
		}

		return resp;
	}

	@Transactional
	@ApiOperation(value = "添加参数", notes = "添加参数")
	@ApiImplicitParams(value = { @ApiImplicitParam(value = "参数等级", name = "paramsLvl"),
			@ApiImplicitParam(value = "参数类型", name = "paramType"),
			@ApiImplicitParam(value = "备注", name = "remark"),
			@ApiImplicitParam(value = "参数值", name = "paramValue"), @ApiImplicitParam(value = "状态", name = "status") })
	@RequestMapping("appendParams")
	public CommonResult appendParams(SalesSettleParams ssParams) {
		CommonResult resp = new CommonResult();
		try {
			String acct_id = ""; // 当前登录人
			if (StrUtil.isBlank(ssParams.getParamValue())) {
				resp.setCode(500);
				resp.setMessage("添加参数异常,参数系数值不能空");
				return resp;
			}
			if (StrUtil.isBlank(ssParams.getParamType())) {
				resp.setCode(500);
				resp.setMessage("添加参数异常,参数类型不能空");
				return resp;
			}
			if (StrUtil.isBlank(ssParams.getParamLvl())) {
				resp.setCode(500);
				resp.setMessage("添加参数异常,参数等级不能空");
				return resp;
			}
			if (StrUtil.isBlank(ssParams.getStatus())) {
				resp.setCode(500);
				resp.setMessage("添加参数异常,参数状态不能空");
				return resp;
			}

			// 系数状态,为正常,则其他参数需要设置为弃用
			if ("0".equals(ssParams.getStatus())) {
				QueryWrapper qw = new QueryWrapper();
				qw.eq("param_lvl", ssParams.getParamLvl());// 等级
				qw.eq("param_type", ssParams.getParamType());// 类型
				SalesSettleParams obj = new SalesSettleParams();
				obj.setStatus("1");// 其他数据为弃用
				obj.setMdfAcct(acct_id);// 修改人编号
				obj.setMdfDt(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
				sspService.update(obj, qw);

			}
			ssParams.setUId(SnowFlake.getSequence());
			ssParams.setCrtAcct(acct_id);// 添加人
			ssParams.setCrtDt(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			sspService.save(ssParams);

		} catch (Exception e) {
			// TODO: handle exception
			resp.setMessage("添加异常" + e.getMessage());
			resp.setCode(500);
			log.info("添加异常", e);
		}

		return resp;
	}

	@Transactional
	@ApiOperation(value = "编辑参数", notes = "编辑参数")
	@ApiImplicitParams(value = { @ApiImplicitParam(value = "参数等级", name = "paramsLvl"),
			@ApiImplicitParam(value = "编号", name = "uId"),
			@ApiImplicitParam(value = "参数类型", name = "paramType"), @ApiImplicitParam(value = "备注", name = "remark"),
			@ApiImplicitParam(value = "参数值", name = "paramValue"), @ApiImplicitParam(value = "状态", name = "status") })
	@RequestMapping("mdfParams")
	public CommonResult mdfParams(SalesSettleParams ssParams) {
		CommonResult resp = new CommonResult();
		try {
			String acct_id = ""; // 当前登录人
			if (StrUtil.isBlank(ssParams.getParamValue())) {
				resp.setCode(500);
				resp.setMessage("编辑参数异常,参数系数值不能空");
				return resp;
			}
			if (StrUtil.isBlank(ssParams.getUId())) {
				resp.setCode(500);
				resp.setMessage("编辑参数异常,参数编号不能空");
				return resp;
			}
			if (StrUtil.isBlank(ssParams.getParamType())) {
				resp.setCode(500);
				resp.setMessage("编辑参数异常,参数类型不能空");
				return resp;
			}
			if (StrUtil.isBlank(ssParams.getParamLvl())) {
				resp.setCode(500);
				resp.setMessage("编辑参数异常,参数等级不能空");
				return resp;
			}
			if (StrUtil.isBlank(ssParams.getStatus())) {
				resp.setCode(500);
				resp.setMessage("编辑参数异常,参数状态不能空");
				return resp;
			}
			ssParams.setUId(SnowFlake.getSequence());
			// 系数状态,为正常,则其他参数需要设置为弃用
			if ("0".equals(ssParams.getStatus())) {
				QueryWrapper qw = new QueryWrapper();
				qw.eq("param_lvl", ssParams.getParamLvl());// 等级
				qw.eq("param_type", ssParams.getParamType());// 类型
				qw.ne("u_id", ssParams.getUId());
				SalesSettleParams obj = new SalesSettleParams();
				obj.setStatus("1");// 其他数据为弃用
				obj.setMdfAcct(acct_id);// 修改人编号
				obj.setMdfDt(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
				sspService.update(obj, qw);
			}
			QueryWrapper qwTarget = new QueryWrapper();
			qwTarget.eq("u_id", ssParams.getUId());
			ssParams.setMdfAcct(acct_id);// 修改人编号
			ssParams.setMdfDt(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			sspService.update(ssParams, qwTarget);

		} catch (Exception e) {
			// TODO: handle exception
			resp.setMessage("编辑异常" + e.getMessage());
			resp.setCode(500);
			log.info("编辑异常", e);
		}

		return resp;
	}

}
