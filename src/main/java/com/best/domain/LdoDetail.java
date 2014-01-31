package com.best.domain;

import java.io.Serializable;

/**
 * ClassName:LdoDetail Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-3-26
 */
public class LdoDetail implements Serializable {

	private static final long serialVersionUID = 4771840839179999716L;

	private String refCode;
	private String createTime;
	private String skuCode;
	private String quantity;

	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}
