import javax.mail.internet.MimeMessage;

import lzcge.crowdfunding.util.DesUtil;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;



public class SpringJavaMailTest {


	/**
	 * JavaMail测试邮件发送
	 * @throws Exception
	 */
	@Test
	public void sendMail() throws Exception {
		// 使用JAVA程序发送邮件
		ApplicationContext application = new ClassPathXmlApplicationContext("spring/spring-*.xml");

		// 邮件发送器，由Spring框架提供的
		JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl) application.getBean("sendMail");

		javaMailSender.setDefaultEncoding("UTF-8");
		
		MimeMessage mail = javaMailSender.createMimeMessage();//一份情书
		MimeMessageHelper helper = new MimeMessageHelper(mail);
		helper.setSubject("告白书"); //邮件标题
		
		StringBuilder content = new StringBuilder();

		String param = "ILY";
		param = DesUtil.encrypt(param, "abcdefghijklmnopquvwxyz");

		content.append("<a href='http://localhost/test/act.do?p=" + param + "'>激活链接</a>");
		
		helper.setText(content.toString(), true);
		
		helper.setFrom("admin@lzc.com");
		helper.setTo("test@lzc.com");
		
		javaMailSender.send(mail);
	}

}
