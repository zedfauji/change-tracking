package com.wizecommerce.cts.utils;

public class UserSettings {
	private int id;
	private int recordsPerPage;
	private int uId;
	
	public UserSettings(){}
	
	public UserSettings(int id, int uId, int recordsPerPage) {
		this.setId(id);
		this.setuId(uId);
		this.setRecordsPerPage(recordsPerPage);
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
	}
}