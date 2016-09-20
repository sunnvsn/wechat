package com.lianlife.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;
import java.sql.Connection;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet{
	protected void service( HttpServletRequest request,
			HttpServletResponse response ) throws IOException ,UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		String uN = "admin";
		String ps = "123";
		
		HashMap returnData=new HashMap();
		
		if(uN.equals(userName)&&ps.equals(password)){
			request.getSession(true).setAttribute("userName", "admin");
			returnData.put("status", true);
			HttpUtil.returnHttpJson(response, returnData);			
		}else{
			returnData.put("status", false);
			HttpUtil.returnHttpJson(response, returnData);
		}
		 
	}
}
