package com.dualwings.basic.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dualwings.basic.dao.BasicInfoMapper;
import com.dualwings.basic.po.BasicInfo;
import com.dualwings.basic.service.BasicInfoService;

@Service("basicInfoService")
public  class BasicInfoServiceImpl extends ServiceImpl<BasicInfoMapper,BasicInfo> implements BasicInfoService {
	@Autowired
	private BasicInfoMapper basicInfoMapper;


}
