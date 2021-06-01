package com.dualwings.basic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dualwings.basic.domain.entity.SysMenu;

@Mapper
@Component
public interface SysMenuMapper extends BaseMapper<SysMenu> {
	
}
