package com.dualwings.sales.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dualwings.sales.dao.SalesInfoMapper;
import com.dualwings.sales.po.SalesInfo;
import com.dualwings.sales.service.SalesInfoService;
@Service("salsInfoService")
public class SalesInfoServiceImpl extends ServiceImpl<SalesInfoMapper,SalesInfo> implements SalesInfoService {
	@Autowired
	private SalesInfoMapper salesInfoMapper;
	
	

}
