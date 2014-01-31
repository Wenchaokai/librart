package com.best.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * ClassName:FieldType Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-2-2
 */
public class FieldType {

	public static final Integer SYSTEM_DEFAULT = 1;
	public static final Integer CUSTOMER_TYPE = 2;

	private Integer fieldType = 1;

	private String fieldInfo = "";

	private String fieldDesc = "";

	private Double fieldValue = 0.0;

	List<FieldValue> fields = new ArrayList<FieldValue>();

	public Integer getFieldType() {
		return fieldType;
	}

	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}

	public List<FieldValue> getFields() {
		return fields;
	}

	public void setFields(List<FieldValue> fields) {
		if (null != fields)
			Collections.sort(fields);
		this.fields = fields;
	}

	public String getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(String fieldInfo) {
		this.fieldInfo = fieldInfo;
	}

	public static List<FieldType> parseFieldString(String fieldTypeString) {
		if (null == fieldTypeString)
			return new ArrayList<FieldType>();
		String[] fieldParams = fieldTypeString.split("\\|");
		List<FieldType> res = new ArrayList<FieldType>();
		for (String fieldParam : fieldParams) {
			String[] params = fieldParam.split("#");
			if (params.length < 2)
				continue;
			Integer type = Integer.parseInt(params[0]);
			FieldType fieldType = new FieldType();
			fieldType.setFieldType(type);
			fieldType.setFieldInfo(params[1]);
			if (type == CUSTOMER_TYPE) {
				int fieldSize = Integer.parseInt(params[2]);
				List<FieldValue> fieldValues = new ArrayList<FieldValue>();
				for (int index = 0; index < fieldSize; index++) {
					try {
						FieldValue fieldValue = FieldValue.parseFieldValue(params[index + 3]);
						if (null == fieldValue)
							continue;
						fieldValues.add(fieldValue);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				fieldType.setFields(fieldValues);
			}
			res.add(fieldType);
		}
		return res;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(fieldType);
		builder.append("#");
		builder.append(fieldInfo);
		if (fieldType == CUSTOMER_TYPE) {
			// 自定义
			builder.append("#");
			builder.append(fields.size());
			for (FieldValue fieldValue : fields) {
				builder.append("#");
				builder.append(fieldValue.toString());
			}
		}
		return builder.toString();
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public Double getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(Double fieldValue) {
		this.fieldValue = fieldValue;
	}

	public void addFieldValue(Double fieldValue) {
		if (this.fieldValue == null)
			this.fieldValue = 0.0;
		if (fieldValue != null)
			this.fieldValue += fieldValue;
	}

	public boolean equalsFieldType(Object obj) {
		if (fieldInfo.equals(obj.toString()))
			return true;
		return false;
	}

	public Double getFieldWarmValue(String dateTime) {
		if (values.containsKey(dateTime))
			return values.get(dateTime);
		return 0.0;
	}

	private Map<String, Double> values = new HashMap<String, Double>();

	public void setFieldWarmValue(String value) {
		if (StringUtils.isNotBlank(value)) {
			String[] params = value.split("\\|");
			for (String param : params) {
				String tmp[] = param.split("\\$");
				try {
					values.put(tmp[0], Double.parseDouble(tmp[1].trim()));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}
}
