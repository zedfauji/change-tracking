<%@page import="com.wizecommerce.cts.utils.Hibernate"%>
<%@page import="com.wizecommerce.cts.utils.ChangeRecord"%>
<%@page import="com.wizecommerce.cts.utils.CTSDate"%>
<%@page import="java.util.Iterator"%>

<%@include file="header.jsp" %>
<%
        Integer p = 0;
        String prev_class = "previous";
        if(request.getParameter("p") != null) {
                p =  Integer.parseInt(request.getParameter("p"));
        }
        if(p < 1) {
                prev_class += " disabled";
                //p = 1;
        }
%>
<script src="resources/js/d3.v3.min.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/Chart.js" type="text/javascript"></script>

<div class="container">
	<div id="treeDiv" class="well well-sm">
    	<%@include file="t2.jsp" %>
    </div>
     <div class="page-header" id="banner">
       <div class="row">
         <div class="col-lg-6">
           <!-- Table was here  -->
           <div class="list-group">
           		<div class="form-group">
           		<form class="bs-example form-horizontal" method="post">
                  <div class="input-group">
                    <span class="input-group-addon">#</span>
                    <input type="text" class="form-control" placeholder="Search Events" name="kw" id="kw">
                    <span class="input-group-btn">
                      <button class="btn btn-default" type="submit">Search</button>
                    </span>
                  </div>
                  </form>
                </div>
		<%
        CTSDate ctsDate = new CTSDate();
        Hibernate hibernate = new Hibernate();
        Iterator<?> userSettingIterator = hibernate.executeSelectQuery("FROM UserSettings WHERE uId = 1", true);
    	UserSettings userSetting = (UserSettings) userSettingIterator.next();
    	String whereStr = "";
    	if(request.getParameter("kw") != null) {
    		whereStr = "WHERE description LIKE '%" + request.getParameter("kw") + "%'";
    	}
        Iterator<?> changeInfoIterator = hibernate.executeSelectQuery("FROM ChangeRecord " + whereStr + " ORDER BY sourceDatetime DESC", p * userSetting.getRecordsPerPage() , userSetting.getRecordsPerPage());
        hibernate.terminateSession();
                while(changeInfoIterator.hasNext()) {
                        ChangeRecord entry = (ChangeRecord) changeInfoIterator.next();
        %>
           
                <a href="./detailChange.jsp?id=<%=entry.getCrId() %>" target = "_blank" class="list-group-item">
                  <span class="list-group-item-heading"><%=entry.getDescription() %></span>
                  <span class="<%=(entry.getStatus().equalsIgnoreCase("SUCCESS") || entry.getStatus().equalsIgnoreCase("COMPLETED"))?
                                "label label-success":"label label-danger"%>"><%=entry.getStatus() %>
                  </span>
                  <p class="list-group-item-text">Source : <%=entry.getSourceName() %></p>
                  <p class="list-group-item-text">Sub Source : <%=entry.getSubSourceName() %></p>
                  <p class="list-group-item-text">Source Datetime : <%=ctsDate.epochToString(Integer.parseInt(entry.getSourceDatetimeString()))  %></p>
                </a>
        <%
                }
        %>
           </div>
           
           <div class="pull-right">
                        <ul class="pager">
                <li class="<%=prev_class%>"><a href="./?p=<%=p - 1%>">Previous</a></li>
                <li class="next"><a href="./?p=<%=p + 1%>" >Next</a></li>
            </ul>
            </div>
         </div>
          <div class="col-xs-6 col-sm-3 sidebar-offcanvas pull-right" id="sidebar" role="navigation">
          <div class="row">
          <div class="col-xs-5 well">
          	<canvas id="canvas" height="250" width="250"></canvas>
          	<script>
				var doughnutData = [
						{
							value: 30,
							name: "GLU",
							color:"#F7464A"
						},
						{
							value : 50,
							name: "Experiment",
							color : "#46BFBD"
						},
						{
							value : 100,
							name: "Deployment",
							color : "#FDB45C"
						},
						{
							value : 40,
							name: "Bugzilla",
							color : "#949FB1"
						},
						{
							value : 120,
							name: "RM",
							color : "#4D5360"
						}
					];
			var myDoughnut = new Chart(document.getElementById("canvas").getContext("2d")).Doughnut(doughnutData);
			</script>
          </div>
          <div class="col-lg-6 well">
          	<canvas id="canvas_line" height="250" width="300"></canvas>
            	<script>
            	var lineChartData = {
            			labels : ["-6 hrs","-4 hrs","-2 hrs","Now"],
            			datasets : [
            				{
            					fillColor : "rgba(151,187,205,0.5)",
            					strokeColor : "rgba(151,187,205,1)",
            					pointColor : "rgba(151,187,205,1)",
            					pointStrokeColor : "#fff",
            					data : [28,38,50,55,65]
            				}
            			]
            			
            		}

            	var myLine = new Chart(document.getElementById("canvas_line").getContext("2d")).Line(lineChartData);
            	</script>
          </div>
          </div> <!-- internal row  -->
			
		  <div class="col-xs-12 list-group">
            <a href="#" class="list-group-item active">Per Data Source Stats</a>
            <a href="#" id="g" class="list-group-item" style = "background-color: #B2B232;">GLU</a>
            <a href="#" id="g" class="list-group-item" style = "background-color: #46BFBD;">Experiment</a>
            <a href="#" id="g" class="list-group-item" style = "background-color: #949FB1;">Bugzilla</a>
            <a href="#" id="g" class="list-group-item" style = "background-color: #FDB45C;">Deployments</a>
          </div>
          <%
          	String t = "485,300,400,450,500";
          %>
          	<script type="text/javascript">
				d3.selectAll("#g")
			    	.data([<%=t%>])
			    	.transition()
			    	.duration(2000)
			    	.style("width", function(d) { return  d + "px"; });
			</script>
        </div><!--/span-->
      </div><!--/row-->
       </div>
     </div>
</div>