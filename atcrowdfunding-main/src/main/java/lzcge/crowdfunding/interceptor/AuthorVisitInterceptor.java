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
		//拆分当前访问路径，获取根访问路径
		String requestParentUrl = requestUrl.split("/")[1];
		//根据当前访问路径拼接出需要比较的url路径
		String current = "/"+requestParentUrl+"/index.htm";
		String current2 = "/"+requestParentUrl+"/toIndex.htm";

		//获取登录用户的权限
		List<Permission> loginUserPermission = (List<Permission>)request.getSession().getAttribute(Const.LOGIN_USER_PERMISSION);
		Set<String> loginUserPermissionUrls = new HashSet<>();
		//用户已经登录
		if(loginUserPermission!=null) {
			for (Permission permission: loginUserPermission ) {
				if(permission.getUrl()!=null && !permission.getUrl().equals("")){
					loginUserPermissionUrls.add("/"+permission.getUrl());
				}

			}
		}else{
			return true;
		}

		//访问的当前路径在所有的权限管理路径中
		if(allPermissionUrls.contains(requestUrl)){
			//判断是否是这个用户可以访问的路径
			if(loginUserPermissionUrls.contains(requestUrl)){
				return true;
			}else{
				response.sendRedirect(request.getContextPath()+"/error.htm");
				return false;
			}

		}else if(allPermissionUrls.contains(current) || allPermissionUrls.contains(current2)){

			//判断是否是权限管理中的子路径
			if(!loginUserPermissionUrls.contains(current) && !loginUserPermissionUrls.contains(current2)){
				//不具有这个权限  提示不具有访问权限
				response.sendRedirect(request.getContextPath()+"/error.htm");
				return false;
			}else{//是合法父级路径下的子操作 放行
				return true;

			}
		}else{
			//其它路径放行
			return true;
		}


	}
}
