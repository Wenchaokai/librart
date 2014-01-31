package com.best.domain;

import java.io.Serializable;

/**
 * ClassName:Priviliege Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
public class Priviliege implements Serializable {

	private static final long serialVersionUID = -816144407291379789L;

	private Integer priviliegeId;
	private String priviliegeName;
	private Integer priviliegeIndex;
	private Integer index;
	private boolean check = false;

	public Integer getPriviliegeId() {
		return priviliegeId;
	}

	public void setPriviliegeId(Integer priviliegeId) {
		this.priviliegeId = priviliegeId;
	}

	public String getPriviliegeName() {
		return priviliegeName;
	}

	public void setPriviliegeName(String priviliegeName) {
		this.priviliegeName = priviliegeName;
	}

	public Integer getPriviliegeIndex() {
		return priviliegeIndex;
	}

	public void setPriviliegeIndex(Integer priviliegeIndex) {
		this.priviliegeIndex = priviliegeIndex;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

}
