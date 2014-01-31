package com.best.domain;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.collections.CollectionUtils;

import com.best.utils.FieldValueCacheUtils;

/**
 * ClassName:Field Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-13
 */
public class Field implements Serializable {
	private static final long serialVersionUID = -2699373529452026203L;

	private Integer fieldId;
	private String fieldName;
	private String fieldUnit;
	private Integer fieldPeriod;
	private String fieldDesc;
	private Integer fieldType;
	private String fieldTypeName;
	private Boolean isChecked;
	private Double fieldValue;
	private String fieldTypeMapping;
	private Integer fieldTypeBaobiaoType = 1;
	private String fieldTypeBaobiaoMapping = "";
	private String fieldTypeBaobiaoMappingKey = "";
	public List<FieldValue> fields = new ArrayList<FieldValue>();

	public Integer getFieldId() {
		return fieldId;
	}

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldUnit() {
		return fieldUnit;
	}

	public void setFieldUnit(String fieldUnit) {
		this.fieldUnit = fieldUnit;
	}

	public Integer getFieldPeriod() {
		return fieldPeriod;
	}

	public void setFieldPeriod(Integer fieldPeriod) {
		this.fieldPeriod = fieldPeriod;
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public Integer getFieldType() {
		return fieldType;
	}

	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldTypeName() {
		return fieldTypeName;
	}

	public void setFieldTypeName(String fieldTypeName) {
		this.fieldTypeName = fieldTypeName;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

	public Double getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(Double fieldValue) {
		this.fieldValue = fieldValue;
	}

	public boolean valueSetFlag() {
		return this.fieldValue != null;
	}

	public String getFieldTypeMapping() {
		return fieldTypeMapping;
	}

	public void setFieldTypeMapping(String fieldTypeMapping) {
		this.fieldTypeMapping = fieldTypeMapping;
	}

	public Integer getFieldTypeBaobiaoType() {
		return fieldTypeBaobiaoType;
	}

	public void setFieldTypeBaobiaoType(Integer fieldTypeBaobiaoType) {
		this.fieldTypeBaobiaoType = fieldTypeBaobiaoType;
	}

	public String getFieldTypeBaobiaoMapping() {
		return fieldTypeBaobiaoMapping;
	}

	public void setFieldTypeBaobiaoMapping(String fieldTypeBaobiaoMapping) {
		this.fieldTypeBaobiaoMapping = fieldTypeBaobiaoMapping;
	}

	public String getFieldTypeBaobiaoMappingKey() {
		return fieldTypeBaobiaoMappingKey;
	}

	public void setFieldTypeBaobiaoMappingKey(String fieldTypeBaobiaoMappingKey) {
		this.fieldTypeBaobiaoMappingKey = fieldTypeBaobiaoMappingKey;
	}

	private WritableWorkbook book = null;
	private WritableSheet sheet = null;
	private Integer index = 0;
	private File file = null;
	private Integer indexSheet = 0;

	public void writeInteger(Integer billId, String time, Integer sum, String baseDir, String billName, String subjectName,
			String billStartTime, String billEndTime) throws IOException, RowsExceededException, WriteException {
		if (book == null) {
			File parentFile = new File(baseDir, "bill");
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			StringBuilder sb = new StringBuilder();
			sb.append(billId).append("-").append(billName).append("-").append(subjectName).append("-").append(fieldName)
					.append("-").append(billStartTime).append("-").append(billEndTime).append(".xls");
			file = new File(parentFile, sb.toString());
			if (file.exists())
				file.delete();
			book = Workbook.createWorkbook(file);
			// 生成名为“第一页”的工作表，参数0表示这是第一页
			sheet = book.createSheet("数据", 0);
			sheet.addCell(new Label(0, 0, "项目名称"));
			sheet.addCell(new Label(1, 0, billName));
			sheet.addCell(new Label(0, 1, "报表日期"));
			sheet.addCell(new Label(1, 1, billStartTime + "-" + billEndTime));
			sheet.addCell(new Label(0, 2, "日期"));
			sheet.addCell(new Label(1, 2, fieldTypeBaobiaoMappingKey));
			index = 3;
		}
		sheet.addCell(new Label(0, index, time));
		sheet.addCell(new Label(1, index, sum + ""));
		index++;
	}

	private List<String> titles = null;
	private List<String> titleNames = null;

	public void writeList(Integer billId, String time, List<Object> res, String baseDir, String billName, String subjectName,
			String billStartTime, String billEndTime) throws RowsExceededException, WriteException, IOException,
			IllegalArgumentException, IllegalAccessException {
		if (book == null && sheet == null) {

			titles = new ArrayList<String>();
			titleNames = new ArrayList<String>();
			String mappingKeys[] = fieldTypeBaobiaoMappingKey.split(";");
			for (String mappingKey : mappingKeys) {
				String[] params = mappingKey.split(":");
				titles.add(params[0]);
				titleNames.add(params[1]);
			}

			File parentFile = new File(baseDir, "bill");
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			StringBuilder sb = new StringBuilder();
			sb.append(billId).append("-").append(billName).append("-").append(subjectName).append("-").append(fieldName)
					.append("-").append(billStartTime).append("-").append(billEndTime).append(".xls");
			file = new File(parentFile, sb.toString());
			if (file.exists())
				file.delete();
			book = Workbook.createWorkbook(file);
			// 生成名为“第一页”的工作表，参数0表示这是第一页
			sheet = book.createSheet("数据", indexSheet++);
			sheet.addCell(new Label(0, 0, "项目名称"));
			sheet.addCell(new Label(1, 0, billName));
			sheet.addCell(new Label(0, 1, "报表日期"));
			sheet.addCell(new Label(1, 1, billStartTime + "-" + billEndTime));
			for (int i = 0; i < titleNames.size(); i++) {
				sheet.addCell(new Label(i, 2, titleNames.get(i)));
			}

			index = 3;
		}

		if (CollectionUtils.isNotEmpty(res)) {
			for (Object obj : res) {
				for (int i = 0; i < titles.size(); i++) {
					sheet.addCell(new Label(i, index, String.valueOf(FieldValueCacheUtils.getFieldValue(titles.get(i), obj))));
				}
				if (index >= 62000) {
					sheet = book.createSheet("数据", indexSheet++);
					sheet.addCell(new Label(0, 0, "项目名称"));
					sheet.addCell(new Label(1, 0, billName));
					sheet.addCell(new Label(0, 1, "报表日期"));
					sheet.addCell(new Label(1, 1, billStartTime + "-" + billEndTime));
					for (int i = 0; i < titleNames.size(); i++) {
						sheet.addCell(new Label(i, 2, titleNames.get(i)));
					}

					index = 3;
				} else
					index++;
			}

		}
	}

	public void close() throws IOException, WriteException {
		if (book != null) {
			// 写入数据
			book.write();
			// 关闭文件
			book.close();
			book = null;
		}
	}

	public void setFieldTypes(List<FieldValue> fieldTypes) {
		this.fields = fieldTypes;
	}

	// public static void main(String[] args) throws ParseException,
	// WriteException, IOException {
	// Field field = new Field();
	// field.setFieldTypeBaobiaoMappingKey("库存量");
	//
	// String nextStartTime = "20130304";
	// String nextEndTime = DateUtil.getNextDate(nextStartTime);
	// Random random = new Random();
	//
	// while (nextStartTime.compareTo("20130331") < 0) {
	// field.writeInteger(nextStartTime, random.nextInt(10) + 2, "D:\\", "项目1",
	// "", "20130304", "20130331");
	// nextStartTime = nextEndTime;
	// nextEndTime = DateUtil.getNextDate(nextStartTime);
	// }
	//
	// field.close();
	// }

}
