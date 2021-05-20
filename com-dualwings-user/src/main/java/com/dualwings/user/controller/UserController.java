package com.dualwings.user.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dualwings.user.po.User;
import com.dualwings.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
//@FeignClient
@Api(tags="用户管理")
@RequestMapping("api")
public class UserController {
    //@Resource
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;
    
    @ApiOperation(value="获取用户详情信息",notes="说明")
    @ApiImplicitParam(value = "用户编号",name ="id" )
    @RequestMapping("userDtl")
    public User getUserDtl(String userId,User user){
    	
    	QueryWrapper queryWrapper =new QueryWrapper();
    	queryWrapper.eq("user_name", "zhangsan");
    	
    	//userService.getOne(null);
    	System.out.println("UserController"+userService.remove(queryWrapper));
    	
    	System.out.println("UserController"+userService.remove(null));
    	
    	
    	return null;
    }
    
}
