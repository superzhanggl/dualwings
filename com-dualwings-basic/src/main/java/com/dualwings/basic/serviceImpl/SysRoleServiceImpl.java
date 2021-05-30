package com.dualwings.basic.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dualwings.basic.dao.SysRoleMapper;
import com.dualwings.basic.po.SysRole;
import com.dualwings.basic.service.SysRoleService;

@Service("sysRoleService")
public  class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole> implements SysRoleService {
	@Autowired
	private SysRoleMapper sysRoleMapper;

	
}
