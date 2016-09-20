package com.lianlife.demo;

import java.io.IOException;
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


public class HomeServlet extends HttpServlet{
	protected void service( HttpServletRequest request,
			HttpServletResponse response ) throws IOException {
		response.sendRedirect("/index");
	}
}
