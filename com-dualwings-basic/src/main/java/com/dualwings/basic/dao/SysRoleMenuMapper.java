package com.dualwings.basic.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dualwings.basic.po.SysRoleMenu;

@Mapper
@Repository
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
	
}
