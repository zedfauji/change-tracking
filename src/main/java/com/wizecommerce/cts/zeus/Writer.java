package com.wizecommerce.cts.zeus;

import java.io.StringReader;
import java.lang.reflect.Method;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.quartz.Job;
//import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.wizecommerce.cts.utils.Hibernate;
import com.wizecommerce.cts.utils.ChangeRecord;
import com.wizecommerce.cts.utils.RabbitMQ;

public class Writer implements Job{

	//public static void main(String[] args) {
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		try {
		    // creating db connection with local db
			Hibernate hib = new Hibernate();
			// Connect to RabbitMQ
			RabbitMQ rabbitMq = new RabbitMQ();
			rabbitMq.connectRabbitMQ("cts_scrubber");
			rabbitMq.setConsume();
		    
			//JobDataMap dataMap = context.getJobDetail().getJobDataMap();
			//int messageToProcess = Integer.parseInt(dataMap.getString("message_to_process"));
			int messageToProcess = 100;
			int counter = 0;
			
			while(counter < messageToProcess) {
		        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				InputSource is = new InputSource(new StringReader(rabbitMq.getPacketFromQueue()));
				Document xmlDoc = builder.parse(is);
				
				Element changePacket = (Element) xmlDoc.getElementsByTagName("changePacket").item(0);
				ClassLoader myClassLoader = ClassLoader.getSystemClassLoader();
				Class<?> sourceClass = myClassLoader.loadClass("com.wizecommerce.cts.utils" + "." + changePacket.getAttribute("source") + "ChangeRecord");
				Object sourceInstance = sourceClass.newInstance();
	        
				NodeList commonNodes = xmlDoc.getElementsByTagName("common");
				NodeList customNodes = xmlDoc.getElementsByTagName("custom");
					
				// setValues in ChangeRecord class
				for(int i = 0; i < commonNodes.getLength(); i++) {
					Element commonNode = (Element) commonNodes.item(i);
					ChangeRecord changeRecord = new ChangeRecord(commonNode);
					hib.executeInsertQuery(changeRecord);
					
					int attempt = Integer.parseInt(commonNode.getAttribute("attempt"));
					attempt=attempt+1;
					
					Element customNode = (Element) customNodes.item(i);
					Method sourceConstruct = sourceClass.getMethod("customChangeRecord", new Class[] { org.w3c.dom.Element.class });
	            	sourceConstruct.invoke(sourceInstance, new Object[] { customNode });
				}
				
				counter++;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
