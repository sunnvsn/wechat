package com.lianlife.demo;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lianlife.velocity.VelocityServiceImpl;

import net.sf.json.JSONArray;


public class ListServlet extends HttpServlet{
	protected void service( HttpServletRequest request,
			HttpServletResponse response ) throws UnsupportedEncodingException {
		
		 request.setCharacterEncoding("UTF-8");
		 
		 //Connection c=DBUtil.getConnection();
		 Connection c = null;
		 ConnectionPool cp = ConnectionPool.getInstance();
		 c = cp.getConnection(); 
		 PreparedStatement ps=null;
		 ResultSet rsDynamic=null;		 

		try {
			c.setAutoCommit(false);
			ps=c.prepareStatement("SELECT U.F_USER_NAME , U.F_USER_IMAGE , M.F_MESSAGE_ID , M.F_CONTENT FROM demo.t_user U , demo.t_message M where U.F_USER_ID = M.F_USER_ID order by M.F_CREATE_DATE DESC;"
);
			rsDynamic = ps.executeQuery();
			c.commit();
			
			List<Dynamic> dynamicList=new ArrayList<Dynamic>();  
			while(rsDynamic.next()){  
				Dynamic dynamic=new Dynamic();  
				dynamic.setUserName(rsDynamic.getString("F_USER_NAME"));   
				dynamic.setUserImage(rsDynamic.getString("F_USER_IMAGE")); 
				String msgId = rsDynamic.getString("F_MESSAGE_ID");
				dynamic.setMessageId(msgId);  
				dynamic.setMessageContent(new String(rsDynamic.getBytes("F_CONTENT"),"UTF-8"));
				
				//Connection commentC=DBUtil.getConnection();
				Connection commentC = null;
				ConnectionPool pool = ConnectionPool.getInstance();
				commentC = pool.getConnection(); 
				PreparedStatement commentPs=null;
				ResultSet rsComment=null; 
				
				try{
					commentC.setAutoCommit(false);
					commentPs=commentC.prepareStatement("SELECT U.F_USER_NAME , C.F_CONTENT FROM demo.t_comment C ,demo.t_user U where U.F_USER_ID = C.F_COMMENT_USER_ID and F_MESSAGE_ID =? order by C.F_CREATE_DATE;");
					commentPs.setString(1, msgId);
					rsComment = commentPs.executeQuery();
					commentC.commit();
					List<Comment> commentList=new ArrayList<Comment>();
					while(rsComment.next()){
						Comment comment = new Comment();
						comment.setCommentUser(rsComment.getString("F_USER_NAME"));
						comment.setComment(new String(rsComment.getBytes("F_CONTENT"),"UTF-8"));
						commentList.add(comment);
					}
					dynamic.setCommentList(commentList);			  
					dynamicList.add(dynamic); 
				}catch (Throwable e){
					DBUtil.rollback(commentC);
					e.printStackTrace();
				}finally{
					DBUtil.close(new Object[]{commentPs,commentC});
				}				 			  
			}  		
			
			JSONArray jsonArray = JSONArray.fromObject(dynamicList);
			
			Map map = new HashMap();
			map.put("list", jsonArray);
			VelocityServiceImpl velocityServiceImpl = new VelocityServiceImpl();
			String str = velocityServiceImpl.mergeTemplateIntoString("List.vm", map);
			HashMap returnData=new HashMap();
			returnData.put("status", true);	
			returnData.put("html", str);
			returnData.put("data", jsonArray);
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
