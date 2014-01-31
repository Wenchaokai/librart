package com.best.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.best.domain.Role;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * ClassName:RoleDao Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
@Repository("roleDao")
public class RoleDao extends BaseDao {

	@Resource(name = "sqlMapClient")
	protected SqlMapClient sqlMapClient;

	private static final String space = "roleSpace.";

	@SuppressWarnings("unchecked")
	public List<Role> listAllRole() {
		return (List<Role>) this.list(space + "GET_ALL_ROLE", sqlMapClient);
	}

	public void addRole(Role role) {
		this.insert(space + "ADD_ROLE", role, sqlMapClient);
	}

	public void updateRole(Role role) {
		this.update(space + "UPDATE_ROLE", role, sqlMapClient);
	}
}
