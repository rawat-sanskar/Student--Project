package com.studentjdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.studentEntities.Marks;
import com.studentEntities.Student;

public class RowMapperImpl implements RowMapper<Student>{

	@Override
	public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Student student=new Student();
		Marks marks=new Marks();
		student.setRollNumber(rs.getInt("rollNumber"));
		student.setFirstName(rs.getString("firstName"));
		student.setLastName(rs.getString("lastName"));
		student.setGender(rs.getString("gender"));
		student.setBloodGroup(rs.getString("bloodGroup"));
		marks.setMarksObtained(rs.getInt("marksObtained"));
		marks.setPercentage(rs.getInt("percentage"));
		student.setMarks(marks);
		return student;
	}

}
