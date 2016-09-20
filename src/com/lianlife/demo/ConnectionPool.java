package com.lianlife.demo;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionPool {
	private DataSource ds;  
    private static ConnectionPool pool;  
    private ConnectionPool() throws PropertyVetoException{  
    	ComboPooledDataSource   cds = new ComboPooledDataSource();  
    	cds.setDriverClass("com.mysql.jdbc.Driver");
    	cds.setJdbcUrl("jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf8");
    	cds.setUser("demo");
    	cds.setPassword("demo");
    	cds.setInitialPoolSize(3);
    	cds.setMinPoolSize(3);
    	cds.setAcquireIncrement(3);
    	cds.setMaxPoolSize(15);
    	cds.setMaxIdleTime(100);
    	cds.setAcquireRetryAttempts(30);
    	cds.setAcquireRetryDelay(1000);
    	ds = cds;
    }  
    public static final ConnectionPool getInstance(){  
        if(pool==null){  
            try{  
                pool = new ConnectionPool();  
            }catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return pool;  
    }  
    public synchronized final Connection getConnection() {    
        try {  
            return ds.getConnection();  
        } catch (SQLException e) {       
            e.printStackTrace();  
        }  
        return null;  
    }  
}
