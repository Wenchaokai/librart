package com.best.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.best.dao.MessageDao;
import com.best.domain.Message;
import com.best.domain.User;

/**
 * ClassName:RoleService Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
@Service("messageService")
public class MessageService extends BaseService {
	@Autowired
	private MessageDao messageDao;

	@Autowired
	private UserService userService;

	@Value("${best.base.memcache.timeout}")
	private Integer memcachedTimeOut;

	public static final String ALL_MESSAGE_KEY = "ALL_MESSAGE_KEY_";

	@SuppressWarnings("unchecked")
	public List<Message> listAllMessage(long userId) {
		Object res = null;
		try {
			res = memcachedClient.get(ALL_MESSAGE_KEY + userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res == null) {
			res = messageDao.listAllMessage(userId);
			User user = userService.getUser(userId);
			Set<Integer> projects = user.getUserProjects();
			if (!CollectionUtils.isEmpty(projects)) {
				List<Message> projectsMessages = messageDao.listAllMessageByProject(new ArrayList<Integer>(projects));
				if (!CollectionUtils.isEmpty(projectsMessages)) {
					((List<Message>) res).addAll(projectsMessages);
				}
			}
		}
		if (null != res) {
			try {
				memcachedClient.set(ALL_MESSAGE_KEY, memcachedTimeOut, res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (null == res)
			return null;
		return (List<Message>) res;
	}

	public void addMessage(Message message) {
		try {
			memcachedClient.delete(ALL_MESSAGE_KEY + message.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		messageDao.addMessage(message);
	}

	public void deleteMessage(Integer messageId, long userId) {
		try {
			memcachedClient.delete(ALL_MESSAGE_KEY + userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		messageDao.deleteMessage(messageId);
	}

}
