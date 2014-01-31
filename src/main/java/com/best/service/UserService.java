package com.best.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.exception.MemcachedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.best.dao.UserDao;
import com.best.domain.User;
import com.best.utils.CommonUtils;

/**
 * ClassName:UserService Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-8-27
 */
@Service("userService")
public class UserService extends BaseService {

	@Autowired
	private UserDao userDao;

	@Value("${best.base.memcache.timeout}")
	private Integer memcachedTimeOut;

	public static final String ALL_USER_KEY = "ALL_USER_KEY";

	public User logging(User user) {
		user.setUserPsw(CommonUtils.encoder(user.getUserPsw()));
		user = userDao.checkUser(user);
		if (user != null) {
			// 写进MemcachedClient
			try {
				memcachedClient.addWithNoReply(user.getUserName(), 0, user);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (MemcachedException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	public int updateUserPassword(Long userId, String oldPassword, String newPassword) {
		oldPassword = CommonUtils.encoder(oldPassword);
		newPassword = CommonUtils.encoder(newPassword);
		int modifyCount = userDao.updateUserPassword(userId, oldPassword, newPassword);
		return modifyCount;
	}

	public List<User> getSearchUsers(String searchLoginName, String searchEnabled, Integer page) throws TimeoutException,
			InterruptedException, MemcachedException {
		List<User> res = null;
		// 从缓存读取数据
		// String key = "";
		// if (StringUtils.isNotBlank(searchName))
		// key = searchName;
		// key += "-";
		// if (StringUtils.isNotBlank(searchCount))
		// key += searchCount;
		// res = memcachedClient.get(key);
		int start = page * userDao.pageSize;

		res = userDao.findUserByPageSize(searchLoginName, searchEnabled, start);

		// memcachedClient.add(key, 0, res);

		if (res == null) {
			return new ArrayList<User>();
		}
		// return res.subList(0, res.size() > end ? end : res.size());
		return res;

	}

	public int getTotalSize(String searchName, String searchEnabled) throws TimeoutException, InterruptedException,
			MemcachedException {
		Integer res = null;

		res = userDao.findUserTotalSize(searchName, searchEnabled);

		return res;
	}

	public User addMember(User user) {
		try {
			memcachedClient.delete(ALL_USER_KEY);
		} catch (Exception e) {
		}
		user.setUserPsw(CommonUtils.encoder(user.getUserPsw()));
		user = (User) userDao.insertUser(user);
		return user;
	}

	public void deleteMember(String userIdString) {
		try {
			memcachedClient.delete(ALL_USER_KEY);
		} catch (Exception e) {
		}
		Long userId = Long.parseLong(userIdString);
		User user = getUser(userId);
		if (user == null)
			return;
		userDao.deleteUser(userId);

	}

	public Integer checkUserCountExisted(String userCount) {
		return userDao.checkUserCountExisted(userCount);
	}

	public List<User> listAllUsers() {
		Object obj = null;
		try {
			obj = memcachedClient.get(ALL_USER_KEY);
		} catch (Exception e) {
		}

		if (obj == null) {
			obj = userDao.listAllUsers();
			if (null != obj) {
				try {
					memcachedClient.set(ALL_USER_KEY, 30 * 60 * 60, obj);
				} catch (Exception e) {
				}
			}
		}

		return (List<User>) obj;
	}

	/**
	 * 
	 * @param userCount
	 * @return
	 */
	public User getUser(Long userId) {
		return (User) userDao.findUserById(userId);
	}

	public void updateMember(User user) {
		try {
			memcachedClient.delete(ALL_USER_KEY);
		} catch (Exception e) {
		}
		user.setUserPsw(CommonUtils.encoder(user.getUserPsw()));
		userDao.updateMember(user);
	}

	public void resetUserPassword(long userId) {
		userDao.resetUserPassword(userId);
	}

}
