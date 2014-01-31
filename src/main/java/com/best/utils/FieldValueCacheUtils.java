package com.best.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.best.domain.wms.Asn;
import com.best.domain.wms.AsnItem;
import com.best.domain.wms.Ldo;
import com.best.domain.wms.LdoItem;
import com.best.domain.wms.SystemField;

/**
 * ClassName:FieldValueUtils Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-1-31
 */
public class FieldValueCacheUtils {

	public static Map<String, Map<String, Field>> fieldCaches = new HashMap<String, Map<String, Field>>();

	public synchronized static Object getFieldValue(String fieldName, Object obj) throws IllegalArgumentException,
			IllegalAccessException {
		String objName = obj.getClass().getName();
		Map<String, Field> fields = fieldCaches.get(objName);
		if (fields == null) {
			initFieldCache(obj);
		}

		fields = fieldCaches.get(objName);
		if (fields == null) {
			return "";
		}

		Field field = fields.get(fieldName);
		if (field == null)
			return "";

		if (field.getType().getName().equals(java.lang.String.class.getName())) {
			String value = "";
			value = (String) field.get(obj);
			return value == null ? "" : value;
		} else if (field.getType().getName().equals(java.lang.Integer.class.getName())) {
			Integer value = 0;
			try {
				value = (Integer) field.get(obj);
			} catch (Exception e) {

			}
			return value == null ? 0 : value;
		} else if (field.getType().getName().equals(java.lang.Float.class.getName())) {
			Float value = 0f;
			try {
				value = (Float) field.get(obj);
			} catch (Exception e) {

			}
			return value == null ? 0f : value;
		} else if (field.getType().getName().equals(java.lang.Long.class.getName())) {
			Long value = 0L;
			try {
				value = (Long) field.get(obj);
			} catch (Exception e) {
				return 0;
			}
			return value == null ? 0L : value;
		}
		return "";
	}

	private static synchronized void initFieldCache(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		Map<String, Field> fieldMap = new HashMap<String, Field>();
		for (Field field : fields) {
			field.setAccessible(true);
			fieldMap.put(field.getName(), field);
		}

		fieldCaches.put(obj.getClass().getName(), fieldMap);
	}

	public static List<SystemField> getNumberSystemFields() {
		List<SystemField> res = new ArrayList<SystemField>();
		res.addAll(Ldo.getSystemFields(true));
		res.addAll(LdoItem.getSystemFields(true));
		res.addAll(Asn.getSystemFields(true));
		res.addAll(AsnItem.getSystemFields(true));
		return res;
	}

	public static List<SystemField> getAllSystemFields() {
		List<SystemField> res = new ArrayList<SystemField>();
		res.addAll(Ldo.getSystemFields(false));
		res.addAll(LdoItem.getSystemFields(false));
		res.addAll(Asn.getSystemFields(false));
		res.addAll(AsnItem.getSystemFields(false));
		return res;
	}

}
