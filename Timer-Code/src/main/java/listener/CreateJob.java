package listener;

import builder.ListenerBuilder;
import builder.MainJobBuilder;
import builder.TriggerBuilder;
import main.MySchedule;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.StringReader;

/**
 * Created with IntelliJ IDEA.
 * User: gdudhwal
 * Date: 28/01/14
 * Time: 4:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class CreateJob {

    public static Logger logger = LoggerFactory.getLogger(CreateJob.class);



    public static void createJob(String packetFromQueue) {

        try
        {
            try
            {


                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                InputSource is = new InputSource(new StringReader(packetFromQueue));
                Document xmlDoc = builder.parse(is);

                NodeList jobInfoNode = xmlDoc.getElementsByTagName("info");
                NodeList jobParamsNode = xmlDoc.getElementsByTagName("job_params");
                Element jobInfo = (Element) jobInfoNode.item(0);
                Element jobParams = (Element) jobParamsNode.item(0);


                logger.info("Recived message to create a job : JobXML packet for - " + jobInfo.getAttribute("job_name"));
                JobDetail job = new MainJobBuilder(jobInfo,jobParams).getJob();

                logger.info("Jobs created , now moving to triggers ");

                SimpleTrigger trigger = new TriggerBuilder(jobInfo).getTrigger();
                 String sched = MySchedule.getSchedulerName();
                Scheduler scheduler = new StdSchedulerFactory().getScheduler(sched);
                logger.info("Adding listener also ");
                logger.info("Adding to scheduler " + scheduler.getSchedulerName());
                scheduler.getListenerManager().addJobListener(new ListenerBuilder(jobInfo));
                if (jobInfo.getAttribute("action").matches("(.*)update(.*)")) {
                		scheduler.addJob(job, true);
                } 
                
                else if (jobInfo.getAttribute("action").matches("(.*)stop(.*)"))
                	{
                		MySchedule.stopJob(jobInfo.getAttribute("job_name"));
                	}	
                	
                	else if (jobInfo.getAttribute("action").matches("(.*)disable(.*)"))
                	{
                		//TODO: Add disabling of Job
                        MySchedule.disableJob(jobInfo.getAttribute("job_name"));

                	}else if(jobInfo.getAttribute("action").matches("(.*)remove(.*)"))
                	{
                		//TODO: Add removal of a job

                        MySchedule.removeJob(jobInfo.getAttribute("job_name"));
                	} else
                	{
                		scheduler.scheduleJob(job,trigger);
                	}
                	
                
                logger.info("Job has been scheduled");
                logger.info("Starting Jobs");
                scheduler.start();
                logger.info("Job has been completed");
                
            }

            catch (SchedulerException sexc) {
                logger.info("Scheduling job failed in createJob");
            }

            catch (Exception e ) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
