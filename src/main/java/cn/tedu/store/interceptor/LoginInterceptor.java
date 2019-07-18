package cn.tedu.store.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器
 */
public class LoginInterceptor 
	implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 获取HttpSession对象
		HttpSession session = request.getSession();
		// 判断session中是否有登录信息
		if (session.getAttribute("uid") == null) {
			// 没有登录信息，则重定向到登录页
			response.sendRedirect("/web/login.html");
			// 执行拦截
			return false;
		}
		// 放行
		return true;
	}

}
