package com.dualwings.user.service.impl;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dualwings.user.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public boolean saveBatch(Collection entityList, int batchSize) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveOrUpdateBatch(Collection entityList, int batchSize) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateBatchById(Collection entityList, int batchSize) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveOrUpdate(Object entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getOne(Wrapper queryWrapper, boolean throwEx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map getMap(Wrapper queryWrapper) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObj(Wrapper queryWrapper, Function mapper) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseMapper getBaseMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
