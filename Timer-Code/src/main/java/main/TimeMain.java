package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: gdudhwal
 * Date: 28/01/14
 * Time: 1:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimeMain {

    public static Logger logger = LoggerFactory.getLogger(TimeMain.class);

    public static void main(String args[]) {
        /**
         * Here we are starting each and every service related to scheduler and rmq listener
         */

         //1. Create a scheduler
         MySchedule mySchedule = MySchedule.getInstance();
         mySchedule.initService();
        logger.info("Calling myschedule initializer from main class ");
        
        

    }

}
