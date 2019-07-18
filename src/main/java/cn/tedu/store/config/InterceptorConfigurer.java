package cn.tedu.store.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.tedu.store.interceptor.LoginInterceptor;

@Configuration
public class InterceptorConfigurer
	implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 创建拦截器对象
		HandlerInterceptor interceptor = new LoginInterceptor();
		
		// 白名单
		List<String> patterns = new ArrayList<>();
		patterns.add("/bootstrap3/**");
		patterns.add("/css/**");
		patterns.add("/js/**");
		patterns.add("/images/**");
		
		patterns.add("/web/register.html");
		patterns.add("/web/login.html");
		patterns.add("/web/index.html");
		patterns.add("/web/product.html");
		
		patterns.add("/users/reg");
		patterns.add("/users/login");
		
		patterns.add("/districts/**");
		patterns.add("/products/**");
		
		// 注册拦截器
		registry.addInterceptor(interceptor)
			.addPathPatterns("/**")
			.excludePathPatterns(patterns);
	}
	
}






