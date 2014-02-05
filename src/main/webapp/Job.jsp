<%@page import="com.wizecommerce.cts.utils.Hibernate"%>
<%@page import="com.wizecommerce.cts.utils.JobDetails"%>
<%@page import="com.wizecommerce.cts.utils.JobTriggers"%>
<%@page import="com.wizecommerce.cts.utils.CTSDate"%>
<%@page import="java.util.Iterator"%>

<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.SQLException"%>

<%@include file="header.jsp" %>
<%
	Integer p = 0;
	String prev_class = "previous";
	if(request.getParameter("p") != null) {
	        p =  Integer.parseInt(request.getParameter("p"));
	}
	if(p < 0) {
	        prev_class += " disabled";
	        p = 0;
	}
	
%>
<div class="container">
     <div class="page-header" id="banner">
       <div class="row">
         <div class="col-lg-6">
           <div class="bs-example">
             	<ul class="list-unstyled">
	                <li><a href="./CreateJob.jsp"><img alt="add job" src="resources/images/add.png" style = "margin-top: -6px">  New Job</a></li>
	            </ul>
           </div>
           <div class="bs-example table-responsive">
             <table class="table table-striped table-bordered table-hover">
               <thead>
                 <tr>
                   <th nowrap>Job Name</th>
                   <th nowrap>Class Factory</th>
                   <th nowrap>Scheduler Name</th>
                   <th nowrap>Trigger</th>
                   <th nowrap>Next Run Time</th>
                   <th nowrap>Last Run Time</th>
                   <th nowrap>State</th>
                   <th nowrap>Start Time</th>
                 </tr>
               </thead>
               <tbody>
        <%
        Connection conn = null;
        Statement stmt = null;
    	ResultSet resultSet = null;
    	try{
    		Class.forName("com.mysql.jdbc.Driver");
    		conn = DriverManager.getConnection("jdbc:mysql://localhost/cts", "root" , "");
    		String query = "SELECT J.JOB_NAME,J.SCHED_NAME,J.JOB_CLASS_NAME,J.IS_DURABLE,T.TRIGGER_NAME,FROM_UNIXTIME(SUBSTRING(T.NEXT_FIRE_TIME,1,10)) AS NEXT_FIRE_TIME,FROM_UNIXTIME(SUBSTRING(T.PREV_FIRE_TIME,1,10)) AS PREV_FIRE_TIME,T.PRIORITY,T.TRIGGER_STATE,FROM_UNIXTIME(SUBSTRING(T.START_TIME,1,10)) AS START_TIME FROM QRTZ_JOB_DETAILS as J INNER JOIN QRTZ_TRIGGERS as T ON J.JOB_NAME = T.JOB_NAME";
    		stmt = conn.createStatement();
    		resultSet = stmt.executeQuery(query);
    			        
        	while(resultSet.next()) {
        		String[] jobName = resultSet.getString("JOB_NAME").split("_");
        		
        %>
				<tr>
                    <td><a target = "_blank" href = "CreateJob.jsp?id=<%=jobName[1] %>"><%=resultSet.getString("JOB_NAME") %></a></td>
                    <td nowrap><%=resultSet.getString("JOB_CLASS_NAME") %></td>
                    <td nowrap><%=resultSet.getString("SCHED_NAME") %></td>
                    <td nowrap><%=resultSet.getString("TRIGGER_NAME") %></td>
                    <td nowrap><%=resultSet.getString("NEXT_FIRE_TIME") %></td>
                    <td nowrap><%=resultSet.getString("PREV_FIRE_TIME") %></td>
                    <td nowrap><%=resultSet.getString("TRIGGER_STATE") %></td>
                    <td nowrap><%=resultSet.getString("START_TIME") %></td>
                </tr>
		<%
			}
        	stmt.close();
			conn.close();    
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
		%>
                 </tbody>
             </table>
           </div>
           <div class="pull-right">
            <ul class="pager">
                <li class="<%=prev_class%>"><a href="./Job.jsp?p=<%=p - 1%>">Previous</a></li>
                <li class="next"><a href="./Job.jsp?p=<%=p + 1%>" >Next</a></li>
            </ul>
           </div>
         </div>
       </div>
     </div>
</div>
