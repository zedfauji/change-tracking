package builder;


import org.quartz.JobBuilder;
import org.quartz.JobDetail;

import com.wizecommerce.cts.zeus.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class MainJobBuilder {
		
		public static Logger logger = LoggerFactory.getLogger(JobBuilder.class);
	    public Element jobInfo;
	    public Element jobParams;
	    
	    public MainJobBuilder(Element jobInfo, Element jobParams) {
	        this.jobInfo = jobInfo;
	        this.jobParams = jobParams;
	        logger.info("Initialized jobName = " + jobInfo.getAttribute("job_name") 
	        		+ "classFactory = " + jobInfo.getAttribute("class_factory") + "params = "
	        		+ jobInfo.getAttribute("job_params") + "with job id " 
	        		+ jobInfo.getAttribute("job_id"));
	    }
	    
	    /**
	     * This method will actually generates and return job object and map job id with parameters
	     * @return {@link Job}
	     */
	    public JobDetail getJob() {

	        JobDetail job = null;
	        logger.info("Defining job with params 1. job name = " + jobInfo.getAttribute("job_name")
	        		+ "with job id "+ jobInfo.getAttribute("job_id"));
	        
	        if(this.jobInfo.getAttribute("class_factory").matches("(.*)Scrubber(.*)")) {
	            job = JobBuilder.newJob(Scrubber.class).withIdentity(jobInfo.getAttribute("job_name") + "_" + jobInfo.getAttribute("job_id"))
	                    .usingJobData("source",jobParams.getAttribute("source"))
	                    .build();
	        }
	        else {
	            job = JobBuilder.newJob(Writer.class).withIdentity(jobInfo.getAttribute("job_name") + "_" + jobInfo.getAttribute("job_id"))
	            		.usingJobData("message_to_process",jobParams.getAttribute("message_to_process"))
	                    .build();
	        }
	        return job;
	    }
	}



