package com.best.domain;

import java.io.Serializable;

/**
 * ClassName:Message Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-2-10
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 5910893308227423516L;

	private Integer messageId;
	private Long userId;
	private String messageInfo;
	private String messageLink;
	private Integer projectId;

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMessageInfo() {
		return messageInfo;
	}

	public void setMessageInfo(String messageInfo) {
		this.messageInfo = messageInfo;
	}

	public String getMessageLink() {
		return messageLink;
	}

	public void setMessageLink(String messageLink) {
		this.messageLink = messageLink;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

}
