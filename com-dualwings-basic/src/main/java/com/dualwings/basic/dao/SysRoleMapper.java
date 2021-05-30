package com.dualwings.basic.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dualwings.basic.po.SysRole;

@Mapper
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {
	
}
