package com.dualwings.basic.domain.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_info")
public class SysRole implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TableId(value = "role_id", type = IdType.AUTO)
	private String roleId;// 角色编号
	private String roleNm;// 角色名称
	private String roleDesc;// 角色描述
	private String type;//角色用途，0-运营端，1-app
	private String status;// 是否启用，0-启用，1-停用
	private String crtAcct;// 创建人
	private String crtDt;// 创建时间
	private String remark;// 备注
	private String extraTxt1;// 拓展字段1
	private String extraTxt2;// 拓展字段2
	private String mdfDt;// 修改时间
	private String mdfAcct;// 修改人
	
	
	
}
