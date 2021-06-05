package com.dualwings.sales.domain.entity;

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
@TableName("sys_sale_settle_param")
public class SalesSettleParams implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty("编号")
	private String uId;// 编号

	@ApiModelProperty("系数值")
	private String paramValue;// 系数值

	@ApiModelProperty("系数所属等级")
	private String paramLvl;// 系数所属等级

	@ApiModelProperty("系数类型，0-自我提升，2-下级团队提升")
	private String paramType;// 系数类型，0-自我提升，2-下级团队提升

	@ApiModelProperty("创建时间")
	private String crtDt;// 创建时间

	@ApiModelProperty("创建人")
	private String crtAcct;// 创建人
	@ApiModelProperty("状态，0-正常，1-弃用")
	private String status;// 状态，0-正常，1-弃用

	@ApiModelProperty("修改人")
	private String mdfAcct;// 修改人

	@ApiModelProperty("修改时间")
	private String mdfDt;// 修改时间

	@ApiModelProperty("备注")
	private String remark;// 备注

	@ApiModelProperty("拓展字段")
	private String extraTxt;// 拓展字段

}
