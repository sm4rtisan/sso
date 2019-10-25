package com.j.sso.intercepter;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.j.sso.annotation.SSOCheckToken;
import com.j.sso.config.SSOConfiguration;
import com.j.sso.entity.R;
import com.j.sso.entity.SSOUser;
import com.j.sso.helper.JwtTokenHelper;

/**
 * 接入时添加此拦截器
 * 
 * @author yizhishaonian
 *
 */
public class SSOIntercepter implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

		// 检查方法是否为HandlerMethod
		if (!(object instanceof HandlerMethod)) {
			
			return true;
		}

		Method method = ((HandlerMethod) object).getMethod();
		// 检查是否为不拦截的方法
		if (method.isAnnotationPresent(SSOCheckToken.class)) {
			
			SSOCheckToken passToken = method.getAnnotation(SSOCheckToken.class);
			if (!passToken.check()) {
				
				return true;
			}
		}
		
		String token = request.getHeader(SSOConfiguration.HEADER_AUTH_KEY);
		// 检查token是否存在
		if (token == null) {
			
			write2Browse(response, R.noLogin().toString());
			return false;
		}
		
		try {
			
			// 检查token是否有效
			Boolean checkTokenInLaw = JwtTokenHelper.checkTokenInLaw(token);
			if (!checkTokenInLaw) {
				
				write2Browse(response, R.notAllow().toString());
				return false;
			}
			SSOUser ssoUser = JwtTokenHelper.getTokenSSOUser(token);
			// 用户信息放入request中
			request.setAttribute("ssoUser", ssoUser);
			return true;
		} catch (JWTVerificationException e) {

			write2Browse(response, R.notAllow().toString());
			return false;
		}
	}

	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	public void write2Browse(HttpServletResponse response, String content) throws IOException {

		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(content);
	}

}