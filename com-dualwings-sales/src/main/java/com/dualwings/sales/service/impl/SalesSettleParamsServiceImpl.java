package com.dualwings.sales.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dualwings.sales.domain.entity.SalesSettleParams;
import com.dualwings.sales.mapper.SalesSettleParamsMapper;
import com.dualwings.sales.service.SalesSettleParamsService;

@Service("salesSettleParamsService")
public class SalesSettleParamsServiceImpl extends ServiceImpl<SalesSettleParamsMapper, SalesSettleParams>
		implements SalesSettleParamsService {
	@Autowired
	private SalesSettleParamsMapper salesSettleParamsMapper;

}
