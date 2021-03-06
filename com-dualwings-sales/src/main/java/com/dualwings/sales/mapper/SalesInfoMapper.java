package com.dualwings.sales.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dualwings.sales.domain.entity.SalesInfo;

@Mapper
@Repository
public interface SalesInfoMapper extends BaseMapper<SalesInfo> {
	
}
