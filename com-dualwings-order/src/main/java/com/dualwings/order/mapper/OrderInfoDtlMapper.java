package com.dualwings.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dualwings.order.domain.entity.OrderInfoDtl;

@Mapper
@Repository
public interface OrderInfoDtlMapper extends BaseMapper<OrderInfoDtl> {

}
