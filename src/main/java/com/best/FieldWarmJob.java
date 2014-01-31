package com.best;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.best.domain.FieldWarm;
import com.best.domain.Message;
import com.best.domain.User;
import com.best.service.FieldWarmService;
import com.best.service.MessageService;
import com.best.service.UserService;
import com.best.utils.DateUtil;

/**
 * ClassName:FieldWarmJob Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-2-10
 */
public class FieldWarmJob {
	@Autowired
	private MessageService messageService;

	@Autowired
	private FieldWarmService fieldWarmService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SimpleMailMessage simpleMailMessage;

	@Autowired
	private UserService userService;

	private String host;

	private ScheduledExecutorService scheduleService = Executors.newScheduledThreadPool(2);

	public void init() {

		scheduleService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				Map<String, MailInfo> map = new HashMap<String, MailInfo>();

				List<FieldWarm> fieldWarms = fieldWarmService.listAllFieldWarm();
				String current = DateUtil.getCurrentDateString();
				for (FieldWarm fieldWarm : fieldWarms) {
					int fieldPeriod = fieldWarm.getFieldWarmFieldPeriod();
					String fieldWarmModified = fieldWarm.getFieldWarmGmtModified();
					boolean flag = false;
					if (fieldPeriod == 1) {
						// 按周 每周 3点4分
						int week = fieldWarm.getWeek();
						if (week != -1) {
							int disPos = DateUtil.getWeekOfDate(week);
							try {
								String date = DateUtil.getPreDate(current, disPos);
								if (fieldWarmModified.compareTo(date) < 0) {
									flag = true;
								}
							} catch (ParseException e) {
							}

						} else {
							if (fieldWarmModified.compareTo(current) < 0)
								flag = true;
						}
					} else if (fieldPeriod == 3) {
						// 按月 每个月5号3点4分
						int month = fieldWarm.getDay();
						if (month != -1) {
							String date = current.substring(0, 6);
							if (month < 10)
								date += "0" + month;
							else
								date += month;
							if (fieldWarmModified.compareTo(date) < 0)
								flag = true;
						} else {
							if (fieldWarmModified.compareTo(current) < 0)
								flag = true;
						}
					} else {
						// 按天
						if (fieldWarmModified.compareTo(current) < 0)
							flag = true;
					}

					if (flag) {

						User user = userService.getUser(fieldWarm.getFieldWarmChargeManFirstId());

						String to = user.getUserEmail() == null ? "" : user.getUserEmail();

						MailInfo mail = map.get(to);

						if (mail == null) {

							mail = new MailInfo();

							mail.userId = fieldWarm.getFieldWarmChargeManFirstId();
							mail.content = "<b>您好，当前处理的消息的日期是 " + DateUtil.getCurrentDateString() + "</b><br />【"
									+ fieldWarm.getFieldWarmProjectName() + "】的字段【" + fieldWarm.getFieldWarmFieldName()
									+ "】需要您录入数据 <a href=\"" + host + "/fieldWarm/field-warm-data-modify-view.do?fieldWarmId="
									+ fieldWarm.getFieldWarmId() + "&dateTime=" + DateUtil.getCurrentDateString()
									+ "\" style=\"color:blue\">去处理</a>";
						} else {
							mail.content += "<br />【" + fieldWarm.getFieldWarmProjectName() + "】的字段【"
									+ fieldWarm.getFieldWarmFieldName() + "】需要您录入数据 <a href=\"" + host
									+ "/fieldWarm/field-warm-data-modify-view.do?fieldWarmId=" + fieldWarm.getFieldWarmId()
									+ "&dateTime=" + DateUtil.getCurrentDateString() + "\" style=\"color:blue\">去处理</a>";
						}

						map.put(to, mail);
					}

				}
				for (Entry<String, MailInfo> entry : map.entrySet()) {
					try {
						Message message = new Message();
						message.setUserId(entry.getValue().userId);
						message.setMessageInfo(entry.getValue().content);
						message.setMessageLink("");

						messageService.addMessage(message);

						sendMail("【结算系统】 报警信息", entry.getValue().content, InternetAddress.parse(entry.getKey()));
					} catch (AddressException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			}
		}, 0L, 24L, TimeUnit.HOURS);
	}

	static class MailInfo {
		public String content;
		public Long userId;
	}

	public void sendMail(final String subject, final String content, final InternetAddress[] tos) throws Exception {

		try {
			mailSender.send(new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					messageHelper.setFrom(simpleMailMessage.getFrom());
					messageHelper.setTo(tos);
					if (subject != null) {
						messageHelper.setSubject(subject);
					} else {
						messageHelper.setSubject("");
					}
					if (content != null) {
						messageHelper.setText(content, true);
					} else {
						messageHelper.setText("", true);
					}
					messageHelper.setSentDate(new Date(System.currentTimeMillis()));
				}
			});
		} catch (Exception e) {
			throw new Exception("Send Mail Error:" + tos.toString());
		}
	}

	public void setHost(String host) {
		this.host = host;
	}

}
