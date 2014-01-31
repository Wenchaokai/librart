package com.best.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * ClassName:User Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-8-27
 */
public class User implements Serializable {

	private static final long serialVersionUID = -398744926531951923L;

	private Long userId;
	private String loginName;
	private String userName;
	private String userPsw;
	private String userRole;
	private String userProject;
	private String userPerson;
	private String userEmail;
	private Integer userEnable;
	private Integer messageSize;
	private String roleName;
	private Boolean checked;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPsw() {
		return userPsw;
	}

	public void setUserPsw(String userPsw) {
		this.userPsw = userPsw;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserProject() {
		return userProject;
	}

	public void setUserProject(String userProject) {
		this.userProject = userProject;
	}

	public String getUserPerson() {
		return userPerson;
	}

	public void setUserPerson(String userPerson) {
		this.userPerson = userPerson;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Integer getUserEnable() {
		return userEnable;
	}

	public void setUserEnable(Integer userEnable) {
		this.userEnable = userEnable;
	}

	public Set<Integer> getUserRoles() {
		if (StringUtils.isEmpty(this.userRole))
			return new HashSet<Integer>();
		String[] parts = this.userRole.split(",");
		Set<Integer> userRoles = new HashSet<Integer>();
		for (String role : parts) {
			userRoles.add(Integer.parseInt(role));
		}
		return userRoles;
	}

	public Set<Integer> getUserProjects() {
		if (StringUtils.isEmpty(this.userProject))
			return new HashSet<Integer>();
		String[] parts = this.userProject.split(",");
		Set<Integer> userProjects = new HashSet<Integer>();
		for (String project : parts) {
			userProjects.add(Integer.parseInt(project.split("#")[0]));
		}
		return userProjects;
	}

	public Integer getMessageSize() {
		return messageSize == null ? 0 : messageSize;
	}

	public void setMessageSize(Integer messageSize) {
		this.messageSize = messageSize;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	private Set<Integer> userPrivliages;

	public Set<Integer> getUserPrivliages() {
		return userPrivliages == null ? new HashSet<Integer>() : userPrivliages;
	}

	public void setUserPrivliages(Set<Integer> userPrivliages) {
		this.userPrivliages = userPrivliages;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getEmailPrefix() {
		if (StringUtils.isNotEmpty(this.userEmail))
			return this.userEmail.split("@")[0];
		return "";
	}

}
