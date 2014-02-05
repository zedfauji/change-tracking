package com.wizecommerce.cts.zeus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminDate {
	
	public static Logger logger = LoggerFactory.getLogger(AdminDate.class);
	
	public static void setAdminDate(String subSourceId){
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection conn = null;
	        Statement stmt = null;
	    	int resultSet = 0;

    		conn = DriverManager.getConnection("jdbc:mysql://localhost/cts", "root" , "");
    		String query = "UPDATE sub_sources SET admin_date = NOW(), last_modified_datetime = NOW() WHERE sub_source_id = '" + subSourceId + "'";
    		logger.info("Updatating admin date and last_modified_date for sub_source_id - " + subSourceId);
    		stmt = conn.createStatement();
    		resultSet = stmt.executeUpdate(query);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
