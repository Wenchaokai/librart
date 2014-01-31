package com.best.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.best.domain.wms.SystemField;

/**
 * ClassName:Subject Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-1-1
 */
public class Subject implements Serializable {

	private static final long serialVersionUID = -5287893406214719394L;

	private Integer subjectId;
	private String subjectName;
	private Integer subjectType;
	private Integer subjectProjectId;
	private String subjectProjectName;
	private String subjectDesc;
	private Integer subjectEnabled;
	private String subjectFormula;
	private String subjectFormulaTxt;
	private Integer parentSubjectId = -1;
	private List<SystemField> systemFields = null;
	private List<Field> fields = null;
	private Boolean isChecked = false;
	private String subjectPrice = "";
	private String subjectMinPrice = "";
	private String subjectFieldCustom = "";
	private Double subjectNumSum = 0.0;
	private String userName;
	private Long modifyTime;

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}

	public Integer getSubjectProjectId() {
		return subjectProjectId;
	}

	public void setSubjectProjectId(Integer subjectProjectId) {
		this.subjectProjectId = subjectProjectId;
	}

	public String getSubjectDesc() {
		return subjectDesc;
	}

	public void setSubjectDesc(String subjectDesc) {
		this.subjectDesc = subjectDesc;
	}

	public Integer getSubjectEnabled() {
		return subjectEnabled;
	}

	public void setSubjectEnabled(Integer subjectEnabled) {
		this.subjectEnabled = subjectEnabled;
	}

	public String getSubjectFormula() {
		return subjectFormula;
	}

	public void setSubjectFormula(String subjectFormula) {
		this.subjectFormula = subjectFormula;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String[] formatFormula() {
		if (StringUtils.isNotBlank(subjectFormula)) {
			systemFields = new ArrayList<SystemField>();
			fields = new ArrayList<Field>();
			String[] parts = subjectFormula.split("\\+|-|\\*|/");
			return parts;
		}
		return null;
	}

	public String getSubjectFormulaTxt() {
		return subjectFormulaTxt;
	}

	public void setSubjectFormulaTxt(String subjectFormulaTxt) {
		this.subjectFormulaTxt = subjectFormulaTxt;
	}

	public String getSubjectProjectName() {
		return subjectProjectName;
	}

	public String getShowSubjcetFormulaTxt() {
		if (null == this.subjectFormulaTxt)
			return "";
		String temp = this.subjectFormulaTxt.replaceAll("\\|", "");
		return temp;
	}

	public void setSubjectProjectName(String subjectProjectName) {
		this.subjectProjectName = subjectProjectName;
	}

	public String getFormulaSpan() {
		if (null == this.subjectFormulaTxt)
			return "";
		String spanForm = "<span class=\"step\">";
		String spanSForm = "<span class=\"step s\">";
		String spanEnd = "</span>";
		StringBuilder builder = new StringBuilder();
		String params[] = this.subjectFormulaTxt.split("\\|");
		for (String param : params) {
			if (param.equals("*") || param.equals("+") || param.equals("-") || param.equals("/"))
				builder.append(spanSForm).append(param).append(spanEnd);
			else
				builder.append(spanForm).append(param).append(spanEnd);
		}
		return builder.toString();

	}

	public List<String> getInitField() {
		String[] fields = formatFormula();
		List<String> res = new ArrayList<String>();
		if (null != fields) {
			for (String field : fields) {
				if (field.startsWith("0"))
					continue;
				res.add(field);
			}
			return res;
		}
		return null;
	}

	public List<SystemField> getSystemFields() {
		if (null != systemFields)
			return systemFields;
		if (null == this.subjectFormula)
			return new ArrayList<SystemField>();
		String formulaParams[] = this.subjectFormula.split("\\|");
		String formulaTxtParams[] = this.subjectFormulaTxt.split("\\|");
		if (formulaParams.length != formulaTxtParams.length)
			return new ArrayList<SystemField>();
		systemFields = new ArrayList<SystemField>();
		for (int index = 0; index < formulaParams.length; index++) {
			if (formulaParams[index].equals("*") || formulaParams[index].equals("-") || formulaParams[index].equals("+")
					|| formulaParams[index].equals("/") || formulaParams[index].equals("-1")
					|| formulaParams[index].startsWith("-2"))
				continue;

			try {
				Integer.parseInt(formulaParams[index]);
				continue;
			} catch (Exception e) {
				// 有错误来判断
			}

			SystemField systemField = new SystemField();
			systemField.setClassIdentify(formulaParams[index].split("-")[0]);
			systemField.setFieldName(formulaParams[index].split("-")[1]);
			systemField.setFieldDesc(formulaTxtParams[index]);
			systemFields.add(systemField);
		}
		return systemFields;
	}

	public List<Field> getFields(List<Field> deriveFields) {
		if (null != fields)
			return fields;
		if (null == this.subjectFormula)
			return new ArrayList<Field>();
		String formulaParams[] = this.subjectFormula.split("\\|");
		String formulaTxtParams[] = this.subjectFormulaTxt.split("\\|");
		if (formulaParams.length != formulaTxtParams.length)
			return new ArrayList<Field>();
		fields = new ArrayList<Field>();

		Set<Integer> deriveIds = new HashSet<Integer>();
		if (null != deriveFields) {
			for (Field field : deriveFields) {
				deriveIds.add(field.getFieldId());
			}
		}
		for (int index = 0; index < formulaParams.length; index++) {
			if (formulaParams[index].equals("*") || formulaParams[index].equals("-") || formulaParams[index].equals("+")
					|| formulaParams[index].equals("/") || formulaParams[index].equals("-1")
					|| formulaParams[index].startsWith("-2"))
				continue;

			try {
				Integer.parseInt(formulaParams[index]);

			} catch (Exception e) {
				// 有错误来判断
				continue;
			}

			Field field = new Field();
			field.setFieldId(Integer.parseInt(formulaParams[index]));
			field.setFieldName(formulaTxtParams[index]);
			if (deriveIds.contains(field.getFieldId()))
				fields.add(field);
		}
		return fields;
	}

	public List<Field> getFields() {
		if (null != fields)
			return fields;
		if (null == this.subjectFormula)
			return new ArrayList<Field>();
		String formulaParams[] = this.subjectFormula.split("\\|");
		String formulaTxtParams[] = this.subjectFormulaTxt.split("\\|");
		if (formulaParams.length != formulaTxtParams.length)
			return new ArrayList<Field>();
		fields = new ArrayList<Field>();
		for (int index = 0; index < formulaParams.length; index++) {
			if (formulaParams[index].equals("*") || formulaParams[index].equals("-") || formulaParams[index].equals("+")
					|| formulaParams[index].equals("/") || formulaParams[index].equals("-1")
					|| formulaParams[index].startsWith("-2"))
				continue;

			try {
				Integer.parseInt(formulaParams[index]);

			} catch (Exception e) {
				// 有错误来判断
				continue;
			}

			Field field = new Field();
			field.setFieldId(Integer.parseInt(formulaParams[index]));
			field.setFieldName(formulaTxtParams[index]);
			fields.add(field);
		}
		return fields;
	}

	public Integer getParentSubjectId() {
		return parentSubjectId;
	}

	public void setParentSubjectId(Integer parentSubjectId) {
		this.parentSubjectId = parentSubjectId;
	}

	public String getSubjectPrice() {
		return subjectPrice;
	}

	public void setSubjectPrice(String subjectPrice) {
		this.subjectPrice = subjectPrice;
	}

	public String getSubjectMinPrice() {
		return subjectMinPrice;
	}

	public void setSubjectMinPrice(String subjectMinPrice) {
		this.subjectMinPrice = subjectMinPrice;
	}

	public String getSubjectFieldCustom() {
		return subjectFieldCustom;
	}

	public void setSubjectFieldCustom(String subjectFieldCustom) {
		this.subjectFieldCustom = subjectFieldCustom;
	}

	public Double getSubjectNumSum() {
		return subjectNumSum;
	}

	public void setSubjectNumSum(Double subjectNumSum) {
		this.subjectNumSum = subjectNumSum;
	}

	public String getUserName() {
		return userName == null ? "" : userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyTimeStr() {
		if (null == modifyTime)
			return "";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		return format.format(new Date(modifyTime));
	}

}
