package com.best.domain;

import java.io.Serializable;

import org.springframework.util.StringUtils;

/**
 * ClassName:ReportDetail Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-2-7
 */
public class ReportDetail implements Serializable {

	private static final long serialVersionUID = -7185118540891694691L;

	private Integer reportDetailId;
	private Integer reportDetailProjectId;
	private String reportDetailProjectName;
	private String reportDetailStart;
	private String reportDetailEnd;
	private Integer reportId;
	private String reportName;
	private Long gmtCreator;
	private String gmtCreatorName;
	private Integer reportProgress;
	private String filePath;
	private String reportField;
	private String projectCustomer;
	private String projectWareHouse;

	public Integer getReportDetailId() {
		return reportDetailId;
	}

	public void setReportDetailId(Integer reportDetailId) {
		this.reportDetailId = reportDetailId;
	}

	public Integer getReportDetailProjectId() {
		return reportDetailProjectId;
	}

	public void setReportDetailProjectId(Integer reportDetailProjectId) {
		this.reportDetailProjectId = reportDetailProjectId;
	}

	public String getReportDetailProjectName() {
		return reportDetailProjectName;
	}

	public void setReportDetailProjectName(String reportDetailProjectName) {
		this.reportDetailProjectName = reportDetailProjectName;
	}

	public String getReportDetailStart() {
		return reportDetailStart;
	}

	public void setReportDetailStart(String reportDetailStart) {
		this.reportDetailStart = reportDetailStart;
	}

	public String getReportDetailEnd() {
		return reportDetailEnd;
	}

	public void setReportDetailEnd(String reportDetailEnd) {
		this.reportDetailEnd = reportDetailEnd;
	}

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

	public Long getGmtCreator() {
		return gmtCreator;
	}

	public void setGmtCreator(Long gmtCreator) {
		this.gmtCreator = gmtCreator;
	}

	public String getGmtCreatorName() {
		return gmtCreatorName;
	}

	public void setGmtCreatorName(String gmtCreatorName) {
		this.gmtCreatorName = gmtCreatorName;
	}

	public Integer getReportProgress() {
		return reportProgress;
	}

	public void setReportProgress(Integer reportProgress) {
		this.reportProgress = reportProgress;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getReportField() {
		return reportField;
	}

	public void setReportField(String reportField) {
		this.reportField = reportField;
	}

	public String getProjectCustomer() {
		return projectCustomer;
	}

	public void setProjectCustomer(String projectCustomer) {
		this.projectCustomer = projectCustomer;
	}

	public String getProjectWareHouse() {
		return projectWareHouse;
	}

	public void setProjectWareHouse(String projectWareHouse) {
		this.projectWareHouse = projectWareHouse;
	}

	public String getCustomerCode() {
		if (StringUtils.isEmpty(this.projectCustomer))
			return "";
		return this.projectCustomer.split("#")[0];
	}

	public String getWareHouseCode() {
		if (StringUtils.isEmpty(this.projectWareHouse))
			return "";
		return this.projectWareHouse.split("#")[0];
	}
}
