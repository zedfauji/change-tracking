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
                   <th nowrap>ip</th>
                   <th nowrap>agent</th>
                   <th nowrap>proxies</th>
                   <th nowrap>bind</th>
                   <th nowrap>lastupdated</th>
                   <th nowrap>policysettemplate</th>
                   <th nowrap>checkReferrer</th>
                   <th nowrap>url</th>
                 </tr>
               </thead>
               <tbody>
        <%
        Connection conn = null;
        Statement stmt = null;
    	ResultSet resultSet = null;
    	try{
    		Class.forName("com.mysql.jdbc.Driver");
    		conn = DriverManager.getConnection("jdbc:mysql://rmdb.pv.sv.nextag.com/requestormanagement", "logger" , "nextag");
    		String query = "SELECT INET_NTOA(ip) as ip,agent,proxies,bind,lastupdated,policysettemplatename,checkReferrer,url,EXPIRY from aclpolicies where date(lastupdated) > '2014-01-20'";
    		stmt = conn.createStatement();
    		resultSet = stmt.executeQuery(query);
    			        
        	while(resultSet.next()) {
        %>
				<tr>
                    <td nowrap><%=resultSet.getString("ip") %></td>
                    <td><%=resultSet.getString("agent") %></td>
                    <td nowrap><%=resultSet.getString("proxies") %></td>
                    <td nowrap><%=resultSet.getString("bind") %></td>
                    <td nowrap><%=resultSet.getString("lastupdated") %></td>
                    <td nowrap><%=resultSet.getString("policysettemplatename") %></td>
                    <td nowrap><%=resultSet.getString("checkReferrer") %></td>
                    <td nowrap><%=resultSet.getString("url") %></td>
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
