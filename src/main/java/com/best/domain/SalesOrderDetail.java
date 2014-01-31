package com.best.domain;

import java.io.Serializable;

/**
 * ClassName:SalesOrderDetail Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-3-27
 */
public class SalesOrderDetail implements Serializable {

	private static final long serialVersionUID = 5950751947722571691L;

	private String orderCode;
	private String refOrderCode;
	private String createTime;
	private String shippingTime;
	private String status;
	private String province;
	private String city;
	private String district;
	private String address;
	private String name;
	private String phone;
	private String orderLineNo;
	private String issuePartyCode;
	private String customerName;
	private String outSideNo;
	private Float grossWeight;
	private Float qtyOrdered;
	private Float cubic;
	private Long ldoId;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getRefOrderCode() {
		return refOrderCode;
	}

	public void setRefOrderCode(String refOrderCode) {
		this.refOrderCode = refOrderCode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getLdoId() {
		return ldoId;
	}

	public void setLdoId(Long ldoId) {
		this.ldoId = ldoId;
	}

	public String getOrderLineNo() {
		return orderLineNo;
	}

	public void setOrderLineNo(String orderLineNo) {
		this.orderLineNo = orderLineNo;
	}

	public String getIssuePartyCode() {
		return issuePartyCode;
	}

	public void setIssuePartyCode(String issuePartyCode) {
		this.issuePartyCode = issuePartyCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOutSideNo() {
		return outSideNo;
	}

	public void setOutSideNo(String outSideNo) {
		this.outSideNo = outSideNo;
	}

	public Float getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Float grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Float getQtyOrdered() {
		return qtyOrdered;
	}

	public void setQtyOrdered(Float qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	public Float getCubic() {
		return cubic;
	}

	public void setCubic(Float cubic) {
		this.cubic = cubic;
	}

}
