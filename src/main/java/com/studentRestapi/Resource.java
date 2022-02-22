package com.studentRestapi;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.studentEntities.Student;
import com.studentServices.ServiceLayer;

@Component
@Path("/studentProject")
public class Resource {

	
	@Autowired
	ServiceLayer serviceLayer;

	/**
	 * Generating key
	 * @return
	 */
	@GET
	@Path("/generateKey")
	@Produces(MediaType.APPLICATION_JSON)
	public Response generateToken() {
		return Response.ok()
				.entity(serviceLayer.generateToken())
				.build();
	}
	
	/**
	 * Searching for full name of student
	 * @param rollNumber
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/getFullName")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFullName(@QueryParam("rollNumber") int rollNumber, @Context HttpServletRequest request) throws Exception {
		return Response.ok()
				.entity(serviceLayer.getFullName(rollNumber,request))
				.build();
	}
	
	
	/**
	 * Updating marks of student
	 * @param rollNumber
	 * @param marksObtained
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("/updateMarks/{rollNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMarks(@PathParam("rollNumber") int rollNumber, @QueryParam("marksObtained") int marksObtained, @Context HttpServletRequest request) throws Exception {
		serviceLayer.updateMarksAndPercentage(rollNumber,marksObtained,request);
		return Response.ok().build();
	}
	
	/**
	 * Deleting student record from database whose rollNumber is given
	 * @param rollNumber
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@DELETE
	@Path("/deleteStudent/{rollNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteStudent(@PathParam("rollNumber") int rollNumber, @Context HttpServletRequest request) throws Exception {
		serviceLayer.deleteStudent(rollNumber,request);
		return Response.ok().build();
	}
	
	
	/**
	 * Returning all the students record
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/getAllStudents")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllStudents(@Context HttpServletRequest request) throws Exception {
		return Response.ok()
				.entity(serviceLayer.getAllStudents(request))
				.build();
	}
	
	
	/**
	 * Adding Student in database
	 * @param student
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/addStudent")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addStudent(Student student,@Context HttpServletRequest request) throws Exception
	{
		serviceLayer.addStudent(student, request);
		return Response.ok().build(); 
	}
	
}
