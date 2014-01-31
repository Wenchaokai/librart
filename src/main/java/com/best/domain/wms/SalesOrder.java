package com.best.domain.wms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:SalesOrder Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-4-5
 */
public class SalesOrder implements Serializable {

	private static final long serialVersionUID = -8584499729207575475L;

	public static final String CLASSIDENTIFY = "SALESORDER";

	public static final String[] mappingFields = new String[] { "createTime:SaleOrder-创建时间:date:CREATETIME:SALESORDER",
			"lastUpdateTime:SaleOrder-最后更新时间:date:LASTUPDATETIME:SALESORDER",
			"orderType:SaleOrder-订单类型:string:ORDERTYPE:SALESORDER", "wareHouseId:SaleOrder-仓库ID:long:WAREHOUSEID:SALESORDER",
			"customerId:SaleOrder-客户ID:long:CUSTOMERID:SALESORDER", "orderCode:SaleOrder-ERP订单号:string:ORDERCODE:SALESORDER",
			"refOrderCode:SaleOrder-销售订单号:string:REFORDERCODE:SALESORDER",
			"orderSource:SaleOrder-订单来源:string:ORDERSOURCE:SALESORDER", "orderTime:SaleOrder-下单时间:date:ORDERTIME:SALESORDER",
			"isValueDeclared:SaleOrder-是否保价:int:ISVALUEDECLARED:SALESORDER",
			"isPaymentCollected:SaleOrder-是否代收货款:int:ISPAYMENTCOLLECTED:SALESORDER",
			"mergeOrderFlag:SaleOrder-是否合并订单:int:MERGEORDERFLAG:SALESORDER",
			"mergeOrderCodes:SaleOrder-合并订单号:string:MERGEORDERCODES:SALESORDER",
			"invoiceFlag:SaleOrder-发票标记:int:INVOICEFLAG:SALESORDER", "note:SaleOrder-买家备注:string:NOTE:SALESORDER",
			"name:SaleOrder-买家姓名:string:NAME:SALESORDER", "phoneNumber:SaleOrder-收件人电话:string:PHONENUMBER:SALESORDER",
			"mobileNumber:SaleOrder-收件人手机:string:MOBILENUMBER:SALESORDER", "email:SaleOrder-收件人Email:string:EMAIL:SALESORDER",
			"postalCode:SaleOrder-收件人邮编:string:POSTALCODE:SALESORDER", "province:SaleOrder-收件人身份:string:PROVINCE:SALESORDER",
			"city:SaleOrder-收件人城市:string:CITY:SALESORDER", "district:SaleOrder-收件人所在区:string:DISTRICT:SALESORDER",
			"shippingAddress:SaleOrder-收件人详细地址:string:SHIPPINGADDRESS:SALESORDER",
			"buyerName:SaleOrder-下单人姓名:string:BUYERNAME:SALESORDER", "buyerPhone:SaleOrder-下单人手机号码:string:BUYERPHONE:SALESORDER",
			"logisticsProviderCode:SaleOrder-快递公司代码:string:LOGISTICSPROVIDERCODE:SALESORDER",
			"shippingOrderNo:SaleOrder-运单号:string:SHIPPINGORDERNO:SALESORDER",
			"shippingTime:SaleOrder-运输时间:date:SHIPPINGTIME:SALESORDER", "boxCodes:SaleOrder-包裹编码:string:BOXCODES:SALESORDER",
			"boxNames:SaleOrder-包裹名字:string:BOXNAMES:SALESORDER", "tmsCompany:SaleOrder-运输公司:string:TMSCOMPANY:SALESORDER",
			"tmsLinkMan:SaleOrder-运输联系人:string:TMSLINKMAN:SALESORDER", "tmsPhone:SaleOrder-运输公司电话:string:TMSPHONE:SALESORDER",
			"tmsLinkManNo:SaleOrder-运输公司联系人身份证号:string:TMSLINKMANNO:SALESORDER",
			"tmsShippingNo:SaleOrder-运输公司运单号:string:TMSSHIPPINGNO:SALESORDER", "status:SaleOrder-订单状态:string:STATUS:SALESORDER",
			"cancelStatus:SaleOrder-取消状态s:string:CANCELSTATUS:SALESORDER",
			"ldoStatus:SaleOrder-仓库状态:string:LDOSTATUS:SALESORDER",
			"acceptedNotifyStatus:SaleOrder-acceptedNotifyStatus:string:ACCEPTEDNOTIFYSTATUS:SALESORDER",
			"acceptFailNotifyStatus:SaleOrder-acceptFailNotifyStatus:string:ACCEPTFAILNOTIFYSTATUS:SALESORDER",
			"printedNotifyStatus:SaleOrder-printedNotifyStatus:string:PRINTEDNOTIFYSTATUS:SALESORDER",
			"pickupedNotifyStatus:SaleOrder-pickupedNotifyStatus:string:PICKUPEDNOTIFYSTATUS:SALESORDER",
			"weightedNotifyStatus:SaleOrder-weightedNotifyStatus:string:WEIGHTEDNOTIFYSTATUS:SALESORDER",
			"deliveredNotifyStatus:SaleOrder-deliveredNotifyStatus:string:DELIVEREDNOTIFYSTATUS:SALESORDER",
			"deliverFailNotifyStatus:SaleOrder-deliverFailNotifyStatus:string:DELIVERFAILNOTIFYSTATUS:SALESORDER",
			"canceledNotifyStatus:SaleOrder-canceledNotifyStatus:string:CANCELEDNOTIFYSTATUS:SALESORDER",
			"cancelMegId:SaleOrder-cancelMegId:string:CANCELMEGID:SALESORDER",
			"synMegId:SaleOrder-synMegId:string:SYNCMEGID:SALESORDER", "ldoCode:SaleOrder-订单编码:string:LDOCODE:SALESORDER",
			"totalAmount:SaleOrder-商品总金额:float:TOTALAMOUNT:SALESORDER",
			"shippingAmount:SaleOrder-运费:float:SHIPPINGAMOUNT:SALESORDER",
			"discountAmount:SaleOrder-折扣费:float:DISCOUNTAMOUNT:SALESORDER",
			"actualAmount:SaleOrder-实际支付费:float:ACTUALAMOUNT:SALESORDER",
			"declaringValueAmount:SaleOrder-报价金额:float:DECLARINGVALUEAMOUNT:SALESORDER",
			"collectingPaymentAmount:SaleOrder-代收货款金额:float:COLLECTINGPAYMENTAMOUNT:SALESORDER",
			"weight:SaleOrder-重量:float:WEIGHT:SALESORDER",
			"recommendedCarrier:SaleOrder-推荐承运商:string:RECOMMENDEDCARRIER:SALESORDER",
			"carrierSelectType:SaleOrder-carrierSelectType:string:CARRIERSELECTTYPE:SALESORDER",
			"carrierSelectStatus:SaleOrder-carrierSelectStatus:string:CARRIERSELECTSTATUS:SALESORDER",
			"auditFailureReason:SaleOrder-auditFailureReason:string:AUDITFAILUREREASON:SALESORDER",
			"auditRuleNote:SaleOrder-auditRuleNote:string:AUDITRULENOTE:SALESORDER",
			"length:SaleOrder-长:float:LENGTH:SALESORDER", "width:SaleOrder-宽:float:WIDTH:SALESORDER",
			"height:SaleOrder-高:float:HEIGHT:SALESORDER", "cubic:SaleOrder-cubic:float:CUBIC:SALESORDER",
			"extorderType:SaleOrder-外部订单类型:string:EXTORDERTYPE:SALESORDER",
			"shippingStatus:SaleOrder-shippingStatus:string:SHIPPINGSTATUS:SALESORDER",
			"errorNote:SaleOrder-errorNote:string:ERRORNOTE:SALESORDER",
			"errorTime:SaleOrder-errorTime:date:ERRORTIME:SALESORDER",
			"invoiceNote:SaleOrder-发票内容:string:INVOICENOTE:SALESORDER",
			"fetchGoodsLocation:SaleOrder-集货地:string:FETCHGOODSLOCATION:SALESORDER",
			"nostackTag:SaleOrder-无库存标记:string:NOSTACKTAG:SALESORDER",
			"wareHouseAcceptedTime:SaleOrder-wareHouseAcceptedTime:date:WAREHOUSEACCEPTEDTIME:SALESORDER",
			"priorityCode:SaleOrder-优先级:string:PRIORITYCODE:SALESORDER", "udfFlag:SaleOrder-UDFFLAG:int:UDFFLAG:SALESORDER",
			"udf1:SaleOrder-udf1:string:UDF1:SALESORDER", "udf2:SaleOrder-udf2:string:UDF2:SALESORDER",
			"udf3:SaleOrder-udf3:string:UDF3:SALESORDER", "udf4:SaleOrder-udf4:string:UDF4:SALESORDER",
			"udf5:SaleOrder-udf5:string:UDF5:SALESORDER", "udf6:SaleOrder-udf6:string:UDF6:SALESORDER",
			"udf7:SaleOrder-udf7:string:UDF7:SALESORDER", "udf8:SaleOrder-udf8:string:UDF8:SALESORDER",
			"subWarehouseStatus:SaleOrder-subWarehouseStatus:string:SUBWAREHOUSESTATUS:SALESORDER",
			"wmsRefuseSoStatus:SaleOrder-仓库拒单状态:string:WMSREFUSESOSTATUS:SALESORDER",
			"manualWarehouseId:SaleOrder-manualWarehouseId:int:MANUALWAREHOUSEID:SALESORDER",
			"cancelLdoStatus:SaleOrder-cancelLdoStatus:string:CANCELLDOSTATUS:SALESORDER",
			"cancelTime:SaleOrder-cancelTime:date:CANCELTIME:SALESORDER",
			"invoiceTitle:SaleOrder-发票抬头:string:INVOICETITLE:SALESORDER",
			"invoiceAmount:SaleOrder-发票金额:float:INVOICEAMOUNT:SALESORDER", "invoiceNo:SaleOrder-发票号:string:INVOICENO:SALESORDER",
			"splitResult:SaleOrder-splitResult:string:SPLITRESULT:SALESORDER",
			"splitOption:SaleOrder-splitOption:string:SPLITOPTION:SALESORDER",
			"shipmentsOption:SaleOrder-shipmentsOption:string:SHIPMENTSOPTION:SALESORDER",
			"beforeCancelStatus:SaleOrder-取消之前的状态:string:BEFORECANCELSTATUS:SALESORDER",
			"isSplit:SaleOrder-isSplit:int:ISSPLIT:SALESORDER", "paymentTime:SaleOrder-付款时间:date:PAYMENTTIME:SALESORDER" };

	private Long id;
	private Long version;
	private String createTime;
	private String lastUpdateTime;
	private String orderType;
	private Long wareHouseId;
	private Long customerId;
	private String orderCode;
	private String refOrderCode;
	private String orderSource;
	private String orderTime;
	private Integer isValueDeclared;
	private Integer isPaymentCollected;
	private Integer mergeOrderFlag;
	private String mergeOrderCodes;
	private Integer invoiceFlag;
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
	private String buyerName;
	private String buyerPhone;
	private String logisticsProviderCode;
	private String shippingOrderNo;
	private String shippingTime;
	private String boxCodes;
	private String boxNames;
	private String tmsCompany;
	private String tmsLinkMan;
	private String tmsPhone;
	private String tmsLinkManNo;
	private String tmsShippingNo;
	private String status;
	private String cancelStatus;
	private String ldoStatus;
	private String acceptedNotifyStatus;
	private String acceptFailNotifyStatus;
	private String printedNotifyStatus;
	private String pickupedNotifyStatus;
	private String weightedNotifyStatus;
	private String deliveredNotifyStatus;
	private String deliverFailNotifyStatus;
	private String canceledNotifyStatus;
	private String cancelMegId;
	private String synMegId;
	private String ldoCode;
	private Float totalAmount;
	private Float shippingAmount;
	private Float discountAmount;
	private Float actualAmount;
	private Float declaringValueAmount;
	private Float collectingPaymentAmount;
	private Float weight;
	private String recommendedCarrier;
	private String carrierSelectType;
	private String carrierSelectStatus;
	private String auditFailureReason;
	private String auditRuleNote;
	private Float length;
	private Float width;
	private Float height;
	private Float cubic;
	private String extorderType;
	private String shippingStatus;
	private String errorNote;
	private String errorTime;
	private String invoiceNote;
	private String fetchGoodsLocation;
	private String nostackTag;
	private String wareHouseAcceptedTime;
	private String priorityCode;
	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
	private String udf6;
	private String udf7;
	private String udf8;
	private String subWarehouseStatus;
	private String wmsRefuseSoStatus;
	private Integer manualWarehouseId;
	private String cancelLdoStatus;
	private String cancelTime;
	private String invoiceTitle;
	private Float invoiceAmount;
	private String invoiceNo;
	private String splitResult;
	private String splitOption;
	private String shipmentsOption;
	private String beforeCancelStatus;
	private Integer isSplit;
	private Integer paymentTime;

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

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
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

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getRefOrderCode() {
		return refOrderCode;
	}

	public void setRefOrderCode(String refOrderCode) {
		this.refOrderCode = refOrderCode;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getIsValueDeclared() {
		return isValueDeclared;
	}

	public void setIsValueDeclared(Integer isValueDeclared) {
		this.isValueDeclared = isValueDeclared;
	}

	public Integer getIsPaymentCollected() {
		return isPaymentCollected;
	}

	public void setIsPaymentCollected(Integer isPaymentCollected) {
		this.isPaymentCollected = isPaymentCollected;
	}

	public Integer getMergeOrderFlag() {
		return mergeOrderFlag;
	}

	public void setMergeOrderFlag(Integer mergeOrderFlag) {
		this.mergeOrderFlag = mergeOrderFlag;
	}

	public String getMergeOrderCodes() {
		return mergeOrderCodes;
	}

	public void setMergeOrderCodes(String mergeOrderCodes) {
		this.mergeOrderCodes = mergeOrderCodes;
	}

	public Integer getInvoiceFlag() {
		return invoiceFlag;
	}

	public void setInvoiceFlag(Integer invoiceFlag) {
		this.invoiceFlag = invoiceFlag;
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

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}

	public String getLogisticsProviderCode() {
		return logisticsProviderCode;
	}

	public void setLogisticsProviderCode(String logisticsProviderCode) {
		this.logisticsProviderCode = logisticsProviderCode;
	}

	public String getShippingOrderNo() {
		return shippingOrderNo;
	}

	public void setShippingOrderNo(String shippingOrderNo) {
		this.shippingOrderNo = shippingOrderNo;
	}

	public String getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}

	public String getBoxCodes() {
		return boxCodes;
	}

	public void setBoxCodes(String boxCodes) {
		this.boxCodes = boxCodes;
	}

	public String getBoxNames() {
		return boxNames;
	}

	public void setBoxNames(String boxNames) {
		this.boxNames = boxNames;
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

	public String getLdoStatus() {
		return ldoStatus;
	}

	public void setLdoStatus(String ldoStatus) {
		this.ldoStatus = ldoStatus;
	}

	public String getAcceptedNotifyStatus() {
		return acceptedNotifyStatus;
	}

	public void setAcceptedNotifyStatus(String acceptedNotifyStatus) {
		this.acceptedNotifyStatus = acceptedNotifyStatus;
	}

	public String getAcceptFailNotifyStatus() {
		return acceptFailNotifyStatus;
	}

	public void setAcceptFailNotifyStatus(String acceptFailNotifyStatus) {
		this.acceptFailNotifyStatus = acceptFailNotifyStatus;
	}

	public String getPrintedNotifyStatus() {
		return printedNotifyStatus;
	}

	public void setPrintedNotifyStatus(String printedNotifyStatus) {
		this.printedNotifyStatus = printedNotifyStatus;
	}

	public String getPickupedNotifyStatus() {
		return pickupedNotifyStatus;
	}

	public void setPickupedNotifyStatus(String pickupedNotifyStatus) {
		this.pickupedNotifyStatus = pickupedNotifyStatus;
	}

	public String getWeightedNotifyStatus() {
		return weightedNotifyStatus;
	}

	public void setWeightedNotifyStatus(String weightedNotifyStatus) {
		this.weightedNotifyStatus = weightedNotifyStatus;
	}

	public String getDeliveredNotifyStatus() {
		return deliveredNotifyStatus;
	}

	public void setDeliveredNotifyStatus(String deliveredNotifyStatus) {
		this.deliveredNotifyStatus = deliveredNotifyStatus;
	}

	public String getDeliverFailNotifyStatus() {
		return deliverFailNotifyStatus;
	}

	public void setDeliverFailNotifyStatus(String deliverFailNotifyStatus) {
		this.deliverFailNotifyStatus = deliverFailNotifyStatus;
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

	public String getSynMegId() {
		return synMegId;
	}

	public void setSynMegId(String synMegId) {
		this.synMegId = synMegId;
	}

	public String getLdoCode() {
		return ldoCode;
	}

	public void setLdoCode(String ldoCode) {
		this.ldoCode = ldoCode;
	}

	public Float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Float getShippingAmount() {
		return shippingAmount;
	}

	public void setShippingAmount(Float shippingAmount) {
		this.shippingAmount = shippingAmount;
	}

	public Float getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Float discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Float getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(Float actualAmount) {
		this.actualAmount = actualAmount;
	}

	public Float getDeclaringValueAmount() {
		return declaringValueAmount;
	}

	public void setDeclaringValueAmount(Float declaringValueAmount) {
		this.declaringValueAmount = declaringValueAmount;
	}

	public Float getCollectingPaymentAmount() {
		return collectingPaymentAmount;
	}

	public void setCollectingPaymentAmount(Float collectingPaymentAmount) {
		this.collectingPaymentAmount = collectingPaymentAmount;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public String getRecommendedCarrier() {
		return recommendedCarrier;
	}

	public void setRecommendedCarrier(String recommendedCarrier) {
		this.recommendedCarrier = recommendedCarrier;
	}

	public String getCarrierSelectType() {
		return carrierSelectType;
	}

	public void setCarrierSelectType(String carrierSelectType) {
		this.carrierSelectType = carrierSelectType;
	}

	public String getCarrierSelectStatus() {
		return carrierSelectStatus;
	}

	public void setCarrierSelectStatus(String carrierSelectStatus) {
		this.carrierSelectStatus = carrierSelectStatus;
	}

	public String getAuditFailureReason() {
		return auditFailureReason;
	}

	public void setAuditFailureReason(String auditFailureReason) {
		this.auditFailureReason = auditFailureReason;
	}

	public String getAuditRuleNote() {
		return auditRuleNote;
	}

	public void setAuditRuleNote(String auditRuleNote) {
		this.auditRuleNote = auditRuleNote;
	}

	public Float getLength() {
		return length;
	}

	public void setLength(Float length) {
		this.length = length;
	}

	public Float getWidth() {
		return width;
	}

	public void setWidth(Float width) {
		this.width = width;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public Float getCubic() {
		return cubic;
	}

	public void setCubic(Float cubic) {
		this.cubic = cubic;
	}

	public String getExtorderType() {
		return extorderType;
	}

	public void setExtorderType(String extorderType) {
		this.extorderType = extorderType;
	}

	public String getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public String getErrorNote() {
		return errorNote;
	}

	public void setErrorNote(String errorNote) {
		this.errorNote = errorNote;
	}

	public String getErrorTime() {
		return errorTime;
	}

	public void setErrorTime(String errorTime) {
		this.errorTime = errorTime;
	}

	public String getInvoiceNote() {
		return invoiceNote;
	}

	public void setInvoiceNote(String invoiceNote) {
		this.invoiceNote = invoiceNote;
	}

	public String getFetchGoodsLocation() {
		return fetchGoodsLocation;
	}

	public void setFetchGoodsLocation(String fetchGoodsLocation) {
		this.fetchGoodsLocation = fetchGoodsLocation;
	}

	public String getNostackTag() {
		return nostackTag;
	}

	public void setNostackTag(String nostackTag) {
		this.nostackTag = nostackTag;
	}

	public String getWareHouseAcceptedTime() {
		return wareHouseAcceptedTime;
	}

	public void setWareHouseAcceptedTime(String wareHouseAcceptedTime) {
		this.wareHouseAcceptedTime = wareHouseAcceptedTime;
	}

	public String getPriorityCode() {
		return priorityCode;
	}

	public void setPriorityCode(String priorityCode) {
		this.priorityCode = priorityCode;
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

	public String getSubWarehouseStatus() {
		return subWarehouseStatus;
	}

	public void setSubWarehouseStatus(String subWarehouseStatus) {
		this.subWarehouseStatus = subWarehouseStatus;
	}

	public String getWmsRefuseSoStatus() {
		return wmsRefuseSoStatus;
	}

	public void setWmsRefuseSoStatus(String wmsRefuseSoStatus) {
		this.wmsRefuseSoStatus = wmsRefuseSoStatus;
	}

	public Integer getManualWarehouseId() {
		return manualWarehouseId;
	}

	public void setManualWarehouseId(Integer manualWarehouseId) {
		this.manualWarehouseId = manualWarehouseId;
	}

	public String getCancelLdoStatus() {
		return cancelLdoStatus;
	}

	public void setCancelLdoStatus(String cancelLdoStatus) {
		this.cancelLdoStatus = cancelLdoStatus;
	}

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public Float getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Float invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getSplitResult() {
		return splitResult;
	}

	public void setSplitResult(String splitResult) {
		this.splitResult = splitResult;
	}

	public String getSplitOption() {
		return splitOption;
	}

	public void setSplitOption(String splitOption) {
		this.splitOption = splitOption;
	}

	public String getShipmentsOption() {
		return shipmentsOption;
	}

	public void setShipmentsOption(String shipmentsOption) {
		this.shipmentsOption = shipmentsOption;
	}

	public String getBeforeCancelStatus() {
		return beforeCancelStatus;
	}

	public void setBeforeCancelStatus(String beforeCancelStatus) {
		this.beforeCancelStatus = beforeCancelStatus;
	}

	public Integer getIsSplit() {
		return isSplit;
	}

	public void setIsSplit(Integer isSplit) {
		this.isSplit = isSplit;
	}

	public Integer getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Integer paymentTime) {
		this.paymentTime = paymentTime;
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
		java.lang.reflect.Field[] fields = SalesOrder.class.getDeclaredFields();
		StringBuilder builder = new StringBuilder();
		for (java.lang.reflect.Field field : fields) {
			builder.append("\"").append(field.getName()).append(":SaleOrder-").append(field.getName()).append(":");
			if (field.getType() == String.class)
				builder.append("string\",");
			else if (field.getType() == Long.class)
				builder.append("long\",");
			else if (field.getType() == Integer.class)
				builder.append("int\",");
			else if (field.getType() == Float.class)
				builder.append("float\",");

		}
		System.out.println(builder.toString());
	}

}
