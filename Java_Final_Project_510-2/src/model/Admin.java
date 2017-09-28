package model;

import java.util.ArrayList;
import java.util.List;
import model.Course;
import model.Student;


public class Admin {

	private String user;
	private String password;
	private List<Student> student = new ArrayList<>();
	private List<Course> course = new ArrayList<>();

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Student> getStudent() {
		return student;
	}
	public void setStudent(List<Student> student) {
		this.student = student;
	}
	public List<Course> getCourse() {
		return course;
	}
	public void setCourse(List<Course> course) {
		this.course = course;
	}


}
