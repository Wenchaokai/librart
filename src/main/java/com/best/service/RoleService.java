package com.best.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.best.dao.RoleDao;
import com.best.domain.Role;

/**
 * ClassName:RoleService Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
@Service("roleService")
public class RoleService extends BaseService {
	@Autowired
	private RoleDao roleDao;

	@Value("${best.base.memcache.timeout}")
	private Integer memcachedTimeOut;

	public static final String ALL_ROLE_KEY = "ALL_ROLE_KEY";

	@SuppressWarnings("unchecked")
	public List<Role> listAllRole() {
		Object res = null;
		try {
			res = memcachedClient.get(ALL_ROLE_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res == null) {
			res = roleDao.listAllRole();
		}
		if (null != res) {
			try {
				memcachedClient.set(ALL_ROLE_KEY, memcachedTimeOut, res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (null == res)
			return null;
		return (List<Role>) res;
	}

	public void addRole(Role role) {
		try {
			memcachedClient.delete(ALL_ROLE_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		roleDao.addRole(role);
	}

	public Role getRole(int roleId) {
		List<Role> roles = listAllRole();
		for (Role role : roles) {
			if (role.getRoleId() == roleId)
				return role;
		}
		return null;
	}

	public void updateRole(Role role) {
		try {
			memcachedClient.delete(ALL_ROLE_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		roleDao.updateRole(role);
	}
}
