package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class FXController implements Initializable{

	@FXML
	private Button weichzeichnen;
	@FXML
	private Label label;
	
	public void handleButton(ActionEvent event) {
		label.setText("In progress.");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		weichzeichnen.setOnAction(this::handleButton);
		
	}	
	
}
