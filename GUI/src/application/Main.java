package application;
	
import org.opencv.core.Core;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception{
		
			//BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("GUI.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI.fxml"));
			Parent root = loader.load();
			FXController controller = (FXController)loader.getController();
			controller.init(primaryStage);
			
			Scene scene = new Scene(root,950,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Biber");
			primaryStage.setScene(scene);
			primaryStage.show();

	}

	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		launch(args);
	}
}
