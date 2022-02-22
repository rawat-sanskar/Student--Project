package com.studentServices;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.studentEntities.Marks;
import com.studentEntities.Student;
import com.studentjdbc.StudentDaoImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class ServiceLayer {

	@Autowired
	StudentDaoImpl studentDaoImpl;
	
	public static String securityKey="P9H2Dzo2GoZ346dQ3IIB7aTw2JiU26d4";

	/**
	 * Generating Token
	 * @return  giving generated token in String 
	 */
	public String generateToken() {
		String jwtToken= Jwts.builder()
				.setSubject("studentApp")
				.setId(UUID.randomUUID().toString())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+ TimeUnit.MINUTES.toMillis(180l)))
				.signWith(SignatureAlgorithm.HS512, securityKey)
				.compact();
		return jwtToken;
	}


	/**
	 * Validating token and throwing exception if validation failed
	 * @param token
	 * @return 
	 * @throws Exception 
	 */
	public void validateToken(HttpServletRequest request) throws Exception {
		try {
			String token=request.getHeader("Authorization")
					.replaceFirst("Bearer ", "");
			Jwts.parser()
			.setSigningKey(securityKey)
			.parseClaimsJws(token);
		}
		catch(Exception e) {
			System.out.println("Validation Failed due to "+e.getMessage());
			throw new Exception("Validation Failed due to "+e.getMessage());
		}
	}
	
	
	/**
	 * Searching for fullName of student based on his rollNumber 
	 * @param rollNumber
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = {Exception.class})
	public String getFullName(int rollNumber, HttpServletRequest request) throws Exception
	{
		try {
			validateToken(request);
			String fullName;
			fullName=studentDaoImpl.getFirstName(rollNumber)+" ";
			fullName+=studentDaoImpl.getLastName(rollNumber);
			return fullName;
		}
		catch(Exception e) {
			System.out.println("Can not return full name due to "+ e.getMessage());
			throw new Exception("Can not return full name due to "+ e.getMessage());
		}
	}
	
	
	/**
	 * Updating marks and percentage of student
	 * @param rollNumber
	 * @param marksObtained
	 * @param request
	 * @throws Exception
	 */
	public void updateMarksAndPercentage(int rollNumber, int marksObtained, HttpServletRequest request) throws Exception
	{
		try {
			validateToken(request);
			double percentage=(Double.valueOf(marksObtained)/50)*100;
			studentDaoImpl.updateMarksAndPercentage(rollNumber, marksObtained, percentage);
		}
		catch(Exception e) {
			System.out.println("Can not update due to "+ e.getMessage());
			throw new Exception("Can not update due to "+ e.getMessage());
		}
	}
	
	
	/**
	 * Deleting student record from database
	 * @param rollNumber
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = {Exception.class})
	public void deleteStudent(int rollNumber, HttpServletRequest request) throws Exception
	{
		int i=0;
		try {
			validateToken(request);
			studentDaoImpl.deleteMarks(rollNumber);
			if(i==0) throw new Exception("manual");
			studentDaoImpl.deleteStudent(rollNumber);
		}
		catch(Exception e) {
			System.out.println("Can not delete student due to "+ e.getMessage());
			throw new Exception("Can not delete student due to "+ e.getMessage());
		}
	}
	
	
	/**
	 * Getting all the students
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<Student> getAllStudents(HttpServletRequest request) throws Exception
	{
		try {
			validateToken(request);
			List<Student> students=studentDaoImpl.getAllStudent();
			return settingMarks(students);
		}
		catch(Exception e) {
			System.out.println("Can not return students due to "+ e.getMessage());
			throw new Exception("Can not return students due to "+ e.getMessage());
		}
	}

	
	/**
	 * Adding Student in database
	 * @param student
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = {Exception.class})
	public void addStudent(Student student,HttpServletRequest request) throws Exception
	{
		int i=0;
		try {
			validateToken(request);
			Marks marks=student.getMarks();
			int marksObtained=marks.getMarksObtained();
			double percentage=(Double.valueOf(marksObtained)/50)*100;
			marks.setPercentage((int)percentage);
			student.setMarks(marks);
			int rollNumber=studentDaoImpl.addStudent(student);
			if(i==0) throw new Exception("manual");
			studentDaoImpl.addMarks(student.getMarks(), rollNumber);
		}
		catch(Exception e) {
			System.out.println("Can not add student due to "+ e.getMessage());
			throw new Exception("Can not add student due to "+ e.getMessage());
		}
	}
	
	
	/**
	 * Setting grade on the basis of percentage
	 * @return
	 */
	List<Student> settingMarks(List<Student> students)
	{
		for(Student student : students)
		{
			int percentage=student.getMarks().getPercentage();
			Marks marks=student.getMarks();
			if(percentage>=76)
			{
				marks.setGrade("A");
				student.setMarks(marks);
			}
			else if(percentage>=61)
			{
				marks.setGrade("B");
				student.setMarks(marks);
			}
			else if(percentage>=46)
			{
				marks.setGrade("C");
				student.setMarks(marks);
			}
			else if(percentage>=35)
			{
				marks.setGrade("D");
				student.setMarks(marks);
			}
			else
			{
				marks.setGrade("F");
				student.setMarks(marks);
			}
		}
		return students;
	}
	
}
