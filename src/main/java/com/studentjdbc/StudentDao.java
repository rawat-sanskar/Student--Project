package com.studentjdbc;

import java.util.List;

import com.studentEntities.Marks;
import com.studentEntities.Student;

public interface StudentDao {
	public int addStudent(Student student);
	public void addMarks(Marks marks,int rollNumber);
	public void updateMarksAndPercentage(int rollNumber,int marks,double percentage);
	public String getFirstName(int rollNumber);
	public String getLastName(int rollNumber);
	public void deleteMarks(int rollNumber);
	public void deleteStudent(int rollNumber);
	public List<Student> getAllStudent();
}
