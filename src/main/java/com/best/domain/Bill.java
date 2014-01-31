package com.best.domain;

import java.io.Serializable;
import java.text.DecimalFormat;

import org.springframework.util.StringUtils;

import com.best.utils.DateUtil;

/**
 * ClassName:Bill Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-2-8
 */
public class Bill implements Serializable {

	private static final long serialVersionUID = -4883116656765331102L;

	public static final DecimalFormat df = new DecimalFormat("#,###,##0.00");;
	private Integer billId;
	private Integer billProjectId;
	private String billProjectName;
	private String billStartTime;
	private String billEndTime;
	private String billBelongTime;
	private Integer billProgress;
	private String billSubmitTime;
	private Long gmtCreator;
	private String gmtCreatorName;
	private Double billSum;
	// 0 生成中 1 已建账单 2 提交对账 3 对账完成
	private Integer billStatus = 0;
	private String billName;
	private String projectCustomer;
	private String projectWareHouse;

	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public Integer getBillProjectId() {
		return billProjectId;
	}

	public void setBillProjectId(Integer billProjectId) {
		this.billProjectId = billProjectId;
	}

	public String getBillProjectName() {
		return billProjectName;
	}

	public void setBillProjectName(String billProjectName) {
		this.billProjectName = billProjectName;
	}

	public String getBillStartTime() {
		return billStartTime == null ? "" : billStartTime;
	}

	public void setBillStartTime(String billStartTime) {
		this.billStartTime = billStartTime;
	}

	public String getBillEndTime() {
		return billEndTime == null ? "" : billEndTime;
	}

	public void setBillEndTime(String billEndTime) {
		this.billEndTime = billEndTime;
	}

	public Integer getBillProgress() {
		return billProgress;
	}

	public void setBillProgress(Integer billProgress) {
		this.billProgress = billProgress;
	}

	public String getBillSubmitTime() {
		return billSubmitTime == null ? "" : billSubmitTime;
	}

	public void setBillSubmitTime(String billSubmitTime) {
		this.billSubmitTime = billSubmitTime;
	}

	public Long getGmtCreator() {
		return gmtCreator;
	}

	public void setGmtCreator(Long gmtCreator) {
		this.gmtCreator = gmtCreator;
	}

	public String getGmtCreatorName() {
		return gmtCreatorName == null ? "" : gmtCreatorName;
	}

	public void setGmtCreatorName(String gmtCreatorName) {
		this.gmtCreatorName = gmtCreatorName;
	}

	public Double getBillSum() {
		return billSum == null ? 0.0 : billSum;
	}

	public void setBillSum(Double billSum) {
		this.billSum = billSum;
	}

	public Integer getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(Integer billStatus) {
		this.billStatus = billStatus;
	}

	public String getBillStatusInfo() {
		if (this.billStatus == 1)
			return "已建账单";
		else if (this.billStatus == 2)
			return "提交对账";
		else if (this.billStatus == 3)
			return "对账完成";
		else
			return "生成中";
	}

	public String getBillName() {
		return billName == null ? "" : billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	public String getShowAmount() {
		if (null == billSum)
			return "0.00";
		return df.format(billSum);
	}

	public String getFormatStartDate() {
		if (StringUtils.isEmpty(this.billStartTime))
			return "";
		return this.billStartTime.substring(0, 4) + "年" + this.billStartTime.substring(4, 6) + "月"
				+ this.billStartTime.substring(6) + "日";
	}

	public String getFormatEndDate() {
		if (StringUtils.isEmpty(this.billEndTime))
			return "";
		return this.billEndTime.substring(0, 4) + "年" + this.billEndTime.substring(4, 6) + "月" + this.billEndTime.substring(6)
				+ "日";
	}

	public String getFormatSubmitDate() {
		if (StringUtils.isEmpty(this.billSubmitTime))
			return "";
		return this.billSubmitTime.substring(0, 4) + "年" + this.billSubmitTime.substring(4, 6) + "月"
				+ this.billSubmitTime.substring(6) + "日";
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

	public String getBillSumString() {
		return DateUtil.formatDouble(this.billSum);
	}

	public String getBillBelongTime() {
		return billBelongTime;
	}

	public void setBillBelongTime(String billBelongTime) {
		this.billBelongTime = billBelongTime;
	}

}
