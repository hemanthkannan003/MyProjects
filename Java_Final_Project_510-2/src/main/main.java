package main;



import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

public class main extends Application {
	public static Stage stage;
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/view/LoginFXML.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setTitle("Login");
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
