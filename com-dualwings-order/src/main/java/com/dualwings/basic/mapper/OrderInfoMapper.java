package com.dualwings.basic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dualwings.basic.po.OrderInfo;

@Mapper
@Repository
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
	
}
