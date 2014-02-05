<!DOCTYPE html>
<%@include file="header.jsp" %>

<%
	Hibernate hibernate = new Hibernate();
	Iterator<?> userSettingIterator = hibernate.executeSelectQuery("FROM UserSettings WHERE uId = 1", true);
	UserSettings userSetting = (UserSettings) userSettingIterator.next();
	hibernate.terminateSession();
%>
<div class = "container">
	<div class="page-header" id="banner">
       <div class="row">
          <div class="col-lg-15">
            <div class="well-lg">
              <form class="bs-example form-horizontal" action="Settings" method="post">
                <fieldset>
                  <legend>User Settings </legend>
                  	<%
                  	if(request.getParameter("rs") != null && request.getParameter("rs").equalsIgnoreCase("1")){
                  	%>
                  	<div class="alert alert-dismissable alert-success">
             			<button type="button" class="close" data-dismiss="alert">×</button>
              			<strong>Great! We preserved your preferences</strong>
            		</div>
            		<% } %>
                  <div class="form-group">
                    <label for="inputUser" class="col-lg-3 control-label">User Name</label>
                    <div class="col-lg-2">
                      <input type="text" class="form-control" id="userName" name = "userName" value = "Guest" readonly="readonly">
                      <input type="hidden" class="form-control" id="userId" name = "userId" value = "1">
                    </div>
                  </div>
               
                  <div class="form-group">
                    <label for="select" class="col-lg-3 control-label">Records Per Page </label>
                    <div class="col-lg-2">
                      <select class="form-control" id="recordsPerPage" name = "recordsPerPage">
                      	<option value = "5" <%=((userSetting.getRecordsPerPage() == 5)?"selected":"")%>>5</option>
                        <option value = "10" <%=((userSetting.getRecordsPerPage() == 10)?"selected":"")%>>10</option>
                        <option value = "15" <%=((userSetting.getRecordsPerPage() == 15)?"selected":"")%> >15</option>
                        <option value = "20" <%=((userSetting.getRecordsPerPage() == 20)?"selected":"")%> >20</option>
                        <option value = "30" <%=((userSetting.getRecordsPerPage() == 30)?"selected":"")%>>30</option>
                        <option value = "40" <%=((userSetting.getRecordsPerPage() == 40)?"selected":"")%>>40</option>
                        <option value = "50" <%=((userSetting.getRecordsPerPage() == 50)?"selected":"")%>>50</option>
                      </select>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <div class="col-lg-10 col-lg-offset-2">
                      <button type="submit" class="btn btn-default btn-sm" name = "cancel">Cancel</button> 
                      <button type="submit" class="btn btn-primary btn-sm" name = "submit">Submit</button> 
                    </div>
                  </div>
                </fieldset>
              </form>
            </div>
          </div>
         </div>
        </div>
	</div>