package com.best.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * ClassName:Project Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
public class Project implements Serializable {

	private static final long serialVersionUID = -4671099600708069317L;

	private Integer projectId = -1;
	private String projectName = "";
	private String projectCustomer = "";
	private String projectWareHouse = "";
	private String projectCooper = "";
	private String projectBusiness = "";
	private Integer projectEnable = 0;
	private String gmtCreate;
	private Boolean isChecked = false;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public String getProjectCooper() {
		return projectCooper;
	}

	public void setProjectCooper(String projectCooper) {
		this.projectCooper = projectCooper;
	}

	public String getProjectBusiness() {
		return projectBusiness;
	}

	public void setProjectBusiness(String projectBusiness) {
		this.projectBusiness = projectBusiness;
	}

	public Integer getProjectEnable() {
		return projectEnable;
	}

	public void setProjectEnable(Integer projectEnable) {
		this.projectEnable = projectEnable;
	}

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getCustomerCode() {
		if (null == this.projectCustomer)
			return "";
		String[] parts = this.projectCustomer.split("#");
		if (null == parts || parts.length < 1)
			return "";
		return parts[0];

	}

	public String getCustomerName() {
		if (null == this.projectCustomer)
			return "";
		String[] parts = this.projectCustomer.split("#");
		if (null == parts || parts.length < 2)
			return "";
		return parts[1];

	}

	public String getWareHouseCode() {
		if (null == this.projectWareHouse)
			return "";
		String[] parts = this.projectWareHouse.split("#");
		if (null == parts || parts.length < 1)
			return "";
		return parts[0];

	}

	public String getWareHouseName() {
		if (null == this.projectWareHouse)
			return "";
		String[] parts = this.projectWareHouse.split("#");
		if (null == parts || parts.length < 2)
			return "";
		return parts[1];

	}

	public List<Customer> getCustomers() {
		List<Customer> res = new ArrayList<Customer>();
		if (StringUtils.isNotBlank(this.projectCustomer)) {
			String[] parts = this.projectCustomer.split(",");
			for (String projectCustomerPart : parts) {
				Customer customer = new Customer();
				String[] customerParts = projectCustomerPart.split("#");
				customer.setCustomerCode(customerParts[0]);
				customer.setCustomerName(customerParts[1]);
				res.add(customer);
			}
		}
		return res;
	}

	public List<WareHouse> getWareHouses() {
		List<WareHouse> res = new ArrayList<WareHouse>();
		if (StringUtils.isNotBlank(this.projectWareHouse)) {
			String[] parts = this.projectWareHouse.split(",");
			for (String projectWareHousePart : parts) {
				WareHouse wareHouse = new WareHouse();
				String[] wareHouseParts = projectWareHousePart.split("#");
				wareHouse.setWareHouseCode(wareHouseParts[0]);
				wareHouse.setWareHouseName(wareHouseParts[1]);
				res.add(wareHouse);
			}
		}
		return res;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

}
