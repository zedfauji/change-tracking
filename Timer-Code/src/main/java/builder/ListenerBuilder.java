package builder;

import java.util.logging.Logger;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.w3c.dom.Element;


public class ListenerBuilder implements JobListener{
	
	Logger logger = Logger.getLogger("Timer Job Listener");
	public Element jobInfo;
	
	public ListenerBuilder(Element jobInfo) {
		this.jobInfo = jobInfo;
	}
	
	public String getName() {
		return "Job ID : - " + jobInfo.getAttribute("job_id") + " - Has been started";
	}
	 
	public void jobToBeExecuted(JobExecutionContext context) {
		logger.info("Job Execution logged in DB");
	}

	public void jobExecutionVetoed(JobExecutionContext context) {}

	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		logger.info("Job Execution logged in DB");
	}

}
