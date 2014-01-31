package com.best.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.best.dao.FieldDao;
import com.best.domain.Field;

/**
 * ClassName:FieldService Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-31
 */
@Service("fieldService")
public class FieldService extends BaseService {

	@Autowired
	private FieldDao fieldDao;

	@Value("${best.base.memcache.timeout}")
	private Integer memcachedTimeOut;

	public static final String ALL_SYSTEM_FIELD_KEY = "ALL_SYSTEM_FIELD_KEY";
	public static final String ALL_TYPE_IN_FIELD_KEY = "ALL_TYPE_IN_FIELD_KEY";
	public static final String ALL_DERIVE_FIELD_KEY = "ALL_DERIVE_FIELD_KEY";

	public List<Field> listAllSystemField() {
		Object res = null;
		try {
			res = memcachedClient.get(ALL_SYSTEM_FIELD_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res == null) {
			res = fieldDao.listAllField(1);
		}
		if (null != res) {
			try {
				memcachedClient.set(ALL_SYSTEM_FIELD_KEY, memcachedTimeOut, res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (null == res)
			return null;
		return (List<Field>) res;
	}

	public List<Field> listAllTypeInField() {
		Object res = null;
		try {
			res = memcachedClient.get(ALL_TYPE_IN_FIELD_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res == null) {
			res = fieldDao.listAllField(2);
		}
		if (null != res) {
			try {
				memcachedClient.set(ALL_TYPE_IN_FIELD_KEY, memcachedTimeOut, res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (null == res)
			return null;
		return (List<Field>) res;
	}

	public List<Field> listAllDeriveField() {
		Object res = null;
		try {
			res = memcachedClient.get(ALL_DERIVE_FIELD_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res == null) {
			res = fieldDao.listAllField(3);
		}
		if (null != res) {
			try {
				memcachedClient.set(ALL_DERIVE_FIELD_KEY, memcachedTimeOut, res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (null == res)
			return null;
		return (List<Field>) res;
	}

	public void addField(Field field) {
		try {
			memcachedClient.delete(ALL_TYPE_IN_FIELD_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		fieldDao.addField(field);
	}

	public Field getField(int fieldId) {
		List<Field> fields = listAllTypeInField();
		for (Field field : fields) {
			if (field.getFieldId() == fieldId)
				return field;
		}
		fields = listAllSystemField();
		for (Field field : fields) {
			if (field.getFieldId() == fieldId)
				return field;
		}
		fields = listAllDeriveField();
		for (Field field : fields) {
			if (field.getFieldId() == fieldId)
				return field;
		}
		return null;
	}

	public void updateField(Field field) {
		try {
			memcachedClient.delete(ALL_TYPE_IN_FIELD_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		fieldDao.updateField(field);
	}

}
