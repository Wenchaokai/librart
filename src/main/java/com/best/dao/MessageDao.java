package com.best.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.best.domain.Message;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * ClassName:RoleDao Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
@Repository("messageDao")
public class MessageDao extends BaseDao {

	@Resource(name = "sqlMapClient")
	protected SqlMapClient sqlMapClient;

	private static final String space = "messageSpace.";

	@SuppressWarnings("unchecked")
	public List<Message> listAllMessage(long userId) {
		return (List<Message>) this.list(space + "GET_ALL_MESSAGE", userId, sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	public List<Message> listAllMessageByProject(List<Integer> projectIds) {
		return (List<Message>) this.list(space + "GET_ALL_MESSAGE_BY_PROJECT", projectIds, sqlMapClient);
	}

	public void addMessage(Message message) {
		this.insert(space + "ADD_MESSAGE", message, sqlMapClient);
	}

	public void deleteMessage(int messageId) {
		this.delete(space + "DELETE_MESSAGE", messageId, sqlMapClient);
	}

}
