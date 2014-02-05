<%@page import="com.wizecommerce.cts.utils.Hibernate"%>
<%@page import="com.wizecommerce.cts.utils.ChangeRecord"%>
<%@page import="com.wizecommerce.cts.utils.CTSDate"%>

<%@page import="java.util.Iterator"%>
<%@include file="header.jsp" %>

<div class="container">
     <div class="page-header" id="banner">
       <div class="row">
         <div class="col-lg-6">
           <div class="bs-example table-responsive">
             <table class="table table-striped table-bordered table-hover">
               <thead>
                 <tr>
                   <th nowrap>#</th>
                   <th nowrap>Description</th>
                   <th nowrap>Source Name</th>
                   <th nowrap>SubSource Name</th>
                   <th nowrap>Status</th>
                   <th nowrap>Timestamp</th>
                 </tr>
               </thead>
               <tbody>
        <%
        CTSDate ctsDate = new CTSDate();
        Hibernate hibernate = new Hibernate();
        Iterator<?> userSettingIterator = hibernate.executeSelectQuery("FROM UserSettings WHERE uId = 1", true);
    	UserSettings userSetting = (UserSettings) userSettingIterator.next();
    	String whereStr = "";
    	if(request.getParameter("id") != null) {
    		whereStr = " WHERE crId = '" + request.getParameter("id") + "'";
    	}
        Iterator<?> changeInfoIterator = hibernate.executeSelectQuery("FROM ChangeRecord" + whereStr, true);
        hibernate.terminateSession();
        	while(changeInfoIterator.hasNext()) {
        		ChangeRecord entry = (ChangeRecord) changeInfoIterator.next();
        %>
				<tr>
                    <td><%=entry.getCrId() %></td>
                    <td nowrap><%=entry.getDescription() %></td>
                    <td nowrap><%=entry.getSourceName() %></td>
                    <td nowrap><%=entry.getSubSourceName() %></td>
                    <td nowrap><%=entry.getStatus() %></td>
                    <td nowrap><%=ctsDate.epochToString(Integer.parseInt(entry.getSourceDatetimeString())) %></td>
                </tr>
		<%
			}
		%>
                 </tbody>
             </table>
           </div>
         </div>
       </div>
     </div>
</div>
