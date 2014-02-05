<%@page import="com.wizecommerce.cts.utils.JobEntry"%>
<%@include file="header.jsp" %>

<%
	int jobId = 0;
	JobEntry entry = null;
	if(request.getParameter("id") != null){
		jobId = Integer.parseInt(request.getParameter("id"));
	}
	if(jobId > 0) {
		Hibernate hibernate = new Hibernate();
		Iterator<?> jobInfoIterator = hibernate.executeSelectQuery("FROM JobEntry jobId WHERE jobId = '" + jobId + "'", true);
		entry = (JobEntry) jobInfoIterator.next();
		entry.getJobName();
		hibernate.terminateSession();
	}
%>
<div class = "container">
	<div class="page-header" id="banner">
       <div class="row">
          <div class="col-lg-15">
            <div class="well-lg">
              <form class="bs-example form-horizontal" action="Job" method="post">
                <fieldset>
                  <legend><%=(jobId > 0)?"Update Job":"Create New Job"%></legend>
                  	<%	if(request.getParameter("rs") != null && request.getParameter("rs").equalsIgnoreCase("1")){ %>
                  	
                  	<div class="alert alert-dismissable alert-success">
             			<button type="button" class="close" data-dismiss="alert">×</button>
              			<strong>Great! We preserved your preferences</strong>
            		</div>
            		<% } %>
                  <div class="form-group">
                    <label for="inputUser" class="col-lg-3 control-label">Job Name</label>
                    <div class="col-lg-2">
                      <input type="text" class="form-control" id="jobName" name = "jobName" value ="<%=(jobId > 0)?entry.getJobName():""%>" <%=(jobId > 0)?"readonly":""%> >
                      <input type="hidden" id="jobId" name="jobId" value = "<%=(jobId > 0)?entry.getJobId():0%>" >
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputUser" class="col-lg-3 control-label">Factory Class</label>
                    <div class="col-lg-4">
                      <input type="text" class="form-control" id=classFactory name = "classFactory" value = <%=(jobId > 0)?entry.getClassFactory():"" %>>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputUser" class="col-lg-3 control-label">Job Parameters</label>
                    <div class="col-lg-6">
                      <textarea class="form-control" rows="3" id="jobParams" name = "jobParams"><%=(jobId > 0)?entry.getJobParams():"" %></textarea>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputUser" class="col-lg-3 control-label">Job Interval</label>
                    <div class="col-lg-2">
                      <input type="number" class="form-control" id="jobInterval" name = "jobInterval" value = <%=(jobId > 0)?entry.getJobInterval():"" %>>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputUser" class="col-lg-3 control-label">Start Date</label>
                    <div class="col-lg-2">
                      <input type="text" class="form-control" id="jobStartDate" name = "jobStartDate" value ="<%=(jobId > 0)?entry.getStartDate():"" %>" >
                    </div>
                  </div>
                  
               
                  <div class="form-group">
                    <label for="select" class="col-lg-3 control-label">Is Repetitive</label>
                    <div class="col-lg-2">
                      <select class="form-control" id="isRepeat" name = "isRepeat">
                      	<option value = "1" <%=(((jobId > 0) && entry.getIsRepeat() == 1)?"selected":"")%>>Yes</option>
                        <option value = "0" <%=(((jobId > 0) && entry.getIsRepeat() == 0)?"selected":"")%>>No</option>
                      </select>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="select" class="col-lg-3 control-label">Status</label>
                    <div class="col-lg-2">
                      <select class="form-control" id="status" name = "status">
                      	<option value = "enabled" <%=(((jobId > 0) && entry.getStatus().equalsIgnoreCase("enabled"))?"selected":"")%>>Enabled</option>
                        <option value = "disabled" <%=(((jobId > 0) && entry.getStatus().equalsIgnoreCase("disabled"))?"selected":"")%>>Disabled</option>
                      </select>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <div class="col-lg-10 col-lg-offset-2">
                      <button type="submit" class="btn btn-default btn-sm" name = "cancel">Back</button> 
                      <button type="submit" class="btn btn-primary btn-sm" name = <%=(jobId > 0)?"edit":"create" %>><%=(jobId > 0)?"Edit":"Create" %></button>
                      <%
                      if(jobId > 0) {
                      %> 
                      	<button type="submit" class="btn btn-danger btn-sm" name = "killJob">Stop</button>
                      	<button type="submit" class="btn btn-danger btn-sm" name = "removeJob">Remove</button>
                      	<button type="submit" class="btn btn-danger btn-sm" name = "disableJob">Disable</button>
                      	<button type="submit" class="btn btn-danger btn-sm" name = "enableJob">Enable</button>
                      <% 
                      } 
                      %> 
                      
                    </div>
                  </div>
                </fieldset>
              </form>
            </div>
          </div>
         </div>
        </div>
	</div>