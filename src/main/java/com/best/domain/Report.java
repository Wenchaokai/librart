package com.best.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.best.domain.wms.Asn;
import com.best.domain.wms.AsnItem;
import com.best.domain.wms.Ldo;
import com.best.domain.wms.LdoItem;
import com.best.domain.wms.SystemField;

/**
 * ClassName:Report Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-2-4
 */
public class Report implements Serializable {

	private static final long serialVersionUID = -1703272904817762641L;

	public static final Integer REPORT_DETAIL_TYPE = 2;
	public static final Integer REPORT_SUMMARY_TYPE = 1;

	public static final Integer REPORT_OBJECT_ORDER_TYPE = 1;
	public static final Integer REPORT_OBJECT_FIELD_TYPE = 2;

	public static final Map<Integer, Class> classObjectMap = new HashMap<Integer, Class>();

	static {
		classObjectMap.put(1, Ldo.class);
		classObjectMap.put(2, LdoItem.class);
		classObjectMap.put(3, Asn.class);
		classObjectMap.put(4, AsnItem.class);
	}

	private Integer reportId;
	private String reportName;
	private Integer reportProjectId;
	private String reportProjectName;
	private Integer reportType;
	private Integer reportObjectType;
	private Integer reportClassObject;
	private String reportField;
	private String reportSortField;
	private Long reportCreator;
	private String reportGmtCreate;
	private Integer reportEnable;

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Integer getReportProjectId() {
		return reportProjectId;
	}

	public void setReportProjectId(Integer reportProjectId) {
		this.reportProjectId = reportProjectId;
	}

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	public Integer getReportObjectType() {
		return reportObjectType;
	}

	public void setReportObjectType(Integer reportObjectType) {
		this.reportObjectType = reportObjectType;
	}

	public Integer getReportClassObject() {
		return reportClassObject;
	}

	public void setReportClassObject(Integer reportClassObject) {
		this.reportClassObject = reportClassObject;
	}

	public String getReportField() {
		return reportField;
	}

	public void setReportField(String reportField) {
		this.reportField = reportField;
	}

	public String getReportSortField() {
		return reportSortField;
	}

	public void setReportSortField(String reportSortField) {
		this.reportSortField = reportSortField;
	}

	public Long getReportCreator() {
		return reportCreator;
	}

	public void setReportCreator(Long reportCreator) {
		this.reportCreator = reportCreator;
	}

	public String getReportGmtCreate() {
		return reportGmtCreate;
	}

	public void setReportGmtCreate(String reportGmtCreate) {
		this.reportGmtCreate = reportGmtCreate;
	}

	public Integer getReportEnable() {
		return reportEnable;
	}

	public void setReportEnable(Integer reportEnable) {
		this.reportEnable = reportEnable;
	}

	public String getReportProjectName() {
		return reportProjectName;
	}

	public void setReportProjectName(String reportProjectName) {
		this.reportProjectName = reportProjectName;
	}

	public List<SystemField> getSystemFields() {
		if (StringUtils.isEmpty(reportField))
			return new ArrayList<SystemField>();

		String[] params = reportField.split(",");
		List<SystemField> systemFields = new ArrayList<SystemField>();
		for (String param : params) {
			String[] temp = param.split("&");
			SystemField systemField = new SystemField();
			systemField.setClassIdentify(temp[0]);
			systemField.setFieldDesc(temp[2]);
			systemField.setFieldName(temp[1]);
			systemFields.add(systemField);
		}

		return systemFields;
	}

}
