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
import org.opencv.imgproc.Imgproc;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

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
	private RadioButton rad_button_blackwhite_average = new RadioButton("Average");
	private RadioButton rad_button_blackwhite_lumi = new RadioButton("Lumi");
	private RadioButton rad_button_blackwhite_pixelwise = new RadioButton("Pixelwise");
	private final ToggleGroup group_rad_blackwhite = new ToggleGroup();
	
	ScrollBar sc = new ScrollBar();
	
	private boolean isOpen = false;
	private FileChooser fileChooser;
	private Stage stage;
	private Image image;
	private BufferedImage bufferedImage;
	private Mat mat;

	private GridPane itembox = new GridPane();
	
	
	private Blur blur = new Blur();
	private BlackAndWhite blackAndwhite = new BlackAndWhite();
	private Threshold thold = new Threshold();
	private String filepath;
	private ImageMan imageMan = new ImageMan();
	
	Service<Void> backgroundThread;
	/**
	 * Blur.
	 */
	public void handleButton(ActionEvent event) {
		/*try {
			mat = blur.imageMan(mat);
			BufferedImage neu = imageMan.matToBuffImage(mat);
			image = SwingFXUtils.toFXImage(neu, null);
			imageView.setImage(image);
		}catch(Exception e) {
			e.printStackTrace();
		}*/
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
		/*try {
			mat = blackAndwhite.imageMan(mat);
			BufferedImage neu = imageMan.matToBuffImage(mat);
			image = SwingFXUtils.toFXImage(neu, null);
			imageView.setImage(image);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}*/
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
		ObservableList<String> options = FXCollections.observableArrayList("Schwarz-Weiß","Weichzeichnen","Schwellwert");
		cbox_filters.getItems().addAll(options);
		//anwenden.setOnAction(this::handleAnwenden);
		//schwellwerte.setOnAction(this::handleButtonSch);
		
		imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
		@Override public void handle(MouseEvent event) {
			System.out.println(event.getX());
		}
		});
		
		ueber.setOnAction(this::handleAbout);
		anwenden.setDisable(true);
		dragndrop.setDisable(true);
		//ComboBox - Changelistener ( Wartet auf Auswahl )
		cbox_filters.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
			if(newVal!= null && newVal.equals("Schwellwert")) {
				vbox.getChildren().removeAll(itembox);
				itembox.getChildren().clear();
				itembox.setHgap(10);						//GridPane Horizontaler Gap
				itembox.setVgap(10);						//GridPane Vertikaler Gap
				itembox.setPadding(new Insets(5, 0, 5, 0));	//Padding Oben/Unten 5px
				itembox.setPrefWidth(anwenden.getPrefWidth());
				txt_fld.setPrefWidth(anwenden.getPrefWidth()/2);
				sc.setPrefWidth(anwenden.getPrefHeight());
				sc.setMin(1);
				sc.setMax(255);
				itembox.add(txt_fld,0,0);					//txt_fld an Stelle 0,0
				itembox.add(new Label("Threshold"),1,0);	//label an stelle 1,0(rechts neben 0,0)
				itembox.add(sc,0,1);
				vbox.getChildren().add(itembox);			//GridPane itembox an Vbox vbox anhaengen
				sc.valueProperty().addListener(new ChangeListener<Number>() {

					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
						// TODO Auto-generated method stub
						txt_fld.setText(Integer.toString(arg2.intValue()));
					}
					
				});
			}
			else if(newVal!=null && newVal.equals("Schwarz-Weiß")) {
				vbox.getChildren().removeAll(itembox);
				itembox.getChildren().clear();
				itembox.setHgap(10);
				itembox.setVgap(10);
				itembox.setPadding(new Insets(5, 0, 5, 0));
				itembox.setPrefWidth(anwenden.getPrefWidth());
				txt_fld.setPrefWidth(anwenden.getPrefWidth()/2);
				sc.setPrefWidth(anwenden.getPrefHeight());
				sc.setMin(1);
				sc.setMax(255);
				rad_button_blackwhite_average.setToggleGroup(group_rad_blackwhite);
				rad_button_blackwhite_lumi.setToggleGroup(group_rad_blackwhite);
				rad_button_blackwhite_pixelwise.setToggleGroup(group_rad_blackwhite);
				rad_button_blackwhite_average.setSelected(true);
				itembox.add(rad_button_blackwhite_average,0,0);					//Radiobutton1 an Stelle 0,0
				itembox.add(rad_button_blackwhite_lumi,0,1);	//Radiobutton2 1,0(rechts neben 0,0)
				itembox.add(rad_button_blackwhite_pixelwise,0,2);
				vbox.getChildren().add(itembox);			//GridPane itembox an Vbox vbox anhaengen
			}
			else if (newVal!=null && newVal.equals("Weichzeichnen")) {
				vbox.getChildren().removeAll(itembox);
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
									if(rad_button_blackwhite_average.isSelected()) {
										mat = blackAndwhite.average(mat);
									}else if (rad_button_blackwhite_lumi.isSelected()) {
										mat = blackAndwhite.luminosity(mat);
									}else {
										for(int i = 0;i < mat.rows();i++) {
											for(int j = 0;j< mat.cols();j++) {
												mat = blackAndwhite.bWPixel(i, j, mat);
											}
											BufferedImage neu = imageMan.matToBuffImage(mat);
											image = SwingFXUtils.toFXImage(neu, null);
											imageView.setImage(image);
											Thread.sleep(2);
										}
									}
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
				int t = (txt_fld.getText().isEmpty() ? 1 : Integer.parseInt(txt_fld.getText()));
				System.out.println(t);
				if(t >= 0 && t < 255) {
					backgroundThread = new Service<Void>() {
						@Override
						protected Task<Void> createTask() {
							// TODO Auto-generated method stub
							return new Task<Void>() {
								@Override
								protected Void call() throws Exception {
									// TODO Auto-generated method stub
									try {
										mat = thold.binarisieren(t, Imgcodecs.imread(filepath, Imgcodecs.CV_LOAD_IMAGE_COLOR));
										BufferedImage neu = imageMan.matToBuffImage(mat);
										image = SwingFXUtils.toFXImage(neu, null);
										imageView.setImage(image);
									}
									catch(Exception e) {e.printStackTrace();}
									return null;
								}
							};
						}
					};
				}
				else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("Nur Werte von 0-255 erlaubt!");
					alert.showAndWait();
				}
				backgroundThread.restart();
				break;
		}
	}

}

