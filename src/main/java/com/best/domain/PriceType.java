package com.best.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ClassName:PriceType Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-2-2
 */
public class PriceType implements Comparable<PriceType> {

	public static final Integer STANDARD_PRICE = 2;
	public static final Integer STEP_PRICE = 1;

	// 1 表示 标准类型
	// 2 表示 阶梯类型
	private Integer priceType;

	// 字段信息
	private String fieldInfo;

	private String fieldDesc;

	// 有效期
	private String startDate;
	private String endDate;

	// 1表示周 2表示月
	private Integer priceUnit;

	private Double standardPrice = 0.0;

	List<Price> prices = new ArrayList<Price>();

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

	public Integer getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(Integer priceUnit) {
		this.priceUnit = priceUnit;
	}

	public Double getStandardPrice() {
		return standardPrice == null ? 0.0 : standardPrice;
	}

	public void setStandardPrice(Double standardPrice) {
		this.standardPrice = standardPrice;
	}

	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		if (null != prices)
			Collections.sort(prices);
		this.prices = prices;
	}

	public void addPrices(Price price) {
		if (null == this.prices)
			this.prices = new ArrayList<Price>();
		this.prices.add(price);
	}

	public Double caculate(Double value, String date) {
		if (date.compareTo(startDate) <= 0)
			return -1.0;
		if (date.compareTo(endDate) > 0)
			return -1.0;
		if (priceType == STANDARD_PRICE)
			return standardPrice;
		if (value == null)
			return -1.0;
		for (Price price : prices) {
			if (value > price.min && value <= price.max)
				return price.value * standardPrice;
		}
		return -1.0;
	}

	public static List<PriceType> parsePriceString(String priceTypeString) {
		if (null == priceTypeString)
			return new ArrayList<PriceType>();
		String[] priceParams = priceTypeString.split("\\|");
		List<PriceType> res = new ArrayList<PriceType>();
		for (String priceParam : priceParams) {
			String[] params = priceParam.split("#");
			if (params.length < 5)
				continue;
			try {
				Integer priceType = Integer.parseInt(params[0]);
				PriceType priceInfo = new PriceType();
				priceInfo.setPriceType(priceType);
				if (priceType == STANDARD_PRICE) {
					Double standardPrice = Double.parseDouble(params[1]);
					priceInfo.setStandardPrice(standardPrice);
					Integer priceUnit = Integer.parseInt(params[2]);
					priceInfo.setPriceUnit(priceUnit);
					priceInfo.setStartDate(params[3]);
					priceInfo.setEndDate(params[4]);
					res.add(priceInfo);

				} else {
					Double standardPrice = Double.parseDouble(params[1]);
					priceInfo.setStandardPrice(standardPrice);
					Integer priceUnit = Integer.parseInt(params[2]);
					priceInfo.setPriceUnit(priceUnit);
					priceInfo.setStartDate(params[3]);
					priceInfo.setEndDate(params[4]);
					priceInfo.setFieldInfo(params[5]);
					int priceSize = Integer.parseInt(params[6]);
					List<Price> prices = new ArrayList<Price>();
					for (int index = 0; index < priceSize; index++) {
						try {
							Price price = Price.parsePrice(params[index + 7]);
							if (null == price)
								continue;
							prices.add(price);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					priceInfo.setPrices(prices);
					res.add(priceInfo);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return res;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(priceType);
		if (priceType == STANDARD_PRICE) {
			// 标准单价
			builder.append("#");
			builder.append(standardPrice == null ? 0.0 : standardPrice);
			builder.append("#");
			builder.append(priceUnit == null ? 1 : priceUnit);
			builder.append("#");
			builder.append(startDate == null ? "" : startDate);
			builder.append("#");
			builder.append(endDate == null ? "" : endDate);
		} else {
			builder.append("#");
			builder.append(standardPrice == null ? 0.0 : standardPrice);
			builder.append("#");
			builder.append(priceUnit == null ? 1 : priceUnit);
			builder.append("#");
			builder.append(startDate == null ? "" : startDate);
			builder.append("#");
			builder.append(endDate == null ? "" : endDate);
			builder.append("#");
			builder.append(fieldInfo == null ? "" : fieldInfo);
			builder.append("#");
			builder.append(prices.size());
			for (Price price : prices) {
				builder.append("#");
				builder.append(price.toString());
			}
		}
		return builder.toString();
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public Double getStandardPriceInfo() {
		return this.standardPrice * 100;
	}

	@Override
	public int compareTo(PriceType o) {
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

}
