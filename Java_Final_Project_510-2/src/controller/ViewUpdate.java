package controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.main;

public class ViewUpdate {
	@FXML
	private ComboBox<String> choice;

	@FXML
	private TextField name;

	@FXML
	private TextField password;

	protected Connection connection;
	private String url = "jdbc:mysql://www.papademas.net:3306/dbfp";
	private String username = "fpuser";
	private String pass = "510";

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


    public void DataBaseConnection() {
    	try {
            connection = DriverManager.getConnection(url, username, pass);
            System.out.println("Connected");
        } catch(SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
   void initialize() {
       //assert choice != null : "fx:id=\"combo\" was not injected: check your FXML file 'ComboboxExample.fxml'.";
       //assert text != null : "fx:id=\"text\" was not injected: check your FXML file 'ComboboxExample.fxml'.";
       ArrayList<String> x = new ArrayList<String>();
       System.out.println("I");
       DataBaseConnection();

       String query = "Select * from student_info;";
       System.out.println("o");
		try(PreparedStatement statement = connection.prepareStatement(query)){
			System.out.println("m");
           ResultSet resultSet = statement.executeQuery();
           System.out.println(resultSet);
           while(resultSet.next()) {

             x.add(resultSet.getString("student_Name"));

           }
       } catch(SQLException e){
           System.out.println("Error fetching Accounts: " + e);
       }

		System.out.println("I crossed ");


        //Initialize your logic here: all @FXML variables will have been injected
       choice.getItems().clear();
       for ( int i=0 ; i< x.size(); i++)
       {
       choice.getItems().add(x.get(i));

       //combo.getItems().add("Others...");
    }
    }

}
