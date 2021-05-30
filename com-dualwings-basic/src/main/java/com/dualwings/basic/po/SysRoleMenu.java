package com.dualwings.basic.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_menu")
public class SysRoleMenu implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sysRoleId;// 角色编号
	private String sysMeunId;// 菜单编号
	private String crtAcct;// 创建人
	private String crtDt;// 创建人
	private String mdfDt;// 修改时间
	private String mdfAcct;// 修改人
	private String extraTxt;// 拓展字段
	
}
