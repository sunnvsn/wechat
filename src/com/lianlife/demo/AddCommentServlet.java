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
public class AddCommentServlet extends HttpServlet{
	protected void service( HttpServletRequest request,
			HttpServletResponse response ) throws UnsupportedEncodingException {
request.setCharacterEncoding("UTF-8");
		
		String commentId=UUID.randomUUID().toString().replaceAll("-", "");		
		String messageId=request.getParameter("messageId");
		String commentUserId=request.getParameter("userId");
		String comment=request.getParameter("comment");
		
		 //Connection c=DBUtil.getConnection();
		Connection c = null;
		ConnectionPool cp = ConnectionPool.getInstance();
		c = cp.getConnection(); 
		PreparedStatement ps=null;
		try {
			c.setAutoCommit(false);
			ps=c.prepareStatement("INSERT INTO t_comment (F_COMMENT_ID, F_MESSAGE_ID, F_COMMENT_USER_ID, F_CONTENT, F_CREATE_DATE) VALUES(?,?,?,?,now())");
			ps.setString(1, commentId);
			ps.setString(2, messageId);
			ps.setString(3, commentUserId);
			ps.setBytes(4, comment.getBytes("UTF-8"));
			ps.execute();
			c.commit();
			HashMap returnData=new HashMap();
			returnData.put("status", true);
			returnData.put("commentId", commentId);
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
