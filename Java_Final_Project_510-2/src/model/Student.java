package model;

import java.util.ArrayList;

public class Student {

	private String id;
	private String name;
	private String password;
	//private Course course;
	private ArrayList<Course> course;




	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
//	public Course getCourse() {
//		return course;
//	}
//	public void setCourse(Course course) {
//		this.course = course;
//	}
	public ArrayList<Course> getCourse() {
		return course;
	}
	public void setCourse(ArrayList<Course> course) {
		this.course = course;
	}
	public static Object getSelectionModel() {
		// TODO Auto-generated method stub
		return null;
	}



}
