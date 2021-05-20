package com.dualwings.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dualwings.user.dao.UserMapper;
import com.dualwings.user.po.User;
import com.dualwings.user.service.UserService;
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
	@Autowired
	private UserMapper userMapper;
	
	

}
