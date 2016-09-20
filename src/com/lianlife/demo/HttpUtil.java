package com.lianlife.demo;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class HttpUtil {
	
	public static void returnHttpJson(HttpServletResponse resp, Map data){
		 JSONObject jsonObject = JSONObject.fromObject( data );  
		 String jsonStr=jsonObject.toString();
		 resp.setCharacterEncoding("UTF-8");
		 resp.setContentType("text/json; charset=utf-8");
		 try {
			resp.getWriter().print(jsonStr);
		} catch (Throwable e) {
		 
			e.printStackTrace();
		}
		
	}
	 

}
