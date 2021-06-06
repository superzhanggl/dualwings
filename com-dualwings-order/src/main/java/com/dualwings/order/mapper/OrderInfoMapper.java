package com.dualwings.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dualwings.order.domain.entity.OrderInfo;

@Mapper
@Repository
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
	public Integer checkRelation(String usr_id, String sale_id);

	public Integer checkMbAccount(String usr_id);

	public Integer checkSaleInfo(String sale_id);
}
