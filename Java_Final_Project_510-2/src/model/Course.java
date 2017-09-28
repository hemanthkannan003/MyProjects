package model;

import java.util.ArrayList;
import java.util.Objects;

import javafx.scene.control.MultipleSelectionModel;


public class Course {
	private int Register_Id;

	private String id;
	private String name;
	private String type;
	private ArrayList<Course> course;
	private ArrayList<Student> student;
private String course_id;
public String getCourse_id() {
	return course_id;
}
public void setCourse_id(String course_id) {
	this.course_id = course_id;
}
public String getCourse_name() {
	return course_name;
}
public void setCourse_name(String course_name) {
	this.course_name = course_name;
}
private String course_name;

	public ArrayList<Course> getCourse() {
		return course;
	}
	public void setCourse(ArrayList<Course> course) {
		this.course = course;
	}
	public ArrayList<Student> getStudent() {
		return student;
	}
	public void setStudent(ArrayList<Student> student) {
		this.student = student;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
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
	public static Object getSelectionModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getRegister_Id() {
		return Register_Id;
	}
	public void setRegister_Id(int register_Id) {
		Register_Id = register_Id;
	}


}
