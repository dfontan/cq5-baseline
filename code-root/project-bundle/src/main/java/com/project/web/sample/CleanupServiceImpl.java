package com.project.web.sample;

import java.util.Dictionary;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.felix.scr.annotations.*;
import org.apache.sling.commons.osgi.*;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.*;

import org.slf4j.*;

@Component(immediate = true, metatype = true, label = "Project-Bundle Cleanup Service")
@Service(value = Runnable.class)
@Property(name = "scheduler.expression", value = "*/5 * * * * ?")
public class CleanupServiceImpl implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(CleanupServiceImpl.class);
	
	@Reference
	private SlingRepository repository;
	
	@Property(label = "Path", description = "Delete this path", value = "/mypath")
	public static final String CLEANUP_PATH = "cleanupPath";
	private String cleanupPath;
		
	protected void activate(ComponentContext componentContext){
		configure(componentContext.getProperties());
		
	}
	
	private void configure(Dictionary<?,?> properties) {
		this.cleanupPath = OsgiUtil.toString(properties.get(CLEANUP_PATH), null);
		LOGGER.info("configure: cleanupPath='{}''",this.cleanupPath);
		
		
	}

	@Override
	public void run() {
		LOGGER.info("Running Cleanup");
		Session session = null;
		
		try{
			session = repository.loginAdministrative(null);
			if(session.itemExists(cleanupPath)){
				session.removeItem(cleanupPath);
				LOGGER.info("node deleted");
				session.save();
			}
		}
		catch(RepositoryException e){
			LOGGER.error("exception during cleanup",e);
		}finally{
			if(session != null){
				session.logout();
			}
		}

	}

}
