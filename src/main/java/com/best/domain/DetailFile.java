package com.best.domain;

import java.io.Serializable;

/**
 * ClassName:DetailFile Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-3-27
 */
public class DetailFile implements Serializable {

	private static final long serialVersionUID = 8169577824322161263L;

	private String fileName;
	private String filePath;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
