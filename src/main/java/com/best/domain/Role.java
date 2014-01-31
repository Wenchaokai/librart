package com.best.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * ClassName:Role Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
public class Role implements Serializable {

	private static final long serialVersionUID = -4716691258181017709L;

	private Integer roleId;
	private String roleName;
	private String roleDesc;
	private String rolePrivilieges;
	private String gmtCreate;
	private boolean checked;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRolePrivilieges() {
		return rolePrivilieges;
	}

	public void setRolePrivilieges(String rolePrivilieges) {
		this.rolePrivilieges = rolePrivilieges;
	}

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Set<Integer> getPriviliege() {
		Set<Integer> res = new HashSet<Integer>();
		if (StringUtils.isNotBlank(this.rolePrivilieges)) {
			String[] parts = this.rolePrivilieges.split(",");
			for (String rolePriviliegeId : parts) {
				res.add(Integer.parseInt(rolePriviliegeId));
			}
		}
		return res;
	}
}
