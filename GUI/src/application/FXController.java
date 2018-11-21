package application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.opencv.core.CvType;
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
	private Button buttonGrayRedPlus;
	@FXML
	private Button buttonGrayRedMinus;
	@FXML
	private Button buttonGrayGreenPlus;
	@FXML
	private Button buttonGrayGreenMinus;
	@FXML
	private Button buttonGrayBluePlus;
	@FXML
	private Button buttonGrayBlueMinus;
	@FXML
	private TextField txtSigmaColour;
	@FXML
	private TextField txtSigmaSpace;
	@FXML
	private TextField txtGrayRed;
	@FXML
	private TextField txtGrayGreen;
	@FXML
	private TextField txtGrayBlue;
	@FXML
	private Label labelFilerSize;
	@FXML
	private Label labelSigmaX;
	@FXML
	private Label labelSigmaY;
	@FXML
	private Label labelGrayRed;
	@FXML
	private Label labelGrayGreen;
	@FXML
	private Label labelGrayBlue;
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
	private FlowPane flowpaneGrayRed;
	@FXML
	private FlowPane flowpaneGrayGreen;
	@FXML
	private FlowPane flowpaneGrayBlue;
	@FXML
	private HBox workspace_hbox;
	@FXML
	private Label toolbar_dimensions;
	@FXML
	private SplitPane toolbar_split;
	@FXML
	private FlowPane toolbar_rightpane;
	@FXML
	private FlowPane flowpaneZhangSuen;
	@FXML
	private Button buttonZhangSuenMinus;
	@FXML
	private Button buttonZhangSuenPlus;
	@FXML
	private TextField txtZhangSuen;
	//Own Filter
	@FXML
	private FlowPane flowpaneOwnFilter;
	@FXML
	private RadioButton radioOwnFilter2;
	@FXML
	private RadioButton radioOwnFilter3;
	@FXML
	private RadioButton radioOwnFilter4;
	@FXML
	private ToggleGroup toggleOwnKernelSize;
	@FXML
	private TextField coef;
	@FXML
	private TextField zero_zero;
	@FXML
	private TextField one_zero;
	@FXML
	private TextField two_zero;
	@FXML
	private TextField three_zero;
	@FXML
	private TextField zero_one;
	@FXML
	private TextField one_one;
	@FXML
	private TextField two_one;
	@FXML
	private TextField three_one;
	@FXML
	private TextField zero_two;
	@FXML
	private TextField one_two;
	@FXML
	private TextField two_two;
	@FXML
	private TextField three_two;
	@FXML
	private TextField zero_three;
	@FXML
	private TextField one_three;
	@FXML
	private TextField two_three;
	@FXML
	private TextField three_three;
	@FXML
	private TextField custom_delta;
	
	private ToggleButton btn_movezoom;
	private ImageView iv_movezoom;
	private ImageView iv_cursor;
	private ToggleButton btn_cursor;
	private ToggleGroup tg_toolbar;

	private TextField txt_fld = new TextField();

	//Linked List for storing filters
	private LinkedList<ImageMan> timeline = new LinkedList<ImageMan>();
	private ScrollBar sc = new ScrollBar();

	private FileChooser fileChooser;
	private Stage stage;
	private Image image;

	private BufferedImage bufferedImage;

	private Mat src;
	private Mat mat;

	//private GridPane itembox = new GridPane();
	
	//Filter Objects
	private Blur blur = new Blur();
	private Grayscale grayscale = new Grayscale();
	private Threshold thold = new Threshold();
	private ZhangSuen zhangsuen = new ZhangSuen();
	private CustomFilter customfilter = new CustomFilter();
	private Mat kernel;
	
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
			stage.getIcons().add(new Image("file:biber.png"));
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
		ObservableList<String> options = FXCollections.observableArrayList("Grayscale","Blur","Threshold","Zhang Suen Thinning","Custom Filter");
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
		txtZhangSuen.setText("1");
		deinitFilter();
		deinitBlurOptionsBila();
		deinitThreshold();
		deinitGrayOptions();
		deinitZhangSuen();
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
						Mat newMat = mat.clone();
						try {
							if(selectedRadioButton.getText().equals("average")) {
								deinitGrayOptions();
								newMat = grayscale.average(newMat);
							}else if (selectedRadioButton.getText().equals("luminosity")) {
								deinitGrayOptions();
								newMat = grayscale.luminosity(newMat);
							}else if(selectedRadioButton.getText().equals("lightness")){
								deinitGrayOptions();
								newMat = grayscale.lightness(newMat);
							}else if(selectedRadioButton.getText().equals("customized")){
								initGrayOptions();
							}
							setTheImage(newMat);
							//System.out.println(mat.cols());
						}catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						//deinitBlurOptionsBila();
					}else if(selectedRadioButton.getText().equals("bilateral")) {
						deinitGrayOptions();
						initBlurOptionsBila();
					}else {
						deinitGrayOptions();
						deinitBlurOptionsBila();
						initBlurOptions();
					}
				}                
			}
		});
		
		toggleOwnKernelSize.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				// TODO Auto-generated method stub
				if(toggleOwnKernelSize.getSelectedToggle() != null) {
					RadioButton selected = (RadioButton)toggleOwnKernelSize.getSelectedToggle();
					if(selected.getText().equals("2")) {
						initSmallKernel();
					}
					else if(selected.getText().equals("3")) {
						initBigKernel();
						zero_three.setDisable(true);
						one_three.setDisable(true);
						two_three.setDisable(true);
						three_three.setDisable(true);
						three_two.setDisable(true);
						three_one.setDisable(true);
						three_zero.setDisable(true);
					}
					else if(selected.getText().equals("4")) {
						initBigKernel();
					}
				}
			}
		});
		/*
		 * Combobox - The changelistener is waiting for the selection.
		 */
		cbox_filters.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
			BufferedImage neu = imageMan.matToBuffImage(mat);
			image = SwingFXUtils.toFXImage(neu, null);
			imageView.setImage(image);
			if(newVal!= null && newVal.equals("Threshold")) {
				deinitFilter();
				deinitRadioButtons();
				deinitGrayOptions();
				deinitBlurOptionsBila();
				deinitZhangSuen();
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
				deinitFilter();
				txt_fld.setPrefWidth(anwenden.getPrefWidth()/2);
				sc.setPrefWidth(anwenden.getPrefHeight());
				sc.setMin(1);
				sc.setMax(255);
				deinitThreshold();
				deinitGrayOptions();
				deinitBlurOptionsBila();
				deinitRadioButtons();
				deinitZhangSuen();
				initRadioButtons("average", "luminosity", "lightness", "customized");
			}
			else if (newVal!=null && newVal.equals("Blur")) {
				deinitFilter();
				txt_fld.setPrefWidth(anwenden.getPrefWidth()/2);
				sc.setPrefWidth(anwenden.getPrefHeight());
				sc.setMin(1);
				sc.setMax(255);
				deinitThreshold();
				deinitZhangSuen();
				deinitRadioButtons();
				deinitGrayOptions();
				initRadioButtons("homogen", "gaussian", "median", "bilateral");
				deinitBlurOptionsBila();
			}
			else if(newVal!= null && newVal.equals("Zhang Suen Thinning")) {
				deinitFilter();
				deinitRadioButtons();
				deinitBlurOptionsBila();
				deinitThreshold();
				deinitGrayOptions();
				initZhangSuen();
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
			else if(newVal!= null && newVal.equals("Custom Filter")) {
				deinitRadioButtons();
				deinitBlurOptionsBila();
				deinitThreshold();
				deinitZhangSuen();
				deinitGrayOptions();
				initFilter();
				txt_fld.setPrefWidth(anwenden.getPrefWidth()/2);
				
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
		PlatformHelper.run(() -> {
		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("PNG", "*.png"),
				new FileChooser.ExtensionFilter("JPG2000", "*.jpg2")
				);
		File file = fileChooser.showOpenDialog(stage);
		filepath = file.getAbsolutePath();
		

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
		});
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
								Mat newMat = mat.clone();
								if(selectedRadioButton.getText().equals("average")) {
									newMat = grayscale.average(mat);
								}else if (selectedRadioButton.getText().equals("luminosity")) {
									newMat = grayscale.luminosity(mat);
								}else if(selectedRadioButton.getText().equals("lightness")){
									newMat = grayscale.lightness(mat);
								}else if(selectedRadioButton.getText().equals("customized")){
									double red = Double.parseDouble(txtGrayRed.getText());
									double green = Double.parseDouble(txtGrayGreen.getText());
									double blue = Double.parseDouble(txtGrayBlue.getText());
									newMat = grayscale.grayOwn(mat, red, green, blue);
								}
								setTheImage(newMat);
								newMat.copyTo(mat);
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
			backgroundThread = new Service<Void>() {
				@Override
				protected Task<Void> createTask() {
					// TODO Auto-generated method stub
					return new Task<Void>() {
						@Override
						protected Void call() throws Exception {
							// TODO Auto-generated method stub
							try {
								Mat newMat = mat.clone();
								if(selectedRadioButton.getText().equals("homogen")) {
									newMat = blur.homogenBlur(mat,Integer.parseInt(txtFilterPower.getText()));
								}else if (selectedRadioButton.getText().equals("gaussian")) {
									newMat = blur.gaussianBlur(mat, Integer.parseInt(txtFilterPower.getText()));
								}else if(selectedRadioButton.getText().equals("median")) {
									newMat = blur.medianBlur(mat,Integer.parseInt(txtFilterPower.getText()));
								}else if(selectedRadioButton.getText().equals("bilateral")){
									newMat = blur.bilateralBlur(mat, Integer.parseInt(txtFilterPower.getText()), Integer.parseInt(txtSigmaColour.getText()), Integer.parseInt(txtSigmaSpace.getText()));
								}									
								setTheImage(newMat);
								newMat.copyTo(mat);
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
									Mat newMat = mat.clone();
									newMat = thold.binarisieren(t, mat);
									setTheImage(newMat);
									newMat.copyTo(mat);
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
		case "Zhang Suen Thinning":
				int thresh = (txtZhangSuen.getText().isEmpty() ? 1 : Integer.parseInt(txtZhangSuen.getText()));
				System.out.println(thresh);
				if(thresh >= 0 && thresh < 256) {
					backgroundThread = new Service<Void>() {
						@Override
						protected Task<Void> createTask() {
							// TODO Auto-generated method stub
							return new Task<Void>() {
								@Override
								protected Void call() throws Exception {
									// TODO Auto-generated method stub
									try {
										mat = zhangsuen.zhangSuen(mat,thresh);
										setTheImage(mat);
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
			case "Custom Filter":
				RadioButton selected = (RadioButton)toggleOwnKernelSize.getSelectedToggle();
				kernel = fillKernel(selected.getText());
				System.out.println(kernel.toString());
				backgroundThread = new Service<Void>() {
					@Override
					protected Task<Void> createTask() {
						// TODO Auto-generated method stub
						return new Task<Void>() {
							@Override
							protected Void call() throws Exception {
								try {
									mat = customfilter.customKernel(mat, kernel, Double.parseDouble(coef.getText()),Double.parseDouble(custom_delta.getText()));
									setTheImage(mat);
								}catch(Exception e) {e.printStackTrace();}
								return null;
							}
						};
					}
				};
				backgroundThread.restart();
				break;
		}
	}

	
	@FXML
	private void handleGrayRedMinusButton(ActionEvent event) {
		Mat newMat = mat.clone();
		double red = Double.parseDouble(txtGrayRed.getText());
		double green = Double.parseDouble(txtGrayGreen.getText());
		double  blue = Double.parseDouble(txtGrayBlue.getText());
		red=round(red-0.01,2);
		System.out.println("hallo");
		txtGrayRed.setText(Double.toString(red));
		if(timeline.isEmpty()) {
			newMat = grayscale.grayOwn(mat, red, green, blue);
		}else {
			newMat = grayscale.grayOwn(timeline.getLast().getDst(), red, green, blue);
		}
		setTheImage(newMat);
	}
	
	@FXML
	private void handleGrayRedPlusButton(ActionEvent event) {
		Mat newMat = mat.clone();
		double red = Double.parseDouble(txtGrayRed.getText());
		double green = Double.parseDouble(txtGrayGreen.getText());
		double  blue = Double.parseDouble(txtGrayBlue.getText());
		red=round(red+0.01,2);
		txtGrayRed.setText(Double.toString(red));
		if(timeline.isEmpty()) {
			newMat = grayscale.grayOwn(mat, red, green, blue);
		}else {
			newMat = grayscale.grayOwn(timeline.getLast().getDst(), red, green, blue);
		}
		setTheImage(newMat);
	}
	
	@FXML
	private void handleGrayGreenMinusButton(ActionEvent event) {
		Mat newMat = mat.clone();
		double red = Double.parseDouble(txtGrayRed.getText());
		double green = Double.parseDouble(txtGrayGreen.getText());
		double  blue = Double.parseDouble(txtGrayBlue.getText());
		green=round(green-0.01,2);
		txtGrayGreen.setText(Double.toString(green));
		if(timeline.isEmpty()) {
			newMat = grayscale.grayOwn(mat, red, green, blue);
		}else {
			newMat = grayscale.grayOwn(timeline.getLast().getDst(), red, green, blue);
		}
		setTheImage(newMat);
	}
	
	@FXML
	private void handleGrayGreenPlusButton(ActionEvent event) {
		Mat newMat = mat.clone();
		double red = Double.parseDouble(txtGrayRed.getText());
		double green = Double.parseDouble(txtGrayGreen.getText());
		double  blue = Double.parseDouble(txtGrayBlue.getText());
		green=round(green+0.01,2);
		txtGrayGreen.setText(Double.toString(green));
		if(timeline.isEmpty()) {
			newMat = grayscale.grayOwn(mat, red, green, blue);
		}else {
			newMat = grayscale.grayOwn(timeline.getLast().getDst(), red, green, blue);
		}
		setTheImage(newMat);
	}
	
	@FXML
	private void handleGrayBlueMinusButton(ActionEvent event) {
		Mat newMat = mat.clone();
		double red = Double.parseDouble(txtGrayRed.getText());
		double green = Double.parseDouble(txtGrayGreen.getText());
		double  blue = Double.parseDouble(txtGrayBlue.getText());
		blue=round(blue-0.01,2);
		txtGrayBlue.setText(Double.toString(blue));
		if(timeline.isEmpty()) {
			newMat = grayscale.grayOwn(mat, red, green, blue);
		}else {
			newMat = grayscale.grayOwn(timeline.getLast().getDst(), red, green, blue);
		}
		setTheImage(newMat);
	}
	
	@FXML
	private void handleGrayBluePlusButton(ActionEvent event) {
		Mat newMat = mat.clone();
		double red = Double.parseDouble(txtGrayRed.getText());
		double green = Double.parseDouble(txtGrayGreen.getText());
		double  blue = Double.parseDouble(txtGrayBlue.getText());
		blue=round(blue+0.01,2);
		txtGrayBlue.setText(Double.toString(blue));
		if(timeline.isEmpty()) {
			newMat = grayscale.grayOwn(mat, red, green, blue);
		}else {
			newMat = grayscale.grayOwn(timeline.getLast().getDst(), red, green, blue);
		}
		setTheImage(newMat);
	}
	
	/**
	 * Adds up the sigma color filter via the plus button.
	 */
	@FXML
	private void handleSgColourPlusButton(ActionEvent event5) {
		Mat newMat = mat.clone();
			int filterPower = Integer.parseInt(txtFilterPower.getText());
			double sigmaColour = Double.parseDouble(txtSigmaColour.getText());
			sigmaColour+=2;
			txtSigmaColour.setText(Double.toString(sigmaColour));
			double sigmaSpace = Double.parseDouble(txtSigmaSpace.getText());
			if(timeline.isEmpty()) {
				newMat = blur.bilateralBlur(mat, filterPower,sigmaColour,sigmaSpace);
			}
			else {
				newMat = blur.bilateralBlur(timeline.getLast().getDst(), filterPower,sigmaColour,sigmaSpace);
			}
		setTheImage(newMat);
	}

	/**
	 * Subtracts the sigma color filter with the minus button.
	 */
	@FXML
	private void handleSgColourMinusButton(ActionEvent event5) {
		Mat newMat = mat.clone();
			int filterPower = Integer.parseInt(txtFilterPower.getText());
			double sigmaColour = Double.parseDouble(txtSigmaColour.getText());
			if(sigmaColour>1) {
				sigmaColour-=2;
			}
			txtSigmaColour.setText(Double.toString(sigmaColour));
			double sigmaSpace = Double.parseDouble(txtSigmaSpace.getText());		
			if(timeline.isEmpty()) {
				newMat = blur.bilateralBlur(mat, filterPower,sigmaColour,sigmaSpace);
			}
			else {
				newMat = blur.bilateralBlur(timeline.getLast().getDst(), filterPower,sigmaColour,sigmaSpace);
			}
		setTheImage(newMat);
	}

	/**
	 * Adds up the sigma space filter via the plus button.
	 */
	@FXML
	private void handleSgSpacePlusButton(ActionEvent event5) {
		Mat newMat = mat.clone();
			int filterPower = Integer.parseInt(txtFilterPower.getText());
			double sigmaSpace = Double.parseDouble(txtSigmaSpace.getText());
			sigmaSpace+=2;
			txtSigmaSpace.setText(Double.toString(sigmaSpace));
			double sigmaColour = Double.parseDouble(txtSigmaColour.getText());		
			if(timeline.isEmpty()) {
				newMat = blur.bilateralBlur(mat, filterPower,sigmaColour,sigmaSpace);
			}
			else {
				newMat = blur.bilateralBlur(timeline.getLast().getDst(), filterPower,sigmaColour,sigmaSpace);
			}
		setTheImage(newMat);
	}

	/**
	 * Subtracts the sigma space filter with the minus button.
	 */
	@FXML
	private void handleSgSpaceMinusButton(ActionEvent event5) {
		Mat newMat = mat.clone();
			int filterPower = Integer.parseInt(txtFilterPower.getText());
			double sigmaSpace = Double.parseDouble(txtSigmaSpace.getText());
			if(sigmaSpace>1) {
				sigmaSpace-=2;
			}
			txtSigmaSpace.setText(Double.toString(sigmaSpace));
			double sigmaColour = Double.parseDouble(txtSigmaColour.getText());		
			if(timeline.isEmpty()) {
				newMat = blur.bilateralBlur(mat, filterPower,sigmaColour,sigmaSpace);
			}
			else {
				newMat = blur.bilateralBlur(timeline.getLast().getDst(), filterPower,sigmaColour,sigmaSpace);
			}
		setTheImage(newMat);
	}

	/**
	 * Subtracts the treshold filter with the minus button.
	 */
	@FXML
	private void handleButtonThreshMinus(ActionEvent event5){
		Mat newMat = mat.clone();
		int threshold = Integer.parseInt(txtThreshold.getText());
		if(threshold>0) {
			threshold -=1;
		}
		txtThreshold.setText(Integer.toString(threshold));		
		if(timeline.isEmpty()) {
			newMat = thold.binarisieren(threshold, mat);
		}
		else {
			newMat = thold.binarisieren(threshold, timeline.getLast().getDst());
		}
		setTheImage(newMat);
	}

	/**
	 * Adds up the threshold filter via the plus button.
	 */
	@FXML
	private void handleButtonThreshPlus(ActionEvent event6) {
		Mat newMat = mat.clone();
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
			newMat = thold.binarisieren(threshold, mat);
		}
		else {
			newMat = thold.binarisieren(threshold, timeline.getLast().getDst());
		}
		setTheImage(newMat);
	}
	/**
	 * Subtracts the zhang suen threshold filter with the minus button.
	 */
	@FXML
	private void handleButtonzhangThreshMinus(ActionEvent event5){
		Mat newMat = mat.clone();
		int threshold = Integer.parseInt(txtZhangSuen.getText());
		if(threshold>0) {
			threshold -=1;
		}
		txtZhangSuen.setText(Integer.toString(threshold));		
		if(timeline.isEmpty()) {
			newMat = thold.binarisieren(threshold, newMat);
		}
		else {
			newMat = thold.binarisieren(threshold, timeline.getLast().getDst());
		}
		setTheImage(newMat);
	}
	
	/**
	 * Adds up the zhang suen threshold filter via the plus button.
	 */
	@FXML
	private void handleButtonzhangThreshPlus(ActionEvent event6) {
		Mat newMat = mat.clone();
		Button btn=(Button)event6.getSource();
		System.out.println(btn.getText());
		while(btn.isPressed()) {
			System.out.println("Button pressed!!");
		}
		int threshold = Integer.parseInt(txtZhangSuen.getText());
		if(threshold < 255) {
			threshold +=1;
		}
		txtZhangSuen.setText(Integer.toString(threshold));
		if(timeline.isEmpty()) {
			newMat = thold.binarisieren(threshold, newMat);
		}
		else {
			newMat = thold.binarisieren(threshold, timeline.getLast().getDst());
		}
		setTheImage(newMat);
	}
	/**
	 * This method coordinates the application of the different settings 
	 * of the blur filter via the plus button.
	 */
	@FXML
	private void handlePlusButton(ActionEvent event3) {
		Mat newMat = mat.clone();
		RadioButton selectedRadioButton = (RadioButton)toggleGroup1.getSelectedToggle();
		int filterPower = Integer.parseInt(txtFilterPower.getText());
		filterPower+=2;
		txtFilterPower.setText(Integer.toString(filterPower));
		try {
			if(selectedRadioButton.getText().equals("median")) {
				if(timeline.isEmpty()) {
					newMat = blur.medianBlur(mat, filterPower);
				}
				else {
					newMat = blur.medianBlur(timeline.getLast().getDst(), filterPower);
				}
				
			}else if (selectedRadioButton.getText().equals("gaussian")) {
				if(timeline.isEmpty()) {
					newMat = blur.gaussianBlur(mat, filterPower);
				}
				else {
					newMat = blur.gaussianBlur(timeline.getLast().getDst(), filterPower);
				}
			}else if (selectedRadioButton.getText().equals("homogen")) {
				if(timeline.isEmpty()) {
					newMat = blur.homogenBlur(mat, filterPower);
				}
				else {
					newMat = blur.homogenBlur(timeline.getLast().getDst(), filterPower);
				}
			}else if (selectedRadioButton.getText().equals("bilateral")) {
				double sigmaColour = Double.parseDouble(txtSigmaColour.getText());
				double sigmaSpace = Double.parseDouble(txtSigmaSpace.getText());
				if(timeline.isEmpty()) {
					newMat = blur.bilateralBlur(mat, filterPower,sigmaColour,sigmaSpace);
				}
				else {
					newMat = blur.bilateralBlur(timeline.getLast().getDst(), filterPower,sigmaColour,sigmaSpace);
				}
				
			}	
			setTheImage(newMat);
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
		Mat newMat = mat.clone();
		RadioButton selectedRadioButton = (RadioButton)toggleGroup1.getSelectedToggle();
		int filterPower = Integer.parseInt(txtFilterPower.getText());
		filterPower-=2;
		txtFilterPower.setText(Integer.toString(filterPower));
		try {
			if(selectedRadioButton.getText().equals("median")) {
				if(timeline.isEmpty()) {
					newMat = blur.medianBlur(mat, filterPower);
				}
				else {
					newMat = blur.medianBlur(timeline.getLast().getDst(), filterPower);
				}

			}else if (selectedRadioButton.getText().equals("gaussian")) {
				if(timeline.isEmpty()) {
					newMat = blur.gaussianBlur(mat, filterPower);
				}
				else {
					newMat = blur.gaussianBlur(timeline.getLast().getDst(), filterPower);
				}
			}else if (selectedRadioButton.getText().equals("homogen")) {
				if(timeline.isEmpty()) {
					newMat = blur.homogenBlur(mat, filterPower);
				}
				else {
					newMat = blur.homogenBlur(timeline.getLast().getDst(), filterPower);
				}
			}else if (selectedRadioButton.getText().equals("bilateral")) {
				double sigmaColour = Double.parseDouble(txtSigmaColour.getText());
				double sigmaSpace = Double.parseDouble(txtSigmaSpace.getText());
				if(timeline.isEmpty()) {
					newMat = blur.bilateralBlur(mat, filterPower,sigmaColour,sigmaSpace);
				}
				else {
					newMat = blur.bilateralBlur(timeline.getLast().getDst(), filterPower,sigmaColour,sigmaSpace);
				}

			}
			setTheImage(newMat);
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
			stage.getIcons().add(new Image("file:biber.png"));
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

			}else if(cbox_filters.getValue().equals("Custom Filter")) {
				RadioButton selected = (RadioButton)toggleOwnKernelSize.getSelectedToggle();
				draggingButton = createButton(cbox_filters.getValue().toString());
				draggingButton.setFilterobject(new CustomFilter(fillKernel(selected.getText())
						,Double.parseDouble(coef.getText())
						,Double.parseDouble(custom_delta.getText())));
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
			}else if(cbox_filters.getValue().equals("Zhang Suen Thinning")){
				draggingButton = createButton(cbox_filters.getValue().toString());
				draggingButton.setFilterobject(new ZhangSuen(Integer.parseInt(txtZhangSuen.getText())));
			}
			else if(cbox_filters.getValue().equals("Grayscale")){
				if(getSelectedRadioButtonText().equals("customized")) {
					draggingButton = createButton(cbox_filters.getValue().toString()+": "+getSelectedRadioButtonText());
					draggingButton.setFilterobject(new Grayscale(getSelectedRadioButtonText(),
							round(Double.parseDouble(txtGrayRed.getText()),2),
							round(Double.parseDouble(txtGrayGreen.getText()),2),
							round(Double.parseDouble(txtGrayBlue.getText()),2)));
				}else {
					draggingButton = createButton(cbox_filters.getValue().toString()+": "+selectedRadioButton.getText());
					draggingButton.setFilterobject(new Grayscale(selectedRadioButton.getText()));
				}
			}

			if(!timeline.isEmpty() && timeline.getLast().getDst() != null) {
				draggingButton.getFilterobject().setSrc(timeline.getLast().getDst());
			}
			else {
				draggingButton.getFilterobject().setSrc(mat);
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
				mat = draggingButton.getFilterobject().getDst();
				draggingButton.setImageWithFilter(image);
				draggingButton.setUserData("draggingButton");
				draggingButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override 
					public void handle(ActionEvent e) {
						System.out.println(" EventHandler of Biber Button");
						BiberButton dragB = (BiberButton)e.getSource();
						System.out.println(dragB.getFilterobject());
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

	private void initGrayOptions() {
		vbox.getChildren().add(flowpaneGrayRed);
		vbox.getChildren().add(flowpaneGrayGreen);
		vbox.getChildren().add(flowpaneGrayBlue);
	}
	
	private void deinitGrayOptions() {
		vbox.getChildren().remove(flowpaneGrayRed);
		vbox.getChildren().remove(flowpaneGrayGreen); 	
		vbox.getChildren().remove(flowpaneGrayBlue);
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


	private String getSelectedRadioButtonText() {
		RadioButton selectedRadioButton = (RadioButton)toggleGroup1.getSelectedToggle();
		return selectedRadioButton.getText();
	}

	private static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();
		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	    private void initThreshold() {
	    	vbox.getChildren().add(flowpaneThreshold);
	    }
	    
	    private void initZhangSuen() {
	    	vbox.getChildren().add(flowpaneZhangSuen);
	    }
	    private void deinitZhangSuen() {
	    	vbox.getChildren().remove(flowpaneZhangSuen);
	    }
	    
	    private void deinitFilter() {
	    	vbox.getChildren().remove(flowpaneOwnFilter);
	    }
	    private void initFilter() {
	    	vbox.getChildren().add(flowpaneOwnFilter);
	    }
	    private void initSmallKernel() {
	    	zero_zero.setDisable(false);
	    	zero_one.setDisable(false);
	    	zero_two.setDisable(true);
	    	zero_three.setDisable(true);
	    	one_zero.setDisable(false);
	    	one_one.setDisable(false);
	    	one_two.setDisable(true);
	    	one_three.setDisable(true);
	    	two_zero.setDisable(true);
	    	two_one.setDisable(true);
	    	two_two.setDisable(true);
	    	two_three.setDisable(true);
	    	three_zero.setDisable(true);
	    	three_one.setDisable(true);
	    	three_two.setDisable(true);
	    	three_three.setDisable(true);
	    }
	    private void initBigKernel() {
	    	zero_zero.setDisable(false);
	    	zero_one.setDisable(false);
	    	zero_two.setDisable(false);
	    	zero_three.setDisable(false);
	    	one_zero.setDisable(false);
	    	one_one.setDisable(false);
	    	one_two.setDisable(false);
	    	one_three.setDisable(false);
	    	two_zero.setDisable(false);
	    	two_one.setDisable(false);
	    	two_two.setDisable(false);
	    	two_three.setDisable(false);
	    	three_zero.setDisable(false);
	    	three_one.setDisable(false);
	    	three_two.setDisable(false);
	    	three_three.setDisable(false);
	    }
	    private Mat fillKernel(String size) {
	    	Mat kernel;
	    	if(size.equals("2")) {
				kernel = new Mat(2,2,CvType.CV_32F);
				kernel.put(0,0,Double.parseDouble(zero_zero.getText()));
				kernel.put(0,1,Double.parseDouble(zero_one.getText()));
				kernel.put(1,0,Double.parseDouble(one_zero.getText()));
				kernel.put(1,1,Double.parseDouble(one_one.getText()));
			}
			else if(size.equals("3")) {
				kernel = new Mat(3,3,CvType.CV_32F);
				kernel.put(0,0,Double.parseDouble(zero_zero.getText()));
				kernel.put(0,1,Double.parseDouble(zero_one.getText()));
				kernel.put(0,2,Double.parseDouble(zero_two.getText()));
				kernel.put(1,0,Double.parseDouble(one_zero.getText()));
				kernel.put(1,1,Double.parseDouble(one_one.getText()));
				kernel.put(1,2,Double.parseDouble(one_two.getText()));
				kernel.put(2,0,Double.parseDouble(two_zero.getText()));
				kernel.put(2,1,Double.parseDouble(two_one.getText()));
				kernel.put(2,2,Double.parseDouble(two_two.getText()));
			}
			else {
				kernel = new Mat(4,4,CvType.CV_32F);
				kernel.put(0,0,Double.parseDouble(zero_zero.getText()));
				kernel.put(0,1,Double.parseDouble(zero_one.getText()));
				kernel.put(0,2,Double.parseDouble(zero_two.getText()));
				kernel.put(0,3,Double.parseDouble(zero_three.getText()));
				kernel.put(1,0,Double.parseDouble(one_zero.getText()));
				kernel.put(1,1,Double.parseDouble(one_one.getText()));
				kernel.put(1,2,Double.parseDouble(one_two.getText()));
				kernel.put(1,3,Double.parseDouble(one_three.getText()));
				kernel.put(2,0,Double.parseDouble(two_zero.getText()));
				kernel.put(2,1,Double.parseDouble(two_one.getText()));
				kernel.put(2,2,Double.parseDouble(two_two.getText()));	
				kernel.put(2,3,Double.parseDouble(two_three.getText()));
				kernel.put(3,0,Double.parseDouble(three_zero.getText()));
				kernel.put(3,1,Double.parseDouble(three_one.getText()));
				kernel.put(3,2,Double.parseDouble(three_two.getText()));	
				kernel.put(3,3,Double.parseDouble(three_three.getText()));
			}
	    	return kernel;
	    }
	   
	    
	    
	    private void setTheImage(Mat mat) {
			BufferedImage neu = imageMan.matToBuffImage(mat);
			image = SwingFXUtils.toFXImage(neu, null);
			imageView.setImage(image);
	    }
	    
	    public static class PlatformHelper {
	    	 
	        public static void run (Runnable treatment) {
	            if(treatment == null) throw new IllegalArgumentException("The treatment to perform can not be null");
	     
	            if(Platform.isFxApplicationThread()) treatment.run();
	            else Platform.runLater(treatment);
	        }
	    }
	    
	    
}
