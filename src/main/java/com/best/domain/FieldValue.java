package com.best.domain;

/**
 * ClassName:FieldValue Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-2-2
 */
public class FieldValue implements Comparable<FieldValue> {
	public String fieldInfo = "";

	public String fieldDesc = "";

	public static final int LEFT_TYPE = 1;
	public static final int EQUAL_TYPE = 2;
	public static final int RIGHT_TYPE = 3;
	public static final int LEFT_EQUAL_TYPE = 4;
	public static final int RIGHT_EQUAL_TYPE = 5;

	public int compare = 1;

	public String fieldType = "string";

	public String value = "";

	@Override
	public String toString() {
		return (fieldInfo == null ? "" : fieldInfo) + "$" + compare + "$" + value;
	}

	public static FieldValue parseFieldValue(String fieldString) {
		if (null == fieldString)
			return null;
		String[] params = fieldString.split("\\$");
		FieldValue field = new FieldValue();
		field.fieldInfo = params[0];
		field.compare = Integer.parseInt(params[1]);
		field.value = params[2];
		return field;
	}

	public String getCompareString() {
		if (this.compare == LEFT_TYPE)
			return "<";
		else if (this.compare == EQUAL_TYPE)
			return "=";
		else if (this.compare == RIGHT_EQUAL_TYPE)
			return ">=";
		if (this.compare == LEFT_EQUAL_TYPE)
			return "<=";
		else
			return ">";
	}

	public int getCompare() {
		return compare;
	}

	public void setCompare(int compare) {
		this.compare = compare;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(String fieldInfo) {
		this.fieldInfo = fieldInfo;
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	@Override
	public int compareTo(FieldValue o) {
		if (o.value == null)
			return 1;
		if (this.value == null)
			return -1;
		if ("string".equals(fieldType) || "date".equals(fieldType))
			return this.value.compareTo(o.value);
		else if ("int".equals(fieldType)) {
			return Integer.parseInt(o.value) >= Integer.parseInt(this.value) ? -1 : 1;
		} else if ("long".equals(fieldType)) {
			return Long.parseLong(o.value) >= Long.parseLong(this.value) ? -1 : 1;
		} else if ("double".equals(fieldType)) {
			return Double.parseDouble(o.value) >= Double.parseDouble(this.value) ? -1 : 1;
		} else
			return -1;

	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public boolean equalsFieldValue(Object obj) {
		if (fieldInfo.equals(obj.toString()))
			return true;
		return false;
	}

	public boolean equalsFieldValue(String className, String fieldName) {
		if (fieldInfo.equals(className + "-" + fieldName))
			return true;
		return false;
	}

}
