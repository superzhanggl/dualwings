package com.dualwings.sales.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dualwings.sales.po.SalesInfo;

@Mapper
@Repository
public interface SalesInfoMapper extends BaseMapper<SalesInfo> {
	
	@Select("select * from sys_sale_info")
	public List<SalesInfo> qryOne();
}
