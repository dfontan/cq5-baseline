package com.baseline.web.sample;

import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.jcr.api.SlingRepository;
import com.day.cq.commons.jcr.JcrUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SlingServlet(paths="/bin/twc/repo", methods="POST")
public class NodeEditingService extends SlingAllMethodsServlet {
	private static final long serialVersionUID = -3777792666512058118L;
	private static final Logger LOGGER = LoggerFactory.getLogger(NodeEditingService.class);
	
	@Reference
	private SlingRepository repository;
	
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
	{
		LOGGER.info("doPost");
		String nodeToCopy = request.getParameter("nodeName");
		LOGGER.info("Node to copy: " + nodeToCopy);
		
		Session session = null;
		
		try{
			// this is the current node that is requested, in case of a page that is the jcr:content node
			
			session = repository.loginAdministrative(null);
		
			Node currentNode = session.getNode(nodeToCopy);
			if (currentNode != null){
				JcrUtil.copy(currentNode, currentNode.getParent(), "copiedNode");
				session.save();
				LOGGER.info("PDM: Node Copied");
			}
			else
				LOGGER.info("Current Node is Null");
			
			
//			if(session.itemExists(cleanupPath)){
//				session.removeItem(cleanupPath);
//				LOGGER.info("node deleted");
//				session.save();
//			}
		}
		catch(RepositoryException e){
			LOGGER.error("exception copying node",e);
		}finally{
			if(session != null){
				session.logout();
				LOGGER.info("Session Logout");
			}
		}
		
	}
}
