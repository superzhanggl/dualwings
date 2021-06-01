package com.dualwings.basic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dualwings.basic.domain.entity.SysAccountRole;
import com.dualwings.basic.mapper.SysAccountRoleMapper;
import com.dualwings.basic.service.SysAccountRoleService;

@Service("sysAccountRoleService")
public  class SysAccountRoleServiceImpl extends ServiceImpl<SysAccountRoleMapper,SysAccountRole> implements SysAccountRoleService {
	@Autowired
	private SysAccountRoleMapper sysAccountRoleMapper;


}
