package com.studentEntities;

import org.springframework.stereotype.Component;

@Component
public class Marks {
	private int marksObtained;
	private int percentage;
	private String grade;
	
	public int getMarksObtained() {
		return marksObtained;
	}
	public void setMarksObtained(int marksObtained) {
		this.marksObtained = marksObtained;
	}
	public int getPercentage() {
		return percentage;
	}
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	@Override
	public String toString() {
		return "Marks [marksObtained=" + marksObtained + ", percentage=" + percentage + ", grade=" + grade + "]";
	}
	
}
