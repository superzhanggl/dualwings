package com.dualwings.basic.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dualwings.basic.dao.SysRoleMenuMapper;
import com.dualwings.basic.dto.CommonResult;
import com.dualwings.basic.po.SysRole;
import com.dualwings.basic.po.SysRoleMenu;
import com.dualwings.basic.service.SysRoleMenuService;
import com.dualwings.basic.service.SysRoleService;
import com.dualwings.common.utils.SnowFlake;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
//@FeignClient
@Api(tags="系统角色管理")
@RequestMapping("sys")
public class SysRoleController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;
	

	
	public CommonResult<IPage<SysRole>> qryRoleList() {
		CommonResult<IPage<SysRole>> commonResult=new CommonResult<IPage<SysRole>>();
		try {
			QueryWrapper<SysRole> qw=new QueryWrapper<SysRole>();
			qw.notLike("role_id", "admin");
			IPage<SysRole> rows = sysRoleService.page(new Page(0,10), qw);
			commonResult.setData(rows);
		} catch (Exception e) {
			// TODO: handle exception
			commonResult.setCode(500);
			commonResult.setMessage("获取角色列表异常"+e.getMessage());
			return commonResult;
			
		}
		return commonResult;
	}
	
	public CommonResult editRole(SysRole sysRole) {
		CommonResult commonResult=new CommonResult();
		try {
			String acct_id="";// 获取当前登录人
			String type=sysRole.getType();
			// 校验类型
			if(StrUtil.isBlank(type)) {
				commonResult.setCode(500);
				commonResult.setMessage("角色类型不能为空");
				return commonResult;
			}
			// 检验编号是否为空
			if(StrUtil.isBlank(sysRole.getRoleId())) {
				commonResult.setCode(500);
				commonResult.setMessage("角色编号不能为空");
				return commonResult;
			}
			// 检验名称是否为空
			if(StrUtil.isBlank(sysRole.getRoleNm())) {
				commonResult.setCode(500);
				commonResult.setMessage("角色名称不能为空");
				return commonResult;
			}
			
			sysRole.setMdfAcct(acct_id);// 修改人
			sysRole.setMdfDt(DateUtil.formatDateTime(new Date()));// 修改时间
			QueryWrapper<SysRole> qw =new QueryWrapper<SysRole>();
			qw.eq("role_id", sysRole.getRoleId());
			sysRoleService.update(qw);
		} catch (Exception e) {
			// TODO: handle exception
			commonResult.setCode(500);
			commonResult.setMessage("修改异常"+e.getMessage());
			return commonResult;
			
		}
		return commonResult;
	}
	
	public CommonResult appendRole(SysRole sysRole) {
		CommonResult commonResult=new CommonResult();
		try {
			String role_id=SnowFlake.getSequence();
			String acct_id="";// 获取当前登录人
			String type=sysRole.getType();
			
			if(StrUtil.isBlank(type)) {
				commonResult.setCode(500);
				commonResult.setMessage("角色类型不能为空");
				return commonResult;
			}
			
			sysRole.setRoleId(role_id);
			sysRole.setCrtAcct(acct_id);
			sysRole.setCrtDt(DateUtil.formatDateTime(new Date()));
			
			sysRoleService.save(sysRole);
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			commonResult.setCode(500);
			commonResult.setMessage("获取角色列表异常"+e.getMessage());
			return commonResult;
			
		}
		return commonResult;
	}
	
	
	public CommonResult delRole(@RequestParam(value="roleIds") String roleIds) {
		CommonResult commonResult=new CommonResult();
		try {
			if(StrUtil.isBlank(roleIds)) {
				commonResult.setCode(500);
				commonResult.setMessage("删除异常，角色编号不存在");
				return commonResult;
			}
			String[] roles=roleIds.split(",");
			QueryWrapper<SysRole> qw=new QueryWrapper<SysRole>();
			qw.in("role_id", roles);
			
			boolean resp=sysRoleService.remove(qw);
			if(!resp) {
				commonResult.setCode(500);
				commonResult.setMessage("删除异常");
				return commonResult;
			}
		} catch (Exception e) {
			// TODO: handle exception
			commonResult.setCode(500);
			commonResult.setMessage("删除异常"+e.getMessage());
			return commonResult;
			
		}
		return commonResult;
	}
	
	public CommonResult bindMenutoRole(@RequestParam(value="roleId") String roleId,@RequestParam(value="newMenuIds")String newMenuIds,
			@RequestParam(value="delMenuIds")String delMenuIds) {
		CommonResult commonResult=new CommonResult();
		try {
			String acct_id="";// 当前登录人
			if(StrUtil.isBlank(roleId)) {
				commonResult.setCode(500);
				commonResult.setMessage("异常，角色编号不能为空");
				return commonResult;
			}
			if(StrUtil.isBlank(newMenuIds)|| StrUtil.isBlank(delMenuIds)) {
				commonResult.setCode(500);
				commonResult.setMessage("异常，菜单编号不能为空");
				return commonResult;
			}
			// 删除
			String[] delMenuIds_limit=delMenuIds.split(",");
			QueryWrapper<SysRoleMenu> qw=new QueryWrapper<SysRoleMenu>();
			qw.eq("sys_role_id", roleId);
			qw.in("sys_meun_id", delMenuIds_limit);
			if(delMenuIds_limit.length>0) {
				sysRoleMenuService.remove(qw);
			}
			
			// 添加
			String[] menuId_limit=newMenuIds.split(",");
			String dateTime=DateUtil.formatDateTime(new Date());
			List<SysRoleMenu> params=new ArrayList<SysRoleMenu>();
			for (String str : menuId_limit) {
				if(StrUtil.isBlank(str)) {
					SysRoleMenu sysRoleMenu=new SysRoleMenu();
					sysRoleMenu.setSysRoleId(roleId);
					sysRoleMenu.setSysMeunId(str);
					sysRoleMenu.setCrtAcct(acct_id);
					sysRoleMenu.setCrtDt(dateTime);
					params.add(sysRoleMenu);
				}
			}
			if(params.size()>0) {
				sysRoleMenuService.saveBatch(params);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			commonResult.setCode(500);
			commonResult.setMessage("授权异常"+e.getMessage());
			return commonResult;
			
		}
		return commonResult;
	}
	
	public CommonResult getRoleBindMenu(@RequestParam(value="roleId") String roleId) {
		
		CommonResult commonResult=new CommonResult();
		try {
			if(StrUtil.isBlankIfStr(roleId)) {
				commonResult.setCode(500);
				commonResult.setMessage("异常，角色编号不能为空");
				return commonResult;
			}
			
			QueryWrapper qw=new QueryWrapper();
			qw.select("sys_meun_id");
			qw.eq("sys_role_id", roleId);
			List rows=sysRoleMenuMapper.selectList(qw);
			
			commonResult.setData(rows);	
			
		} catch (Exception e) {
			// TODO: handle exception
			commonResult.setCode(500);
			commonResult.setMessage("异常"+e.getMessage());
			return commonResult;
		}
		
		return commonResult;
	}
	
}
