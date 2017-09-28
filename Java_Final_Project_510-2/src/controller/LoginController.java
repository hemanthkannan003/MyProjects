package controller;
import main.main;
import databaseDAO.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
public class LoginController {

	@FXML
	private TextField user_name;

	@FXML
	private TextField password;

	private Stage dialogStage;

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
public void LoginFromError(){
	try {
		AnchorPane root;
		root = (AnchorPane) FXMLLoader.load(getClass().getResource(
				"/view/LoginFXML.fxml"));
		Scene scene = new Scene(root);
		main.stage.setScene(scene);
		main.stage.setTitle("Professor Home Page");
	} catch (Exception e) {
		System.out.println("Could not open professor view = " + e);
	}

}
	public void login(){

		String user_name=this.user_name.getText();
		String password=this.password.getText();

		if (user_name.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")){
			try {
				AnchorPane root;
				root = (AnchorPane) FXMLLoader.load(getClass().getResource(
						"/view/AdminView.fxml"));
				Scene scene = new Scene(root);
				main.stage.setScene(scene);
				main.stage.setTitle("Professor Home Page");
			} catch (Exception e) {
				System.out.println("Could not open professor view = " + e);
			}

		}
		else{
			StudentDAO su = new StudentDAO();
           su.loginstudentfinder(user_name, password);

		}
	}

	public void loadStudentLogin(){
		try {
			AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource(
					"/view/StudentLoginOperationsFXML.fxml"));
			System.out.println("I cressed this");
			Scene scene = new Scene(root);
			main.stage.setScene(scene);
			main.stage.setTitle("Student_Operation");

		} catch (Exception e) {
			System.out.println("Could not login view boo = " + e);
		}


	}

	public void errorlogin(){
		try {
			AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource(
					"/view/ErrorMsg.fxml"));
			Scene scene = new Scene(root);
			main.stage.setScene(scene);
			main.stage.setTitle("Professor Home Page");
		} catch (Exception e) {
			System.out.println("error view = " + e);
		}

	}

	public void cancel(){
		System.exit(0);
	}

}
