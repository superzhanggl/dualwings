package com.dualwings.sales.domain.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_sale_info")
public class SalesInfo implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId(type= IdType.ASSIGN_ID)
	@ApiModelProperty("销售成员编号")
	private String mbSaleId; // 销售成员编号
	
	@NonNull
	@ApiModelProperty("销售等级，0-初级，1-中级，2-高级")
	private String saleLvl; // 销售等级，0-初级，1-中级，2-高级
	
	@ApiModelProperty("上级销售编号")
	private String mbSalePid; // 上级销售编号
	
	@NonNull
	@ApiModelProperty("销售编号路径")
	private String salePath; // 销售编号路径
	
	@NonNull
	@ApiModelProperty("销售名称")
	private String mbSaleNm; // 销售名称
	
	@NonNull
	@ApiModelProperty("手机号")
	private String mbSalePhone; // 手机号
	@ApiModelProperty("销售区域")
	private String mbSaleArea; // 销售区域
	
	@NonNull
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
	
	
	@TableField(exist = false)
	@ApiModelProperty("上级销售信息")
	private Map<String,Object> pmapMess;
	
	@TableField(exist = false)
	@ApiModelProperty("下级销售列表,树形结构")
	private List<SalesInfo> children;// 下级销售列表
}
