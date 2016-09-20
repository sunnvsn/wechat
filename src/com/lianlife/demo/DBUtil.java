package com.lianlife.demo;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

 

public class DBUtil {
	public static Connection getConnection(){
		String url="jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf8";  
        String user="demo";  
        String password="demo"; 
        Connection conn=null; 	
           try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=(Connection) DriverManager.getConnection(url,user,password);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		} 	           
          
           return conn;
	}
	public static void rollback(Connection c)
	{
		try {
			c.rollback();
		} catch (Throwable e) {
			 
			e.printStackTrace();
		}
	}
	public static void  close(Object obj){
		try {
			Method method=obj.getClass().getMethod("close", new Class[]{});
			method.invoke(obj, new Object[]{});
		} catch (Throwable e) {
			 
			e.printStackTrace();
		} 
		
	}
	
	public static void close(Object[] list){
		if(list!=null){
			for(Object o:list){
				close(o);
			}
		}
		
	}
}
