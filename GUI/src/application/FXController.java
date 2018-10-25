package application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;


import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class FXController implements Initializable{
	
	private final DataFormat buttonFormat = new DataFormat("com.example.myapp.formats.button");
	private BiberButton draggingButton;
	double width;
	double height;
	private static final int MIN_PIXELS = 10;

	@FXML
	private FlowPane flowpane;
	@FXML
	private VBox vbox;
	@FXML
	private Label label;
	@FXML
	private ImageView imageView, imageView2, imageView3, imageView4;
	@FXML
	private MenuBar menubar;
	@FXML
	private MenuItem about;
	@FXML
	private ComboBox<String> cbox_filters;
	@FXML
	private Button anwenden;
	@FXML
	private Button preview;
	@FXML
	private Button next;
	@FXML
	private Button back;
	@FXML
	private Button srcButton;
	@FXML
	private Button buttonFilterPic;
	@FXML
	private ToolBar toolbar;
	@FXML
	private RadioButton radioButton1;
	@FXML
	private RadioButton radioButton2;
	@FXML
	private RadioButton radioButton3;
	@FXML
	private RadioButton radioButton4;
	@FXML
	private ToggleGroup toggleGroup1;
	@FXML
	private TextField txtFilterPower;
	@FXML
	private Button buttonPlus;
	@FXML
	private Button buttonMinus;
	@FXML
	private Button buttonSgColourPlus;
	@FXML
	private Button buttonSgColourMinus;
	@FXML
	private Button buttonSgSpacePlus;
	@FXML
	private Button buttonSgSpaceMinus;
	@FXML
	private TextField txtSigmaColour;
	@FXML
	private TextField txtSigmaSpace;
	@FXML
	private Label filerSize;
	@FXML
	private Label labelSigmaX;
	@FXML
	private Label labelSigmaY;
	@FXML
	private Button buttonThreshMinus;
	@FXML
	private Button buttonThreshPlus;
	@FXML
	private TextField txtThreshold;
	@FXML
	private FlowPane flowpaneThreshold;
	@FXML
	private FlowPane flowpaneSigmaColour;
	@FXML
	private FlowPane flowpaneSigmaSpace;
	@FXML
	private FlowPane flowpaneFilterSize;
	@FXML
	private FlowPane flowpaneRadioButton;
	@FXML
	private FlowPane thumpnailPane;
	@FXML
	private HBox workspace_hbox;
	@FXML
	private Label toolbar_dimensions;
	@FXML
	private SplitPane toolbar_split;
	@FXML
	private FlowPane toolbar_rightpane;
	
	private ToggleButton btn_movezoom;
	private ImageView iv_movezoom;
	private ImageView iv_cursor;
	private ToggleButton btn_cursor;
	private ToggleGroup tg_toolbar;
	
	private boolean isApplied = false;
	
	private TextField txt_fld = new TextField();

	//Linked List for storing filters
	private LinkedList<ImageMan> timeline = new LinkedList();
	private ScrollBar sc = new ScrollBar();
	
	private FileChooser fileChooser;
	private Stage stage;
	private Image image;

	private BufferedImage bufferedImage;
	
	private Mat src;
	private Mat mat;

	//private GridPane itembox = new GridPane();
		
	private Blur blur = new Blur();
	private Grayscale grayscale = new Grayscale();
	private Threshold thold = new Threshold();
	private String filepath;
	private ImageMan imageMan = new ImageMan();
	private Dragboard db;
	private ClipboardContent cc = new ClipboardContent();
	
	Service<Void> backgroundThread;
	
	private SharedObject so = SharedObject.getInstance();
	private int thumpnailPosition = 0;
	
	//get screen size for imageview
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	/**
	 * About Biber.
	 * This method opens a new window, which provides brief information about the program.
	 */
	public void handleAbout(ActionEvent event) {
		try {
			//stage = (Stage) menubar.getScene().getWindow();
			
			Parent root = FXMLLoader.load(getClass().getResource("GUI2.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("About");
			stage.show();
		}catch(Exception e) {
			e.printStackTrace();			
		}
	}
	
	/**
	 * Initialize.
	 * This method assigns the appropriate functions to the various image processing options.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> options = FXCollections.observableArrayList("Grayscale","Blur","Threshold");
		cbox_filters.getItems().addAll(options);
		//cbox_filters.applyCss();
		//cbox_filters.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		imageView.setPreserveRatio(true);
		//srcButton = createButton("Drag ME!");
		//vbox.getChildren().add(srcButton);
		setDragOnButton(srcButton);
		addDropHandling(flowpane);
		addDropHandling(vbox);
		cbox_filters.setDisable(true);
		about.setOnAction(this::handleAbout);
		preview.setOnAction(this::handleWindow);
		anwenden.setDisable(true);
		preview.setDisable(true);
		srcButton.setDisable(true);
		deinitRadioButtons();
		txtFilterPower.setText("1");
		txtSigmaColour.setText("1");
		txtSigmaSpace.setText("1");
		txtThreshold.setText("1");
		deinitBlurOptionsBila();
		deinitThreshold();
		initToolbar();
		/*
		 * At this point we assign the listener to the radiobuttons.
		 */
		toggleGroup1.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
		        Toggle old_toggle, Toggle new_toggle) {
		            if (toggleGroup1.getSelectedToggle() != null) {
		        		RadioButton selectedRadioButton = (RadioButton)toggleGroup1.getSelectedToggle();
		        			if(cbox_filters.getSelectionModel().getSelectedItem().toString().equals("Grayscale")) {
		        				deinitBlurOptionsBila();
		        			}else if(selectedRadioButton.getText().equals("bilateral")) {
		        				initBlurOptionsBila();
		        			}else {
		        				deinitBlurOptionsBila();
		        				initBlurOptions();
		        			}
		            }                
		        }
		});
		/*
		 * Combobox - The changelistener is waiting for the selection.
		 */
		cbox_filters.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
			if(newVal!= null && newVal.equals("Threshold")) {
//				if(!isApplied && timeline.isEmpty()) {
//					BufferedImage neu = imageMan.matToBuffImage(src);
//					image = SwingFXUtils.toFXImage(neu, null);
//					imageView.setImage(image);
//				}
//				else if(!isApplied && !timeline.isEmpty()) {
//					BufferedImage neu = timeline.getLast().returnImage();
//					image = SwingFXUtils.toFXImage(neu, null);
//					imageView.setImage(image);
//				}
				isApplied = false;
				deinitRadioButtons();
				deinitBlurOptionsBila();
				initThreshold();
				txt_fld.setPrefWidth(anwenden.getPrefWidth()/2);
				sc.setPrefWidth(anwenden.getPrefHeight());
				sc.setMin(1);
				sc.setMax(255);
				sc.valueProperty().addListener(new ChangeListener<Number>() {

					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
						// TODO Auto-generated method stub
						txt_fld.setText(Integer.toString(arg2.intValue()));
					}
					
				});
			}
			else if(newVal!=null && newVal.equals("Grayscale")) {
//				if(!isApplied && timeline.isEmpty()) {
//					BufferedImage neu = imageMan.matToBuffImage(src);
//					image = SwingFXUtils.toFXImage(neu, null);
//					imageView.setImage(image);
//				}
//				else if(!isApplied && !timeline.isEmpty()) {
//					BufferedImage neu = timeline.getLast().returnImage();
//					image = SwingFXUtils.toFXImage(neu, null);
//					imageView.setImage(image);
//				}
				isApplied = false;
				txt_fld.setPrefWidth(anwenden.getPrefWidth()/2);
				sc.setPrefWidth(anwenden.getPrefHeight());
				sc.setMin(1);
				sc.setMax(255);
				deinitThreshold();
				deinitBlurOptionsBila();
				deinitRadioButtons();
				initRadioButtons("average", "luminosity", "lightness", "pixelwise");
			}
			else if (newVal!=null && newVal.equals("Blur")) {
//				if(!isApplied && timeline.isEmpty()) {
//					BufferedImage neu = imageMan.matToBuffImage(src);
//					image = SwingFXUtils.toFXImage(neu, null);
//					imageView.setImage(image);
//				}
//				else if(!isApplied && !timeline.isEmpty()) {
//					BufferedImage neu = timeline.getLast().returnImage();
//					image = SwingFXUtils.toFXImage(neu, null);
//					imageView.setImage(image);
//				}
				isApplied = false;
				txt_fld.setPrefWidth(anwenden.getPrefWidth()/2);
				sc.setPrefWidth(anwenden.getPrefHeight());
				sc.setMin(1);
				sc.setMax(255);
				deinitThreshold();
				deinitRadioButtons();
				initRadioButtons("homogen", "gaussian", "median", "bilateral");
				deinitBlurOptionsBila();
			}
		});		
		
	}
	
	public void init(Stage stage) {
		this.stage = stage;
	}

	
	/**
	 * This method will open a file.
	 * The file formats available to the user are PNG and JPG2000.
	 */
	
	public void openFile() {
		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("PNG", "*.png"),
				new FileChooser.ExtensionFilter("JPG2000", "*.jpg2")
			);
		File file = fileChooser.showOpenDialog(stage);
		filepath = file.getAbsolutePath();
		//String localURL = file.toURI().toString();
		//Image image2 = new Image(localURL);
		
		/**
		 * Loading the image
		 */
		try {
			mat = Imgcodecs.imread(filepath, Imgcodecs.CV_LOAD_IMAGE_COLOR);
			src = mat.clone();
			if(mat.empty()) {
				System.out.println("Error opening image");
				System.out.println("Usage: filechooserPath");
				System.exit(-1);
				}
			bufferedImage = imageMan.matToBuffImage(mat);
			image = SwingFXUtils.toFXImage(bufferedImage, null);
			//set Imageview to fit image if < screenSize
			if(!(mat.rows() > screenSize.getHeight()/2 || mat.cols() > screenSize.getWidth()/2)){
				imageView.setFitHeight(mat.rows());
				imageView.setFitWidth(mat.cols());
			}
			//else set Imageview to height and width of workspace_hbox
			else {
				imageView.setFitHeight(workspace_hbox.heightProperty().intValue());
				imageView.setFitWidth(workspace_hbox.heightProperty().intValue());
			}
			//Display Shape of Image in bottom right corner
			//toolbar_dimensions.setText("["+mat.cols()+";"+mat.rows()+"]");
			imageView.setImage(image);
			so.setOriginalImage(image);
						
			/*************************************
			 *pluto-explorer scrollable imageview*
			 *************************************/
			
			btn_movezoom.setDisable(false);
			btn_cursor.setDisable(false);
			anwenden.setDisable(false);
			preview.setDisable(false);
			srcButton.setDisable(false);
			cbox_filters.setDisable(false);
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
	 * This method will save a file. 
	 * The file formats available to the user are PNG and JPG2000.
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

	/**
	 * This method provides the various controls with functions.
	 */
	public void handleAnwenden(ActionEvent event) {
		isApplied = true;
		System.out.println(cbox_filters.getValue());
		RadioButton selectedRadioButton = (RadioButton)toggleGroup1.getSelectedToggle();
		switch(cbox_filters.getValue().toString()) {
			case "Grayscale":
				backgroundThread = new Service<Void>() {
					@Override
					protected Task<Void> createTask() {
						// TODO Auto-generated method stub
						return new Task<Void>() {
							@Override
							protected Void call() throws Exception {
								// TODO Auto-generated method stub
								try {
									if(selectedRadioButton.getText().equals("average")) {
										mat = grayscale.average(src);
									}else if (selectedRadioButton.getText().equals("luminosity")) {
										mat = grayscale.luminosity(src);
									}else if(selectedRadioButton.getText().equals("lightness")){
										mat = grayscale.lightness(src);
									}else if(selectedRadioButton.getText().equals("pixelwise")){
										mat = grayscale.grayPixelFor(src);
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
			case "Blur":
				/*backgroundThread = new Service<Void>() {
					@Override
					protected Task<Void> createTask() {
						// TODO Auto-generated method stub
						return new Task<Void>() {
							
							@Override
							protected Void call() throws Exception {
								// TODO Auto-generated method stub
								try {
//									if(selectedRadioButton.getText().equals("homogen")) {
//										mat = blur.homogenBlur(mat,filter);
//									}else if (selectedRadioButton.getText().equals("gaussian")) {
//										mat = blur.imageMan(mat);
//									}else if(selectedRadioButton.getText().equals("median")) {
//										mat = blur.medianBlur(mat);
//									}else if(selectedRadioButton.getText().equals("bilateral")){
//										mat = blur.biliteralBlur(mat);
//									}									
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
					backgroundThread.restart();*/
					break;
			case "Threshold":
				int t = (txtThreshold.getText().isEmpty() ? 1 : Integer.parseInt(txtThreshold.getText()));
				System.out.println(t);
				if(t >= 0 && t < 256) {
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
					alert.setContentText("Only values from 0-255 allowed!");
					alert.showAndWait();
				}
				backgroundThread.restart();
				break;
		}
	}
	
	/**
	 * Adds up the sigma color filter via the plus button.
	 */
	@FXML
	private void handleSgColourPlusButton(ActionEvent event5) {
		int filterPower = Integer.parseInt(txtFilterPower.getText());
		double sigmaColour = Double.parseDouble(txtSigmaColour.getText());
		sigmaColour+=2;
		txtSigmaColour.setText(Double.toString(sigmaColour));
		double sigmaSpace = Double.parseDouble(txtSigmaSpace.getText());
		if(timeline.isEmpty()) {
			mat = blur.bilateralBlur(src, filterPower,sigmaColour,sigmaSpace);
		}
		else {
			mat = blur.bilateralBlur(timeline.getLast().getDst(), filterPower,sigmaColour,sigmaSpace);
		}
		BufferedImage neu = imageMan.matToBuffImage(mat);
		image = SwingFXUtils.toFXImage(neu, null);
		imageView.setImage(image);
		
	}
	
	/**
	 * Subtracts the sigma color filter with the minus button.
	 */
	@FXML
	private void handleSgColourMinusButton(ActionEvent event5) {
		int filterPower = Integer.parseInt(txtFilterPower.getText());
		double sigmaColour = Double.parseDouble(txtSigmaColour.getText());
		if(sigmaColour>1) {
			sigmaColour-=2;
		}
		txtSigmaColour.setText(Double.toString(sigmaColour));
		double sigmaSpace = Double.parseDouble(txtSigmaSpace.getText());		
		if(timeline.isEmpty()) {
			mat = blur.bilateralBlur(src, filterPower,sigmaColour,sigmaSpace);
		}
		else {
			mat = blur.bilateralBlur(timeline.getLast().getDst(), filterPower,sigmaColour,sigmaSpace);
		}
		BufferedImage neu = imageMan.matToBuffImage(mat);
		image = SwingFXUtils.toFXImage(neu, null);
		imageView.setImage(image);
		
	}
	
	/**
	 * Adds up the sigma space filter via the plus button.
	 */
	@FXML
	private void handleSgSpacePlusButton(ActionEvent event5) {
		int filterPower = Integer.parseInt(txtFilterPower.getText());
		double sigmaSpace = Double.parseDouble(txtSigmaSpace.getText());
		sigmaSpace+=2;
		txtSigmaSpace.setText(Double.toString(sigmaSpace));
		double sigmaColour = Double.parseDouble(txtSigmaColour.getText());		
		if(timeline.isEmpty()) {
			mat = blur.bilateralBlur(src, filterPower,sigmaColour,sigmaSpace);
		}
		else {
			mat = blur.bilateralBlur(timeline.getLast().getDst(), filterPower,sigmaColour,sigmaSpace);
		}
		BufferedImage neu = imageMan.matToBuffImage(mat);
		image = SwingFXUtils.toFXImage(neu, null);
		imageView.setImage(image);
		
	}
	
	/**
	 * Subtracts the sigma space filter with the minus button.
	 */
	@FXML
	private void handleSgSpaceMinusButton(ActionEvent event5) {
		int filterPower = Integer.parseInt(txtFilterPower.getText());
		double sigmaSpace = Double.parseDouble(txtSigmaSpace.getText());
		if(sigmaSpace>1) {
			sigmaSpace-=2;
		}
		txtSigmaSpace.setText(Double.toString(sigmaSpace));
		double sigmaColour = Double.parseDouble(txtSigmaColour.getText());		
		if(timeline.isEmpty()) {
			mat = blur.bilateralBlur(src, filterPower,sigmaColour,sigmaSpace);
		}
		else {
			mat = blur.bilateralBlur(timeline.getLast().getDst(), filterPower,sigmaColour,sigmaSpace);
		}

		BufferedImage neu = imageMan.matToBuffImage(mat);
		image = SwingFXUtils.toFXImage(neu, null);
		imageView.setImage(image);
		
	}
	
	/**
	 * Subtracts the treshold filter with the minus button.
	 */
	@FXML
	private void handleButtonThreshMinus(ActionEvent event5){
		int threshold = Integer.parseInt(txtThreshold.getText());
		if(threshold>0) {
			threshold -=1;
		}
		txtThreshold.setText(Integer.toString(threshold));		
		if(timeline.isEmpty()) {
			mat = thold.binarisieren(threshold, src);
		}
		else {
			mat = thold.binarisieren(threshold, timeline.getLast().getDst());
		}

		BufferedImage neu = imageMan.matToBuffImage(mat);
		image = SwingFXUtils.toFXImage(neu, null);
		imageView.setImage(image);
	}
	
	/**
	 * Adds up the threshold filter via the plus button.
	 */
	@FXML
	private void handleButtonThreshPlus(ActionEvent event6) {
		Button btn=(Button)event6.getSource();
		System.out.println(btn.getText());
		while(btn.isPressed()) {
			System.out.println("Button pressed!!");
		}
		int threshold = Integer.parseInt(txtThreshold.getText());
		if(threshold < 255) {
			threshold +=1;
		}
		txtThreshold.setText(Integer.toString(threshold));
		if(timeline.isEmpty()) {
			mat = thold.binarisieren(threshold, src);
		}
		else {
			mat = thold.binarisieren(threshold, timeline.getLast().getDst());
		}
		BufferedImage neu = imageMan.matToBuffImage(mat);
		image = SwingFXUtils.toFXImage(neu, null);
		imageView.setImage(image);
	}
	
	/**
	 * This method coordinates the application of the different settings 
	 * of the blur filter via the plus button.
	 */
	@FXML
	private void handlePlusButton(ActionEvent event3) {
		RadioButton selectedRadioButton = (RadioButton)toggleGroup1.getSelectedToggle();
		int filterPower = Integer.parseInt(txtFilterPower.getText());
		filterPower+=2;
		txtFilterPower.setText(Integer.toString(filterPower));
		try {
			if(selectedRadioButton.getText().equals("median")) {
				if(timeline.isEmpty()) {
					mat = blur.medianBlur(src, filterPower);
				}
				else {
					mat = blur.medianBlur(timeline.getLast().getDst(), filterPower);
				}
				
			}else if (selectedRadioButton.getText().equals("gaussian")) {
				if(timeline.isEmpty()) {
					mat = blur.gaussianBlur(src, filterPower);
				}
				else {
					mat = blur.gaussianBlur(timeline.getLast().getDst(), filterPower);
				}
			}else if (selectedRadioButton.getText().equals("homogen")) {
				if(timeline.isEmpty()) {
					mat = blur.homogenBlur(src, filterPower);
				}
				else {
					mat = blur.homogenBlur(timeline.getLast().getDst(), filterPower);
				}
			}else if (selectedRadioButton.getText().equals("bilateral")) {
				double sigmaColour = Double.parseDouble(txtSigmaColour.getText());
				double sigmaSpace = Double.parseDouble(txtSigmaSpace.getText());
				if(timeline.isEmpty()) {
					mat = blur.bilateralBlur(src, filterPower,sigmaColour,sigmaSpace);
				}
				else {
					mat = blur.bilateralBlur(timeline.getLast().getDst(), filterPower,sigmaColour,sigmaSpace);
				}
				
			}							
			BufferedImage neu = imageMan.matToBuffImage(mat);
			image = SwingFXUtils.toFXImage(neu, null);
			imageView.setImage(image);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method coordinates the application of the different settings 
	 * of the blur filter via the minus button.
	 */
	@FXML
	private void handleMinusButton(ActionEvent event4) {
		RadioButton selectedRadioButton = (RadioButton)toggleGroup1.getSelectedToggle();
		int filterPower = Integer.parseInt(txtFilterPower.getText());
		if(filterPower>1) {
			filterPower-=2;
		}
		txtFilterPower.setText(Integer.toString(filterPower));
		try {
			if(selectedRadioButton.getText().equals("median")) {
				if(timeline.isEmpty()) {
					mat = blur.medianBlur(src, filterPower);
				}
				else {
					mat = blur.medianBlur(timeline.getLast().getDst(), filterPower);
				}
				
			}else if (selectedRadioButton.getText().equals("gaussian")) {
				if(timeline.isEmpty()) {
					mat = blur.gaussianBlur(src, filterPower);
				}
				else {
					mat = blur.gaussianBlur(timeline.getLast().getDst(), filterPower);
				}
			}else if (selectedRadioButton.getText().equals("homogen")) {
				if(timeline.isEmpty()) {
					mat = blur.homogenBlur(src, filterPower);
				}
				else {
					mat = blur.homogenBlur(timeline.getLast().getDst(), filterPower);
				}
			}else if (selectedRadioButton.getText().equals("bilateral")) {
				double sigmaColour = Double.parseDouble(txtSigmaColour.getText());
				double sigmaSpace = Double.parseDouble(txtSigmaSpace.getText());
				if(timeline.isEmpty()) {
					mat = blur.bilateralBlur(src, filterPower,sigmaColour,sigmaSpace);
				}
				else {
					mat = blur.bilateralBlur(timeline.getLast().getDst(), filterPower,sigmaColour,sigmaSpace);
				}
				
			}							
			BufferedImage neu = imageMan.matToBuffImage(mat);
			image = SwingFXUtils.toFXImage(neu, null);
			imageView.setImage(image);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method contains the second window to see the changes in the image.
	 * It is important to explicitly change loader.load to root class, here (parent).
	 * The orders are important, too. First loader.load then get controller.
	 */
	public void handleWindow(ActionEvent event2) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI3.fxml"));
			Parent root = (Parent)loader.load();
			FXController2 controller2 = (FXController2)loader.getController();
			Scene scene = new Scene(root);
			stage = new Stage();
			stage.setScene(scene);
			//stage = new Stage();
			stage.setTitle("Biber");
			stage.show();
			controller2.setImage(image);
			controller2.setImageInImageView();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		}catch(Exception e) {
			e.printStackTrace();			
		}		
	}
	
	/**
	 * This method creates the drag button.
	 */
	 private BiberButton createButton(String text) {
	        BiberButton button = new BiberButton(text);
	        button.setOnDragDetected(e -> {
	            db = button.startDragAndDrop(TransferMode.COPY);
	            db.setDragView(button.snapshot(null, null));
	            cc.put(buttonFormat, "button");
	            db.setContent(cc);
	            //draggingButton = createButton(cbox_filters.getValue().toString());
	            //System.out.println(draggingButton.getText());
	        });
	        //button.setOnDragDone(e -> draggingButton = null);
	        return button;
	    }
	 
	/**
	 * This method assigns the selected filters to the drag button.
	*/
	 private Button setDragOnButton(Button button) {
		 button.setOnDragDetected(e -> {
	            db = button.startDragAndDrop(TransferMode.COPY);
	            db.setDragView(button.snapshot(null, null));
	            cc.put(buttonFormat, "button");
	            db.setContent(cc);
	            RadioButton selectedRadioButton = (RadioButton)toggleGroup1.getSelectedToggle();
	            if(cbox_filters.getValue().equals("Threshold")) {
	            	draggingButton = createButton(cbox_filters.getValue().toString());
	            	draggingButton.setFilterobject(new Threshold(Integer.parseInt(txtThreshold.getText())));
	            	
	            }else if(cbox_filters.getValue().equals("Blur")){
	            	if(selectedRadioButton.getText().equals("bilateral")) {
	            		draggingButton = createButton(cbox_filters.getValue().toString()+": "+selectedRadioButton.getText());
	            		draggingButton.setFilterobject(new Blur(selectedRadioButton.getText(),
	            				Integer.parseInt(txtFilterPower.getText()),
	            				Double.parseDouble(txtSigmaColour.getText()),
	            				Double.parseDouble(txtSigmaSpace.getText())));
	            	}else {
	            		draggingButton = createButton(cbox_filters.getValue().toString()+": "+selectedRadioButton.getText());
	            		draggingButton.setFilterobject(new Blur(selectedRadioButton.getText(),Integer.parseInt(txtFilterPower.getText())));
	            	}
	            }else if(cbox_filters.getValue().equals("Grayscale")){
	            	//Selber kram wie oben
	            	draggingButton = createButton(cbox_filters.getValue().toString()+": "+selectedRadioButton.getText());
	            	draggingButton.setFilterobject(new Grayscale(selectedRadioButton.getText()));
	            }
	            
	            if(!timeline.isEmpty() && timeline.getLast().getDst() != null) {
	            	draggingButton.getFilterobject().setSrc(timeline.getLast().getDst());
	            }
	            else {
	            	draggingButton.getFilterobject().setSrc(src);
	            }
	            
	            //draggingButton.addEventHandler(ActionEvent.ACTION, eventForDragButtons);
	            //System.out.println(draggingButton.getText());
	        });
	        //button.setOnDragDone(e -> draggingButton = null);
	        return button;
	    }
	 
	/**
	 * This method deals with the drop handling of the drag button.
	 */
	 private void addDropHandling(Pane pane) {
		 pane.setOnDragOver(e -> {
			 //System.out.println(e.toString());
			 db = e.getDragboard();
			 if(db.hasContent(buttonFormat)&& draggingButton != null //&&  draggingButton.getParent().getId() != pane.getId()
					 ) {
				 //System.out.println(draggingButton.toString());
				 //System.out.println(pane.getId());
				 e.acceptTransferModes(TransferMode.COPY);
			 }
		 });
		 pane.setOnDragDropped(e -> {
			 db = e.getDragboard();
			 if(db.hasContent(buttonFormat)) {
				 draggingButton.getFilterobject().useFilter();
				 timeline.add(draggingButton.getFilterobject());
				 BufferedImage neu = timeline.getLast().returnImage();
				 image = SwingFXUtils.toFXImage(neu, null);
				 imageView.setImage(image);
				 draggingButton.setImageWithFilter(image);
				 draggingButton.setUserData("draggingButton");
				 draggingButton.setOnAction(new EventHandler<ActionEvent>() {
					    @Override 
					    public void handle(ActionEvent e) {
					    	System.out.println(" EventHandler of Biber Button");
					    	//Hier text vom Button auslesen und je nach dem was drin steht den filter anwenden ...
					    	//absolut nicht elegant aber was anderes f�llt mir nich ein
					    	BiberButton dragB = (BiberButton)e.getSource();
					    	System.out.println(dragB.getFilterobject());
					    	
					    	/*switch (dragB.getFilter()) {
							case "Blur":
								if(dragB.getBlurOption().equals("bilateral")) {
									mat = blur.bilateralBlur(src, dragB.getFilterPower(), dragB.getSigmaColour(), dragB.getSigmaSpace());
								}else if(dragB.getBlurOption().equals("homogen")) {
									mat = blur.homogenBlur(src, dragB.getFilterPower());
								}else if(dragB.getBlurOption().equals("gaussian")) {
									mat = blur.gaussianBlur(src, dragB.getFilterPower());
								}else if(dragB.getBlurOption().equals("median")) {
									mat = blur.medianBlur(src, dragB.getFilterPower());
								}
								break;
							case "Threshold":
								mat = thold.binarisieren(dragB.getThreshold(), src);
								break;
							case "Grayscale":
								if(dragB.getGrayScale().equals("average")) {
									mat = grayscale.average(src);
								}else if(dragB.getGrayScale().equals("luminosity")) {
									mat = grayscale.luminosity(src);
								}else if(dragB.getGrayScale().equals("lightness")) {
									mat = grayscale.lightness(src);
								}else if(dragB.getGrayScale().equals("pixelwise")) {
									mat = grayscale.grayPixelFor(src);
								}
								break;
							default:
								break;
							}*/
					    	dragB.getFilterobject().useFilter();
							BufferedImage neu = dragB.getFilterobject().returnImage();
							image = SwingFXUtils.toFXImage(neu, null);
							imageView.setImage(image);
					    }
					});
				 
				 pane.getChildren().add(draggingButton);
				 e.setDropCompleted(true);
				 ImageView thumb = new ImageView(image);
				 //TODO: get size of button here. scaling is just a workaround
				 thumb.setFitWidth(image.getWidth()/3);
				 thumb.setFitHeight(image.getHeight()/3);
				 draggingButton.setMinWidth(image.getWidth()/3);
				 thumpnailPane.getChildren().add(thumpnailPosition, thumb);
				 thumpnailPosition++;
			}
		 });
		 
	 }
	 
	 /**
	  * Pluto-explorer helpful functions.
	  */
	 private void reset(ImageView imageView, double width, double height) {
	        imageView.setViewport(new Rectangle2D(0, 0, width, height));
	    }
	 
	 /**
	  * Shift the viewport of the imageView by the specified delta, clamping so
	  * the viewport does not move off the actual image.
	  */
	    private void shift(ImageView imageView, Point2D delta) {
	        Rectangle2D viewport = imageView.getViewport();

	        double width = imageView.getImage().getWidth() ;
	        double height = imageView.getImage().getHeight() ;

	        double maxX = width - viewport.getWidth();
	        double maxY = height - viewport.getHeight();
	        
	        double minX = clamp(viewport.getMinX() - delta.getX(), 0, maxX);
	        double minY = clamp(viewport.getMinY() - delta.getY(), 0, maxY);

	        imageView.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));
	    }

	    private double clamp(double value, double min, double max) {

	        if (value < min)
	            return min;
	        if (value > max)
	            return max;
	        return value;
	    }

	    /**
	     * Convert mouse coordinates in the imageView to coordinates in the actual image.
	     */
	    private Point2D imageViewToImage(ImageView imageView, Point2D imageViewCoordinates) {
	        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInLocal().getWidth();
	        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInLocal().getHeight();

	        Rectangle2D viewport = imageView.getViewport();
	        return new Point2D(
	                viewport.getMinX() + xProportion * viewport.getWidth(), 
	                viewport.getMinY() + yProportion * viewport.getHeight());
	    }
	    
/*	    private EventHandler eventForDragButtons = new EventHandler<ActionEvent>() {
	    	public void handle(ActionEvent event) {
	    		//System.out.println(draggingButton.getText()+"!!!!!!");
	    		System.out.println(event.getTarget());
	    	}
		};*/
	    
	    private void initToolbar() {
	    	tg_toolbar = new ToggleGroup();
	    	btn_movezoom = new ToggleButton();
	    	btn_cursor = new ToggleButton();
	    	iv_cursor = new ImageView("file:cursor.png");
	    	iv_movezoom = new ImageView("file:move.png");
	    	iv_cursor.setFitHeight(20);
	    	iv_cursor.setFitWidth(20);
	    	iv_movezoom.setFitHeight(20);
	    	iv_movezoom.setFitWidth(20);
	    	btn_cursor.setGraphic(iv_cursor);
	    	btn_movezoom.setGraphic(iv_movezoom);
	    	btn_cursor.setUserData("cursor");
	    	btn_movezoom.setUserData("move");
	    	btn_movezoom.setDisable(true);
			btn_cursor.setDisable(true);
	    	btn_cursor.setToggleGroup(tg_toolbar);
	    	btn_movezoom.setToggleGroup(tg_toolbar);
	    	toolbar.getItems().add(btn_movezoom);
	    	toolbar.getItems().add(btn_cursor);
	    	tg_toolbar.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

				@Override
				public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
					// TODO Auto-generated method stub
					//System.out.println(tg_toolbar.getSelectedToggle().getUserData());
					if(tg_toolbar.getSelectedToggle().getUserData() == "move") {
						enableMoveZoom(imageView);
						
					}
					else if(tg_toolbar.getSelectedToggle().getUserData()=="cursor")
						disableMoveZoom(imageView);
					else if(newValue == null) {
						
					}
				}
	    		
	    	});
	    	//fix dividers of splitpane for toolbar 80% and dimensions 20%
	    	//toolbar.maxWidthProperty().bind(toolbar_split.widthProperty().multiply(0.90));
	    	//toolbar_rightpane.maxWidthProperty().bind(toolbar_split.widthProperty().multiply(0.1));	   
	    	//toolbar_split.setDividerPositions(0.9);
	    }
	    
	    /**
	     * enableMoveZoom
	     */
	    private void enableMoveZoom(ImageView imageView) {
	    	removeAllListeners(imageView);
	    	width = image.getWidth();
			height = image.getHeight();
			
			reset(imageView,width-1,height-1);
			
			ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();

	        imageView.setOnMousePressed(e -> {
	            
	            Point2D mousePress = imageViewToImage(imageView, new Point2D(e.getX(), e.getY()));
	            mouseDown.set(mousePress);
	        });

	        imageView.setOnMouseDragged(e -> {
	            Point2D dragPoint = imageViewToImage(imageView, new Point2D(e.getX(), e.getY()));
	            shift(imageView, dragPoint.subtract(mouseDown.get()));
	            mouseDown.set(imageViewToImage(imageView, new Point2D(e.getX(), e.getY())));
	        });

	        imageView.setOnScroll(e -> {
	            double delta = e.getDeltaY();
	            Rectangle2D viewport = imageView.getViewport();

	            double scale = clamp(Math.pow(1.01, -delta),

	                // don't scale so we're zoomed in to fewer than MIN_PIXELS in any direction:
	                Math.min(MIN_PIXELS / viewport.getWidth(), MIN_PIXELS / viewport.getHeight()),

	                // don't scale so that we're bigger than image dimensions:
	                Math.max(width / viewport.getWidth(), height / viewport.getHeight())

	            );

	            Point2D mouse = imageViewToImage(imageView, new Point2D(e.getX(), e.getY()));

	            double newWidth = viewport.getWidth() * scale;
	            double newHeight = viewport.getHeight() * scale;

	            // To keep the visual point under the mouse from moving, we need
	            // (x - newViewportMinX) / (x - currentViewportMinX) = scale
	            // where x is the mouse X coordinate in the image

	            // solving this for newViewportMinX gives

	            // newViewportMinX = x - (x - currentViewportMinX) * scale 

	            // we then clamp this value so the image never scrolls out
	            // of the imageview:

	            double newMinX = clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale, 
	                    0, width - newWidth);
	            double newMinY = clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale, 
	                    0, height - newHeight);

	            imageView.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
	        });

	            imageView.setOnMouseClicked(e -> {
	                if (e.getClickCount() == 2) {
	                reset(imageView, width, height);
	            }
	        });
	    }
	    private void disableMoveZoom(ImageView imageView) {
	    	removeAllListeners(imageView);
	    	//Event wenn Maus sich innerhalb ImageView bewegt, spaeter fuer Detailauswahl
	    	imageView.setOnMouseMoved(new EventHandler<MouseEvent>() {
	    		@Override public void handle(MouseEvent event) {
	    			System.out.println(event.getX());
	    			System.out.println(event.getY());
	    		}
	    	});
	    }
	    
	    private void removeAllListeners(ImageView imageView) {
	    	imageView.setOnMousePressed(null);
	    	imageView.setOnScroll(null);
	    	imageView.setOnMouseClicked(null);
	    	imageView.setOnMouseMoved(null);
	    }
	    
	    private void initRadioButtons(String textButton1, String textButton2, String textButton3, String textButton4) {
	    	radioButton1.setSelected(false);
	    	radioButton2.setSelected(false);
	    	radioButton3.setSelected(false);
	    	radioButton4.setSelected(false);
	    	vbox.getChildren().add(flowpaneRadioButton);
		    radioButton1.setText(textButton1);
		    radioButton2.setText(textButton2);
		    radioButton3.setText(textButton3);
		    radioButton4.setText(textButton4);
	    }
	    
	    private void deinitRadioButtons() {
	    	vbox.getChildren().remove(flowpaneRadioButton);
	    }
	    
	    private void initBlurOptions() {
	    	vbox.getChildren().add(flowpaneFilterSize);
	    }
	    
	    private void deinitBlurOptions() {
	    	vbox.getChildren().remove(flowpaneFilterSize);
	    }
	    
	    private void initBlurOptionsBila() {
	    	vbox.getChildren().remove(flowpaneFilterSize);
	    	vbox.getChildren().add(flowpaneFilterSize);
	    	vbox.getChildren().add(flowpaneSigmaColour);
	    	vbox.getChildren().add(flowpaneSigmaSpace);
	    }
	    
	    private void deinitBlurOptionsBila() {
	    	vbox.getChildren().remove(flowpaneFilterSize);
	    	vbox.getChildren().remove(flowpaneSigmaColour);
	    	vbox.getChildren().remove(flowpaneSigmaSpace);
	    }
	    
	    private void deinitThreshold() {
	    	vbox.getChildren().remove(flowpaneThreshold);
	    }
	    
	    private void initThreshold() {
	    	vbox.getChildren().add(flowpaneThreshold);
	    }
}
