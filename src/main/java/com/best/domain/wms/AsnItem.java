package com.best.domain.wms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:ASNItem Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-1-31
 */
public class AsnItem implements Serializable {

	private static final long serialVersionUID = 2555446892636563741L;

	public static final String CLASSIDENTIFY = "ASN_ITEM";

	public static final String[] mappingFields = new String[] { "createTime:入库明细-创建时间:int", "lastUpdateTime:入库明细-最后更新时间:int",
			"asnId:入库明细-入库单ID:long", "providerCode:入库明细-供应商编码:string", "providerName:入库明细-供应商名称:string",
			"skuCode:入库明细-SKU编码:string", "name:入库明细-商品名称:string", "quantity:入库明细-数量:int", "note:入库明细-备注:string",
			"lineNo:入库明细-行号:long", "fixStatusCode:入库明细-固定批次编码:string", "productionDate:入库明细-生产日期:int",
			"expiryDate:入库明细-失效日期:int", "lotAtt01:入库明细-批次属性编码01:string", "lotAtt02:入库明细-批次属性编码02:string",
			"lotAtt03:入库明细-批次属性编码03:string", "lotAtt04:入库明细-批次属性编码04:string", "lotAtt05:入库明细-批次属性编码05:string",
			"lotAtt06:入库明细-批次属性编码06:string", "lotAtt07:入库明细-批次属性编码07:string", "lotAtt08:入库明细-批次属性编码08:string",
			"lotAtt09:入库明细-批次属性编码09:string", "lotAtt10:入库明细-批次属性编码10:string", "lotAtt11:入库明细-批次属性编码11:string",
			"lotAtt12:入库明细-批次属性编码12:string", "packCode:入库明细-包装编码:string", "uomCode:入库明细-单位:string", "unitPrice:入库明细-单价:double",
			"providerFrom:入库明细-供应商形式:string", "udfFlag:入库明细-收货通知状态:int", "udf1:入库明细-用户自定义字段1:string",
			"udf2:入库明细-用户自定义字段2:string", "udf3:入库明细-用户自定义字段3:string", "udf4:入库明细-用户自定义字段4:string", "udf5:入库明细-用户自定义字段5:string",
			"udf6:入库明细-用户自定义字段6:string", "udf7:入库明细-用户自定义字段7:string", "udf8:入库明细-用户自定义字段8:string" };
	private Long id;
	private Long version;
	private String createTime;
	private String lastUpdateTime;
	private Long asnId;

	private String providerCode;
	private String providerName;
	private String skuCode;
	private String name;

	private Long quantity;
	private String note;
	private Long lineNo;
	private String fixStatusCode;
	private String productionDate;
	private String expiryDate;
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
	private String packCode;

	private String uomCode;
	private Float unitPrice;

	private String providerFrom;
	private Integer udfFlag;

	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
	private String udf6;
	private String udf7;
	private String udf8;

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

	public Long getAsnId() {
		return asnId;
	}

	public void setAsnId(Long asnId) {
		this.asnId = asnId;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getLineNo() {
		return lineNo;
	}

	public void setLineNo(Long lineNo) {
		this.lineNo = lineNo;
	}

	public String getFixStatusCode() {
		return fixStatusCode;
	}

	public void setFixStatusCode(String fixStatusCode) {
		this.fixStatusCode = fixStatusCode;
	}

	public String getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
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

	public Float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getProviderFrom() {
		return providerFrom;
	}

	public void setProviderFrom(String providerFrom) {
		this.providerFrom = providerFrom;
	}

	public Integer getUdfFlag() {
		return udfFlag;
	}

	public void setUdfFlag(Integer udfFlag) {
		this.udfFlag = udfFlag;
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
		List<SystemField> fields = getSystemFields(true);
		for (SystemField systemField : fields) {
			System.out
					.println(systemField.getFieldDesc() + "\t" + systemField.getFieldName() + "\t" + systemField.getFieldType());
		}
	}

}
