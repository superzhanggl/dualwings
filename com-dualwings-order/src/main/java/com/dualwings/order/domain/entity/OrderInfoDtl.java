package com.dualwings.order.domain.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @ClassName OrderInfoDtl
 * @Description 订单详情
 * @author zhanggl
 * @date 2021-6-6
 * @since JDK 1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("mb_petrol_order_dtl")
public class OrderInfoDtl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty("订单编号")
	private String orderId;// 订单编号

	@ApiModelProperty("数据记录者工号")
	private String crtAcct;// 数据记录者工号

	@ApiModelProperty("记录时间")
	private String crtDt;// 记录时间

	@ApiModelProperty("创建方式，0-自主加油，1-系统导入,2-批量导入")
	private String crtType;// 创建方式，0-自主加油，1-系统导入,2-批量导入

	@ApiModelProperty("导入时间")
	private String importDt;// 导入时间

	@ApiModelProperty("导入者")
	private String importAcct;// 导入者

	@ApiModelProperty("修改人")
	private String mdfAcct;// 修改人

	@ApiModelProperty("修改时间")
	private String mdfDt;// 修改时间

	@ApiModelProperty("拓展字段")
	private String extraTxt;// 拓展字段

	@ApiModelProperty("备注")
	private String dtlRemark;// 备注

}
