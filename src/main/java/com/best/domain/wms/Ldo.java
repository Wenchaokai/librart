package com.best.domain.wms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:LDO Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-1-31
 */
public class Ldo implements Serializable {

	private static final long serialVersionUID = -4776243608624762895L;

	public static final String CLASSIDENTIFY = "LDO";

	public static final String[] mappingFields = new String[] { "createTime:出库单-创建时间:date", "lastUpdateTime:出库单-最后更新时间:date",
			"orgCode:出库单-组织编码:string", "whCode:出库单-仓库编码:string", "customerCode:出库单-客户编码:string", "customerName:出库单-客户名称:string",
			"salesOrderNo:出库单-订单号:string", "relationNo:出库单-客户单号:string", "orderTypeCode:出库单-外部单据类型:string",
			"orderTime:出库单-下单时间:date", "fmExpectedShipmentTime:出库单-期望发货时间从:date", "toExpectedShipmentTime:出库单-期望发货时间到:date",
			"requiredDeliveryTime:出库单-要求送货时间:date", "soreference1:出库单-参考号1:string", "soreference2:出库单-参考号2:string",
			"soreference3:出库单-参考号3:string", "soreference4:出库单-参考号4:string", "consigneeCode:出库单-收货人Code:string",
			"consigneeName:出库单-收货人名字:string", "consigneeAddress:出库单-收货人地址:string", "consigneeLinkMann:出库单-收货联系人:string",
			"consigneePhone:出库单-收货人Phone:string", "consigneeProvince:出库单-收货人省份:string", "consigneeCity:出库单-收货人城市:string",
			"consigneeCantonCode:出库单-收货人区/县:string", "selletementCode:出库单-SelletementCode:string",
			"doorCode:出库单-DoorCode:string", "carrierAddress:出库单-CarrierAddress:string", "carrierCode:出库单-承运商编码:string",
			"carrierName:出库单承运商:string", "placeOfDischarge:出库单-PlaceOfDischarge:string",
			"placeOfLoading:出库单-PlaceOfLoading:string", "placeOfDelivery:出库单-PlaceOfDelivery:string",
			"deliveryVehicleNo:出库单-送货车牌号:string", "vehicleType:出库单-车辆类型:string", "driver:出库单-司机:string",
			"telephone:出库单-司机电话:string", "deliveryTermsCode:出库单-DeliveryTermsCode:string",
			"deliveryTermsDescr:出库单-DeliveryTermsDescr:string", "paymentTermsCode:出库单-PaymentTermsCode:string",
			"paymentTermsDescr:出库单-PaymentTermsDescr:string", "skuItem:出库单-SkuItem:string",
			"issueAddress:出库单-IssueAddress:string", "issueLinkMan:出库单-IssueLinkMan:string",
			"issuePartyCode:出库单-IssuePartyCode:string", "issuePartyName:出库单-IssuePartyName:string",
			"issuePhone:出库单-IssuePhone:string", "priorityCode:出库单-PriorityCode:string", "modeCode:出库单-订单作业类型:string",
			"reMark:出库单-买家备注:string", "statusCode:出库单-状态Code:string", "udf1:出库单-自定义字段UDF1:string", "udf2:出库单-自定义字段UDF2:string",
			"udf3:出库单-自定义字段UDF3:string", "udf4:出库单-自定义字段UDF4:string", "udf5:出库单-自定义字段UDF5:string", "udf6:出库单-自定义字段UDF6:string",
			"udf7:出库单-自定义字段UDF7:string", "udf8:出库单-自定义字段UDF8:string", "status:出库单-订单状态:string", "cancelStatus:出库单-取消标记:string",
			"sendToWmsStatus:出库单-下发WMS状态:string", "allocateFinishTime:出库单-订单分配时间:int", "allocatePerson:出库单-分配人:string",
			"pickupFinishTime:出库单-订单拣货时间:int", "pickPerson:出库单-拣货人:string", "checkFinishTime:出库单-订单验货时间:int",
			"checkPerson:出库单-验货人:string", "weighingTime:出库单-订单称重时间:int", "weighingPerson:出库单-称重人:string",
			"shipFinishTime:出库单-订单发货时间:int", "sendPerson:出库单-发货人:string", "boxCodes:出库单-包装编码:string", "boxNames:出库单-包装名称:string",
			"outsideNo:出库单-外部单号:string", "preCarrierCode:出库单-推荐承运商编码:string", "consigneeZipCode:出库单-收货人邮编:string",
			"totalAmount:出库单-订单总金额:double", "totalCubic:出库单-订单体积:double", "totalWeight:出库单-订单毛量:double",
			"totalNetWeight:出库单-订单净重:double", "totalPrice:出库单-商品总金额:double", "length:出库单-包裹长:double", "width:出库单-包裹宽:double",
			"height:出库单-包裹高:double", "cubic:出库单-包裹体积:double", "collectionAddress:出库单-CollectionAddress:string",
			"bigHeadPen:出库单-大头笔:string", "errorTime:出库单-错误时间:int", "errorNote:出库单-错误备注:string",
			"wareHouseAcceptedTime:出库单-WMS接单时间:int", "consigneeDistrict:出库单-ConsigneeDistrict:string",
			"isSettled:出库单-IsSettled:int", "invoiceContent:出库单-发票内容:string", "invoiceHead:出库单-发票抬头:string",
			"invoicePrice:出库单-发票金额:double", "invoiceCode:出库单-发票代面:string", "ldoType:出库单-出库单类型:string",
			"ldoActiveStatus:出库单-激活状态:string", "carrierSelectStatus:出库单-承运商选择状态:string", "carrierSelectType:出库单-承运商选择类型:string",
			"carrierAssignType:出库单-承运商分配类型:string", "carrierAssignResult:出库单-承运商分配结果:string",
			"outterOrderTypeCode:出库单-OutterOrderTypeCode:string", "codPrice:出库单-COD金额:double", "payPrice:出库单-已付款金额:double",
			"lackInventoryTag:出库单-库存不足标记:string", "tradeChannel:出库单-交易渠道:string", "discountCurrency:出库单-折扣金额:double",
			"freightCurrency:出库单-运费金额:double", "expressCollection:出库单-ExpressCollection:double",
			"stagedSoState:出库单-StagedSoState:string", "stagedSoPaidCurrency:出库单-StagedSoPaidCurrency:double",
			"paidTime:出库单-付款时间:int", "approvePassTime:出库单-通过时间:int", "isPrintInvoice:出库单-打印:string",
			"buyerNickName:出库单-购买者昵称:string", "email:出库单-购买者Email:string", "outerSendAddr:出库单-发货地址:string",
			"isNoticeBeforeSending:出库单-发货前是否提醒:string", "sendTime:出库单-发货时间:int", "partPickCode:出库单-PartPickCode:string",
			"deliveryPortName:出库单-DeliveryPortName:string", "expectedReceivedName:出库单-期望收货名字:string",
			"estimateArrivedDays:出库单-预估送达时间:double", "isConnectionBeforeSending:出库单-发货前是否联系:string",
			"isCashOnDelivery:出库单-是否COD订单:string", "packageNum:出库单-包裹数量:double", "packagePickTag:出库单-PackagePickTag:string",
			"wmsCreateTime:出库单-WMS接单时间:int", "wmsFulfilledTime:出库单-WMS发货时间:int", "wmsClosedTime:出库单-WMS关闭时间:int",
			"freezeStatus:出库单-冰冻状态:string" };

	private Long id;
	private Long version;
	private String createTime;
	private String lastUpdateTime;
	private String orgCode;
	private String whCode;
	private String customerCode;
	private String customerName;
	private String salesOrderNo;
	private String relationNo;
	private String orderTypeCode;
	private String orderTime;
	private String fmExpectedShipmentTime;
	private String toExpectedShipmentTime;
	private String requiredDeliveryTime;
	private String soreference1;
	private String soreference2;
	private String soreference3;
	private String soreference4;
	private String consigneeCode;
	private String consigneeName;
	private String consigneeAddress;
	private String consigneeLinkMann;
	private String consigneePhone;
	private String consigneeProvince;
	private String consigneeCity;
	private String consigneeCantonCode;
	private String selletementCode;
	private String doorCode;
	private String carrierAddress;
	private String carrierCode;
	private String carrierName;
	private String placeOfDischarge;
	private String placeOfLoading;
	private String placeOfDelivery;
	private String deliveryVehicleNo;
	private String vehicleType;
	private String driver;
	private String telephone;
	private String deliveryTermsCode;
	private String deliveryTermsDescr;
	private String paymentTermsCode;
	private String paymentTermsDescr;
	private String skuItem;
	private String issueAddress;
	private String issueLinkMan;
	private String issuePartyCode;
	private String issuePartyName;
	private String issuePhone;
	private String priorityCode;
	private String modeCode;
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
	private String status;
	private String cancelStatus;
	private String sendToWmsStatus;
	private String allocateFinishTime;
	private String allocatePerson;
	private String pickupFinishTime;
	private String pickPerson;
	private String checkFinishTime;
	private String checkPerson;
	private String weighingTime;
	private String weighingPerson;
	private String shipFinishTime;
	private String sendPerson;
	private String boxCodes;
	private String boxNames;
	private String outsideNo;
	private String preCarrierCode;
	private String consigneeZipCode;
	private Float totalAmount;
	private Float totalCubic;
	private Float totalWeight;
	private Float totalNetWeight;
	private Float totalPrice;
	private Float length;
	private Float width;
	private Float height;
	private Float cubic;
	private String collectionAddress;
	private String bigHeadPen;
	private String errorTime;
	private String errorNote;
	private String wareHouseAcceptedTime;
	private String consigneeDistrict;
	private Integer isSettled;
	private String invoiceContent;
	private String invoiceHead;
	private Float invoicePrice;
	private String invoiceCode;
	private String ldoType;
	private String ldoActiveStatus;
	private String carrierSelectStatus;
	private String carrierSelectType;
	private String carrierAssignType;
	private String carrierAssignResult;
	private String outterOrderTypeCode;
	private Float codPrice;
	private Float payPrice;
	private String lackInventoryTag;
	private String tradeChannel;
	private Float discountCurrency;
	private Float freightCurrency;
	private Float expressCollection;
	private String stagedSoState;
	private Float stagedSoPaidCurrency;
	private String paidTime;
	private String approvePassTime;
	private String isPrintInvoice;
	private String buyerNickName;
	private String email;
	private String outerSendAddr;
	private String isNoticeBeforeSending;
	private String sendTime;
	private String partPickCode;
	private String deliveryPortName;
	private String expectedReceivedName;
	private Float estimateArrivedDays;
	private String isConnectionBeforeSending;
	private String isCashOnDelivery;
	private Float packageNum;
	private String packagePickTag;
	private String wmsCreateTime;
	private String wmsFulfilledTime;
	private String wmsClosedTime;
	private String freezeStatus;

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

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSalesOrderNo() {
		return salesOrderNo;
	}

	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}

	public String getRelationNo() {
		return relationNo;
	}

	public void setRelationNo(String relationNo) {
		this.relationNo = relationNo;
	}

	public String getOrderTypeCode() {
		return orderTypeCode;
	}

	public void setOrderTypeCode(String orderTypeCode) {
		this.orderTypeCode = orderTypeCode;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getFmExpectedShipmentTime() {
		return fmExpectedShipmentTime;
	}

	public void setFmExpectedShipmentTime(String fmExpectedShipmentTime) {
		this.fmExpectedShipmentTime = fmExpectedShipmentTime;
	}

	public String getToExpectedShipmentTime() {
		return toExpectedShipmentTime;
	}

	public void setToExpectedShipmentTime(String toExpectedShipmentTime) {
		this.toExpectedShipmentTime = toExpectedShipmentTime;
	}

	public String getRequiredDeliveryTime() {
		return requiredDeliveryTime;
	}

	public void setRequiredDeliveryTime(String requiredDeliveryTime) {
		this.requiredDeliveryTime = requiredDeliveryTime;
	}

	public String getSoreference1() {
		return soreference1;
	}

	public void setSoreference1(String soreference1) {
		this.soreference1 = soreference1;
	}

	public String getSoreference2() {
		return soreference2;
	}

	public void setSoreference2(String soreference2) {
		this.soreference2 = soreference2;
	}

	public String getSoreference3() {
		return soreference3;
	}

	public void setSoreference3(String soreference3) {
		this.soreference3 = soreference3;
	}

	public String getSoreference4() {
		return soreference4;
	}

	public void setSoreference4(String soreference4) {
		this.soreference4 = soreference4;
	}

	public String getConsigneeCode() {
		return consigneeCode;
	}

	public void setConsigneeCode(String consigneeCode) {
		this.consigneeCode = consigneeCode;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public String getConsigneeLinkMann() {
		return consigneeLinkMann;
	}

	public void setConsigneeLinkMann(String consigneeLinkMann) {
		this.consigneeLinkMann = consigneeLinkMann;
	}

	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	public String getConsigneeProvince() {
		return consigneeProvince;
	}

	public void setConsigneeProvince(String consigneeProvince) {
		this.consigneeProvince = consigneeProvince;
	}

	public String getConsigneeCity() {
		return consigneeCity;
	}

	public void setConsigneeCity(String consigneeCity) {
		this.consigneeCity = consigneeCity;
	}

	public String getConsigneeCantonCode() {
		return consigneeCantonCode;
	}

	public void setConsigneeCantonCode(String consigneeCantonCode) {
		this.consigneeCantonCode = consigneeCantonCode;
	}

	public String getSelletementCode() {
		return selletementCode;
	}

	public void setSelletementCode(String selletementCode) {
		this.selletementCode = selletementCode;
	}

	public String getDoorCode() {
		return doorCode;
	}

	public void setDoorCode(String doorCode) {
		this.doorCode = doorCode;
	}

	public String getCarrierAddress() {
		return carrierAddress;
	}

	public void setCarrierAddress(String carrierAddress) {
		this.carrierAddress = carrierAddress;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getPlaceOfDischarge() {
		return placeOfDischarge;
	}

	public void setPlaceOfDischarge(String placeOfDischarge) {
		this.placeOfDischarge = placeOfDischarge;
	}

	public String getPlaceOfLoading() {
		return placeOfLoading;
	}

	public void setPlaceOfLoading(String placeOfLoading) {
		this.placeOfLoading = placeOfLoading;
	}

	public String getPlaceOfDelivery() {
		return placeOfDelivery;
	}

	public void setPlaceOfDelivery(String placeOfDelivery) {
		this.placeOfDelivery = placeOfDelivery;
	}

	public String getDeliveryVehicleNo() {
		return deliveryVehicleNo;
	}

	public void setDeliveryVehicleNo(String deliveryVehicleNo) {
		this.deliveryVehicleNo = deliveryVehicleNo;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getDeliveryTermsCode() {
		return deliveryTermsCode;
	}

	public void setDeliveryTermsCode(String deliveryTermsCode) {
		this.deliveryTermsCode = deliveryTermsCode;
	}

	public String getDeliveryTermsDescr() {
		return deliveryTermsDescr;
	}

	public void setDeliveryTermsDescr(String deliveryTermsDescr) {
		this.deliveryTermsDescr = deliveryTermsDescr;
	}

	public String getPaymentTermsCode() {
		return paymentTermsCode;
	}

	public void setPaymentTermsCode(String paymentTermsCode) {
		this.paymentTermsCode = paymentTermsCode;
	}

	public String getPaymentTermsDescr() {
		return paymentTermsDescr;
	}

	public void setPaymentTermsDescr(String paymentTermsDescr) {
		this.paymentTermsDescr = paymentTermsDescr;
	}

	public String getSkuItem() {
		return skuItem;
	}

	public void setSkuItem(String skuItem) {
		this.skuItem = skuItem;
	}

	public String getIssueAddress() {
		return issueAddress;
	}

	public void setIssueAddress(String issueAddress) {
		this.issueAddress = issueAddress;
	}

	public String getIssueLinkMan() {
		return issueLinkMan;
	}

	public void setIssueLinkMan(String issueLinkMan) {
		this.issueLinkMan = issueLinkMan;
	}

	public String getIssuePartyCode() {
		return issuePartyCode;
	}

	public void setIssuePartyCode(String issuePartyCode) {
		this.issuePartyCode = issuePartyCode;
	}

	public String getIssuePartyName() {
		return issuePartyName;
	}

	public void setIssuePartyName(String issuePartyName) {
		this.issuePartyName = issuePartyName;
	}

	public String getIssuePhone() {
		return issuePhone;
	}

	public void setIssuePhone(String issuePhone) {
		this.issuePhone = issuePhone;
	}

	public String getPriorityCode() {
		return priorityCode;
	}

	public void setPriorityCode(String priorityCode) {
		this.priorityCode = priorityCode;
	}

	public String getModeCode() {
		return modeCode;
	}

	public void setModeCode(String modeCode) {
		this.modeCode = modeCode;
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

	public String getAllocateFinishTime() {
		return allocateFinishTime;
	}

	public void setAllocateFinishTime(String allocateFinishTime) {
		this.allocateFinishTime = allocateFinishTime;
	}

	public String getAllocatePerson() {
		return allocatePerson;
	}

	public void setAllocatePerson(String allocatePerson) {
		this.allocatePerson = allocatePerson;
	}

	public String getPickupFinishTime() {
		return pickupFinishTime;
	}

	public void setPickupFinishTime(String pickupFinishTime) {
		this.pickupFinishTime = pickupFinishTime;
	}

	public String getPickPerson() {
		return pickPerson;
	}

	public void setPickPerson(String pickPerson) {
		this.pickPerson = pickPerson;
	}

	public String getCheckFinishTime() {
		return checkFinishTime;
	}

	public void setCheckFinishTime(String checkFinishTime) {
		this.checkFinishTime = checkFinishTime;
	}

	public String getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}

	public String getWeighingTime() {
		return weighingTime;
	}

	public void setWeighingTime(String weighingTime) {
		this.weighingTime = weighingTime;
	}

	public String getWeighingPerson() {
		return weighingPerson;
	}

	public void setWeighingPerson(String weighingPerson) {
		this.weighingPerson = weighingPerson;
	}

	public String getShipFinishTime() {
		return shipFinishTime;
	}

	public void setShipFinishTime(String shipFinishTime) {
		this.shipFinishTime = shipFinishTime;
	}

	public String getSendPerson() {
		return sendPerson;
	}

	public void setSendPerson(String sendPerson) {
		this.sendPerson = sendPerson;
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

	public String getOutsideNo() {
		return outsideNo;
	}

	public void setOutsideNo(String outsideNo) {
		this.outsideNo = outsideNo;
	}

	public String getPreCarrierCode() {
		return preCarrierCode;
	}

	public void setPreCarrierCode(String preCarrierCode) {
		this.preCarrierCode = preCarrierCode;
	}

	public String getConsigneeZipCode() {
		return consigneeZipCode;
	}

	public void setConsigneeZipCode(String consigneeZipCode) {
		this.consigneeZipCode = consigneeZipCode;
	}

	public Float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Float getTotalCubic() {
		return totalCubic;
	}

	public void setTotalCubic(Float totalCubic) {
		this.totalCubic = totalCubic;
	}

	public Float getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Float totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Float getTotalNetWeight() {
		return totalNetWeight;
	}

	public void setTotalNetWeight(Float totalNetWeight) {
		this.totalNetWeight = totalNetWeight;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
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

	public String getCollectionAddress() {
		return collectionAddress;
	}

	public void setCollectionAddress(String collectionAddress) {
		this.collectionAddress = collectionAddress;
	}

	public String getBigHeadPen() {
		return bigHeadPen;
	}

	public void setBigHeadPen(String bigHeadPen) {
		this.bigHeadPen = bigHeadPen;
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

	public String getWareHouseAcceptedTime() {
		return wareHouseAcceptedTime;
	}

	public void setWareHouseAcceptedTime(String wareHouseAcceptedTime) {
		this.wareHouseAcceptedTime = wareHouseAcceptedTime;
	}

	public String getConsigneeDistrict() {
		return consigneeDistrict;
	}

	public void setConsigneeDistrict(String consigneeDistrict) {
		this.consigneeDistrict = consigneeDistrict;
	}

	public Integer getIsSettled() {
		return isSettled;
	}

	public void setIsSettled(Integer isSettled) {
		this.isSettled = isSettled;
	}

	public String getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	public String getInvoiceHead() {
		return invoiceHead;
	}

	public void setInvoiceHead(String invoiceHead) {
		this.invoiceHead = invoiceHead;
	}

	public Float getInvoicePrice() {
		return invoicePrice;
	}

	public void setInvoicePrice(Float invoicePrice) {
		this.invoicePrice = invoicePrice;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getLdoType() {
		return ldoType;
	}

	public void setLdoType(String ldoType) {
		this.ldoType = ldoType;
	}

	public String getLdoActiveStatus() {
		return ldoActiveStatus;
	}

	public void setLdoActiveStatus(String ldoActiveStatus) {
		this.ldoActiveStatus = ldoActiveStatus;
	}

	public String getCarrierSelectStatus() {
		return carrierSelectStatus;
	}

	public void setCarrierSelectStatus(String carrierSelectStatus) {
		this.carrierSelectStatus = carrierSelectStatus;
	}

	public String getCarrierSelectType() {
		return carrierSelectType;
	}

	public void setCarrierSelectType(String carrierSelectType) {
		this.carrierSelectType = carrierSelectType;
	}

	public String getCarrierAssignType() {
		return carrierAssignType;
	}

	public void setCarrierAssignType(String carrierAssignType) {
		this.carrierAssignType = carrierAssignType;
	}

	public String getCarrierAssignResult() {
		return carrierAssignResult;
	}

	public void setCarrierAssignResult(String carrierAssignResult) {
		this.carrierAssignResult = carrierAssignResult;
	}

	public String getOutterOrderTypeCode() {
		return outterOrderTypeCode;
	}

	public void setOutterOrderTypeCode(String outterOrderTypeCode) {
		this.outterOrderTypeCode = outterOrderTypeCode;
	}

	public Float getCodPrice() {
		return codPrice;
	}

	public void setCodPrice(Float codPrice) {
		this.codPrice = codPrice;
	}

	public Float getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(Float payPrice) {
		this.payPrice = payPrice;
	}

	public String getLackInventoryTag() {
		return lackInventoryTag;
	}

	public void setLackInventoryTag(String lackInventoryTag) {
		this.lackInventoryTag = lackInventoryTag;
	}

	public String getTradeChannel() {
		return tradeChannel;
	}

	public void setTradeChannel(String tradeChannel) {
		this.tradeChannel = tradeChannel;
	}

	public Float getDiscountCurrency() {
		return discountCurrency;
	}

	public void setDiscountCurrency(Float discountCurrency) {
		this.discountCurrency = discountCurrency;
	}

	public Float getFreightCurrency() {
		return freightCurrency;
	}

	public void setFreightCurrency(Float freightCurrency) {
		this.freightCurrency = freightCurrency;
	}

	public Float getExpressCollection() {
		return expressCollection;
	}

	public void setExpressCollection(Float expressCollection) {
		this.expressCollection = expressCollection;
	}

	public String getStagedSoState() {
		return stagedSoState;
	}

	public void setStagedSoState(String stagedSoState) {
		this.stagedSoState = stagedSoState;
	}

	public Float getStagedSoPaidCurrency() {
		return stagedSoPaidCurrency;
	}

	public void setStagedSoPaidCurrency(Float stagedSoPaidCurrency) {
		this.stagedSoPaidCurrency = stagedSoPaidCurrency;
	}

	public String getPaidTime() {
		return paidTime;
	}

	public void setPaidTime(String paidTime) {
		this.paidTime = paidTime;
	}

	public String getApprovePassTime() {
		return approvePassTime;
	}

	public void setApprovePassTime(String approvePassTime) {
		this.approvePassTime = approvePassTime;
	}

	public String getIsPrintInvoice() {
		return isPrintInvoice;
	}

	public void setIsPrintInvoice(String isPrintInvoice) {
		this.isPrintInvoice = isPrintInvoice;
	}

	public String getBuyerNickName() {
		return buyerNickName;
	}

	public void setBuyerNickName(String buyerNickName) {
		this.buyerNickName = buyerNickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOuterSendAddr() {
		return outerSendAddr;
	}

	public void setOuterSendAddr(String outerSendAddr) {
		this.outerSendAddr = outerSendAddr;
	}

	public String getIsNoticeBeforeSending() {
		return isNoticeBeforeSending;
	}

	public void setIsNoticeBeforeSending(String isNoticeBeforeSending) {
		this.isNoticeBeforeSending = isNoticeBeforeSending;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getPartPickCode() {
		return partPickCode;
	}

	public void setPartPickCode(String partPickCode) {
		this.partPickCode = partPickCode;
	}

	public String getDeliveryPortName() {
		return deliveryPortName;
	}

	public void setDeliveryPortName(String deliveryPortName) {
		this.deliveryPortName = deliveryPortName;
	}

	public String getExpectedReceivedName() {
		return expectedReceivedName;
	}

	public void setExpectedReceivedName(String expectedReceivedName) {
		this.expectedReceivedName = expectedReceivedName;
	}

	public Float getEstimateArrivedDays() {
		return estimateArrivedDays;
	}

	public void setEstimateArrivedDays(Float estimateArrivedDays) {
		this.estimateArrivedDays = estimateArrivedDays;
	}

	public String getIsConnectionBeforeSending() {
		return isConnectionBeforeSending;
	}

	public void setIsConnectionBeforeSending(String isConnectionBeforeSending) {
		this.isConnectionBeforeSending = isConnectionBeforeSending;
	}

	public String getIsCashOnDelivery() {
		return isCashOnDelivery;
	}

	public void setIsCashOnDelivery(String isCashOnDelivery) {
		this.isCashOnDelivery = isCashOnDelivery;
	}

	public Float getPackageNum() {
		return packageNum;
	}

	public void setPackageNum(Float packageNum) {
		this.packageNum = packageNum;
	}

	public String getPackagePickTag() {
		return packagePickTag;
	}

	public void setPackagePickTag(String packagePickTag) {
		this.packagePickTag = packagePickTag;
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

	public String getFreezeStatus() {
		return freezeStatus;
	}

	public void setFreezeStatus(String freezeStatus) {
		this.freezeStatus = freezeStatus;
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
