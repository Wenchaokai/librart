package com.best.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.best.domain.User;
import com.best.utils.CommonUtils;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * ClassName:UserDao Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-8-27
 */
@Repository("userDao")
public class UserDao extends BaseDao {

	@Resource(name = "sqlMapClient")
	protected SqlMapClient sqlMapClient;

	private static final String space = "userSpace.";

	public List<User> listAllUsers() {
		return (List<User>) list(space + "LIST_ALL_USERS", sqlMapClient);
	}

	public Object insertUser(User user) {
		return insert(space + "INSERT_USER", user, sqlMapClient);
	}

	public int updateUserInfo(User user) {
		return update(space + "UPDATE_USER_INFO", user, sqlMapClient);
	}

	public int updateUserPassword(Long userId, String oldPassword, String newPassword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("oldUserPsw", oldPassword);
		map.put("newUserPsw", newPassword);
		return update(space + "UPDATE_USER_PASSWORD", map, sqlMapClient);
	}

	public Object deleteUser(Long userId) {
		return delete(space + "DELETE_USER", userId, sqlMapClient);
	}

	public User checkUser(User user) {
		Object res = object(space + "CHECK_USER", user, sqlMapClient);
		if (null == res || !(res instanceof User)) {
			return null;
		}
		return (User) res;
	}

	@SuppressWarnings("unchecked")
	public List<User> findUser(User user) {
		return (List<User>) list(space + "GET_USER", user, sqlMapClient);
	}

	public Object findUserById(Long userId) {
		return this.object(space + "GET_USER_BY_ID", userId, sqlMapClient);
	}

	public Object findUserByName(String userName) {
		return this.object(space + "GET_USER_BY_NAME", userName, sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	public List<User> findUserByPageSize(String searchName, String searchEnabled, Integer startIndex) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		String selectString = "GET_USER_BY_PAGESIZE";
		if (StringUtils.isNotBlank(searchName))
			map.put("searchName", "%" + searchName + "%");
		if (StringUtils.isNotBlank(searchEnabled))
			map.put("searchEnabled", Integer.parseInt(searchEnabled));

		map.put("pageSize", pageSize);
		map.put("startIndex", startIndex);
		return (List<User>) this.list(space + selectString, map, sqlMapClient);
	}

	public Integer findUserTotalSize(String searchName, String searchEnabled) {
		Map<String, Object> map = new HashMap<String, Object>();
		String selectString = "GET_USER_BY_TOTALSIZE";
		if (StringUtils.isNotBlank(searchName))
			map.put("searchName", "%" + searchName + "%");
		if (StringUtils.isNotBlank(searchEnabled))
			map.put("searchEnabled", Integer.parseInt(searchEnabled));

		Integer res = (Integer) this.object(space + selectString, map, sqlMapClient);
		if (res % pageSize == 0)
			return res / pageSize;
		return res / pageSize + 1;
	}

	public Integer checkUserCountExisted(String userCount) {
		return (Integer) this.object(space + "CHECKED_USER_COUNT", userCount, sqlMapClient);
	}

	public void updateMember(User user) {
		this.update(space + "UPDATE_USER_INFO", user, sqlMapClient);
	}

	public void resetUserPassword(long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("newUserPsw", CommonUtils.encoder("Abc123"));
		map.put("userId", userId);
		this.update(space + "USER_PASSWORD_RESET", map, sqlMapClient);
	}

}