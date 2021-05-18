package com.dualwings.user.utils;

import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dualwings.user.dao.UserMapper;
import com.dualwings.user.po.User;



public class Test {
	@Autowired
	private UserMapper userMapper;
	public void get() {
		//E page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper
		QueryWrapper queryWrapper =new QueryWrapper();
		String name = "";
		String age = "";
		queryWrapper.between(StringUtils.checkValNotNull(name) || StringUtils.checkValNotNull(age),
				"user_name"
				, 3, 8);
		
		IPage<User> selectPage = userMapper.selectPage(new Page(1,9), queryWrapper);
		selectPage.getPages();
		selectPage.getRecords();
		
	}
}
