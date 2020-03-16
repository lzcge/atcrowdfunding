package lzcge.crowdfunding.interceptor;

import lzcge.crowdfunding.entity.Permission;
import lzcge.crowdfunding.entity.User;
import lzcge.crowdfunding.manager.service.PermissionService;
import lzcge.crowdfunding.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 访问权限拦截器
 */
public class AuthorVisitInterceptor extends HandlerInterceptorAdapter {


	@Autowired
	private PermissionService permissionService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


		//所有权限
		//从application中取，不用每次都查询数据库
		Set<String> allPermissionUrls = (Set<String>)request.getSession().getServletContext().getAttribute(Const.ALL_PERMISSION_URLS);

//		List<Permission> allPermission =  permissionService.selectAll();
//		Set<String> allPermissionUrls = new HashSet<>();
//		//判断当前访问路径是否在其中
//		for (Permission permission:allPermission ) {
//			if(permission.getUrl()!=null && !permission.getUrl().equals("")){
//				allPermissionUrls.add("/"+permission.getUrl());
//			}
//
//		}

		//获取当前路径
		String requestUrl = request.getRequestURI();
		if(allPermissionUrls.contains(requestUrl)){
			//判断是否是这个用户可以访问的路径
			List<Permission> loginUserPermission = (List<Permission>)request.getSession().getAttribute(Const.LOGIN_USER_PERMISSION);
			Set<String> loginUserPermissionUrls = new HashSet<>();
			for (Permission permission: loginUserPermission ) {
				if(permission.getUrl()!=null && !permission.getUrl().equals("")){
					loginUserPermissionUrls.add("/"+permission.getUrl());
				}

			}
			if(loginUserPermissionUrls.contains(requestUrl)){
				return true;
			}else{
				response.sendRedirect(request.getContextPath()+"/index.htm");
				return false;
			}


		}else{
			//不需要拦截的直接放行
			return true;
		}

	}
}
