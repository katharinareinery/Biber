package application;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXController implements Initializable{

	@FXML
	private VBox vbox;
	@FXML
	private Label label;
	@FXML
	private ImageView imageView;
	@FXML
	private MenuItem ueber;
	@FXML
	private ComboBox<String> cbox_filters;
	@FXML
	private Button anwenden;
	@FXML
	private Button dragndrop;
	
	TextField txt_fld = new TextField();
	
	private boolean isOpen = false;
	private FileChooser fileChooser;
	private Stage stage;
	private Image image;
	private BufferedImage bufferedImage;
	private Mat mat;
	
	private Blur blur = new Blur();
	private BlackAndWhite blackAndwhite = new BlackAndWhite();
	private String filepath;
	private ImageMan imageMan = new ImageMan();
	
	Service<Void> backgroundThread;
	/**
	 * Blur.
	 */
	public void handleButton(ActionEvent event) {
		try {
			mat = blur.imageMan(mat);
			BufferedImage neu = imageMan.matToBuffImage(mat);
			image = SwingFXUtils.toFXImage(neu, null);
			imageView.setImage(image);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Threshold.
	 */
	public void handleButtonSch(ActionEvent event) {
	//	label.setText("Schwellwert.");
	}
	
	
	/**
	 * Black and White.
	 */
	public void handleButtonSchWe(ActionEvent event) {
	//	label.setText("Schwarz-Weiß.");
		try {
			mat = blackAndwhite.imageMan(mat);
			BufferedImage neu = imageMan.matToBuffImage(mat);
			image = SwingFXUtils.toFXImage(neu, null);
			imageView.setImage(image);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * About Biber.
	 */
	public void handleAbout(ActionEvent event) {
		label = new Label("Biber ist ein Bildbearbeitungsprogramm, das die Bildbearbeitung"
				+ "von einem technischen Standpunkt aus betrachtet. "
				+ "\n"
				+ "Dem Anwender soll die Möglichkeite gegeben werden, die Veränderungen an "
				+ "einem Bild mittels Filter nachvollziehen zu können.");
		try {
			Parent root = FXMLLoader.load(getClass().getResource("GUI2.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}catch(Exception e) {
			e.printStackTrace();			
		}
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> options = FXCollections.observableArrayList("Schwarz-Weiß","Schwarz-Weiß Pixelweise","Weichzeichnen","Schwellwert");
		cbox_filters.getItems().addAll(options);
		//anwenden.setOnAction(this::handleAnwenden);
		//schwellwerte.setOnAction(this::handleButtonSch);
		ueber.setOnAction(this::handleAbout);
		anwenden.setDisable(true);
		dragndrop.setDisable(true);
		cbox_filters.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
			if(newVal!= null && newVal.equals("Schwellwert")) {
				txt_fld.setPrefWidth(anwenden.getPrefWidth());
				vbox.getChildren().add(txt_fld);
			}
			else if(newVal!= null && !newVal.equals("Schwellwert")) {
				vbox.getChildren().removeAll(txt_fld);
			}
		});
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
		filepath = file.getAbsolutePath();
		/*
		 * Loading the image
		 */
		try {
			mat = Imgcodecs.imread(filepath, Imgcodecs.CV_LOAD_IMAGE_COLOR);
			if(mat.empty()) {
				System.out.println("Error opening image");
				System.out.println("Usage: filechooserPath");
				System.exit(-1);
				}
			bufferedImage = imageMan.matToBuffImage(mat);
			image = SwingFXUtils.toFXImage(bufferedImage, null);
			imageView.setImage(image);
			anwenden.setDisable(false);
			dragndrop.setDisable(false);
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	/**
	 * This method will close the window.
	 */
	 public void doExit() {
		 Platform.exit();
		 System.exit(0);
	 }
	 
	/**
	 * This method will allow the user to import an image.
	 */
	public void chooseImage(ActionEvent event) {
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
	}
	
	/**
	 * This method will save an file.
	 */
	public void saveFile() {
		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("PNG", "*.png"),
				new FileChooser.ExtensionFilter("JPG2000", "*.jpg2")
			);
		File file = fileChooser.showSaveDialog(stage);
	
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(), null), "png", file);
			
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
	public void handleAnwenden(ActionEvent event) {
		System.out.println(cbox_filters.getValue());
		switch(cbox_filters.getValue().toString()) {
			case "Schwarz-Weiß":
				backgroundThread = new Service<Void>() {
					@Override
					protected Task<Void> createTask() {
						// TODO Auto-generated method stub
						return new Task<Void>() {
							@Override
							protected Void call() throws Exception {
								// TODO Auto-generated method stub
								try {
									mat = blackAndwhite.imageMan(mat);
									BufferedImage neu = imageMan.matToBuffImage(mat);
									image = SwingFXUtils.toFXImage(neu, null);
									imageView.setImage(image);
									//System.out.println(mat.cols());
								}catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
								return null;
							}
						};
					}
				};
				backgroundThread.restart();
				break;
			case "Schwarz-Weiß Pixelweise":
				backgroundThread = new Service<Void>() {
					@Override
					protected Task<Void> createTask() {
						// TODO Auto-generated method stub
						return new Task<Void>() {
							@Override
							protected Void call() throws Exception {
								// TODO Auto-generated method stub
								try {
									for(int i = 0;i < mat.rows();i++) {
										for(int j = 0;j< mat.cols();j++) {
											mat = blackAndwhite.bWPixel(i, j, mat);
										}
									BufferedImage neu = imageMan.matToBuffImage(mat);
									image = SwingFXUtils.toFXImage(neu, null);
									imageView.setImage(image);
									Thread.sleep(1);
									}
									
								}catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
								return null;
							}
						};
					}
				};
				backgroundThread.restart();
				break;
			case "Weichzeichnen":
				backgroundThread = new Service<Void>() {
					@Override
					protected Task<Void> createTask() {
						// TODO Auto-generated method stub
						return new Task<Void>() {
							
							@Override
							protected Void call() throws Exception {
								// TODO Auto-generated method stub
								try {
									mat = blur.imageMan(mat);
									BufferedImage neu = imageMan.matToBuffImage(mat);
									image = SwingFXUtils.toFXImage(neu, null);
									imageView.setImage(image);
								}catch(Exception e) {
									e.printStackTrace();
								}
								return null;
							}
						};
					}
				};
					backgroundThread.restart();
					break;
			case "Schwellwert":
				break;
				
		}
	}
}
