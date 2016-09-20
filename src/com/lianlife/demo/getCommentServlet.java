package com.lianlife.demo;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class getCommentServlet {
	protected void service( HttpServletRequest request,
			HttpServletResponse response ) throws UnsupportedEncodingException {
		
		 request.setCharacterEncoding("UTF-8");
		 //Connection c=DBUtil.getConnection();
		 Connection c = null;
		 ConnectionPool cp = ConnectionPool.getInstance();
		 c = cp.getConnection(); 
		 PreparedStatement ps=null;
		 ResultSet rs=null;
		try {
			c.setAutoCommit(false);
			ps=c.prepareStatement("SELECT U.F_USER_NAME , U.F_USER_IMAGE , M.F_MESSAGE_ID , M.F_CONTENT FROM demo.t_user U , demo.t_message M where U.F_USER_ID = M.F_USER_ID order by M.F_CREATE_DATE DESC;"
);
			rs = ps.executeQuery();
			c.commit();
			HashMap returnData=new HashMap();
			returnData.put("status", true);			
			JSONArray dynamicJson = new JSONArray(); 
			ResultSetMetaData metaData = rs.getMetaData();  
			int columnCount = metaData.getColumnCount();  
			while (rs.next()) {  
		        JSONObject jsonObj = new JSONObject();  
		         
		        // 遍历每一列  
		        for (int i = 1; i <= columnCount; i++) {
		        	String columnName =metaData.getColumnLabel(i);
		        	String value = null;
		        	if(i == 4){
		        		value = new String(rs.getBytes(columnName),"UTF-8");    
		        	}else{  
			            value = rs.getString(columnName); 
		        	}
		             
		            jsonObj.put(columnName, value);  
		        }   
		        dynamicJson.add(jsonObj);   
		    }
			returnData.put("data", dynamicJson);
			HttpUtil.returnHttpJson(response, returnData);
		} catch (Throwable e) {
			HashMap returnData=new HashMap();
			returnData.put("status", false);
			returnData.put("message", "系统错误1");
			HttpUtil.returnHttpJson(response, returnData);
			DBUtil.rollback(c);
			e.printStackTrace();
			 
		}finally{
			DBUtil.close(new Object[]{ps,c});
		}
		 
	}

}
