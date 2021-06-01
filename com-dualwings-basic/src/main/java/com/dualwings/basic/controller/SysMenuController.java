package com.dualwings.basic.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dualwings.basic.domain.entity.SysAccountRole;
import com.dualwings.basic.domain.entity.SysMenu;
import com.dualwings.basic.domain.entity.SysRoleMenu;
import com.dualwings.basic.mapper.SysRoleMenuMapper;
import com.dualwings.basic.service.SysAccountRoleService;
import com.dualwings.basic.service.SysMenuService;
import com.dualwings.basic.service.SysRoleMenuService;
import com.dualwings.common.utils.CommonResult;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
//@FeignClient
@Api(tags="系统菜单管理")
@RequestMapping("sys")
public class SysMenuController {
	
	@Autowired
	private SysMenuService sysMenuService;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private SysAccountRoleService sysAccountRoleService;
	
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;
	
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	/**
	 * 获取菜单树结构
	 * @param useChnl
	 * @return
	 */
	@ApiOperation(value="角色拥有的菜单",notes="角色拥有的菜单")
	@ApiImplicitParam(value = "使用渠道，0-运营端，1-app",name ="useChnl")
	@RequestMapping("getMenuTree")
	public CommonResult getMenuList(@RequestParam(value = "useChnl")String useChnl) {
		CommonResult commonResult=new CommonResult();
		try {
			
			if(StrUtil.isBlank(useChnl)) {
				commonResult.setCode(500);
				commonResult.setMessage("异常，菜单使用端口不能为空");
				return commonResult;
			}
			
			QueryWrapper qw=new QueryWrapper();
			qw.eq("use_chnl", useChnl);
			// 获取所有菜单
			List<SysMenu> rows=sysMenuService.list(qw);
			// 筛选父菜单，剩余子菜单
			List<SysMenu> tree = new ArrayList<>();
		    for (SysMenu node : rows) {
		    	// 获取父菜单
		        if (StrUtil.isBlank(node.getMenuPid())) {
		            tree.add(node);
		            continue;
		        }
		        // 组装子菜单
		        for (SysMenu parent : rows) {
		            if (null != node.getMenuPid() && node.getMenuPid().equals(parent.getMenuId())) {
		                if (null == parent.getChildren()) {
		                    parent.setChildren(new ArrayList<>());
		                }
		                parent.getChildren().add(node);
		                break;
		            }
		        }
		    }
			
			commonResult.setData(tree);
			
		} catch (Exception e) {
			// TODO: handle exception
			commonResult.setCode(500);
			commonResult.setMessage("获取菜单异常"+e.getMessage());
			return commonResult;
		}

		return commonResult;
	}
	
	@ApiOperation(value="账号拥有的菜单",notes="账号拥有的菜单")
	@RequestMapping("getMenu")
	public CommonResult getMenu(HttpServletRequest httpRequest) {
		CommonResult commonResult=new CommonResult();
		try {
			String acct_token=httpRequest.getParameter("login_token"); // 当前登录人
			// redis根据token,获取当前登录人账号
			
			
			String acct_id="";
			QueryWrapper qw=new QueryWrapper();
			qw.eq("acct_id", acct_id);
			// 获取账号绑定的角色列表
			List<SysAccountRole> roles=sysAccountRoleService.list(qw);
			List rolesId=new ArrayList();
			for (SysAccountRole sysAccountRole : roles) {
				rolesId.add(sysAccountRole.getSysRoleId());
			}
			QueryWrapper meumQw=new QueryWrapper();
			meumQw.in("sys_role_id", rolesId);
			meumQw.select("sys_meun_id");
			// 获取角色绑定的菜单
			List<SysRoleMenu> roleMenu=sysRoleMenuService.list(meumQw);
			List roleMenuId=new ArrayList();
			for (SysRoleMenu sysRoleMenu : roleMenu) {
				roleMenuId.add(sysRoleMenu.getSysMeunId());
			}
			// 获取菜单
			QueryWrapper mqw=new QueryWrapper();
			mqw.in("menu_id", roleMenuId);
			List<SysMenu> rows=sysMenuService.list(mqw);
			// 筛选父菜单，剩余子菜单
			List<SysMenu> tree = new ArrayList<>();
		    for (SysMenu node : rows) {
		    	// 获取父菜单
		        if (StrUtil.isBlank(node.getMenuPid())) {
		            tree.add(node);
		            continue;
		        }
		        // 组装子菜单
		        for (SysMenu parent : rows) {
		            if (null != node.getMenuPid() && node.getMenuPid().equals(parent.getMenuId())) {
		                if (null == parent.getChildren()) {
		                    parent.setChildren(new ArrayList<>());
		                }
		                parent.getChildren().add(node);
		                break;
		            }
		        }
		    }
			commonResult.setData(tree);
		} catch (Exception e) {
			// TODO: handle exception
			commonResult.setCode(500);
			commonResult.setMessage("获取菜单异常"+e.getMessage());
			return commonResult;
		}

		return commonResult;
	}
	
	
}
