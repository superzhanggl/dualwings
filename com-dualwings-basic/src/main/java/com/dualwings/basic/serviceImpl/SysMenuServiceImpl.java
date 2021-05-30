package com.dualwings.basic.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dualwings.basic.dao.SysMenuMapper;
import com.dualwings.basic.po.SysMenu;
import com.dualwings.basic.service.SysMenuService;

@Service("sysMenuService")
public  class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper,SysMenu> implements SysMenuService {
	@Autowired
	private SysMenuMapper sysMenuMapper;


}
