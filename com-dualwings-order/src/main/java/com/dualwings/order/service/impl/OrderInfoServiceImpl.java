package com.dualwings.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dualwings.order.domain.entity.OrderInfo;
import com.dualwings.order.mapper.OrderInfoMapper;
import com.dualwings.order.service.OrderInfoService;
@Service("orderInfoService")
public  class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper,OrderInfo> implements OrderInfoService {
	@Autowired
	private OrderInfoMapper orderInfoMapper;

	@Override
	public Integer checkRelation(String usr_id, String sale_id) {
		// TODO Auto-generated method stub
		return orderInfoMapper.checkRelation(usr_id, sale_id);
	}

	@Override
	public Integer checkMbAccount(String usr_id) {
		// TODO Auto-generated method stub
		return orderInfoMapper.checkMbAccount(usr_id);
	}

	@Override
	public Integer checkSaleInfo(String sale_id) {
		// TODO Auto-generated method stub
		return orderInfoMapper.checkSaleInfo(sale_id);
	}

}
