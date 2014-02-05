package com.wizecommerce.cts.utils;

import java.util.Set;

public class JobTriggers {
	private String triggerName;
	private String jobName;
	private String triggerState;
	private long nextFireTime;
	private long prevFireTime;
	private long startTime;
	private int priority;

	private Set<JobDetails> jobDetails;
	
	public JobTriggers() {}
	
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getTriggerState() {
		return triggerState;
	}
	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
	}
	public long getNextFireTime() {
		return nextFireTime;
	}
	public void setNextFireTime(long nextFireTime) {
		this.nextFireTime = nextFireTime;
	}
	public long getPrevFireTime() {
		return prevFireTime;
	}
	public void setPrevFireTime(long prevFireTime) {
		this.prevFireTime = prevFireTime;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public Set<JobDetails> getJobDetails() {
		return jobDetails;
	}

	public void setJobDetails(Set<JobDetails> jobDetails) {
		this.jobDetails = jobDetails;
	}
}
