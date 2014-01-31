package com.best.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * ClassName:BillDetail Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-2-8
 */
public class BillDetail implements Serializable {

	private static final long serialVersionUID = 1490265069653874611L;

	private Integer billDetailId;
	private Integer billSubjectId;
	private String billSubjectName;
	private Double billDetailSum;
	private Integer billId;
	private String subjectFormulaTxt;
	private String billFieldFile = "";

	public Integer getBillDetailId() {
		return billDetailId;
	}

	public void setBillDetailId(Integer billDetailId) {
		this.billDetailId = billDetailId;
	}

	public Integer getBillSubjectId() {
		return billSubjectId;
	}

	public void setBillSubjectId(Integer billSubjectId) {
		this.billSubjectId = billSubjectId;
	}

	public String getBillSubjectName() {
		return billSubjectName;
	}

	public void setBillSubjectName(String billSubjectName) {
		this.billSubjectName = billSubjectName;
	}

	public Double getBillDetailSum() {
		return billDetailSum;
	}

	public void setBillDetailSum(Double billDetailSum) {
		this.billDetailSum = billDetailSum;
	}

	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public String getSubjectFormulaTxt() {
		return subjectFormulaTxt;
	}

	public void setSubjectFormulaTxt(String subjectFormulaTxt) {
		this.subjectFormulaTxt = subjectFormulaTxt;
	}

	public String getShowSubjcetFormulaTxt() {
		if (null == this.subjectFormulaTxt)
			return "";
		String temp = this.subjectFormulaTxt.replaceAll("\\|", "");
		return temp;
	}

	public String getShowAmount() {
		if (null == billDetailSum)
			return "0.00";
		return Bill.df.format(billDetailSum);
	}

	public String getBillFieldFile() {
		return billFieldFile;
	}

	public void setBillFieldFile(String billFieldFile) {
		this.billFieldFile = billFieldFile;
	}

	public List<DetailFile> getAllBillFieldFiles() {
		List<DetailFile> files = new ArrayList<DetailFile>();
		if (StringUtils.isNotBlank(billFieldFile)) {
			String[] params = billFieldFile.split(";");
			for (String param : params) {
				String[] fileParams = param.split(":");
				if (fileParams.length != 2)
					continue;
				DetailFile file = new DetailFile();
				file.setFileName(fileParams[0]);
				file.setFilePath(fileParams[1]);
				files.add(file);
			}
		}
		return files;
	}
}
