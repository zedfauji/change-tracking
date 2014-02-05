package com.wizecommerce.cts.zeus;

import java.util.Date;
import java.util.Iterator;

import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wizecommerce.cts.utils.Hibernate;
import com.wizecommerce.cts.utils.Source;

/**
 * Scrubber class will create n number of threads that will go to specific data sources
 * it will scrub data and put it in rabbitMQ
 * Integrating {@link Hibernate}
 * @author panand
 * @version 0.0.1
 */
public class Scrubber implements Job,InterruptableJob{
	private volatile boolean isJobInterrupted = false;
	private volatile Thread  thisThread;
	public static Logger logger = LoggerFactory.getLogger(Scrubber.class);
	private JobKey jobKey           = null;
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		// Here we are handling current executing thread object
		thisThread = Thread.currentThread();
		logger.info("Thread name of current Job:" + thisThread.getName());
		
		jobKey = context.getJobDetail().getKey();
		logger.info("Job"  + jobKey + "executing at " + new Date());
		try
		{
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		logger.info("==============Scrubber Class================");
		
		Hibernate hib = new Hibernate();
		Iterator<?> sourceInfoIterator = hib.executeSelectQuery("FROM Source WHERE is_active = 1 AND sourceName = '" + dataMap.getString("source") + "'", false);
		
		while(sourceInfoIterator.hasNext()) {
			Source entry = (Source) sourceInfoIterator.next();
			logger.info("==============STARTING THREAD FOR ====" + entry.getSourceName() + "============");
			new ExtractData(entry, dataMap);
		}
		hib.terminateSession();
		} catch(Exception es) {
			es.printStackTrace();
		} finally {
			if(isJobInterrupted ) {
				logger.info("Job" + jobKey + "did not complete");
			}
			else{
				logger.info("Job" + jobKey + "completed at" + new Date());
			}
		}
	}
	
	public void interrupt() throws UnableToInterruptJobException {
		logger.info("Job" + jobKey + " --------INTERRUPTING -------- ");
		isJobInterrupted = true;
		if(thisThread != null) {
			//this call causes the closedbyinterrupt exception to happen 
			thisThread.interrupt();
		}
	}
	
	
}
