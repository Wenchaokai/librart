package com.best.domain.wms;

/**
 * ClassName:SystemField Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-1-31
 */
public class SystemField {

	public static final String LONG_TYPE = "long";
	public static final String INT_TYPE = "int";
	public static final String STRING_TYPE = "string";

	private String classIdentify;

	private String fieldName;

	private String fieldDesc;

	private String fieldType;

	private String mappingField;

	private Double fieldValue = 0.0;

	private Boolean checked = false;

	public String getClassIdentify() {
		return classIdentify;
	}

	public void setClassIdentify(String classIdentify) {
		this.classIdentify = classIdentify;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Double getFieldValue() {
		return fieldValue;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
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

	public String getMappingField() {
		return mappingField;
	}

	public void setMappingField(String mappingField) {
		this.mappingField = mappingField;
	}

}
