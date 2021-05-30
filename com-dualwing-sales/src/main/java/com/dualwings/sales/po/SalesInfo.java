package com.dualwings.sales.po;

import java.io.Serializable;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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
	private String mbSaleId; // 销售成员编号
	
	@NonNull
	private String saleLvl; // 销售等级，0-初级，1-中级，2-高级
	
	@NonNull
	private String mbSalePid; // 上级销售编号
	
	@NonNull
	private String salePath; // 销售编号路径
	
	@NonNull
	private String mbSaleNm; // 销售名称
	
	@NonNull
	private String mbSalePhone; // 手机号
	
	private String mbSaleArea; // 销售区域
	
	@NonNull
	private String saleStatus; // 状态，0-正常，1-黑名单
	
	private String crtDt; // 创建时间
	
	private String mdfAcct; // 修改人
	
	private String mdfDt; // 修改时间
	
	private String crtAcct; // 创建人
	
	private String remark; // 备注
	
	private String extraTxt; // 拓展字段
	@TableField(exist = false)
	private Map<String,Object> pmapMess;

}
