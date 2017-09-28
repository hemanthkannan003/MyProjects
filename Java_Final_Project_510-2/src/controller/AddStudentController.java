package controller;
import model.Student;

import java.util.List;

import databaseDAO.StudentDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.main;

public class AddStudentController extends DialogForm {
	@FXML
	private TextField ID;
	@FXML
	private TextField Name;
	@FXML
	private TextField Password;
/**
 * This method is to load the studentActionFXML
 */
	public void main(){

		try{
			AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource(
					"/view/StudentActionFXML.FXML"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			main.stage.setScene(scene);
			main.stage.setTitle("Student Action");

		} catch(Exception e) {
			System.out.println("Error While loading Student Action Page: " + e);
		}

	}

	/**
	 * This is and SetDialogStage
	 */

	public void setDialogStage(Stage dialogStage) {

	}

	/**
	 * This method to open addStudent
	 */
	public void addStudent(){
		
		String ID = this.ID.getText();
		String Name=this.Name.getText();
		String Password = this.Password.getText();


		if (ID == null || ID.trim().equals("") || ID.equalsIgnoreCase("admin")){
			return;
		}
		if (Name == null || Name.trim().equals("")){
			return;
		}
		if (Password == null || Password.trim().equals("")|| Password.equalsIgnoreCase("admin")){
			return;
		}

		Student st= new Student();
		st.setId(ID);
		st.setName(Name);
		st.setPassword(Password);
		StudentDAO in =new StudentDAO();
		in.create(st);
		in.close();
		ViewStudent();
	}

	/**
	 * This is to load ViewStudent
	 */
	public void ViewStudent() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(
					"/view/ViewStudentDetails.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			main.stage.setScene(scene);
			main.stage.setTitle("View Student");
			StudentViewController controller = loader.getController();
			List<Student> student;
			try (StudentDAO su = new StudentDAO()) {
				student = su.getstudent();
			}
			controller.setStudent(FXCollections
					.observableArrayList(student));
		} catch (Exception e) {
			System.out
			.println("Error while showing View Student: "+ e);
		}
	}
	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		super.cancel();
	}


	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

}
