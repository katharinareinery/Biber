package application;
	
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import org.opencv.core.Core;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;


public class Main extends Application {
//test
	@Override
	public void start(Stage primaryStage) throws Exception{
		
			//BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("GUI.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("LGUI.fxml"));
			Parent root = loader.load();
			FXController controller = (FXController)loader.getController();
			controller.init(primaryStage);
							
			Scene scene = new Scene(root,950,650);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Biber");
			primaryStage.getIcons().add(new Image("file:biber.png"));
			primaryStage.setScene(scene);
			//Fullscreenmodus
			//primaryStage.setFullScreen(true);
			primaryStage.setMaximized(true);
			primaryStage.show();
	}

	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		launch(args);
	}
}
