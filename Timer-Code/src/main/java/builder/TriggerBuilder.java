package builder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.quartz.SimpleTrigger;
import org.w3c.dom.Element;

import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;



public class TriggerBuilder {
	
	public static Logger logger = Logger.getLogger("devtriggerBuilder");
    public Element jobInfo;

    public TriggerBuilder(Element jobInfo){
    	this.jobInfo = jobInfo;
    }
    
    public SimpleTrigger getTrigger() throws ParseException {
        logger.info("Now creating trigger");
        //SimpleTrigger trigger;
        String start_date = jobInfo.getAttribute("start_date");
        DateFormat formatter;
        Date startDate;
        //SimpleTrigger trigger;
        formatter =  new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
 		startDate = formatter.parse(start_date);

        if (Integer.parseInt(this.jobInfo.getAttribute("is_repeat")) > 0) {
            logger.info("Job is repetitive , so creating a repeat enabled trigger");
          
            SimpleTrigger trigger = newTrigger()
            		.withIdentity("trigger_" + jobInfo.getAttribute("job_id"))
            		.startAt(startDate)
            		.withSchedule(simpleSchedule().repeatForever().withIntervalInMinutes(Integer.parseInt(jobInfo.getAttribute("job_interval"))))
            		.build();
            
            return trigger;
            
        } 
        else {
            logger.info("Job isn't repeatitive , so create non repeatitive trigger");
       
            SimpleTrigger trigger = newTrigger()
            		.withIdentity("trigger_" + jobInfo.getAttribute("job_id"))
            		.startAt(startDate)
            		.withSchedule(simpleSchedule().withIntervalInMinutes(Integer.parseInt(jobInfo.getAttribute("job_interval"))))
            		.build();
            
            return trigger;
            		
        }
        
    }

}
