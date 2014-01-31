import java.util.Date;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * ClassName:Main Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-9-4
 */
public class Main {
	public static void main(String[] args) throws AddressException, Exception {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.126.com");
		mailSender.setUsername("ecboss_800best@126.com");
		mailSender.setPassword("ecboss");

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("ecboss_800best@126.com");

		sendMail(mailSender, message, "服务器测试！", "知道不！！其实我好想剁了你",
				InternetAddress.parse("wenchaokai@vip.qq.com,xma@800best.com,katrina1218@qq.com"));
	}

	public static void sendMail(final JavaMailSender mailSender, final SimpleMailMessage simpleMailMessage, final String subject,
			final String content, final InternetAddress[] tos) throws Exception {
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
}
