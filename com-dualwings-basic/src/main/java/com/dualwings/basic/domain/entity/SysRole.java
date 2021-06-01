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
@TableName("sys_role_info")
public class SysRole  implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId(type= IdType.ASSIGN_ID)
	@ApiModelProperty("角色编号")
	private String roleId;// 角色编号
	@NonNull
	@ApiModelProperty("角色名称")
	private String roleNm;// 角色名称
	
	@ApiModelProperty("角色描述")
	private String roleDesc;// 角色描述
	@NonNull
	@ApiModelProperty("角色用途，0-运营端，1-app")
	private String type;//角色用途，0-运营端，1-app
	@NonNull
	@ApiModelProperty("是否启用，0-启用，1-停用")
	private String status;// 是否启用，0-启用，1-停用
	
	@ApiModelProperty("创建人")
	private String crtAcct;// 创建人
	
	@ApiModelProperty("创建时间")
	private String crtDt;// 创建时间
	
	@ApiModelProperty("备注")
	private String remark;// 备注
	
	@ApiModelProperty("拓展字段1")
	private String extraTxt1;// 拓展字段1
	
	@ApiModelProperty("拓展字段2")
	private String extraTxt2;// 拓展字段2
	
	@ApiModelProperty("修改时间")
	private String mdfDt;// 修改时间
	
	@ApiModelProperty("修改人")
	private String mdfAcct;// 修改人
	
	
	
}
