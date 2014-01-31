package com.best.domain;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.best.utils.DateUtil;

/**
 * ClassName:FieldWarm Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-31
 */
public class FieldWarm implements Serializable, Cloneable {

	private static final long serialVersionUID = -3714735132250236062L;

	private Integer fieldWarmId;
	private Integer fieldWarmProjectId;
	private String fieldWarmProjectName;
	private Integer fieldWarmFieldId;
	private String fieldWarmFieldName;
	private int fieldWarmFieldPeriod;
	private Long fieldWarmChargeManFirstId;
	private String fieldWarmChargeManFirstName;
	private String fieldWarmChargeManFirstEmail;
	private Long fieldWarmChargeManSecondId;
	private Integer fieldWarmEnabled;
	private Double fieldWarmValue;
	private String fieldWarmPeriod;
	private Long fieldWarmModifier = -1L;
	private String fieldWarmModifierName = "";
	private String fieldWarmGmtModified = "";
	private String fieldWarmServiceDate = "";
	private String fieldWarmDataDesc = "";
	private Integer dataType = 0;
	private String historyRecord;

	public Integer getFieldWarmId() {
		return fieldWarmId;
	}

	public void setFieldWarmId(Integer fieldWarmId) {
		this.fieldWarmId = fieldWarmId;
	}

	public Integer getFieldWarmProjectId() {
		return fieldWarmProjectId;
	}

	public void setFieldWarmProjectId(Integer fieldWarmProjectId) {
		this.fieldWarmProjectId = fieldWarmProjectId;
	}

	public String getFieldWarmProjectName() {
		return fieldWarmProjectName;
	}

	public void setFieldWarmProjectName(String fieldWarmProjectName) {
		this.fieldWarmProjectName = fieldWarmProjectName;
	}

	public Integer getFieldWarmFieldId() {
		return fieldWarmFieldId;
	}

	public void setFieldWarmFieldId(Integer fieldWarmFieldId) {
		this.fieldWarmFieldId = fieldWarmFieldId;
	}

	public Integer getFieldWarmEnabled() {
		return fieldWarmEnabled;
	}

	public void setFieldWarmEnabled(Integer fieldWarmEnabled) {
		this.fieldWarmEnabled = fieldWarmEnabled;
	}

	public Double getFieldWarmValue() {
		return fieldWarmValue == null ? 0.0 : fieldWarmValue;
	}

	public void setFieldWarmValue(Double fieldWarmValue) {
		this.fieldWarmValue = fieldWarmValue;
	}

	public Long getFieldWarmChargeManFirstId() {
		return fieldWarmChargeManFirstId;
	}

	public void setFieldWarmChargeManFirstId(Long fieldWarmChargeManFirstId) {
		this.fieldWarmChargeManFirstId = fieldWarmChargeManFirstId;
	}

	public Long getFieldWarmChargeManSecondId() {
		return fieldWarmChargeManSecondId;
	}

	public void setFieldWarmChargeManSecondId(Long fieldWarmChargeManSecondId) {
		this.fieldWarmChargeManSecondId = fieldWarmChargeManSecondId;
	}

	public String getFieldWarmPeriod() {
		return fieldWarmPeriod;
	}

	public void setFieldWarmPeriod(String fieldWarmPeriod) {
		this.fieldWarmPeriod = fieldWarmPeriod;
	}

	public String getFieldWarmFieldName() {
		return fieldWarmFieldName;
	}

	public void setFieldWarmFieldName(String fieldWarmFieldName) {
		this.fieldWarmFieldName = fieldWarmFieldName;
	}

	public int getFieldWarmFieldPeriod() {
		return fieldWarmFieldPeriod;
	}

	public void setFieldWarmFieldPeriod(Integer fieldWarmFieldPeriod) {
		this.fieldWarmFieldPeriod = fieldWarmFieldPeriod;
	}

	public String getFieldWarmChargeManFirstName() {
		return fieldWarmChargeManFirstName;
	}

	public void setFieldWarmChargeManFirstName(String fieldWarmChargeManFirstName) {
		this.fieldWarmChargeManFirstName = fieldWarmChargeManFirstName;
	}

	public String getFieldWarmChargeManFirstEmail() {
		return fieldWarmChargeManFirstEmail;
	}

	public void setFieldWarmChargeManFirstEmail(String fieldWarmChargeManFirstEmail) {
		this.fieldWarmChargeManFirstEmail = fieldWarmChargeManFirstEmail;
	}

	public String getFieldWarmPeriodName() {
		if (this.fieldWarmFieldPeriod == 1)
			return "周";
		else if (this.fieldWarmFieldPeriod == 2)
			return "日";
		else {
			return "月";
		}
	}

	public Integer getDay() {
		if (null == this.fieldWarmPeriod)
			return -1;
		String[] parts = this.fieldWarmPeriod.split(" +");

		if (parts.length > 3 && !parts[3].equals("*"))
			return Integer.parseInt(parts[3]);
		return -1;
	}

	public Integer getHour() {
		if (null == this.fieldWarmPeriod)
			return 0;
		String[] parts = this.fieldWarmPeriod.split(" +");

		if (parts.length > 2 && !parts[2].equals("*"))
			return Integer.parseInt(parts[2]);
		return 0;
	}

	public Integer getMinute() {
		if (null == this.fieldWarmPeriod)
			return 0;
		String[] parts = this.fieldWarmPeriod.split(" +");

		if (parts.length > 1 && !parts[1].equals("*"))
			return Integer.parseInt(parts[1]);
		return 0;
	}

	public Integer getWeek() {
		if (null == this.fieldWarmPeriod)
			return -1;
		String[] parts = this.fieldWarmPeriod.split(" +");

		if (parts.length > 5 && !parts[5].equals("*") && !parts[5].equals("?"))
			return Integer.parseInt(parts[5]);
		return -1;
	}

	public String getFieldWarmTime() {
		if (null == this.fieldWarmPeriod)
			return "";
		String[] parts = this.fieldWarmPeriod.split(" +");
		String res = "";
		if (this.fieldWarmFieldPeriod == 1 && parts.length > 5) {
			if (parts[5].equals("1"))
				res += "星期一";
			else if (parts[5].equals("2"))
				res += "星期二";
			else if (parts[5].equals("3"))
				res += "星期三";
			else if (parts[5].equals("4"))
				res += "星期四";
			else if (parts[5].equals("5"))
				res += "星期五";
			else if (parts[5].equals("6"))
				res += "星期六";
			else if (parts[5].equals("7"))
				res += "星期日";
			else
				res += "每天";

		} else if (this.fieldWarmFieldPeriod == 3 && parts.length > 3) {
			if (!parts[3].equals("*") && !parts[3].equals("?"))
				res += parts[3] + "号";
			else
				res += "每天";
		}

		if (parts.length > 2 && !parts[2].equals("*"))
			res += parts[2] + "时";
		if (parts.length > 1 && !parts[1].equals("*"))
			res += parts[1] + "分";
		return res;

	}

	public Long getFieldWarmModifier() {
		return fieldWarmModifier;
	}

	public void setFieldWarmModifier(Long fieldWarmModifier) {
		this.fieldWarmModifier = fieldWarmModifier;
	}

	public String getFieldWarmGmtModified() {
		return fieldWarmGmtModified;
	}

	public void setFieldWarmGmtModified(String fieldWarmGmtModified) {
		this.fieldWarmGmtModified = fieldWarmGmtModified;
	}

	public String getFieldWarmModifierName() {
		return fieldWarmModifierName;
	}

	public void setFieldWarmModifierName(String fieldWarmModifierName) {
		this.fieldWarmModifierName = fieldWarmModifierName;
	}

	public String getFieldWarmServiceDate() {
		return fieldWarmServiceDate;
	}

	public void setFieldWarmServiceDate(String fieldWarmServiceDate) {
		this.fieldWarmServiceDate = fieldWarmServiceDate;
	}

	public String getFieldWarmDataDesc() {
		return fieldWarmDataDesc;
	}

	public void setFieldWarmDataDesc(String fieldWarmDataDesc) {
		this.fieldWarmDataDesc = fieldWarmDataDesc;
	}

	public String getServiceYear() {
		if (StringUtils.isNotBlank(fieldWarmServiceDate)) {
			return fieldWarmServiceDate.substring(0, 4);
		}
		return "";
	}

	public String getCurrentServiceYear() {
		if (StringUtils.isBlank(this.fieldWarmGmtModified)) {
			String currentDay = DateUtil.getCurrentDateString();
			return currentDay.substring(0, 4);
		} else {
			return fieldWarmGmtModified.substring(0, 4);
		}
	}

	public String getServiceMonth() {

		if (StringUtils.isNotBlank(fieldWarmServiceDate)) {
			return fieldWarmServiceDate.substring(4, 6);
		}
		return "";
	}

	public String getCurrentServiceMonth() {
		if (StringUtils.isBlank(this.fieldWarmGmtModified)) {
			String currentDay = DateUtil.getCurrentDateString();
			return currentDay.substring(4, 6);
		} else {
			return fieldWarmGmtModified.substring(4, 6);
		}
	}

	public String getServiceDay() {
		if (StringUtils.isNotBlank(fieldWarmServiceDate)) {
			return fieldWarmServiceDate.substring(6);
		}
		return "";
	}

	public String getCurrentServiceDay() {
		if (StringUtils.isBlank(this.fieldWarmGmtModified)) {
			String currentDay = DateUtil.getCurrentDateString();
			return currentDay.substring(6, 8);
		} else {
			return fieldWarmGmtModified.substring(6, 8);
		}
		// String currentDay = DateUtil.getCurrentDateString();
		// return currentDay.substring(6, 8);
	}

	public Integer getDataType() {
		return dataType == null ? 0 : dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getHistoryRecord() {
		return historyRecord == null ? "" : historyRecord;
	}

	public void setHistoryRecord(String historyRecord) {
		this.historyRecord = (historyRecord == null ? "" : historyRecord);
	}

	public List<ValueDate> getDateValues() {
		List<ValueDate> res = new ArrayList<ValueDate>();
		if (StringUtils.isNotBlank(historyRecord)) {
			String[] params = historyRecord.split("\\|");
			for (String param : params) {
				String tmp[] = param.split("\\$");
				if (tmp.length != 3)
					continue;
				try {
					ValueDate date = new ValueDate();
					date.setDateTime(tmp[0]);
					date.setDateValue(Double.parseDouble(tmp[1].trim()));
					date.setDateType(Integer.parseInt(tmp[2].trim()));
					res.add(date);
				} catch (Exception e) {
				}
			}
		}
		Collections.sort(res);
		return res;
	}

	public String getFormatValue() {
		return null == fieldWarmValue ? "0.0" : new DecimalFormat("0.0").format(fieldWarmValue);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Object getClone() throws CloneNotSupportedException {
		return clone();
	}

}
