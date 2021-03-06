package com.dualwings.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dualwings.user.po.User;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
