package com.project.web.sample;

import java.io.IOException;

import javax.jcr.Repository;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;



@SlingServlet(paths="/bin/company/repo", methods="GET")
public class MySafeServletMethod extends SlingSafeMethodsServlet {
	private static final long serialVersionUID = -3960692666512058118L;
	
	@Reference
	private Repository repository;
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException
	{
		//response.setHeader("Content-Type", "application/json");
		response.getWriter().print("{\"coming\" : \"soon\"}");
	}
	

}
