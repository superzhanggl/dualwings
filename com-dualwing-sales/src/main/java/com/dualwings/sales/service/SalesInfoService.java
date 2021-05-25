package com.dualwings.sales.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dualwings.sales.po.SalesInfo;

public interface SalesInfoService extends IService<SalesInfo> {
	public List qryOne();
}
