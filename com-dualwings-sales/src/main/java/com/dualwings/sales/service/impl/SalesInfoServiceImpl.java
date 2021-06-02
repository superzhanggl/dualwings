package com.dualwings.sales.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dualwings.sales.domain.entity.SalesInfo;
import com.dualwings.sales.mapper.SalesInfoMapper;
import com.dualwings.sales.service.SalesInfoService;
@Service("salsInfoService")
public class SalesInfoServiceImpl extends ServiceImpl<SalesInfoMapper,SalesInfo> implements SalesInfoService {
	@Autowired
	private SalesInfoMapper salesInfoMapper;
	
	

}
