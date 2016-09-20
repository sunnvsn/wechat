package com.lianlife.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class StaticServlet extends HttpServlet{
	protected void doGet( HttpServletRequest request,
			HttpServletResponse response ) {
		String resource="/resource"+request.getPathInfo();
        InputStream in=StaticServlet.class.getResourceAsStream(resource);
        if(in!=null)
		{
			try {
				if(resource.toLowerCase().endsWith(".js")){
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/javascript");
				}else if(resource.toLowerCase().endsWith(".html")){
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html");
				}else if(resource.toLowerCase().endsWith(".css")){
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/css");
				}
				response.setStatus(HttpServletResponse.SC_OK);
				Util.copyStream(in, response.getOutputStream());
				response.getOutputStream().flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}finally{
				
				try {
					in.close();
				} catch (Throwable e) {
					
				}
			}
		}else
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}