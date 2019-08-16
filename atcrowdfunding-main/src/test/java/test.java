import lzcge.crowdfunding.entity.User;
import lzcge.crowdfunding.manager.service.UserService;
import lzcge.crowdfunding.util.MD5Util;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @description:
 * @author: lzcge
 * @create: 2019-08-08
 **/
public class test {
	public static void main(String[] args) {
		ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring*.xml");
		UserService userService = ioc.getBean(UserService.class);
		for (int i = 0; i < 100 ; i++) {
			User user  = new User();
			user.setLoginacct("test"+i);
			user.setUserpswd(MD5Util.digest("123"));
			user.setEmail("test@qq.com");
			user.setCreatetime("2017-09-54");
			user.setUsername("testUsername"+i);
			userService.insert(user);
		}
	}
}
