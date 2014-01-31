package com.best.domain.wms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:LdoItem Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-1-31
 */
public class LdoItem implements Serializable {

	private static final long serialVersionUID = -4547357886266971272L;

	public static final String CLASSIDENTIFY = "LDO_ITEM";

	public static final String[] mappingFields = new String[] { "createTime:出库明细-创建时间:int", "lastUpdateTime:出库明细-最后更新时间:int",
			"ldoId:出库明细-订单ID:long", "manufacturerCode:出库明细-商品制造商编码:string", "skuCode:出库明细-SKU编码:string",
			"skuDescrC:出库明细-SKU名称:string", "packCode:出库明细-包装编码:string", "uomCode:出库明细-单位:string", "orderLineNo:出库明细-行号:long",
			"fixStatusCode:出库明细-固定批次编码:string", "fixCreatedTime:出库明细-生产日期:int", "fixExpiredTime:出库明细-失效日期:int",
			"lotAtt01:出库明细-批次属性编码01:string", "lotAtt02:出库明细-批次属性编码02:string", "lotAtt03:出库明细-批次属性编码03:string",
			"lotAtt04:出库明细-批次属性编码04:string", "lotAtt05:出库明细-批次属性编码05:string", "lotAtt06:出库明细-批次属性编码06:string",
			"lotAtt07:出库明细-批次属性编码07:string", "lotAtt08:出库明细-批次属性编码08:string", "lotAtt09:出库明细-批次属性编码09:string",
			"lotAtt10:出库明细-批次属性编码10:string", "lotAtt11:出库明细-批次属性编码11:string", "lotAtt12:出库明细-批次属性编码12:string",
			"pickZoneCode:出库明细-PickZoneCode:string", "picLocationCode:出库明细-PicLocationCode:string",
			"shippedLocationCode:出库明细-ShippedLocationCode:string", "traceCode:出库明细-TraceCode:string",
			"rulRotationHeaderCode:出库明细-RulRotationHeaderCode:string", "reMark:出库明细-买家备注:string", "statusCode:出库明细-状态码:string",
			"udf1:出库明细-用户自定义字段1:string", "udf2:出库明细-用户自定义字段2:string", "udf3:出库明细-用户自定义字段3:string", "udf4:出库明细-用户自定义字段4:string",
			"udf5:出库明细-用户自定义字段5:string", "udf6:出库明细-用户自定义字段6:string", "udf7:出库明细-用户自定义字段7:string", "udf8:出库明细-用户自定义字段8:string",
			"qtyOrdered:出库明细-数量:double", "cubic:出库明细-体积:double", "grossWeight:出库明细-毛重:double", "netWeight:出库明细-净重:double",
			"price:出库明细-商品总价:double" };
	private Long id;
	private Long version;
	private String createTime;
	private String lastUpdateTime;
	private Long ldoId;

	private String manufacturerCode;
	private String skuCode;
	private String skuDescrC;
	private String packCode;
	private String uomCode;
	private Long orderLineNo;
	private String fixStatusCode;
	private String fixCreatedTime;
	private String fixExpiredTime;
	private String lotAtt01;
	private String lotAtt02;
	private String lotAtt03;
	private String lotAtt04;
	private String lotAtt05;
	private String lotAtt06;
	private String lotAtt07;
	private String lotAtt08;
	private String lotAtt09;
	private String lotAtt10;
	private String lotAtt11;
	private String lotAtt12;
	private String pickZoneCode;
	private String picLocationCode;
	private String shippedLocationCode;
	private String traceCode;
	private String rulRotationHeaderCode;
	private String reMark;
	private String statusCode;
	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
	private String udf6;
	private String udf7;
	private String udf8;
	private Float qtyOrdered;
	private Float cubic;
	private Float grossWeight;
	private Float netWeight;
	private Float price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Long getLdoId() {
		return ldoId;
	}

	public void setLdoId(Long ldoId) {
		this.ldoId = ldoId;
	}

	public String getManufacturerCode() {
		return manufacturerCode;
	}

	public void setManufacturerCode(String manufacturerCode) {
		this.manufacturerCode = manufacturerCode;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getSkuDescrC() {
		return skuDescrC;
	}

	public void setSkuDescrC(String skuDescrC) {
		this.skuDescrC = skuDescrC;
	}

	public String getPackCode() {
		return packCode;
	}

	public void setPackCode(String packCode) {
		this.packCode = packCode;
	}

	public String getUomCode() {
		return uomCode;
	}

	public void setUomCode(String uomCode) {
		this.uomCode = uomCode;
	}

	public Long getOrderLineNo() {
		return orderLineNo;
	}

	public void setOrderLineNo(Long orderLineNo) {
		this.orderLineNo = orderLineNo;
	}

	public String getFixStatusCode() {
		return fixStatusCode;
	}

	public void setFixStatusCode(String fixStatusCode) {
		this.fixStatusCode = fixStatusCode;
	}

	public String getFixCreatedTime() {
		return fixCreatedTime;
	}

	public void setFixCreatedTime(String fixCreatedTime) {
		this.fixCreatedTime = fixCreatedTime;
	}

	public String getFixExpiredTime() {
		return fixExpiredTime;
	}

	public void setFixExpiredTime(String fixExpiredTime) {
		this.fixExpiredTime = fixExpiredTime;
	}

	public String getLotAtt01() {
		return lotAtt01;
	}

	public void setLotAtt01(String lotAtt01) {
		this.lotAtt01 = lotAtt01;
	}

	public String getLotAtt02() {
		return lotAtt02;
	}

	public void setLotAtt02(String lotAtt02) {
		this.lotAtt02 = lotAtt02;
	}

	public String getLotAtt03() {
		return lotAtt03;
	}

	public void setLotAtt03(String lotAtt03) {
		this.lotAtt03 = lotAtt03;
	}

	public String getLotAtt04() {
		return lotAtt04;
	}

	public void setLotAtt04(String lotAtt04) {
		this.lotAtt04 = lotAtt04;
	}

	public String getLotAtt05() {
		return lotAtt05;
	}

	public void setLotAtt05(String lotAtt05) {
		this.lotAtt05 = lotAtt05;
	}

	public String getLotAtt06() {
		return lotAtt06;
	}

	public void setLotAtt06(String lotAtt06) {
		this.lotAtt06 = lotAtt06;
	}

	public String getLotAtt07() {
		return lotAtt07;
	}

	public void setLotAtt07(String lotAtt07) {
		this.lotAtt07 = lotAtt07;
	}

	public String getLotAtt08() {
		return lotAtt08;
	}

	public void setLotAtt08(String lotAtt08) {
		this.lotAtt08 = lotAtt08;
	}

	public String getLotAtt09() {
		return lotAtt09;
	}

	public void setLotAtt09(String lotAtt09) {
		this.lotAtt09 = lotAtt09;
	}

	public String getLotAtt10() {
		return lotAtt10;
	}

	public void setLotAtt10(String lotAtt10) {
		this.lotAtt10 = lotAtt10;
	}

	public String getLotAtt11() {
		return lotAtt11;
	}

	public void setLotAtt11(String lotAtt11) {
		this.lotAtt11 = lotAtt11;
	}

	public String getLotAtt12() {
		return lotAtt12;
	}

	public void setLotAtt12(String lotAtt12) {
		this.lotAtt12 = lotAtt12;
	}

	public String getPickZoneCode() {
		return pickZoneCode;
	}

	public void setPickZoneCode(String pickZoneCode) {
		this.pickZoneCode = pickZoneCode;
	}

	public String getPicLocationCode() {
		return picLocationCode;
	}

	public void setPicLocationCode(String picLocationCode) {
		this.picLocationCode = picLocationCode;
	}

	public String getShippedLocationCode() {
		return shippedLocationCode;
	}

	public void setShippedLocationCode(String shippedLocationCode) {
		this.shippedLocationCode = shippedLocationCode;
	}

	public String getTraceCode() {
		return traceCode;
	}

	public void setTraceCode(String traceCode) {
		this.traceCode = traceCode;
	}

	public String getRulRotationHeaderCode() {
		return rulRotationHeaderCode;
	}

	public void setRulRotationHeaderCode(String rulRotationHeaderCode) {
		this.rulRotationHeaderCode = rulRotationHeaderCode;
	}

	public String getReMark() {
		return reMark;
	}

	public void setReMark(String reMark) {
		this.reMark = reMark;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getUdf1() {
		return udf1;
	}

	public void setUdf1(String udf1) {
		this.udf1 = udf1;
	}

	public String getUdf2() {
		return udf2;
	}

	public void setUdf2(String udf2) {
		this.udf2 = udf2;
	}

	public String getUdf3() {
		return udf3;
	}

	public void setUdf3(String udf3) {
		this.udf3 = udf3;
	}

	public String getUdf4() {
		return udf4;
	}

	public void setUdf4(String udf4) {
		this.udf4 = udf4;
	}

	public String getUdf5() {
		return udf5;
	}

	public void setUdf5(String udf5) {
		this.udf5 = udf5;
	}

	public String getUdf6() {
		return udf6;
	}

	public void setUdf6(String udf6) {
		this.udf6 = udf6;
	}

	public String getUdf7() {
		return udf7;
	}

	public void setUdf7(String udf7) {
		this.udf7 = udf7;
	}

	public String getUdf8() {
		return udf8;
	}

	public void setUdf8(String udf8) {
		this.udf8 = udf8;
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

	public Float getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Float grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Float getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Float netWeight) {
		this.netWeight = netWeight;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public static String getClassIdentify() {
		return CLASSIDENTIFY;
	}

	public static List<SystemField> systemFields = null;

	public static List<SystemField> getSystemFields(boolean onlyNumber) {

		systemFields = new ArrayList<SystemField>();
		for (String mappingField : mappingFields) {
			String params[] = mappingField.split(":");
			if (params.length != 3) {
				System.out.println("Error");
				continue;
			}
			SystemField field = new SystemField();
			field.setClassIdentify(getClassIdentify());
			field.setFieldName(params[0]);
			field.setFieldDesc(params[1]);
			field.setFieldType(params[2]);
			if (onlyNumber) {
				if (!"string".equals(field.getFieldType()) && !"date".equals(field.getFieldType()))
					systemFields.add(field);
			} else
				systemFields.add(field);
		}

		return systemFields;
	}

	public static void main(String[] args) {
		List<SystemField> fields = getSystemFields(false);
		for (SystemField systemField : fields) {
			System.out
					.println(systemField.getFieldDesc() + "\t" + systemField.getFieldName() + "\t" + systemField.getFieldType());
		}
	}

}
