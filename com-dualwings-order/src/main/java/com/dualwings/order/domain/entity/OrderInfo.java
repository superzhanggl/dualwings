package com.dualwings.order.domain.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("mb_petrol_order")
public class OrderInfo implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty("订单编号")
	private String orderId;// 订单编号
	
	@ApiModelProperty("用户编号")
	private String usrId;// 用户编号

	@ApiModelProperty("加油量")
	private String petrolAmount;// 加油量

	@ApiModelProperty("石油种类")
	private String petrolCate;// 石油种类

	@ApiModelProperty("加油状态;0-成功，1-异常，2-失败")
	private String petrolStatus;// 加油状态;0-成功，1-异常，2-失败

	@ApiModelProperty("订单日期")
	private String orderDt;// 订单日期

	@ApiModelProperty("订单状态")
	private String orderStatus;// 订单状态

	@ApiModelProperty("订单直属销售编号")
	private String bindSaleId;// 订单直属销售编号

	@ApiModelProperty("结算状态，0-待结算，1-结算成功，2-结算异常")
	private String settleStatus;// 结算状态，0-待结算，1-结算成功，2-结算异常

	@ApiModelProperty("结算时间")
	private String settleDt;// 结算时间

	@ApiModelProperty("结算销售流水编号，只有直属销售需要记录编号，下级团队的销售结算编号不记录")
	private String settleSaleFlowId;// 结算销售流水编号，只有直属销售需要记录编号，下级团队的销售结算编号不记录

	@ApiModelProperty("结算人")
	private String settleCreator;// 结算人

	@ApiModelProperty("备注")
	private String remark;// 备注

}
