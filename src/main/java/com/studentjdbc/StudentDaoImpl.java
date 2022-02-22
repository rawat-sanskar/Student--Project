package com.studentjdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import com.studentEntities.Marks;
import com.studentEntities.Student;

@Component
public class StudentDaoImpl implements StudentDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int addStudent(Student student) {
		MapSqlParameterSource params=new MapSqlParameterSource()
				.addValue("firstName", student.getFirstName())
				.addValue("lastName", student.getLastName())
				.addValue("gender",student.getGender())
				.addValue("bloodGroup", student.getBloodGroup());

		return new SimpleJdbcInsert(jdbcTemplate).withTableName("student")
				.usingGeneratedKeyColumns("rollNumber").executeAndReturnKey(params)
				.intValue();
	}

	@Override
	public void addMarks(Marks marks,int rollNumber) {
		MapSqlParameterSource params=new MapSqlParameterSource()
				.addValue("marksObtained", marks.getMarksObtained())
				.addValue("percentage", marks.getPercentage())
				.addValue("rollNumber",rollNumber);

		 new SimpleJdbcInsert(jdbcTemplate).withTableName("marks")
				.usingGeneratedKeyColumns("id").executeAndReturnKey(params)
				.intValue();
	}

	
	/**
	 * 
	 */
	@Override
	public void updateMarksAndPercentage(int rollNumber, int marksObtained, double percentage) {
		String query="UPDATE marks SET marksObtained=?, percentage=? WHERE rollNumber=?";
		jdbcTemplate.update(query,marksObtained,(int)percentage,rollNumber);
	}

	
	/**
	 * Searching for firstName of student in student table
	 */
	@Override
	public String getFirstName(int rollNumber) {
		String query="SELECT firstName FROM student WHERE rollNumber=?";
		String r=(String) jdbcTemplate.queryForObject(
	            query, new Object[] { rollNumber }, String.class);
		return r;
	}

	/**
	 * Searching for lastName of student in student table
	 */
	@Override
	public String getLastName(int rollNumber) {
		String query="SELECT lastName FROM student WHERE rollNumber=?";
		String r=(String) jdbcTemplate.queryForObject(
	            query, new Object[] { rollNumber }, String.class);
		return r;
	}

	/**
	 * Deleting row in marks table whose rollNumber is given
	 */
	@Override
	public void deleteMarks(int rollNumber) {
		String query="DELETE FROM marks WHERE rollNumber=?";
		jdbcTemplate.update(query,rollNumber);
	}

	/**
	 * Deleting row in student table whose rollNumber is given
	 */
	@Override
	public void deleteStudent(int rollNumber) {
		String query="DELETE FROM student WHERE rollNumber=?";
		jdbcTemplate.update(query,rollNumber);
	}

	/**
	 * Returning all the students
	 */
	@Override
	public List<Student> getAllStudent() {
		String query="select * from student s join marks m on s.rollNumber=m.rollNumber";
		List<Student> students=jdbcTemplate.query(query,new RowMapperImpl());
		return students;
	}

}
