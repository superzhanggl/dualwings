package com.dualwings.basic.domain.entity;

import java.io.Serializable;
import java.util.List;

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
@TableName("sys_menu")
public class SysMenu implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@TableId(type= IdType.ASSIGN_ID)
	@ApiModelProperty(" 菜单编号")
	private String menuId;// 菜单编号
	
	@ApiModelProperty("菜单操作码")
	private String menuCode;// 菜单操作码
	
	@NonNull
	@ApiModelProperty("菜单名称")
	private String menuNm;// 菜单名称
	
	@ApiModelProperty("上级菜单编号")
	private String menuPid;// 上级菜单编号
	
	@ApiModelProperty("菜单分类，0-分类，1-功能，2-操作")
	private String type;// 菜单分类，0-分类，1-功能，2-操作
	
	@NonNull
	@ApiModelProperty("使用渠道，0-运营端，1-app")
	private String useChnl;// 使用渠道，0-运营端，1-app
	
	@ApiModelProperty("是否影藏，0-显示，1-隐藏")
	private String disable;// 是否影藏，0-显示，1-隐藏
	
	@ApiModelProperty("排序")
	private String sortNm;// 排序
	
	@ApiModelProperty("创建人")
	private String crtAcct;// 创建人
	
	@ApiModelProperty("创建时间")
	private String crtDt;// 创建时间
	
	@ApiModelProperty("修改人")
	private String mdfAcct;// 修改人
	
	@ApiModelProperty("修改时间")
	private String mdfDt;// 修改时间
	
	@ApiModelProperty("拓展字段")
	private String extraTxt;// 拓展字段
	@TableField(exist = false)
	private List<SysMenu> children;// 子菜单
		
	
}
