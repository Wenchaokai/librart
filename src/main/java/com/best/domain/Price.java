package com.best.domain;

import com.best.utils.DateUtil;

/**
 * ClassName:Price Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-2-2
 */
public class Price implements Comparable<Price> {
	public Double min = -100000.0;
	public Double max = 100000000000.0;
	public Double value = 0.0;

	@Override
	public String toString() {
		return min + "$" + max + "$" + value;
	}

	public static Price parsePrice(String priceString) {
		if (null == priceString)
			return null;
		String[] params = priceString.split("\\$");
		Price price = new Price();
		price.min = Double.parseDouble(params[0]);
		price.max = Double.parseDouble(params[1]);
		price.value = Double.parseDouble(params[2]);
		return price;
	}

	@Override
	public int compareTo(Price o) {
		if (o.max == null)
			return 1;
		if (this.max == null)
			return -1;
		if (o.max.doubleValue() != this.max)
			return (o.max > this.max) ? -1 : 1;
		if (o.min == null)
			return 1;
		if (this.min == null)
			return -1;
		return (o.min > this.min) ? -1 : 1;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public String getMinString() {
		if (this.min == -100000.0)
			return "";
		return DateUtil.formatDouble(this.min) + "";
	}

	public String getMaxString() {
		if (this.max == 100000000000.0)
			return "";
		return DateUtil.formatDouble(this.max) + "";
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
