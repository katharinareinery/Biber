package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXController implements Initializable{

	@FXML
	private Button weichzeichnen;
	@FXML
	private Button schwellwerte;
	@FXML
	private Button schwarzweiss;
	@FXML
	private Label label;
	@FXML
	private ImageView imageView;
	
	private FileChooser fileChooser;
	private Stage stage;
	private Image image;
	
	public void handleButton(ActionEvent event) {
	//	label.setText("Weichzeichnen.");
	}
	
	public void handleButtonSch(ActionEvent event) {
	//	label.setText("Schwellwert.");
	}
	
	public void handleButtonSchWe(ActionEvent event) {
	//	label.setText("Schwarz-Weiß.");
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//weichzeichnen.setOnAction(this::handleButton);
		//schwellwerte.setOnAction(this::handleButtonSch);
		//schwarzweiss.setOnAction(this::handleButtonSchWe);
	}
	
	public void init(Stage stage) {
		this.stage = stage;
	}
	
	/**
	 * This method will open an file.
	 */
	public void openFile() {
		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("PNG", "*.png"),
				new FileChooser.ExtensionFilter("JPG2000", "*.jpg2")
			);
		File file = fileChooser.showOpenDialog(stage);
		
		/*
		 * Loading the image
		 */
		try {
			BufferedImage bufferedImage = ImageIO.read(file);
			image = SwingFXUtils.toFXImage(bufferedImage, null);
			imageView.setImage(image);
			
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	/**
	 * This method will close the window.
	 */
	 public void doExit() {
		 Platform.exit();
		 System.exit(0);;
	 }
	 
	/**
	 * This method will allow the user to import an image.
	 */
	public void chooseImage(ActionEvent event) {
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	}
	
	
	
}
