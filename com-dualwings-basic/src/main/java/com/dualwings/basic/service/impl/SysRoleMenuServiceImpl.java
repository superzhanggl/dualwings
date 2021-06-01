package com.dualwings.basic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dualwings.basic.domain.entity.SysRoleMenu;
import com.dualwings.basic.mapper.SysRoleMenuMapper;
import com.dualwings.basic.service.SysRoleMenuService;

@Service("sysRoleMenuService")
public  class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper,SysRoleMenu> implements SysRoleMenuService {
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;


}
