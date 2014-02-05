package com.wizecommerce.cts.zeus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class RM {
	
	public Connection conn = null;
	public Statement stmt = null;
	public ResultSet resultSet = null;
	
	public void connectDb(HashMap<String, String> credentialDetails) throws SQLException {
		conn = DriverManager.getConnection(credentialDetails.get("host"), credentialDetails.get("user") , credentialDetails.get("password"));
	}
	
	public String getChanges(HashMap<String, String> sourceInfo_) {
		try{
			
			String query = "SELECT INET_NTOA(ip) as ip,INET_NTOA(ipnetmask) as ipnetmask,agent,proxies,bind,unix_timestamp(lastupdated) as datetime,"
					+ "policysettemplatename,checkReferrer,url,EXPIRY"
					+ " FROM aclpolicies WHERE date(lastupdated) >= '" +  sourceInfo_.get("adminDate") + "'";
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(query);
			
			HashMap<String, String> changeInfoCommon = new HashMap<String, String>();
			HashMap<String, String> changeInfoCustom = new HashMap<String, String>();
			
			String changeXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?><changePacket source = \"" + sourceInfo_.get("sourceName") + "\">";
			
			while(resultSet.next()){
				
				changeInfoCommon.put("attempt", "0");
				changeInfoCommon.put("source_name", sourceInfo_.get("sourceName"));
				changeInfoCommon.put("source_id", sourceInfo_.get("sourceID"));
				changeInfoCommon.put("sub_source_name", sourceInfo_.get("subSourceName"));
				changeInfoCommon.put("sub_source_id", sourceInfo_.get("subSourceID"));
				changeInfoCommon.put("description", resultSet.getString("policysettemplatename") + " - " + resultSet.getString("ip")
						+ " - " + resultSet.getString("agent"));
				changeInfoCommon.put("source_datetime", resultSet.getString("datetime"));
				changeInfoCommon.put("status", "ENABLED");
				
				changeInfoCustom.put("ip", resultSet.getString("ip"));
				changeInfoCustom.put("ipnetmask", resultSet.getString("ipnetmask"));
				changeInfoCustom.put("agent", resultSet.getString("agent"));
				changeInfoCustom.put("proxies", resultSet.getString("proxies"));
				changeInfoCustom.put("bind", resultSet.getString("bind"));
				changeInfoCustom.put("policysettemplatename", resultSet.getString("policysettemplatename"));
				changeInfoCustom.put("checkReferrer", resultSet.getString("checkReferrer"));
				changeInfoCustom.put("url", resultSet.getString("url"));
				changeInfoCustom.put("EXPIRY", resultSet.getString("EXPIRY"));
				
				changeXML += new ChangeXML(changeInfoCommon, changeInfoCustom).getchangeXML();
			}

			changeXML += "</changePacket>";

			stmt.close();
			conn.close();    

			return changeXML;
		}
		catch(SQLException se){
		    //Handle errors for JDBC
			return se.getMessage();
		}
		catch(Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public void closeConnection() throws SQLException{
		if(!this.conn.isClosed())
			this.conn.close();
	}
}
