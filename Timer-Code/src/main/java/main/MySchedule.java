package main;

import listener.RMQlistener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.PrivateService;

import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gdudhwal
 * Date: 28/01/14
 * Time: 1:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class MySchedule extends PrivateService {

    private static volatile MySchedule instance;
    public static final Logger logger = LoggerFactory.getLogger(MySchedule.class);
    public static String SchedulerName;

    public static MySchedule getInstance(){
        if(instance == null) {
            synchronized (MySchedule.class) {
                if(instance == null) {
                    instance = new MySchedule();
                    instance.init();
                }
            }
        }

        return instance;
    }


    @Override
    public void initService() {

        logger.info("Initializing Myschedule ....");

        try{
        startListener();
        initScheduler();
        
        }
        catch (Exception ecx) {
            logger.info("Exception while initializing in initService()");
            ecx.printStackTrace();
        }

    }

    @Override
    public void destroyService() {

        logger.info("Shutting down myschedule..");

        shutdownScheduler();
    }



    private void initScheduler() throws SchedulerException{
        logger.info("Starting scheduler with default quartz.properties");
        String quartzFileName = "quartz.properties";
       StdSchedulerFactory sf = new StdSchedulerFactory(quartzFileName);
       Scheduler scheduler = sf.getScheduler();
        SchedulerName = scheduler.getSchedulerName();
        logger.info("Scheduler started , now resuming jobs ");
        scheduler.start();
        
        for(String group : scheduler.getTriggerGroupNames()) {
            //parse each trigger in group
            for(TriggerKey triggerKey : scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(group))) {
               logger.info("resuming trigger = " + scheduler.getTrigger(triggerKey));
                scheduler.resumeTrigger(triggerKey);
            }
        }
        for(String group: scheduler.getJobGroupNames()) {
            //parse each job in group
            for(JobKey jobKey: scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group))) {

                logger.info("resume Job = " + scheduler.getJobDetail(jobKey));
                scheduler.resumeJob(jobKey);
            }
        }
        //scheduler.start();
    }
    
    public  static void stopJob(String jobName) throws SchedulerException{
    	
    	logger.info("Interrupting current Job");
    	
    	Scheduler scheduler = new StdSchedulerFactory().getScheduler(SchedulerName);
    	for(String group: scheduler.getJobGroupNames()) {
    		//parsing each job in group 
    			for(JobKey jobKey: scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group))){
    				logger.info("searching for job now ");
    					if(jobKey.toString().contentEquals(jobName) ) {
    						try {
    							//TODO:Add job completion wait time 
    							// Interrupting job 
    							
    							scheduler.interrupt(jobKey);
    							//TODO: add thread sleep if required
    						} catch(Exception exsc) {
    							exsc.printStackTrace();
    							logger.info("Exception while stopping job");
    						}
    					}
    			}
    	}
    	
    }
    
    public static void removeJob(String jobName) throws SchedulerException {
    	logger.info("Removing Job");
        Scheduler scheduler = new StdSchedulerFactory().getScheduler(SchedulerName);
        // Step 1. get job key to find related triggers to disable
        for(String group: scheduler.getJobGroupNames()) {
            //parsing each job in group
            for(JobKey jobKey: scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group))){
                logger.info("searching for job now ");
                if(jobKey.toString().contentEquals(jobName) ){

                  scheduler.deleteJob(jobKey);
                }


            }
        }
    }
    
    @SuppressWarnings("unchecked")
	public static void disableJob(String jobName) throws SchedulerException{
    	logger.info("Disabling Job " + jobName);
    	Scheduler scheduler = new StdSchedulerFactory().getScheduler(SchedulerName);
    	// Step 1. get job key to find related triggers to disable
    	for(String group: scheduler.getJobGroupNames()) {
    		//parsing each job in group 
    			for(JobKey jobKey: scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group))){
    				logger.info("searching for job now ");
    					if(jobKey.toString().contentEquals(jobName) ){
                             List<Trigger> jobTrigger = (List<Trigger>) scheduler.getTriggersOfJob(JobKey.jobKey(String.valueOf(jobKey)));
                            Iterator<Trigger> triggerIterator= jobTrigger.iterator();
                            while(triggerIterator.hasNext())
                            {
                                scheduler.unscheduleJob(TriggerKey.triggerKey(String.valueOf(jobTrigger))) ;

                            }

                            }
    						

    					}
        }
    	
    }
    
    

    public static String getSchedulerName() {
        return SchedulerName;
    }
    
    private void startListener() {
    	RMQlistener th = new RMQlistener();
    	Thread t1 = new Thread(th);
    	t1.setDaemon(true);
    	t1.start();
    }

    private void shutdownScheduler() {
        logger.info("Shutting down scheduler ");


    }

}
