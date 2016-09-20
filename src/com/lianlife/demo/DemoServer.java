package com.lianlife.demo;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;


public class DemoServer {

	 
	public static void main(String[] args) throws Exception {
		 
      
        Server server = new Server(8080);
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.getSessionHandler().getSessionManager().setMaxInactiveInterval(30*60);
        context.setContextPath("/");
        context.setResourceBase(System.getProperty("java.io.tmpdir"));
        server.setHandler(context);
        
        EnumSet<DispatcherType> set=EnumSet.noneOf(DispatcherType.class);
        // set.add(DispatcherType.ASYNC);
        set.add(DispatcherType.REQUEST);
        context.addFilter(LoginFilter.class, "*", set);
        context.addServlet(IndexServlet.class, "/index");
        context.addServlet(HomeServlet.class, "/");
        context.addServlet(StaticServlet.class, "/static/*");
        context.addServlet(AddCommentServlet.class, "/addComment");
        context.addServlet(AddMessageServlet.class, "/addMessage");
        //context.addServlet(ListServlet.class, "/list");
        context.addServlet(LoginServlet.class, "/login");
        server.start();
        System.out.println("Server is Started");
        server.join();

	}

}
