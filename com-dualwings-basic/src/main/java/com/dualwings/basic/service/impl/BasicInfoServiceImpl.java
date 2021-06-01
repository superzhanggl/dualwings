package com.dualwings.basic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dualwings.basic.mapper.BasicInfoMapper;
import com.dualwings.basic.domain.entity.BasicInfo;
import com.dualwings.basic.service.BasicInfoService;

@Service("basicInfoService")
public  class BasicInfoServiceImpl extends ServiceImpl<BasicInfoMapper,BasicInfo> implements BasicInfoService {
	@Autowired
	private BasicInfoMapper basicInfoMapper;


}
