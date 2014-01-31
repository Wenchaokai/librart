package com.best.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.best.dao.PriviliegeDao;
import com.best.domain.Priviliege;

/**
 * ClassName:PriviliegeService Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
@Service("priviliegeService")
public class PriviliegeService extends BaseService {

	@Autowired
	private PriviliegeDao priviliegeDao;

	@Value("${best.base.memcache.timeout}")
	private Integer memcachedTimeOut;

	public static final String ALL_PRIVILIEGE_KEY = "ALL_PRIVILIEGE_KEY";

	@SuppressWarnings("unchecked")
	public List<Priviliege> listAllPriviliege() {
		Object res = null;
		try {
			res = memcachedClient.get(ALL_PRIVILIEGE_KEY);
		} catch (Exception e) {
		}
		if (res == null) {
			res = priviliegeDao.listAllPriviliege();
		}
		if (null != res) {
			try {
				memcachedClient.set(ALL_PRIVILIEGE_KEY, memcachedTimeOut, res);
			} catch (Exception e) {
			}
		}

		if (null == res)
			return null;
		return (List<Priviliege>) res;
	}

	public List<Priviliege> searchPriviliege(String name) {
		List<Priviliege> allPrivilieges = listAllPriviliege();
		List<Priviliege> res = new ArrayList<Priviliege>();
		for (Priviliege priviliege : allPrivilieges) {
			if (priviliege.getPriviliegeName().contains(name)) {
				res.add(priviliege);
			}
		}
		return res;

	}

}
