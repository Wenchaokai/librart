package com.best.domain.wms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:ASN Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-1-31
 */
public class Asn implements Serializable {

	private static final long serialVersionUID = 2123935724860027504L;

	public static final String CLASSIDENTIFY = "ASN";

	public static final String[] mappingFields = new String[] { "createTime:入库单-创建时间:int", "lastUpdateTime:入库单-最后更新时间:int",
			"wareHouseId:入库单-仓库ID:long", "customerId:入库单-客户ID:long", "asnCode:入库单-编码:string", "refAsnCode:入库单-外部单号:string",
			"refTradeId:入库单-客户单号:string", "refOrderCode:入库单-原始订单号:string", "earliestArrivalTime:入库单-最早到货时间:int",
			"latestArrivalTime:入库单-最晚到货时间:int", "name:入库单-联系人名字:string", "phoneNumber:入库单-电话:string",
			"mobileNumber:入库单-手机:string", "email:入库单-Email:string", "postalCode:入库单-邮编:string", "province:入库单-省:string",
			"city:入库单-市:string", "district:入库单-区/县:string", "shippingAddress:入库单-地址:string", "tmsCompany:入库单-送货人公司:string",
			"tmsLinkMan:入库单-送货人:string", "tmsPhone:入库单-送货人Phone:string", "tmsLinkManNo:入库单-送货人No:string",
			"tmsShippingNo:入库单-送货人装单号:string", "status:入库单-入库单状态:string", "cancelStatus:入库单-取消状态:string",
			"sendToWmsStatus:入库单-下发WMS状态:string", "storedNotifyStatus:入库单-收货通知状态:string",
			"storeFailNotifyStatus:入库单-收货失败通知状态:string", "canceledNotifyStatus:入库单-取消通知状态:string",
			"cancelMegId:入库单-取消消息ID:string", "syncMegId:入库单-同步消息ID:string", "errorTime:入库单-错误时间:int",
			"errorNote:入库单-错误说明:string", "extOrderType:入库单-外部订单类型:string", "storeTime:入库单-收货时间:int",
			"wmsCreateTime:入库单-WMS创建时间:int", "wmsFulfilledTime:入库单-WMS收货时间:int", "wmsClosedTime:入库单-WMS关闭时间:int",
			"udfFlag:入库单-收货通知状态:int", "udf1:入库单-UDF1:string", "udf2:入库单-UDF2:string", "udf3:入库单-UDF3:string",
			"udf4:入库单-UDF4:string", "udf5:入库单-UDF5:string", "udf6:入库单-UDF6:string", "udf7:入库单-UDF7:string",
			"udf8:入库单-UDF8:string", "cancelTime:入库单-取消时间:int", "statusBeforeCancel:入库单-取消前状态:string",
			"checkStartTime:入库单-check开始时间:int", "checkStartTime:入库单-check结束时间:int" };

	private Long id;
	private Long version;
	private String createTime;
	private String lastUpdateTime;
	private Long wareHouseId;
	private Long customerId;
	private String asnCode;
	private String refAsnCode;
	private String refTradeId;
	private String refOrderCode;
	private String earliestArrivalTime;
	private String latestArrivalTime;
	private String note;
	private String name;
	private String phoneNumber;
	private String mobileNumber;
	private String email;
	private String postalCode;
	private String province;
	private String city;
	private String district;
	private String shippingAddress;
	private String tmsCompany;
	private String tmsLinkMan;
	private String tmsPhone;
	private String tmsLinkManNo;
	private String tmsShippingNo;

	private String status;
	private String cancelStatus;
	private String sendToWmsStatus;
	private String storedNotifyStatus;
	private String storeFailNotifyStatus;
	private String canceledNotifyStatus;
	private String cancelMegId;
	private String syncMegId;
	private String errorTime;
	private String errorNote;
	private String extOrderType;
	private String storeTime;
	private String wmsCreateTime;
	private String wmsFulfilledTime;
	private String wmsClosedTime;
	private Integer udfFlag;

	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
	private String udf6;
	private String udf7;
	private String udf8;
	private String cancelTime;
	private String statusBeforeCancel;
	private String checkStartTime;
	private String checkEndTime;

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

	public Long getWareHouseId() {
		return wareHouseId;
	}

	public void setWareHouseId(Long wareHouseId) {
		this.wareHouseId = wareHouseId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getAsnCode() {
		return asnCode;
	}

	public void setAsnCode(String asnCode) {
		this.asnCode = asnCode;
	}

	public String getRefAsnCode() {
		return refAsnCode;
	}

	public void setRefAsnCode(String refAsnCode) {
		this.refAsnCode = refAsnCode;
	}

	public String getRefTradeId() {
		return refTradeId;
	}

	public void setRefTradeId(String refTradeId) {
		this.refTradeId = refTradeId;
	}

	public String getRefOrderCode() {
		return refOrderCode;
	}

	public void setRefOrderCode(String refOrderCode) {
		this.refOrderCode = refOrderCode;
	}

	public String getEarliestArrivalTime() {
		return earliestArrivalTime;
	}

	public void setEarliestArrivalTime(String earliestArrivalTime) {
		this.earliestArrivalTime = earliestArrivalTime;
	}

	public String getLatestArrivalTime() {
		return latestArrivalTime;
	}

	public void setLatestArrivalTime(String latestArrivalTime) {
		this.latestArrivalTime = latestArrivalTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getTmsCompany() {
		return tmsCompany;
	}

	public void setTmsCompany(String tmsCompany) {
		this.tmsCompany = tmsCompany;
	}

	public String getTmsLinkMan() {
		return tmsLinkMan;
	}

	public void setTmsLinkMan(String tmsLinkMan) {
		this.tmsLinkMan = tmsLinkMan;
	}

	public String getTmsPhone() {
		return tmsPhone;
	}

	public void setTmsPhone(String tmsPhone) {
		this.tmsPhone = tmsPhone;
	}

	public String getTmsLinkManNo() {
		return tmsLinkManNo;
	}

	public void setTmsLinkManNo(String tmsLinkManNo) {
		this.tmsLinkManNo = tmsLinkManNo;
	}

	public String getTmsShippingNo() {
		return tmsShippingNo;
	}

	public void setTmsShippingNo(String tmsShippingNo) {
		this.tmsShippingNo = tmsShippingNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCancelStatus() {
		return cancelStatus;
	}

	public void setCancelStatus(String cancelStatus) {
		this.cancelStatus = cancelStatus;
	}

	public String getSendToWmsStatus() {
		return sendToWmsStatus;
	}

	public void setSendToWmsStatus(String sendToWmsStatus) {
		this.sendToWmsStatus = sendToWmsStatus;
	}

	public String getStoredNotifyStatus() {
		return storedNotifyStatus;
	}

	public void setStoredNotifyStatus(String storedNotifyStatus) {
		this.storedNotifyStatus = storedNotifyStatus;
	}

	public String getStoreFailNotifyStatus() {
		return storeFailNotifyStatus;
	}

	public void setStoreFailNotifyStatus(String storeFailNotifyStatus) {
		this.storeFailNotifyStatus = storeFailNotifyStatus;
	}

	public String getCanceledNotifyStatus() {
		return canceledNotifyStatus;
	}

	public void setCanceledNotifyStatus(String canceledNotifyStatus) {
		this.canceledNotifyStatus = canceledNotifyStatus;
	}

	public String getCancelMegId() {
		return cancelMegId;
	}

	public void setCancelMegId(String cancelMegId) {
		this.cancelMegId = cancelMegId;
	}

	public String getSyncMegId() {
		return syncMegId;
	}

	public void setSyncMegId(String syncMegId) {
		this.syncMegId = syncMegId;
	}

	public String getErrorTime() {
		return errorTime;
	}

	public void setErrorTime(String errorTime) {
		this.errorTime = errorTime;
	}

	public String getErrorNote() {
		return errorNote;
	}

	public void setErrorNote(String errorNote) {
		this.errorNote = errorNote;
	}

	public String getExtOrderType() {
		return extOrderType;
	}

	public void setExtOrderType(String extOrderType) {
		this.extOrderType = extOrderType;
	}

	public String getStoreTime() {
		return storeTime;
	}

	public void setStoreTime(String storeTime) {
		this.storeTime = storeTime;
	}

	public String getWmsCreateTime() {
		return wmsCreateTime;
	}

	public void setWmsCreateTime(String wmsCreateTime) {
		this.wmsCreateTime = wmsCreateTime;
	}

	public String getWmsFulfilledTime() {
		return wmsFulfilledTime;
	}

	public void setWmsFulfilledTime(String wmsFulfilledTime) {
		this.wmsFulfilledTime = wmsFulfilledTime;
	}

	public String getWmsClosedTime() {
		return wmsClosedTime;
	}

	public void setWmsClosedTime(String wmsClosedTime) {
		this.wmsClosedTime = wmsClosedTime;
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

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getStatusBeforeCancel() {
		return statusBeforeCancel;
	}

	public void setStatusBeforeCancel(String statusBeforeCancel) {
		this.statusBeforeCancel = statusBeforeCancel;
	}

	public String getCheckStartTime() {
		return checkStartTime;
	}

	public void setCheckStartTime(String checkStartTime) {
		this.checkStartTime = checkStartTime;
	}

	public String getCheckEndTime() {
		return checkEndTime;
	}

	public void setCheckEndTime(String checkEndTime) {
		this.checkEndTime = checkEndTime;
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
				System.out.println("ERROR");
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

	public static String convertInfo(String tt) {
		int len = tt.length();
		String ttt = "";
		for (int i = 0; i < len; i++) {
			if (tt.charAt(i) >= 'A' && tt.charAt(i) <= 'Z')
				ttt += "_" + (Character.toLowerCase(tt.charAt(i)));
			else
				ttt += tt.charAt(i);
		}
		return ttt;
	}

}
