package com.dualwings.basic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dualwings.basic.domain.entity.SysRole;
import com.dualwings.basic.mapper.SysRoleMapper;
import com.dualwings.basic.service.SysRoleService;

@Service("sysRoleService")
public  class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole> implements SysRoleService {
	@Autowired
	private SysRoleMapper sysRoleMapper;

	
}
