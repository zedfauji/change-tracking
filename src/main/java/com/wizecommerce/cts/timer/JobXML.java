package com.wizecommerce.cts.timer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class JobXML {
	HashMap<String, String> jobHash;
	String jobParams;
	
	public JobXML(HashMap<String, String> jobHash, String jobParams) {
		this.jobHash = jobHash;
		this.jobParams = jobParams;
	}
	
	public String getJobXML(){
		
		String jobXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?><job><info ";
		
		Set<Entry<String, String>> jobHashInfo = jobHash.entrySet();
		Iterator<Entry<String, String>> jobInfoIterator = jobHashInfo.iterator(); 
		while(jobInfoIterator.hasNext()) {
			Entry<String, String> entry = jobInfoIterator.next();
			jobXML += entry.getKey() + "=\"" + entry.getValue() + "\" ";
		}
		
		jobXML += "></info>" + jobParams;
		jobXML += "</job>";
		
		return jobXML;
	}
}
