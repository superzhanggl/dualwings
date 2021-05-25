package com.dualwings.sales.dto;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dualwings.sales.po.SalesInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("销售成员信息类")
public class QryDataInfo implements Serializable{
	@ApiModelProperty("销售成员编号")
	private String mbSaleId; // 销售成员编号
	
	@ApiModelProperty("销售等级，0-初级，1-中级，2-高级")
	private String saleLvl; // 销售等级，0-初级，1-中级，2-高级
	
	@ApiModelProperty("上级销售编号")
	private String mbSalePid; // 上级销售编号
	
	@ApiModelProperty("销售编号路径")
	private String salePath; // 销售编号路径
	
	@ApiModelProperty("销售名称")
	private String mbSaleNm; // 销售名称
	
	@ApiModelProperty("手机号")
	private String mbSalePhone; // 手机号
	
	@ApiModelProperty("销售区域")
	private String mbSaleArea; // 销售区域
	
	@ApiModelProperty("状态，0-正常，1-黑名单")
	private String saleStatus; // 状态，0-正常，1-黑名单
	
	@ApiModelProperty("创建时间")
	private String crtDt; // 创建时间
	
	@ApiModelProperty("修改人")
	private String mdfAcct; // 修改人
	
	@ApiModelProperty("修改时间")
	private String mdfDt; // 修改时间
	
	@ApiModelProperty("创建人")
	private String crtAcct; // 创建人
	
	@ApiModelProperty("备注")
	private String remark; // 备注
	
	@ApiModelProperty("拓展字段")
	private String extraTxt; // 拓展字段
	
	private boolean flag;
	
}
