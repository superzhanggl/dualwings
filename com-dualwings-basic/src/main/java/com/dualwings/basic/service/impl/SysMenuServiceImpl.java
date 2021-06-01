package com.dualwings.basic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dualwings.basic.domain.entity.SysMenu;
import com.dualwings.basic.mapper.SysMenuMapper;
import com.dualwings.basic.service.SysMenuService;

@Service("sysMenuService")
public  class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper,SysMenu> implements SysMenuService {
	@Autowired
	private SysMenuMapper sysMenuMapper;


}
