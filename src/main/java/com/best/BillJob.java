package com.best;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jxl.write.WriteException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.best.dao.DeriveFieldDao;
import com.best.dao.EcbossDao;
import com.best.dao.FieldDao;
import com.best.dao.FieldWarmDao;
import com.best.dao.InventoryDao;
import com.best.domain.Bill;
import com.best.domain.BillDetail;
import com.best.domain.Field;
import com.best.domain.FieldType;
import com.best.domain.FieldValue;
import com.best.domain.FieldWarm;
import com.best.domain.Inventory;
import com.best.domain.MinPrice;
import com.best.domain.Price;
import com.best.domain.PriceType;
import com.best.domain.Subject;
import com.best.domain.wms.Asn;
import com.best.domain.wms.AsnItem;
import com.best.domain.wms.Ldo;
import com.best.domain.wms.LdoItem;
import com.best.domain.wms.SystemField;
import com.best.service.BillDetailService;
import com.best.service.BillService;
import com.best.service.SubjectService;
import com.best.utils.DateUtil;
import com.best.utils.DerivedField;
import com.best.utils.FieldValueCacheUtils;

/**
 * ClassName:BillJob Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-2-11
 */
public class BillJob {

	private static final Logger LOG = LoggerFactory.getLogger(BillJob.class);

	@Autowired
	private BillService billService;

	@Autowired
	private BillDetailService billDetailService;

	@Autowired
	private FieldWarmDao fieldWarmDao;

	@Autowired
	private FieldDao fieldDao;

	@Autowired
	private EcbossDao ecbossDao;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private DeriveFieldDao deriveFieldDao;

	@Autowired
	private InventoryDao inventoryDao;

	private String baseDir;

	private ScheduledExecutorService scheduleService = Executors.newScheduledThreadPool(2);

	public void init() {

		scheduleService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				String currentTime = DateUtil.getCurrentDateString();
				List<Bill> nonFinishBills = billService.listAllNonFinishBill(currentTime);
				for (Bill bill : nonFinishBills) {
					LOG.warn(">>>>>>>>>>>>Start Caculate Bill={} <<<<<<<<<<<<", bill.getBillName());
					String billBelongTime = bill.getBillBelongTime();
					String startTime = bill.getBillStartTime();
					String endTime = bill.getBillEndTime();

					String wareHouseCode = bill.getWareHouseCode();
					String customerCode = bill.getCustomerCode();
					if (StringUtils.isEmpty(wareHouseCode) || StringUtils.isEmpty(customerCode))
						continue;

					double allSum = 0.0;
					List<Subject> subjects = subjectService.listSubjectById(bill.getBillProjectId());
					for (Subject subject : subjects) {
						LOG.warn(">>>>>>>>>>>>Start Caculate Bill={},Subject={}<<<<<<<<<<<<", bill.getBillName(),
								subject.getSubjectName());
						List<FieldType> fieldTypes = FieldType.parseFieldString(subject.getSubjectFieldCustom());
						List<PriceType> priceTypes = PriceType.parsePriceString(subject.getSubjectPrice());

						List<FieldType> readyFieldTypes = new ArrayList<FieldType>();
						List<FieldType> finishFieldTypes = new ArrayList<FieldType>();
						Set<Integer> tables = new HashSet<Integer>();

						List<Field> deriveFields = new ArrayList<Field>();

						Set<Integer> hasSetedFieldIds = new HashSet<Integer>();

						if (null != fieldTypes) {
							// 所有的都是衍生字段
							for (FieldType fieldType : fieldTypes) {
								try {
									hasSetedFieldIds.add(Integer.parseInt(fieldType.getFieldInfo().trim()));
								} catch (Exception e) {
									// TODO: handle exception
								}
								if (fieldType.getFieldType() == 1) {
									// 系统定义的
									try {
										Field field = fieldDao.getField(Integer.parseInt(fieldType.getFieldInfo().trim()));
										if (field == null)
											fieldType.setFieldValue(0.0);
										else {
											if (field.getFieldType() == 2) {
												FieldWarm fieldWarm = fieldWarmDao.getFieldWarm(bill.getBillProjectId(),
														field.getFieldId());
												if (fieldWarm != null)
													fieldType.setFieldWarmValue(fieldWarm.getHistoryRecord());
											} else {
												deriveFields.add(field);
												continue;
											}
										}
										finishFieldTypes.add(fieldType);
									} catch (Exception e) {

										// 属于系统字段的值
										readyFieldTypes.add(fieldType);
									}
								} else {
									// 自定义的
									try {
										Field field = fieldDao.getField(Integer.parseInt(fieldType.getFieldInfo().trim()));
										if (field.getFieldType() != 3)
											readyFieldTypes.add(fieldType);
										else {
											field.setFieldTypes(fieldType.getFields());
											deriveFields.add(field);
										}
									} catch (Exception e) {

										// 属于系统字段的值
										readyFieldTypes.add(fieldType);
									}

								}
							}
						}

						List<Field> allFields = subject.getFields();
						if (null != allFields) {
							for (Field tmpField : allFields) {
								if (hasSetedFieldIds.contains(tmpField.getFieldId()))
									continue;

								tmpField = fieldDao.getField(tmpField.getFieldId());

								if (tmpField.getFieldType() == 3) {

									deriveFields.add(tmpField);
									continue;
								}

								FieldType fieldType = new FieldType();
								fieldType.setFieldInfo(tmpField.getFieldId() + "");
								fieldType.setFieldType(1);

								Field field = fieldDao.getField(tmpField.getFieldId());
								if (field == null)
									fieldType.setFieldValue(0.0);
								else {
									if (field.getFieldType() == 2) {
										FieldWarm fieldWarm = fieldWarmDao.getFieldWarm(bill.getBillProjectId(),
												field.getFieldId());
										if (fieldWarm != null)
											fieldType.setFieldWarmValue(fieldWarm.getHistoryRecord());
									} else {
										deriveFields.add(field);
										continue;
									}
								}
								finishFieldTypes.add(fieldType);

							}
						}

						List<SystemField> systemFields = subject.getSystemFields();
						for (SystemField systemField : systemFields) {
							int name = getTableType(systemField.getClassIdentify());
							if (name == -1)
								systemField.setFieldValue(0.0);
							else
								tables.add(name);
						}
						for (FieldType fieldType : readyFieldTypes) {
							if (fieldType.getFieldType() == 1) {
								// 属于系统字段
								String fieldInfo = fieldType.getFieldInfo();
								String[] params = fieldInfo.split("-");
								int name = getTableType(params[0]);
								if (name != -1)
									tables.add(name);
							} else if (fieldType.getFieldType() == 2) {
								// 自定义
								List<FieldValue> fieldValues = fieldType.getFields();
								for (FieldValue fieldValue : fieldValues) {
									String fieldInfo = fieldValue.getFieldInfo();
									String[] params = fieldInfo.split("-");
									int name = getTableType(params[0]);
									if (name != -1)
										tables.add(name);
								}
							}
						}
						// 处理未完成的Field
						String nextStartTime = startTime;
						boolean flag = true;
						double result = 0.0;
						while (nextStartTime.compareTo(endTime) <= 0) {

							try {
								String nextEndTime = DateUtil.getNextDate(nextStartTime);
								Map<Integer, Object> map = new HashMap<Integer, Object>();
								for (Integer type : tables) {
									if (type == 1) {
										List<Asn> asns = ecbossDao.listAsns(customerCode, wareHouseCode, nextStartTime,
												nextEndTime);
										map.put(type, asns);
									} else if (type == 2) {
										List<AsnItem> asnItems = ecbossDao.listAsnItems(customerCode, wareHouseCode,
												nextStartTime, nextEndTime);
										map.put(type, asnItems);
									} else if (type == 3) {
										List<Ldo> ldos = ecbossDao.listLdos(customerCode, wareHouseCode, nextStartTime,
												nextEndTime);
										map.put(type, ldos);
									} else if (type == 4) {
										List<LdoItem> ldoItems = ecbossDao.listLdoItems(new ArrayList<String>(), customerCode,
												wareHouseCode, nextStartTime, nextEndTime);
										map.put(type, ldoItems);
									}
								}

								Map<String, Object> paramMap = new HashMap<String, Object>();
								paramMap.put("customerCode", customerCode);
								paramMap.put("warehouseCode", wareHouseCode);
								paramMap.put("startTime", nextStartTime);
								paramMap.put("endTime", nextEndTime);
								for (Field deriveField : deriveFields) {
									if (deriveField.fields != null && deriveField.fields.size() > 0) {
										// 加参数
										StringBuilder sb = new StringBuilder();
										List<SystemField> systems = DerivedField.getSystemFields();
										for (FieldValue fieldValue : deriveField.fields) {
											String[] params = fieldValue.fieldInfo.split("-");
											if (params.length != 2)
												continue;
											for (SystemField field : systems) {
												if (field.getClassIdentify().equals(params[0])
														&& field.getFieldName().equals(params[1])) {
													sb.append(" AND ");
													sb.append(field.getMappingField()).append(fieldValue.getCompareString());
													if (field.getFieldType().equals("date")) {
														sb.append("to_date('").append(fieldValue.getValue())
																.append("','yyyyMMdd') ");
													} else if (field.getFieldType().equals("string")) {
														sb.append("\'").append(fieldValue.getValue()).append("\' ");
													} else {
														sb.append(fieldValue.getValue());
													}
													break;
												}
											}
										}
										paramMap.put("param", sb.toString());
									}
									deriveField.setFieldValue(0.0);
									Object obj = 0.0;
									if (deriveField.getFieldTypeBaobiaoType() != 0)
										obj = deriveFieldDao.getData(paramMap, deriveField.getFieldTypeMapping());
									else {
										Map<String, Object> paramMap2 = new HashMap<String, Object>();
										paramMap2.put("subjectId", subject.getSubjectId());
										paramMap2.put("projectId", bill.getBillProjectId());
										paramMap2.put("fieldId", deriveField.getFieldId());
										paramMap2.put("dateTime", nextStartTime);
										Inventory inventory = inventoryDao.getInventory(paramMap2);

										if (null == inventory || inventory.getValue() == null)
											obj = 0;
										else
											obj = inventory.getValue();
									}
									if (obj != null && obj instanceof Integer) {
										deriveField.setFieldValue(((Integer) obj).doubleValue());
									}

									if (deriveField.getFieldTypeBaobiaoType() == 0) {
										// 库存表的东西
										deriveField.writeInteger(bill.getBillId(), nextStartTime, deriveField.getFieldValue()
												.intValue(), baseDir, bill.getBillProjectName(), subject.getSubjectName(), bill
												.getBillStartTime(), bill.getBillEndTime());
									} else {
										if (StringUtils.isNotBlank(deriveField.getFieldTypeBaobiaoMapping())) {
											List<Object> list = deriveFieldDao.getDataList(paramMap,
													deriveField.getFieldTypeBaobiaoMapping());
											deriveField.writeList(bill.getBillId(), nextStartTime, list, baseDir,
													bill.getBillProjectName(), subject.getSubjectName(), bill.getBillStartTime(),
													bill.getBillEndTime());
										}
									}
								}

								for (SystemField systemField : systemFields) {
									systemField.setFieldValue(0.0);
									int name = getTableType(systemField.getClassIdentify());
									List obj = (List) map.get(name);
									if (obj != null) {
										for (Object object : obj) {
											Object value = FieldValueCacheUtils.getFieldValue(systemField.getFieldName(), object);
											if (value instanceof Number) {
												systemField.addFieldValue(((Number) value).doubleValue());
											} else if (value instanceof String) {
												try {
													systemField.addFieldValue(Long.valueOf(value.toString()).doubleValue());
													continue;
												} catch (Exception e) {

												}
												try {
													systemField.addFieldValue(Double.valueOf(value.toString()).doubleValue());
													continue;
												} catch (Exception e) {

												}
											}
										}
									}
								}
								for (FieldType fieldType : readyFieldTypes) {
									fieldType.setFieldValue(0.0);
									if (fieldType.getFieldType() == 1) {
										// 属于系统字段
										String fieldInfo = fieldType.getFieldInfo();
										String[] params = fieldInfo.split("-");
										int name = getTableType(params[0]);
										List obj = (List) map.get(name);
										if (obj != null) {
											for (Object object : obj) {
												Object value = FieldValueCacheUtils.getFieldValue(params[1], object);
												if (value instanceof Number) {
													fieldType.addFieldValue(((Number) value).doubleValue());
												} else if (value instanceof String) {
													try {
														fieldType.addFieldValue(Long.valueOf(value.toString()).doubleValue());
													} catch (Exception e) {

													}
													try {
														fieldType.addFieldValue(Double.valueOf(value.toString()).doubleValue());
													} catch (Exception e) {

													}
												}
											}
										}
									} else {
										// 属于自定义字段
										List<FieldValue> fieldValues = fieldType.getFields();
										boolean fieldFlag = true;
										for (FieldValue fieldValue : fieldValues) {

											String fieldInfo = fieldValue.getFieldInfo();
											String[] params = fieldInfo.split("-");
											int name = getTableType(params[0]);
											List obj = (List) map.get(name);
											if (obj != null) {
												for (Object object : obj) {
													fieldFlag = true;
													Object value = FieldValueCacheUtils.getFieldValue(params[1], object);
													if (value instanceof Number) {
														Double temp = ((Number) value).doubleValue();
														if (fieldValue.getCompare() == FieldValue.LEFT_TYPE
																&& temp < Double.parseDouble(fieldValue.getValue())) {

														} else if (fieldValue.getCompare() == FieldValue.RIGHT_TYPE
																&& temp > Double.parseDouble(fieldValue.getValue())) {

														} else if (fieldValue.getCompare() == FieldValue.LEFT_EQUAL_TYPE
																&& temp <= Double.parseDouble(fieldValue.getValue())) {

														} else if (fieldValue.getCompare() == FieldValue.RIGHT_EQUAL_TYPE
																&& temp >= Double.parseDouble((fieldValue.getValue()))) {

														} else if (fieldValue.getCompare() == FieldValue.EQUAL_TYPE
																&& temp == Double.parseDouble(fieldValue.getValue())) {

														} else {
															fieldFlag = false;
														}
													} else if (value instanceof String) {
														String temp = (String) value;
														if (fieldValue.getCompare() == FieldValue.LEFT_TYPE
																&& temp.compareTo(fieldValue.getValue()) < 0) {

														} else if (fieldValue.getCompare() == FieldValue.RIGHT_TYPE
																&& temp.compareTo(fieldValue.getValue()) > 0) {

														} else if (fieldValue.getCompare() == FieldValue.LEFT_EQUAL_TYPE
																&& temp.compareTo(fieldValue.getValue()) <= 0) {

														} else if (fieldValue.getCompare() == FieldValue.RIGHT_EQUAL_TYPE
																&& temp.compareTo(fieldValue.getValue()) >= 0) {

														} else if (fieldValue.getCompare() == FieldValue.EQUAL_TYPE
																&& temp.compareTo(fieldValue.getValue()) == 0) {

														} else {
															fieldFlag = false;
														}
													}
													if (fieldFlag) {
														fieldType.addFieldValue(1.0);
													}
												}
											}
										}
									}
								}

								List<FieldType> numberTypes = new ArrayList<FieldType>(finishFieldTypes);
								numberTypes.addAll(readyFieldTypes);
								double unitPrice = getUnitPrice(priceTypes, systemFields, finishFieldTypes, deriveFields,
										nextStartTime);
								result += caculateResult(subject, systemFields, finishFieldTypes, deriveFields, unitPrice,
										nextStartTime);

								nextStartTime = nextEndTime;

							} catch (Exception e) {
								e.printStackTrace();
								flag = false;
								break;
							}
						}

						StringBuilder builder = new StringBuilder();

						for (Field deriveField : deriveFields) {
							try {
								builder.append(deriveField.getFieldName()).append(":").append("/bill/").append(bill.getBillId())
										.append("-").append(bill.getBillProjectName()).append("-")
										.append(subject.getSubjectName()).append("-").append(deriveField.getFieldName())
										.append("-").append(bill.getBillStartTime()).append("-").append(bill.getBillEndTime())
										.append(".xls").append(";");
								deriveField.close();
							} catch (WriteException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						if (flag) {

							List<MinPrice> minPrices = MinPrice.parseMinPrice(subject.getSubjectMinPrice());

							double totalResult = getResult(minPrices, billBelongTime, result);

							allSum += totalResult;
							BillDetail billDetail = new BillDetail();
							billDetail.setBillDetailSum(totalResult);
							billDetail.setBillSubjectId(subject.getSubjectId());
							billDetail.setBillId(bill.getBillId());
							billDetail.setBillFieldFile(builder.toString());
							billDetailService.addBillDetail(billDetail);

						}
						LOG.warn(">>>>>>>>>>>>End Caculate Bill={},Subject={}<<<<<<<<<<<<", bill.getBillName(),
								subject.getSubjectName());

						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
					}
					bill.setBillProgress(100);
					bill.setBillSum(allSum);
					bill.setBillStatus(1);
					billService.updateBill(bill);
					LOG.warn(">>>>>>>>>>>>End Caculate Bill={} <<<<<<<<<<<<", bill.getBillName());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			}
		}, 0L, 30L, TimeUnit.MINUTES);
	}

	public static int getTableType(String tableName) {
		if (tableName.equals("ASN"))
			return 1;
		else if (tableName.equals("ASN_ITEM"))
			return 2;
		else if (tableName.equals("LDO"))
			return 3;
		else if (tableName.equals("LDO_ITEM"))
			return 4;
		return -1;
	}

	public static double getUnitPrice(List<PriceType> priceTypes, List<SystemField> systemFields, List<FieldType> fieldTypes,
			List<Field> deriveFields, String currentTime) {
		double unitPrice = 0.0;

		PriceType priceTypeLeader = null;
		for (PriceType priceType : priceTypes) {
			if (currentTime.compareTo(priceType.getEndDate()) <= 0 && currentTime.compareTo(priceType.getStartDate()) >= 0) {
				priceTypeLeader = priceType;
				break;
			}
		}
		if (null == priceTypeLeader)
			return 0.0;
		if (priceTypeLeader.getPriceType() == PriceType.STANDARD_PRICE)
			unitPrice = priceTypeLeader.getStandardPrice();
		else {
			String fieldInfo = priceTypeLeader.getFieldInfo();
			Double value = getFieldValue(systemFields, fieldTypes, deriveFields, fieldInfo, currentTime);
			List<Price> prices = priceTypeLeader.getPrices();
			for (Price price : prices) {
				if (value >= price.min && value < price.max) {
					unitPrice = priceTypeLeader.getStandardPrice() * price.value;
					break;
				}
			}
		}

		return unitPrice;
	}

	public static Double getFieldValue(List<SystemField> systemFields, List<FieldType> fieldTypes, List<Field> deriveFields,
			String fieldInfo, String dateTime) {

		for (SystemField systemField : systemFields) {
			if (fieldInfo.equals(systemField.getClassIdentify() + "-" + systemField.getFieldName())) {
				return systemField.getFieldValue();
			}
		}
		for (FieldType fieldType : fieldTypes) {
			String fieldInfoString = fieldType.getFieldInfo();
			if (fieldInfo.equals(fieldInfoString)) {
				return fieldType.getFieldWarmValue(dateTime);
			}
		}
		for (Field field : deriveFields) {
			String fieldInfoString = field.getFieldId() + "";
			if (fieldInfo.equals(fieldInfoString)) {
				return field.getFieldValue();
			}
		}
		return 0.0;
	}

	public static double caculateResult(Subject subject, List<SystemField> systemFields, List<FieldType> fieldTypes,
			List<Field> deriveFields, double unitPrice, String dateTime) {
		try {
			double res = 0.0;
			String formula = subject.getSubjectFormula();
			if (StringUtils.isEmpty(formula))
				return 0.0;
			String[] params = formula.split("\\|");
			Stack<Object> stack = new Stack<Object>();
			for (int index = 0; index < params.length; index++) {
				if (params[index].equals("+") || params[index].equals("-")) {
					if (stack.size() == 1) {
						stack.push(params[index]);
						continue;
					}
					Double topValue = (Double) stack.pop();
					String tt = (String) stack.pop();
					Double bottomValue = (Double) stack.pop();
					double result = 0.0;
					if (tt.equals("*"))
						result = bottomValue * topValue;
					else if (tt.equals("/"))
						result = bottomValue / topValue;
					else if (tt.equals("+"))
						result = bottomValue + topValue;
					else if (tt.equals("-"))
						result = bottomValue - topValue;
					stack.push(result);
					stack.push(params[index]);

				} else if (params[index].equals("*") || params[index].equals("/")) {
					double nextValue = unitPrice;
					if (params[index + 1].startsWith("-2")) {
						String[] tmpParams = params[index + 1].split("\\$");
						if (tmpParams.length < 2)
							nextValue = 0.0;
						else {
							try {
								nextValue = Double.parseDouble(tmpParams[1].trim());
							} catch (Exception e) {
								nextValue = 0.0;
							}
						}
					} else if (!params[index + 1].equals("-1")) {
						nextValue = getFieldValue(systemFields, fieldTypes, deriveFields, params[index + 1], dateTime);
					}
					Double topValue = (Double) stack.pop();
					double result = 0.0;
					if (params[index].equals("*"))
						result = nextValue * topValue;
					else
						result = topValue / nextValue;
					stack.push(result);
					index++;
				} else if (params[index].equals("-1")) {
					stack.push(unitPrice);
				} else if (params[index].startsWith("-2")) {
					String[] tmpParams = params[index].split("\\$");
					if (tmpParams.length < 2)
						stack.push(0.0);
					else {
						try {
							stack.push(Double.parseDouble(tmpParams[1].trim()));
						} catch (Exception e) {
							stack.push(0.0);
						}
					}
				} else {
					stack.push(getFieldValue(systemFields, fieldTypes, deriveFields, params[index], dateTime));
				}
			}

			if (stack.size() == 1) {
				return (Double) stack.pop();
			}

			Double topValue = (Double) stack.pop();
			String tt = (String) stack.pop();
			Double bottomValue = (Double) stack.pop();
			if (tt.equals("*"))
				res = bottomValue * topValue;
			else if (tt.equals("/"))
				res = bottomValue / topValue;
			else if (tt.equals("+"))
				res = bottomValue + topValue;
			else if (tt.equals("-"))
				res = bottomValue - topValue;

			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}

	public static double getResult(List<MinPrice> minPrices, String billTime, double caculateResult) {

		// 只会存在一个 MinPrice
		MinPrice minPriceLeader = null;
		for (MinPrice minPrice : minPrices) {
			if (minPrice.getStartDate().compareTo(billTime) <= 0 && minPrice.getEndDate().compareTo(billTime) >= 0) {
				minPriceLeader = minPrice;
				break;
			}
		}
		if (null == minPriceLeader || minPriceLeader.getPriceType() == MinPrice.NON_LOWEST)
			return caculateResult;
		else {
			if (caculateResult < minPriceLeader.getStandardPrice())
				return minPriceLeader.getStandardPrice();
		}
		return caculateResult;

	}

	public String getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

}
