package com.lianlife.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Util {
	public static void copyStream(InputStream in,OutputStream out) throws IOException
	{
		copyStream(  in,  out , 2048);
	}
	public static void copyStream(InputStream in,OutputStream out,int bufSize) throws IOException
	{
		byte[] buf=new byte[bufSize];
		int c=0;
		do
		{
			c=in.read(buf);
			if(c>0)
			{
				out.write(buf, 0, c);
			}
			
		}while(c!=-1);
		out.flush();
	}
}
