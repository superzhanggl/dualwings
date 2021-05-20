package com.dualwings.user.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("mb_account")
public class MemberAccount implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	`usr_id` varchar(64) NOT NULL COMMENT '用户编号',
//	  `status` varchar(1) DEFAULT '0' COMMENT '用户状态，0-正常，1-锁定，2-注销',
//	  `crt_acct` varchar(128) DEFAULT NULL COMMENT '创建人',
//	  `crt_dt` datetime DEFAULT NULL COMMENT '创建时间',
//	  `mb_acct_login` varchar(64) DEFAULT NULL COMMENT '登录账号',
//	  `mb_acct_pwd` varchar(64) DEFAULT NULL COMMENT '登录密码',
//	  `phone` varchar(32) DEFAULT NULL COMMENT '手机',
//	  `login_ticket` varchar(128) DEFAULT NULL COMMENT '登录令牌',
//	  `last_login_dt` datetime DEFAULT NULL COMMENT '最后登录时间',
//	  `mdf_acct` varchar(64) DEFAULT NULL COMMENT '修改人',
//	  `last_mdf_pwd_dt` datetime DEFAULT NULL COMMENT '最后修改密码时间',
//	  `login_pwd_count` varchar(2) DEFAULT NULL COMMENT '密码错误次数',
//	  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
//	  `mdf_dt` datetime DEFAULT NULL COMMENT '修改时间',
//	  `extra_txt` varchar(255) DEFAULT NULL COMMENT '拓展字段',
	
	@NonNull
	private String usr_id;
	private String status;
	private String crt_acct;
	private String crt_dt;
	private String mb_acct_login;
	private String mb_acct_pwd;
	private String phone;
	private String login_ticket;
	private String last_login_dt;
	private String mdf_acct;
	private String last_mdf_pwd_dt;
	private String login_pwd_count;
	private String remark;
	private String mdf_dt;
	private String extra_txt;
	

}
