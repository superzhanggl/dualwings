package com.dualwings.sales.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dualwings.sales.domain.entity.SalesSettleParams;

@Mapper
@Repository
public interface SalesSettleParamsMapper extends BaseMapper<SalesSettleParams> {

}
