package com.lianlife.demo;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter{

	 
	public void destroy() {
	}

 
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		 
		HttpServletRequest r=(HttpServletRequest) req;
		HttpServletResponse  hr=(HttpServletResponse) resp;
		String userName=(String) r.getSession(true).getAttribute("userName");
		
		if(userName!=null){
			chain.doFilter(req, resp);
			return ;
		}

		if(r.getRequestURI().startsWith("/static/comm/login")||r.getRequestURI().equals("/login")){
			chain.doFilter(req, resp);
			return ;
		}else{
			hr.sendRedirect("http://localhost:8080/static/comm/login/login.html");
		}
	}
	public void init(FilterConfig cfg) throws ServletException {	
		
        System.out.println("Filter is Started");
	}

}
