package com.wizecommerce.cts.agenor;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wizecommerce.cts.timer.JobXML;
import com.wizecommerce.cts.utils.Hibernate;
import com.wizecommerce.cts.utils.JobEntry;
import com.wizecommerce.cts.utils.RabbitMQ;

/**
 * Servlet implementation class Job
 */
public class Job extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Job() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("edit") != null || request.getParameter("killJob") != null || request.getParameter("create") != null 
				|| request.getParameter("removeJob") != null || request.getParameter("disableJob") != null || request.getParameter("enableJob") != null) {
			int rs = 0;
			int jobId = Integer.parseInt(request.getParameter("jobId"));
			if(request.getParameter("edit") != null || request.getParameter("killJob") != null || jobId > 0) {
				Hibernate hibernate = new Hibernate();
				String query = "UPDATE JobEntry SET "
						+ "jobName = '" + request.getParameter("jobName") + "',"
						+ "classFactory = '" + request.getParameter("classFactory") + "',"
						+ "jobParams = '" + request.getParameter("jobParams") + "',"
						+ "jobInterval = '" + request.getParameter("jobInterval") + "',"
						+ "isRepeat = '" + request.getParameter("isRepeat") + "',"
						+ "status = '" + request.getParameter("status") + "'"
						+ " WHERE jobId = '" + request.getParameter("jobId") + "'";
				
				rs = hibernate.executeUpdateQuery(query);
				hibernate.terminateSession();
				
				HashMap<String, String> jobHash = new HashMap<String, String>();
				jobHash.put("job_id", request.getParameter("jobId"));
				jobHash.put("job_name", request.getParameter("jobName"));
				jobHash.put("class_factory", request.getParameter("classFactory"));
				jobHash.put("job_interval", request.getParameter("jobInterval"));
				jobHash.put("is_repeat", request.getParameter("isRepeat"));
				jobHash.put("start_date", request.getParameter("jobStartDate"));
				
				if(request.getParameter("edit") != null) { 
					jobHash.put("action", "update");
				}
				else if(request.getParameter("killJob") != null) {
					jobHash.put("action", "stop");
				}
				else if(request.getParameter("removeJob") != null) {
					jobHash.put("action", "remove");
				}
				else if(request.getParameter("disableJob") != null) {
					jobHash.put("action", "disable");
				}
				else if(request.getParameter("enableJob") != null) {
					jobHash.put("action", "enable");
				}
				
				JobXML jobXML = new JobXML(jobHash, request.getParameter("jobParams"));
				System.out.println(jobXML.getJobXML());
	        	
				RabbitMQ rabbitMq = new RabbitMQ();
	            rabbitMq.connectRabbitMQ("cts_job");
	            rabbitMq.publishPacket(jobXML.getJobXML());
	            rabbitMq.terminateSession();
	            
				response.sendRedirect("./CreateJob.jsp?id=" + request.getParameter("jobId") + "&rs="+rs);
			}
			else if(request.getParameter("create") != null){
				System.out.println("====Create New Job===");
				JobEntry jobEntry = new JobEntry();
				jobEntry.setJobName(request.getParameter("jobName"));
				jobEntry.setClassFactory(request.getParameter("classFactory"));
				jobEntry.setJobParams(request.getParameter("jobParams"));
				jobEntry.setJobInterval(Integer.parseInt(request.getParameter("jobInterval")));
				jobEntry.setStartDate(request.getParameter("jobStartDate"));
				jobEntry.setStatus(request.getParameter("status"));
				
				if(request.getParameter("isRepeat") != null)
					jobEntry.setIsRepeat(Integer.parseInt(request.getParameter("isRepeat")));
				
				Hibernate hibernate = new Hibernate();
				hibernate.executeInsertQuery(jobEntry);
				hibernate.terminateSession();
				System.out.println("## JOB ID generated is ### " + jobEntry.getJobId());
				
				//Create Job packet - RabbitMQ 
				HashMap<String, String> jobHash = new HashMap<String, String>();
				jobHash.put("job_id", String.valueOf(jobEntry.getJobId()));
				jobHash.put("job_name", request.getParameter("jobName"));
				jobHash.put("class_factory", request.getParameter("classFactory"));
				jobHash.put("job_interval", request.getParameter("jobInterval"));
				jobHash.put("is_repeat", request.getParameter("isRepeat"));
				jobHash.put("status", request.getParameter("status"));
				jobHash.put("start_date", request.getParameter("jobStartDate"));
				jobHash.put("action", "create");
				
				JobXML jobXML = new JobXML(jobHash, request.getParameter("jobParams"));
				System.out.println(jobXML.getJobXML());
	        	
				RabbitMQ rabbitMq = new RabbitMQ();
	            rabbitMq.connectRabbitMQ("cts_job");
	            rabbitMq.publishPacket(jobXML.getJobXML());
	            rabbitMq.terminateSession();
	            
				response.sendRedirect("./Job.jsp");
			}
		}
		else{
			response.sendRedirect("./Job.jsp");
		}
	}

}
