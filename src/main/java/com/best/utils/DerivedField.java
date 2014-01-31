package com.best.utils;

import java.util.ArrayList;
import java.util.List;

import com.best.domain.wms.SystemField;

/**
 * ClassName:DerivedField Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-4-7
 */
public class DerivedField {
	public static final String[] mappingFields = new String[] { "createTime:入库明细-创建时间:date:CREATE_TIME:ASM_ITEM",
			"lastUpdateTime:入库明细-最后更新时间:date:LAST_UPDATE_TIME:ASM_ITEM", "asnId:入库明细-入库单ID:long:ASN_ID:ASM_ITEM",
			"ramId:入库明细-RMAID:long:RMA_ID:RMA_ITEM", "providerCode:入库明细-供应商编码:string:PROVIDER_CODE:ASM_ITEM",
			"providerName:入库明细-供应商名称:string:PROVIDER_NAME:ASM_ITEM", "skuCode:入库明细-SKU编码:string:SKU_CODE:ASM_ITEM",
			"name:入库明细-商品名称:string:NAME:ASM_ITEM", "quantity:入库明细-数量:int:QUANTITY:ASM_ITEM", "note:入库明细-备注:string:NOTE:ASM_ITEM",
			"lineNo:入库明细-行号:long:LINE_NO:ASM_ITEM", "fixStatusCode:入库明细-固定批次编码:string:FIX_STATUS_CODE:ASM_ITEM",
			"productionDate:入库明细-生产日期:date:PRODUCTION_DATE:ASM_ITEM", "expiryDate:入库明细-失效日期:date:EXPIRY_DATE:ASM_ITEM",
			"lotAtt01:入库明细-批次属性编码01:string:LOT_ATT01:ASM_ITEM", "lotAtt02:入库明细-批次属性编码02:string:LOT_ATT02:ASM_ITEM",
			"lotAtt03:入库明细-批次属性编码03:string:LOT_ATT03:ASM_ITEM", "lotAtt04:入库明细-批次属性编码04:string:LOT_ATT04:ASM_ITEM",
			"lotAtt05:入库明细-批次属性编码05:string:LOT_ATT05:ASM_ITEM", "lotAtt06:入库明细-批次属性编码06:string:LOT_ATT06:ASM_ITEM",
			"lotAtt07:入库明细-批次属性编码07:string:LOT_ATT07:ASM_ITEM", "lotAtt08:入库明细-批次属性编码08:string:LOT_ATT08:ASM_ITEM",
			"lotAtt09:入库明细-批次属性编码09:string:LOT_ATT09:ASM_ITEM", "lotAtt10:入库明细-批次属性编码10:string:LOT_ATT10:ASM_ITEM",
			"lotAtt11:入库明细-批次属性编码11:string:LOT_ATT11:ASM_ITEM", "lotAtt12:入库明细-批次属性编码12:string:LOT_ATT12:ASM_ITEM",
			"packCode:入库明细-包装编码:string:PACK_CODE:ASM_ITEM", "uomCode:入库明细-单位:string:UOM_CODE:ASM_ITEM",
			"unitPrice:入库明细-单价:double:UNIT_PRICE:ASM_ITEM", "providerFrom:入库明细-供应商形式:string:PROVIDER_FROM:ASM_ITEM",
			"udfFlag:入库明细-收货通知状态:int:UDFFLAG:ASM_ITEM", "udf1:入库明细-用户自定义字段1:string:UDF1:ASM_ITEM",
			"udf2:入库明细-用户自定义字段2:string:UDF2:ASM_ITEM", "udf3:入库明细-用户自定义字段3:string:UDF3:ASM_ITEM",
			"udf4:入库明细-用户自定义字段4:string:UDF4:ASM_ITEM", "udf5:入库明细-用户自定义字段5:string:UDF5:ASM_ITEM",
			"udf6:入库明细-用户自定义字段6:string:UDF6:ASM_ITEM", "udf7:入库明细-用户自定义字段7:string:UDF7:ASM_ITEM",
			"udf8:入库明细-用户自定义字段8:string:UDF8:ASM_ITEM", "createTime:出库明细-创建时间:date:CREATE_TIME:LDO_ITEM",
			"lastUpdateTime:出库明细-最后更新时间:date:LAST_UPDATE_TIME:LDO_ITEM", "ldoId:出库明细-订单ID:long:LDO_ID:LDO_ITEM",
			"manufacturerCode:出库明细-商品制造商编码:string:MANUFACTURER_CODE:LDO_ITEM", "skuCode:出库明细-SKU编码:string:SKU_CODE:LDO_ITEM",
			"skuDescrC:出库明细-SKU名称:string:SKU_DESCR_C:LDO_ITEM", "packCode:出库明细-包装编码:string:PACK_CODE:LDO_ITEM",
			"uomCode:出库明细-单位:string:UOM_CODE:LDO_ITEM", "orderLineNo:出库明细-行号:long:ORDER_LINE_NO:LDO_ITEM",
			"fixStatusCode:出库明细-固定批次编码:string:FIX_STATUS_CODE:LDO_ITEM",
			"fixCreatedTime:出库明细-生产日期:date:FIX_CREATED_TIME:LDO_ITEM", "fixExpiredTime:出库明细-失效日期:date:FIX_EXPIRED_TIME:LDO_ITEM",
			"lotAtt01:出库明细-批次属性编码01:string:LOT_ATT01:LDO_ITEM", "lotAtt02:出库明细-批次属性编码02:string:LOT_ATT02:LDO_ITEM",
			"lotAtt03:出库明细-批次属性编码03:string:LOT_ATT03:LDO_ITEM", "lotAtt04:出库明细-批次属性编码04:string:LOT_ATT04:LDO_ITEM",
			"lotAtt05:出库明细-批次属性编码05:string:LOT_ATT05:LDO_ITEM", "lotAtt06:出库明细-批次属性编码06:string:LOT_ATT06:LDO_ITEM",
			"lotAtt07:出库明细-批次属性编码07:string:LOT_ATT07:LDO_ITEM", "lotAtt08:出库明细-批次属性编码08:string:LOT_ATT08:LDO_ITEM",
			"lotAtt09:出库明细-批次属性编码09:string:LOT_ATT09:LDO_ITEM", "lotAtt10:出库明细-批次属性编码10:string:LOT_ATT10:LDO_ITEM",
			"lotAtt11:出库明细-批次属性编码11:string:LOT_ATT11:LDO_ITEM", "lotAtt12:出库明细-批次属性编码12:string:LOT_ATT12:LDO_ITEM",
			"pickZoneCode:出库明细-PickZoneCode:string:PICK_ZONE_CODE:LDO_ITEM",
			"picLocationCode:出库明细-PicLocationCode:string:PIC_LOCATION_CODE:LDO_ITEM",
			"shippedLocationCode:出库明细-ShippedLocationCode:string:SHIPPED_LOCATION_CODE:LDO_ITEM",
			"traceCode:出库明细-TraceCode:string:TRACE_CODE:LDO_ITEM",
			"rulRotationHeaderCode:出库明细-RulRotationHeaderCode:string:RUL_ROTATION_HEADER_CODE:LDO_ITEM",
			"reMark:出库明细-买家备注:string:RE_MARK:LDO_ITEM", "statusCode:出库明细-状态码:string:STATUS_CODE:LDO_ITEM",
			"udf1:出库明细-用户自定义字段1:string:UDF1:LDO_ITEM", "udf2:出库明细-用户自定义字段2:string:UDF2:LDO_ITEM",
			"udf3:出库明细-用户自定义字段3:string:UDF3:LDO_ITEM", "udf4:出库明细-用户自定义字段4:string:UDF4:LDO_ITEM",
			"udf5:出库明细-用户自定义字段5:string:UDF5:LDO_ITEM", "udf6:出库明细-用户自定义字段6:string:UDF6:LDO_ITEM",
			"udf7:出库明细-用户自定义字段7:string:UDF7:LDO_ITEM", "udf8:出库明细-用户自定义字段8:string:UDF8:LDO_ITEM",
			"qtyOrdered:出库明细-数量:double:QTY_ORDERED:LDO_ITEM", "cubic:出库明细-体积:double:CUBIC:LDO_ITEM",
			"grossWeight:出库明细-毛重:double:GROSS_WEIGHT:LDO_ITEM", "netWeight:出库明细-净重:double:NET_WEIGHT:LDO_ITEM",
			"price:出库明细-商品总价:double:PRICE:LDO_ITEM" };

	public static List<SystemField> systemFields = null;

	public static List<SystemField> getSystemFields() {

		systemFields = new ArrayList<SystemField>();
		for (String mappingField : mappingFields) {
			String params[] = mappingField.split(":");
			if (params.length < 5) {
				System.out.println("ERROR");
				continue;
			}
			SystemField field = new SystemField();
			field.setClassIdentify(params[4]);
			field.setFieldName(params[0]);
			field.setFieldDesc(params[1]);
			field.setFieldType(params[2]);
			field.setMappingField(params[3]);

			systemFields.add(field);
		}

		return systemFields;
	}
}
