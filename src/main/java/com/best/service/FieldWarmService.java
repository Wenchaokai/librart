package com.best.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.best.dao.FieldWarmDao;
import com.best.domain.FieldWarm;

/**
 * ClassName:FieldWarmService Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-31
 */
@Service("fieldWarmService")
public class FieldWarmService extends BaseService {

	@Autowired
	private FieldWarmDao fieldWarmDao;

	@Value("${best.base.memcache.timeout}")
	private Integer memcachedTimeOut;

	public static final String ALL_FIELD_WARM_KEY = "ALL_FIELD_WARM_KEY";

	@SuppressWarnings("unchecked")
	public List<FieldWarm> listAllFieldWarm() {
		Object res = null;
		try {
			res = memcachedClient.get(ALL_FIELD_WARM_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res == null) {
			res = fieldWarmDao.listAllFieldWarm();
		}
		if (null != res) {
			try {
				memcachedClient.set(ALL_FIELD_WARM_KEY, memcachedTimeOut, res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (null == res)
			return null;
		return (List<FieldWarm>) res;
	}

	public void addFieldWarm(FieldWarm fieldWarm) {
		try {
			memcachedClient.delete(ALL_FIELD_WARM_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		fieldWarmDao.addFieldWarm(fieldWarm);
	}

	public void deleteFieldWarmByProjectId(int projectId) {
		try {
			memcachedClient.delete(ALL_FIELD_WARM_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		fieldWarmDao.deleteFieldWarmByProjectId(projectId);
	}

	public void deleteFieldWarmById(int id) {
		try {
			memcachedClient.delete(ALL_FIELD_WARM_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		fieldWarmDao.deleteFieldWarmById(id);
	}

	public FieldWarm getFieldWarm(int fieldWarmId) {
		List<FieldWarm> fieldWarms = listAllFieldWarm();
		for (FieldWarm fieldWarm : fieldWarms) {
			if (fieldWarm.getFieldWarmId() == fieldWarmId)
				return fieldWarm;
		}
		return null;
	}

	public void updateFieldWarm(FieldWarm fieldWarm) {
		try {
			memcachedClient.delete(ALL_FIELD_WARM_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		fieldWarmDao.updateFieldWarm(fieldWarm);
	}

	public List<FieldWarm> listFieldWarm(int projectId) {
		List<FieldWarm> allFieldWarms = listAllFieldWarm();
		List<FieldWarm> res = new ArrayList<FieldWarm>();
		for (FieldWarm fieldWarm : allFieldWarms) {
			if (fieldWarm.getFieldWarmProjectId() == projectId) {
				res.add(fieldWarm);
			}
		}
		return res;
	}

	public List<FieldWarm> listFieldWarmEnabled(int projectId) {
		List<FieldWarm> allFieldWarms = listFieldWarm(projectId);
		List<FieldWarm> res = new ArrayList<FieldWarm>();
		for (FieldWarm fieldWarm : allFieldWarms) {
			if (fieldWarm.getFieldWarmEnabled() == 1) {
				res.add(fieldWarm);
			}
		}
		return res;
	}

	public List<FieldWarm> listFieldWarmEnabled(int projectId, Set<Integer> filterIds) {
		List<FieldWarm> allFieldWarms = listAllFieldWarm();
		List<FieldWarm> res = new ArrayList<FieldWarm>();
		for (FieldWarm fieldWarm : allFieldWarms) {
			if (fieldWarm.getFieldWarmProjectId() == projectId && fieldWarm.getFieldWarmEnabled() == 1
					&& filterIds.contains(fieldWarm.getFieldWarmFieldId())) {
				res.add(fieldWarm);
			}
		}
		return res;
	}

	public List<FieldWarm> listAllFieldWarmEnabled() {
		List<FieldWarm> allFieldWarms = listAllFieldWarm();
		List<FieldWarm> res = new ArrayList<FieldWarm>();
		for (FieldWarm fieldWarm : allFieldWarms) {
			if (fieldWarm.getFieldWarmEnabled() == 1)
				res.add(fieldWarm);
		}
		return res;
	}

}
