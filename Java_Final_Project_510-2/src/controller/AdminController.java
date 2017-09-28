package controller;

import java.util.List;

import main.main;
import databaseDAO.StudentDAO;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.main;
import model.Student;


public class AdminController {

	public void main(){

		try{
            AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource(
					"/view/StudentActionFXML.FXML"));
			Scene scene = new Scene(root);
			main.stage.setScene(scene);
			main.stage.setTitle("Add Student");

		} catch(Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}

	}

	public void AdminEntrymain(){

		try{
            AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource(
					"/view/AdminView.FXML"));
			Scene scene = new Scene(root);
			main.stage.setScene(scene);
			main.stage.setTitle("Add Student");

		} catch(Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}

	}


	public void studentActivity(){
           try{
			AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource(
					"/view/StudentActionFXML.FXML"));
			Scene scene = new Scene(root);
			main.stage.setScene(scene);
			main.stage.setTitle("Student Action");


		} catch(Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}


	}

	public void courseActivity(){

		try{
			AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource(
					"/view/CourseMainFXML.FXML"));
			Scene scene = new Scene(root);
			main.stage.setScene(scene);
			main.stage.setTitle("Course Activity");


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
			System.out.println("Error while coming back to Login: " + e);
		}

	}

	public void updatestudent(){
            try{
            	FXMLLoader loader = new FXMLLoader(getClass().getResource(
    					"/view/UpdateStudentDetails.fxml"));
    			AnchorPane root = (AnchorPane) loader.load();
    			Scene scene = new Scene(root);
    			main.stage.setScene(scene);
    			main.stage.setTitle("Update Student");
    			StudentViewController controller = loader.getController();
    			List<Student> student;
    			try (StudentDAO su = new StudentDAO()) {
    				student = su.getstudent();


    			}
    			controller.setStudent(FXCollections
    					.observableArrayList(student));
		} catch(Exception e) {
			System.out.println("Error in loading occured while inflating view: " + e);
		}

	}

	public void addStudent(){
		try {

			AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource(
					"/view/AddStudent.FXML"));
			Scene scene = new Scene(root);
			main.stage.setScene(scene);
			main.stage.setTitle("Student Action");


		} catch(Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}


	}

	public void ViewStudent() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(
					"/view/ViewStudentDetails.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			main.stage.setScene(scene);
			main.stage.setTitle("View Student");
			StudentViewController controller = loader.getController();
			List<Student> student;
			try (StudentDAO su = new StudentDAO()) {
				student = su.getstudent();
			}
			controller.setStudent(FXCollections.observableArrayList(student));
		} catch (Exception e) {
			System.out
					.println("Error occured while inflating teaching assistant view: "
							+ e);
		}
	}

	public void DeleteStudent() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(
					"/view/DeleteStudentDetails.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			main.stage.setScene(scene);
			main.stage.setTitle("View Student");
			StudentViewController controller = loader.getController();
			List<Student> student;
			try (StudentDAO su = new StudentDAO()) {
				student = su.getstudent();
						//getTeachingAssistants();

			}
			controller.setStudent(FXCollections
					.observableArrayList(student));
			// dialogStage.show();
		} catch (Exception e) {
			System.out
					.println("Error occured while inflating teaching assistant view: "
							+ e);
		}
	}

}
