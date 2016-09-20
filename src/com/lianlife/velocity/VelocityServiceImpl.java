package com.lianlife.velocity;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;


public class VelocityServiceImpl implements VelocityService  {

	private VelocityEngine ve ;
	
	
	public VelocityServiceImpl()
	{
		 ve = new VelocityEngine();
		 ve.setProperty(Velocity.RESOURCE_LOADER, "class");
		 ve.setProperty("class.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		 ve.setProperty("input.encoding", "utf-8");
		 ve.setProperty("output.encoding", "utf-8");
		 ve.init();
	}
	
	
	public String mergeTemplateIntoString(String template, Map model) {
		Template tmp=ve.getTemplate(template);
		tmp.setEncoding("utf-8");
		VelocityContext context = new VelocityContext();
		for(Object k:model.keySet())
		{
			context.put((String) k, model.get(k));
		}
		StringWriter writer = new StringWriter();
		tmp.merge(context, writer);
		try {
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return writer.toString();
	}


	public String evaluate(String str, Map model) {
		
		VelocityContext context = new VelocityContext();
		for(Object k:model.keySet())
		{
			context.put((String) k, model.get(k));
		}
		StringWriter writer = new StringWriter();
		
		
		ve.evaluate(context, writer, "", new StringReader(str));
		try {
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return writer.toString();
	}

}
