package com.dualwings.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dualwings.order.domain.entity.OrderInfo;

public interface OrderInfoService extends IService<OrderInfo> {
	public Integer checkRelation(String usr_id, String sale_id);

	public Integer checkMbAccount(String usr_id);

	public Integer checkSaleInfo(String sale_id);
}
