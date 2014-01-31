package com.best.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ClassName:MinPrice Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-2-2
 */
public class MinPrice implements Comparable<MinPrice> {

	public static final Integer LOWEST_NUM_PRICE = 2;
	public static final Integer LOWEST_PRICE_PRICE = 1;
	public static final Integer NON_LOWEST = 3;

	private Integer priceType = 3;
	// 字段信息
	private String fieldInfo = "";

	private String fieldDesc = "";

	// 有效期
	private String startDate = "";
	private String endDate = "";

	private Double standardPrice = 0.0;

	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public String getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(String fieldInfo) {
		this.fieldInfo = fieldInfo;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Double getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(Double standardPrice) {
		this.standardPrice = standardPrice;
	}

	public static Integer getLowestNumPrice() {
		return LOWEST_NUM_PRICE;
	}

	public static Integer getLowestPricePrice() {
		return LOWEST_PRICE_PRICE;
	}

	public static Integer getNonLowest() {
		return NON_LOWEST;
	}

	public static List<MinPrice> parseMinPrice(String minPriceString) {
		if (null == minPriceString || minPriceString.length() == 0)
			return new ArrayList<MinPrice>();

		String[] priceParams = minPriceString.split("\\|");
		List<MinPrice> res = new ArrayList<MinPrice>();
		for (String priceParam : priceParams) {

			String[] params = priceParam.split("#");
			if (params.length < 3)
				continue;

			MinPrice minPrice = new MinPrice();
			int type = Integer.parseInt(params[0]);
			minPrice.setPriceType(type);
			if (type == NON_LOWEST) {
				minPrice.setStartDate(params[1]);
				minPrice.setEndDate(params[2]);
			} else if (type == LOWEST_PRICE_PRICE) {
				Double standardPrice = Double.parseDouble(params[1]);
				minPrice.setStandardPrice(standardPrice);
				minPrice.setStartDate(params[2]);
				minPrice.setEndDate(params[3]);
			} else if (type == LOWEST_NUM_PRICE) {
				Double standardPrice = Double.parseDouble(params[1]);
				minPrice.setStandardPrice(standardPrice);
				minPrice.setStartDate(params[2]);
				minPrice.setEndDate(params[3]);
				minPrice.setFieldInfo(params[4]);
			}

			res.add(minPrice);
		}
		return res;

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(priceType);
		if (priceType == NON_LOWEST) {
			// 没有最低消费
			builder.append("#");
			builder.append(startDate == null ? "" : startDate);
			builder.append("#");
			builder.append(endDate == null ? "" : endDate);
		} else if (priceType == LOWEST_PRICE_PRICE) {
			builder.append("#");
			builder.append(standardPrice == null ? 0.0 : standardPrice);
			builder.append("#");
			builder.append(startDate == null ? "" : startDate);
			builder.append("#");
			builder.append(endDate == null ? "" : endDate);
		} else if (priceType == LOWEST_NUM_PRICE) {
			builder.append("#");
			builder.append(standardPrice == null ? 0.0 : standardPrice);
			builder.append("#");
			builder.append(startDate == null ? "" : startDate);
			builder.append("#");
			builder.append(endDate == null ? "" : endDate);
			builder.append("#");
			builder.append(fieldInfo == null ? "" : fieldInfo);
		}
		return builder.toString();
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	@Override
	public int compareTo(MinPrice o) {
		if (o.endDate == null)
			return 1;
		if (this.endDate == null)
			return -1;
		if (!o.endDate.equals(this.endDate))
			return this.endDate.compareTo(o.endDate);
		if (o.startDate == null)
			return 1;
		if (this.startDate == null)
			return -1;
		return this.startDate.compareTo(o.startDate);
	}

	public static void main(String[] args) {

		List<MinPrice> fieldValues = new ArrayList<MinPrice>();
		MinPrice fieldValue1 = new MinPrice();
		fieldValue1.startDate = "20130201";
		fieldValue1.endDate = "20130401";
		fieldValues.add(fieldValue1);

		MinPrice fieldValue2 = new MinPrice();
		fieldValue2.startDate = "20130401";
		fieldValue2.endDate = "20130501";
		fieldValues.add(fieldValue2);

		MinPrice fieldValue3 = new MinPrice();
		fieldValue3.startDate = "20130301";
		fieldValue3.endDate = "20130401";
		fieldValues.add(fieldValue3);

		Collections.sort(fieldValues);

		for (MinPrice fieldValue : fieldValues) {
			System.out.println(fieldValue.startDate + "\t" + fieldValue.endDate);
		}
	}

}
