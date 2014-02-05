<%@page import="com.wizecommerce.cts.utils.Hibernate"%>
<%@page import="com.wizecommerce.cts.utils.UserSettings"%>
<%@page import="java.util.Iterator"%>

 <head>
   <title>Change Tracking</title>
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <meta charset="utf-8">
   <link rel="shortcut icon" href="resources/images/favicon.png" />
   <link rel="stylesheet" href="resources/css/bootstrap.css" media="screen">
   <link rel="stylesheet" href="resources/css/bootswatch.min.css">
   
   <script src="resources/js/jquery-1.10.2.min.js"></script>
   <script src="resources/js/bootstrap.min.js"></script>
   <script src="resources/js/bootswatch.js"></script>
 </head>
    
 <div class="navbar navbar-default navbar-fixed-top">
   <div class="container">
     <div class="navbar-header">
     <!-- <img alt="logo" src="resources/img/logo.png"> -->
       <a href="./" class="navbar-brand">CHANGE TRACKING </a>
       <button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
       </button>
     </div>
     <div class="navbar-collapse collapse" id="navbar-main">
       <ul class="nav navbar-nav">
         
       </ul>

       <ul class="nav navbar-nav navbar-right">
         <li class="dropdown">
           <a class="dropdown-toggle" data-toggle="dropdown" href="#" id="preferences"><img alt="preferences" src="resources/img/tune.png"> <span class="caret"></span></a>
           <ul class="dropdown-menu">
           	
             <li><a tabindex="-1" href="General.jsp" >General</a></li>
             <li class="divider"></li>
             <li><a tabindex="-1" href="Job.jsp" >Job Ops</a></li>
           </ul>
         </li>
         <li class="dropdown">
         	<a class="dropdown-toggle" data-toggle="dropdown" href="#" id="tools"><img alt="preferences" src="resources/img/tools.png"> <span class="caret"></span></a>
         	<ul class="dropdown-menu">
         		<li><a tabindex="-1" href="http://graphite2.pv.sv.nextag.com/cubism/dashboard/main-dashboard.html" target="_blank">Cubism</a></li>
         		<li class="divider"></li>
             	<li><a tabindex="-1" href="http://sv-graphite1/tattle/tattle_console.php?reload=1" target="_blank">Tattle Console</a></li>
           	</ul>
         </li>  
       </ul>

     </div>
   </div>
 </div>