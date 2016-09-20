package com.lianlife.demo;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class AddMessageServlet extends HttpServlet{
	protected void service( HttpServletRequest request,
			HttpServletResponse response ) throws UnsupportedEncodingException {
		
		request.setCharacterEncoding("UTF-8");
		
		String userId=request.getParameter("userId");
		String message=request.getParameter("message");
		String msgId=UUID.randomUUID().toString().replaceAll("-", "");
		
		 //Connection c=DBUtil.getConnection();
		Connection c = null;
		ConnectionPool cp = ConnectionPool.getInstance();
		c = cp.getConnection(); 
		PreparedStatement ps=null;
		try {
			c.setAutoCommit(false);
			ps=c.prepareStatement("INSERT INTO t_message (F_MESSAGE_ID, F_USER_ID,F_CONTENT, F_CREATE_DATE) VALUES(?,?,?,now())");
			ps.setString(1, msgId);
			ps.setString(2, userId);
			ps.setBytes(3, message.getBytes("UTF-8"));
			ps.execute();
			c.commit();
			HashMap returnData=new HashMap();
			returnData.put("status", true);
			returnData.put("messageId", msgId);
			HttpUtil.returnHttpJson(response, returnData);
		} catch (Throwable e) {
			HashMap returnData=new HashMap();
			returnData.put("status", false);
			returnData.put("message", "ÏµÍ³´íÎó1");
			HttpUtil.returnHttpJson(response, returnData);
			DBUtil.rollback(c);
			e.printStackTrace();
			 
		}finally{
			DBUtil.close(new Object[]{ps,c});
		}
	}
}
