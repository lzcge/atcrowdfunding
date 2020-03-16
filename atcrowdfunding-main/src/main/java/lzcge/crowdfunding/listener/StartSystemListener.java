package lzcge.crowdfunding.listener;

import lzcge.crowdfunding.entity.Permission;
import lzcge.crowdfunding.manager.service.PermissionService;
import lzcge.crowdfunding.util.Const;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class StartSystemListener implements ServletContextListener {

	//服务器启动时，创建application时执行的方法
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		//得到application对象,并将项目的上下午路径放入application域中
		ServletContext application = servletContextEvent.getServletContext();
		String contextPath = application.getContextPath();
		application.setAttribute("APP_PATH",contextPath);
		System.out.println("APP_PATH....");


		//加载所有的许可列表

		//获取IOC容器
		ApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(application);

		//从ioc中获取permissionService实例
		PermissionService permissionService = ioc.getBean(PermissionService.class);
		//获取所有权限
		List<Permission> allPermission =  permissionService.selectAll();
		Set<String> allPermissionUrls = new HashSet<>();
		//判断当前访问路径是否在其中
		for (Permission permission:allPermission ) {
			if(permission.getUrl()!=null && !permission.getUrl().equals("")){
				allPermissionUrls.add("/"+permission.getUrl());
			}

		}

		application.setAttribute(Const.ALL_PERMISSION_URLS,allPermissionUrls);

	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {

	}
}
