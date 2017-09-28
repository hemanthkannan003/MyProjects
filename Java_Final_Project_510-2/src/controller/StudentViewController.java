package controller;

import model.Student;

import java.util.List;
import databaseDAO.StudentDAO;
import main.main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StudentViewController {
	@FXML
	private TableView<Student> student;

	private Boolean locked = false;

	@FXML
	private TextField name;

	@FXML
	private TextField password;

	public void setStudent(ObservableList<Student> stu) {
		this.student.setItems(stu);
	}

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


	public void DeleteStudent(){

		ObservableList<Student> stu;
		stu = student.getSelectionModel().getSelectedItems();
		StudentDAO t = new StudentDAO();
		t.delete(stu.get(0));
        AdminController ad = new AdminController();
		ad.DeleteStudent();
	}




	public void updateStudent(){
		String Name=this.name.getText();
		String Password = this.password.getText();
		if (Name == null || Name.trim().equals("")){
			return;
			}
		if (Password == null || Password.trim().equals("")){
			return;
			}


		ObservableList<Student> stu;
		stu = student.getSelectionModel().getSelectedItems();
        String Id=stu.get(0).getId();
        System.out.println(Id);
		Student st= new Student();
        st.setId(Id);
		st.setName(Name);
		st.setPassword(Password);
		StudentDAO t = new StudentDAO();
		System.out.println("weewew");
		t.update(st);
        AdminController ad = new AdminController();
		ad.updatestudent();
	}

	public void addStudent(){
		if(locked) {
			return;
		}
		locked = true;

           try{
            AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource(
					"/view/AddStudent.FXML"));
			Scene scene = new Scene(root);
			main.stage.setScene(scene);
			main.stage.setTitle("Add Student");

		} catch(Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}

	}



}
