package com.best.domain;

import java.io.Serializable;

/**
 * ClassName:ValueDate Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-4-7
 */
public class ValueDate implements Serializable, Comparable<ValueDate> {

	private static final long serialVersionUID = -3882485562885408839L;

	private String dateTime;
	private Double dateValue;
	private Integer dateType;

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public Double getDateValue() {
		return dateValue;
	}

	public void setDateValue(Double dateValue) {
		this.dateValue = dateValue;
	}

	public Integer getDateType() {
		return dateType;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}

	@Override
	public int compareTo(ValueDate o) {
		return dateTime.compareTo(o.dateTime);
	}

}
