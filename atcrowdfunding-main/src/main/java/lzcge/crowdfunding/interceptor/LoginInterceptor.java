package lzcge.crowdfunding.interceptor;

import lzcge.crowdfunding.entity.User;
import lzcge.crowdfunding.util.Const;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * 登录权限拦截器
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		//白名单
		Set<String> urls = new HashSet<>();
		urls.add("/index.htm");
		urls.add("/login.htm");
		urls.add("/doLogin.do");
		urls.add("/reg.htm");
		urls.add("/member/doRegist.do");
		urls.add("/logout.do");
		String url = request.getServletPath();
		if(urls.contains(url)){
			return true;
		}

		//判断是否登录
		User user = (User) request.getSession().getAttribute(Const.LOGIN_USER);
		if(user!=null){
			return true;
		}else{
			//跳转到登录页面
			response.sendRedirect(request.getContextPath()+"/login.htm");
			return false;
		}




	}
}
