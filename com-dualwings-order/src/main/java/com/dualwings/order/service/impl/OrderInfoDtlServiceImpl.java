package com.dualwings.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dualwings.order.domain.entity.OrderInfoDtl;
import com.dualwings.order.mapper.OrderInfoDtlMapper;
import com.dualwings.order.service.OrderInfoDtlService;

@Service("orderInfoDtlService")
public class OrderInfoDtlServiceImpl extends ServiceImpl<OrderInfoDtlMapper, OrderInfoDtl>
		implements OrderInfoDtlService {
	@Autowired
	private OrderInfoDtlMapper orderInfoDtlMapper;


}
