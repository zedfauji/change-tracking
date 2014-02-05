package com.wizecommerce.cts.utils;

public class JobDetails {
	private String schedName;
	private String jobName;
	private String jobClassName;
	private String isDurable;
	private JobTriggers jobTriggers;
	

	public JobTriggers getJobTriggers() {
		return jobTriggers;
	}
	
	public void setJobTriggers(JobTriggers jobTriggers) {
		this.jobTriggers = jobTriggers;
	}
	
	public JobDetails() {}
	
	public String getSchedName() {
		return schedName;
	}

	public void setSchedName(String schedName) {
		this.schedName = schedName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobClassName() {
		return jobClassName;
	}

	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}

	public String getIsDurable() {
		return isDurable;
	}

	public void setIsDurable(String isDurable) {
		this.isDurable = isDurable;
	}
}
