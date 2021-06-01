package com.dualwings.basic.domain.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("sys_role_menu")
public class SysRoleMenu implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@TableId(type= IdType.ASSIGN_ID)
	@ApiModelProperty("角色编号")
	private String sysRoleId;// 角色编号
	@NonNull
	@ApiModelProperty("菜单编号")
	private String sysMeunId;// 菜单编号
	
	@ApiModelProperty("创建人")
	private String crtAcct;// 创建人
	
	@ApiModelProperty("创建时间")
	private String crtDt;// 创建时间
	
	@ApiModelProperty("修改时间")
	private String mdfDt;// 修改时间
	
	@ApiModelProperty("修改人")
	private String mdfAcct;// 修改人
	
	@ApiModelProperty("拓展字段")
	private String extraTxt;// 拓展字段
	
}
