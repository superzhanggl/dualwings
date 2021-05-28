package com.dualwings.basic.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dualwings.basic.dao.OrderInfoMapper;
import com.dualwings.basic.po.OrderInfo;
import com.dualwings.basic.service.OrderInfoService;
@Service("orderInfoService")
public  class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper,OrderInfo> implements OrderInfoService {
	@Autowired
	private OrderInfoMapper orderInfoMapper;


}
