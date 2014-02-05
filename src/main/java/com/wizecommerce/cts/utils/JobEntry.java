package com.wizecommerce.cts.utils;

import java.util.Date;

public class JobEntry {
	
	private int jobId;
	private String jobName;
	private String classFactory;
	private String jobParams;
	private int jobInterval;
	private String status;
	private Date lastRunDate;
	private int isRepeat;
	private String startDate;
	
	public JobEntry() {}
	
	public JobEntry(int jobId, String jobName, String classFactory, String jobParams, int jobInterval, String status, Date lastRunDate, int isRepeat, String startDate) {
		this.jobId=jobId;
		this.jobName=jobName;
		this.classFactory=classFactory;
		this.jobParams=jobParams;
		this.jobInterval=jobInterval;
		this.status=status;
		this.lastRunDate=lastRunDate;
		this.isRepeat=isRepeat;
		this.setStartDate(startDate);
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getClassFactory() {
		return classFactory;
	}

	public void setClassFactory(String classFactory) {
		this.classFactory = classFactory;
	}

	public String getJobParams() {
		return jobParams;
	}

	public void setJobParams(String jobParams) {
		this.jobParams = jobParams;
	}

	public int getJobInterval() {
		return jobInterval;
	}

	public void setJobInterval(int jobInterval) {
		this.jobInterval = jobInterval;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLastRunDate() {
		return lastRunDate;
	}

	public void setLastRunDate(Date lastRunDate) {
		this.lastRunDate = lastRunDate;
	}

	public int getIsRepeat() {
		return isRepeat;
	}

	public void setIsRepeat(int isRepeat) {
		this.isRepeat = isRepeat;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
}
