package com.best;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.best.dao.SubjectDao;
import com.best.domain.Message;
import com.best.domain.MinPrice;
import com.best.domain.PriceType;
import com.best.domain.Subject;
import com.best.service.MessageService;
import com.best.utils.DateUtil;

/**
 * ClassName:ProjectMonitorJob Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-2-10
 */
public class ProjectMonitorJob {

	@Autowired
	private SubjectDao subjectDao;

	@Autowired
	private MessageService messageService;

	private String host;

	private ScheduledExecutorService scheduleService = Executors.newScheduledThreadPool(2);

	public void init() {
		scheduleService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				List<Subject> enabledSubjects = subjectDao.listAllEnableSubject();
				String currentDate = DateUtil.getCurrentDateString();
				for (Subject subject : enabledSubjects) {
					List<MinPrice> minPrices = MinPrice.parseMinPrice(subject.getSubjectMinPrice());
					boolean flag = false;
					for (MinPrice minPrice : minPrices) {
						if (currentDate.compareTo(minPrice.getEndDate()) >= 0) {
							flag = true;
							break;
						}
					}
					if (flag) {
						Message message = new Message();
						message.setProjectId(subject.getSubjectProjectId());
						message.setMessageInfo("您好，【" + subject.getSubjectProjectName() + "】的字段【最低收费标准】需要您更新数据 <a href=\"" + host
								+ "/project/project-subject-minprice-index.do?subjectId=" + subject.getSubjectId()
								+ "&projectId=" + subject.getSubjectProjectId() + " \">去处理</a>");
						message.setMessageLink("");
						messageService.addMessage(message);
					}

					List<PriceType> priceTypes = PriceType.parsePriceString(subject.getSubjectPrice());
					flag = false;
					for (PriceType priceType : priceTypes) {
						if (currentDate.compareTo(priceType.getEndDate()) >= 0) {
							flag = true;
							break;
						}
					}

					if (flag) {
						Message message = new Message();
						message.setProjectId(subject.getSubjectProjectId());
						message.setMessageInfo("您好，【" + subject.getSubjectProjectName() + "】的字段【单价】需要您更新数据 <a href=\"" + host
								+ "/project/project-subject-unitprice-index.do?subjectId=" + subject.getSubjectId()
								+ "&projectId=" + subject.getSubjectProjectId() + " \">去处理</a>");
						message.setMessageLink("");
						messageService.addMessage(message);
					}

				}
			}
		}, 0L, 12L, TimeUnit.HOURS);
	}

	public void setHost(String host) {
		this.host = host;
	}

}
