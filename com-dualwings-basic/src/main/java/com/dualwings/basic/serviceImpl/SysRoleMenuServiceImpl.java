package com.dualwings.basic.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dualwings.basic.dao.SysRoleMenuMapper;
import com.dualwings.basic.po.SysRoleMenu;
import com.dualwings.basic.service.SysRoleMenuService;

@Service("sysRoleMenuService")
public  class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper,SysRoleMenu> implements SysRoleMenuService {
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;


}
