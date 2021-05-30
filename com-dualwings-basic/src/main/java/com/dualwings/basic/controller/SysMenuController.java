package com.dualwings.basic.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dualwings.basic.dto.CommonResult;
import com.dualwings.basic.po.SysMenu;
import com.dualwings.basic.service.SysMenuService;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
//@FeignClient
@Api(tags="系统角色管理")
@RequestMapping("sys")
public class SysMenuController {
	
	@Autowired
	private SysMenuService sysMenuService;
	
	/**
	 * 获取菜单树结构
	 * @param useChnl
	 * @return
	 */
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
			commonResult.setMessage(e.getMessage());
			return commonResult;
		}

		return commonResult;
	}
	
	
	
}
