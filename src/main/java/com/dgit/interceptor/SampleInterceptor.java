package com.dgit.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SampleInterceptor extends HandlerInterceptorAdapter{

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("[SampleInterceptor] postHandle");
		
		//doB에서 실행시켜도 result라는 key값이 있으면 doA로 가라는 의미
		Object result = modelAndView.getModel().get("result");
		if(result!=null){
			request.getSession().setAttribute("result", result);
			response.sendRedirect("doA");
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("[SampleInterceptor] preHandle");
		HandlerMethod method=(HandlerMethod)handler;
		Method methodObj = method.getMethod();
		
		System.out.println("Bean : "+method.getBean());
		System.out.println("method : "+methodObj);
		
		return true;
	}
 
	
}
