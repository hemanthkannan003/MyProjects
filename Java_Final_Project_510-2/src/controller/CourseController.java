package controller;

import java.util.List;

import databaseDAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import main.main;
import model.Student;
import model.Course;

public class CourseController {
	@FXML
	private TableView<Course> course;
	@FXML
	private TextField Course_ID;
	@FXML
	private TextField Course_Name;
//	@FXML
//	private TextField Course_Type;

	@FXML
	private RadioButton OnlineClass;

	@FXML
	private RadioButton LiveClass;

	public void addCourseStudent(){

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(
					"/view/AddCourseForStudentFXML.FXML"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			main.stage.setScene(scene);
			main.stage.setTitle("Course Details");
			CourseViewController controller = loader.getController();
			List<Course> course;
			try (CourseDAO co = new CourseDAO()) {
				course = co.getcourse();
			}
			controller.setcourse(FXCollections.observableArrayList(course));
		} catch (Exception e) {
			System.out
					.println("Error while loading the FXML file: "
							+ e);
		}

	}



	public void AddCourse(){

		try{
	        AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource(
					"/view/AddCourseFXML.FXML"));
			Scene scene = new Scene(root);
			main.stage.setScene(scene);
			main.stage.setTitle("Add Student");
					} catch(Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}

	}

	public void studentloginmain(){
		try{
	        AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource(
					"/view/StudentLoginOperationsFXML.FXML"));
			Scene scene = new Scene(root);
			main.stage.setScene(scene);
			main.stage.setTitle("Add Student");

		} catch(Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}

	}

	public void ViewStudentRegisteredCourse(){

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(
					"/view/StudentViewRegisteredCourseFXML.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			main.stage.setScene(scene);
			main.stage.setTitle("Course Details");
			CourseViewController controller = loader.getController();
			List<Course> course;
			try (StudentDAO co = new StudentDAO()) {
				course = co.getstudentcourse();
			}
			controller.setcourse(FXCollections.observableArrayList(course));
		} catch (Exception e) {
			System.out
					.println("Error while loading the FXML file: "
							+ e);
		}


	}


	public void deleteStudentRegisteredCourse(){

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(
					"/view/DeleteCourseForStudentFXML.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			main.stage.setScene(scene);
			main.stage.setTitle("Drop Course");
			CourseViewController controller = loader.getController();
			List<Course> course;
			try (StudentDAO co = new StudentDAO()) {
				course = co.getstudentcourse();
			}
			controller.setcourse(FXCollections.observableArrayList(course));
		} catch (Exception e) {
			System.out
					.println("Error while loading the FXML file: "
							+ e);
		}

	}

	public void LoadCourse(){

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(
					"/view/ViewCourseStudentLoginFXML.FXML"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			main.stage.setScene(scene);
			main.stage.setTitle("Course Details");
			CourseViewController controller = loader.getController();
			List<Course> course;
			try (CourseDAO co = new CourseDAO()) {
				course = co.getcourse();
			}
			controller.setcourse(FXCollections.observableArrayList(course));
		} catch (Exception e) {
			System.out
					.println("Error while loading the FXML file: "
							+ e);
		}

	}

	public void AddCourseToDataBase(){
		String ID = this.Course_ID.getText();
		String Name=this.Course_Name.getText();
		String Course_type=null;
		Boolean Online = this.OnlineClass.isSelected();
		Boolean Live = this.LiveClass.isSelected();
		if (Online){
			Course_type = "online";
		}
		else
		{
			Course_type = "live";
		}

		if (ID == null || ID.trim().equals("")){
			return;
			}
		if (Name == null || Name.trim().equals("")){
			return;
			}
		if (Course_type == null || Course_type.trim().equals("")){
			return;
			}

		Course st= new Course();
		st.setId(ID);
		st.setName(Name);
		st.setType(Course_type);
		CourseDAO in =new CourseDAO();
        in.create(st);
        ViewCourse();

	}

public void DeleteCourse(){

	try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				"/view/DeleteCourseFXML.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		Scene scene = new Scene(root);
		main.stage.setScene(scene);
		main.stage.setTitle("Delete Course");
		CourseViewController controller = loader.getController();
		List<Course> course;
		try (CourseDAO co = new CourseDAO()) {
			course = co.getcourse();
		}
		controller.setcourse(FXCollections.observableArrayList(course));
	} catch (Exception e) {
		System.out
				.println("Error while loading the FXML file: "
						+ e);
	}


	}

public void ViewCourse(){
	try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				"/view/ViewCourseFXML.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		Scene scene = new Scene(root);
		main.stage.setScene(scene);
		main.stage.setTitle("Course Details");
		CourseViewController controller = loader.getController();
		List<Course> course;
		try (CourseDAO co = new CourseDAO()) {
			course = co.getcourse();
		}
		controller.setcourse(FXCollections.observableArrayList(course));
	} catch (Exception e) {
		System.out
				.println("Error while loading the FXML file: "
						+ e);
	}

}

public void UpdateCourse(){
try{
	FXMLLoader loader = new FXMLLoader(getClass().getResource(
			"/view/UpdateCourseFXML.fxml"));
	AnchorPane root = (AnchorPane) loader.load();
	Scene scene = new Scene(root);
	main.stage.setScene(scene);
	main.stage.setTitle("Course Details");
	CourseViewController controller = loader.getController();
	List<Course> course;
	try (CourseDAO co = new CourseDAO()) {
		course = co.getcourse();
	}
	controller.setcourse(FXCollections.observableArrayList(course));
} catch (Exception e) {
	System.out
			.println("Error while loading the FXML file: "
					+ e);
}

}

public void AdminEntrymain(){

	try{
        AnchorPane root;
		root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/AdminView.FXML"));
		Scene scene = new Scene(root);
		main.stage.setScene(scene);
		main.stage.setTitle("Add Student");

	} catch(Exception e) {
		System.out.println("Error occured while inflating view: " + e);
	}

}

public void main(){

	try{
        AnchorPane root;
		root = (AnchorPane) FXMLLoader.load(getClass().getResource(
				"/view/CourseMainFXML.FXML"));
		Scene scene = new Scene(root);
		main.stage.setScene(scene);
		main.stage.setTitle("Add Student");

	} catch(Exception e) {
		System.out.println("Error occured while inflating view: " + e);
	}

}
public void logout(){

	try{
        AnchorPane root;
		root = (AnchorPane) FXMLLoader.load(getClass().getResource(
				"/view/LoginFXML.FXML"));
		Scene scene = new Scene(root);
		main.stage.setScene(scene);
		main.stage.setTitle("Login");

	} catch(Exception e) {
		System.out.println("Error occured while inflating view: " + e);
	}

}
}
