package lzcge.crowdfunding.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @description:
 * @author: lzcge
 * @create: 2019-07-27
 **/
public class StartSystemListener implements ServletContextListener {

	//服务器启动时，创建application时执行的方法
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		//得到application对象,并将项目的上下午路径放入application域中
		ServletContext application = servletContextEvent.getServletContext();
		String contextPath = application.getContextPath();
		application.setAttribute("APP_PATH",contextPath);
		System.out.println("APP_PATH....");
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {

	}
}
