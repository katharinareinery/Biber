package application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
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
	private Button draggingButton;
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
	private ImageView imageView, imageView2;
	@FXML
	private MenuBar menubar;
	@FXML
	private MenuItem ueber;
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
	
	private ToggleButton btn_movezoom;
	private ImageView iv_movezoom;
	private ImageView iv_cursor;
	private ToggleButton btn_cursor;
	private ToggleGroup tg_toolbar;
	
	private TextField txt_fld = new TextField();
	private RadioButton rad_button_grayscale_average = new RadioButton("Average");
	private RadioButton rad_button_grayscale_lumi = new RadioButton("Lumi");
	private RadioButton rad_button_grayscale_lightness = new RadioButton("Lightness");
	private RadioButton rad_button_grayscale_pixelwise = new RadioButton("Pixelwise");
	private final ToggleGroup group_rad_grayscale = new ToggleGroup();
	
	private RadioButton rad_button_blur_homogen = new RadioButton("Homogen");
	private RadioButton rad_button_blur_gaussian = new RadioButton("Gaussian");
	private RadioButton rad_button_blur_median = new RadioButton("Median");
	private RadioButton rad_button_blur_bilateral = new RadioButton("Biliteral");
	private final ToggleGroup group_rad_blur = new ToggleGroup();
	
	private ScrollBar sc = new ScrollBar();
	
	private boolean isOpen = false;
	private FileChooser fileChooser;
	private Stage stage;
	private Image image;
	private BufferedImage bufferedImage;
	
	private Mat src;
	private Mat mat;

	private GridPane itembox = new GridPane();
		
	private Blur blur = new Blur();
	private Grayscale grayscale = new Grayscale();
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
	//	label.setText("Schwarz-WeiÃŸ.");
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
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> options = FXCollections.observableArrayList("Grayscale","Weichzeichnen","Schwellwert");
		cbox_filters.getItems().addAll(options);
		imageView.setPreserveRatio(true);
		//srcButton = createButton("Drag ME!");
		//vbox.getChildren().add(srcButton);
		setDragOnButton(srcButton);
		addDropHandling(flowpane);
		addDropHandling(vbox);
		ueber.setOnAction(this::handleAbout);
		preview.setOnAction(this::handleWindow);
		anwenden.setDisable(true);
		preview.setDisable(true);
		srcButton.setDisable(true);
		radioButton1.setVisible(false);
		radioButton2.setVisible(false);
		radioButton3.setVisible(false);
		radioButton4.setVisible(false);
		initToolbar();
		//ComboBox - Changelistener ( Wartet auf Auswahl )
		cbox_filters.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
			if(newVal!= null && newVal.equals("Schwellwert")) {
				vbox.getChildren().removeAll(itembox);
				itembox.getChildren().clear();
				radioButton1.setVisible(false);
				radioButton2.setVisible(false);
				radioButton3.setVisible(false);
				radioButton4.setVisible(false);
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
			else if(newVal!=null && newVal.equals("Grayscale")) {
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
				initRadioButton("average", "luminosity", "lightness", "pixelwise");
//			
//				rad_button_grayscale_average.setToggleGroup(group_rad_grayscale);
//				rad_button_grayscale_lumi.setToggleGroup(group_rad_grayscale);
//				rad_button_grayscale_pixelwise.setToggleGroup(group_rad_grayscale);
//				rad_button_grayscale_lightness.setToggleGroup(group_rad_grayscale);
//				rad_button_grayscale_average.setSelected(true);
//				itembox.add(rad_button_grayscale_average, 0, 0);					//Radiobutton1 an Stelle 0,0
//				itembox.add(rad_button_grayscale_lumi, 0, 1);	//Radiobutton2 0,1(drunter)
//				itembox.add(rad_button_grayscale_lightness, 0, 2);
//				itembox.add(rad_button_grayscale_pixelwise, 0, 3); //Radiobutton3 0,2(drunter)
//				vbox.getChildren().add(itembox);			//GridPane itembox an Vbox vbox anhaengen
			}
			else if (newVal!=null && newVal.equals("Weichzeichnen")) {
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
				initRadioButton("homogen", "gaussian", "median", "bilateral");
//				rad_button_blur_homogen.setToggleGroup(group_rad_blur);
//				rad_button_blur_gaussian.setToggleGroup(group_rad_blur);
//				rad_button_blur_median.setToggleGroup(group_rad_blur);
//				rad_button_blur_bilateral.setToggleGroup(group_rad_blur);
//				rad_button_blur_homogen.setSelected(true);
//				itembox.add(rad_button_blur_homogen,0,0);					//Radiobutton1 an Stelle 0,0
//				itembox.add(rad_button_blur_gaussian,0,1);	//Radiobutton2 0,1(drunter)
//				itembox.add(rad_button_blur_median,0,2); //Radiobutton3 0,2(drunter)
//				itembox.add(rad_button_blur_bilateral, 0, 3);
//				vbox.getChildren().add(itembox);			
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
		String localURL = file.toURI().toString();
		Image image2 = new Image(localURL);
		
		/*
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
			imageView.setImage(image);
			/*************************************
			 *pluto-explorer scrollable imageview*
			 *************************************/
			
	            
            
			btn_movezoom.setDisable(false);
			btn_cursor.setDisable(false);
			anwenden.setDisable(false);
			preview.setDisable(false);
			srcButton.setDisable(false);
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
										for(int i = 0; i <mat.rows();i++) {
											for(int j = 0; j < mat.cols(); j++) {
												mat = grayscale.grayPixel(i, j, mat);
											}
											BufferedImage neu = imageMan.matToBuffImage(mat);
											image = SwingFXUtils.toFXImage(neu, null);
											imageView.setImage(image);
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
									if(selectedRadioButton.getText().equals("homogen")) {
										mat = blur.homogenBlur(mat);
									}else if (selectedRadioButton.getText().equals("gaussian")) {
										mat = blur.imageMan(mat);
									}else if(selectedRadioButton.getText().equals("median")) {
										mat = blur.medianBlur(mat);
									}else if(selectedRadioButton.getText().equals("bilateral")){
										mat = blur.biliteralBlur(mat);
									}									
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
	/**
	 * This method contains the second window to see the changes in the image.
	 */
	public void handleWindow(ActionEvent event2) {
		try {
			//au�erdem loader.load auf root class hier (parent) explizit wandeln
			//Reihenfolge wichtig erst loader.load dann controller holen
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI3.fxml"));
			Parent root = (Parent)loader.load();
			FXController2 controller2 = (FXController2)loader.getController();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage = new Stage();
			stage.setTitle("Biber");
			controller2.setImage(image);
			controller2.setImageInImageView();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		}catch(Exception e) {
			e.printStackTrace();			
		}		
	}
	 private Button createButton(String text) {
	        Button button = new Button(text);
	        button.setOnDragDetected(e -> {
	            Dragboard db = button.startDragAndDrop(TransferMode.COPY);
	            db.setDragView(button.snapshot(null, null));
	            ClipboardContent cc = new ClipboardContent();
	            cc.put(buttonFormat, "button");
	            db.setContent(cc);
	            //draggingButton = createButton(cbox_filters.getValue().toString());
	            //System.out.println(draggingButton.getText());
	        });
	        //button.setOnDragDone(e -> draggingButton = null);
	        return button;
	    }
	 
	 private Button setDragOnButton(Button button) {
	        button.setOnDragDetected(e -> {
	            Dragboard db = button.startDragAndDrop(TransferMode.COPY);
	            db.setDragView(button.snapshot(null, null));
	            ClipboardContent cc = new ClipboardContent();
	            cc.put(buttonFormat, "button");
	            db.setContent(cc);
	            draggingButton = createButton(cbox_filters.getValue().toString());
	            //System.out.println(draggingButton.getText());
	        });
	        //button.setOnDragDone(e -> draggingButton = null);
	        return button;
	    }
	 
	 private void addDropHandling(Pane pane) {
		 pane.setOnDragOver(e -> {
			 //System.out.println(e.toString());
			 Dragboard db = e.getDragboard();
			 if(db.hasContent(buttonFormat)&& draggingButton != null //&&  draggingButton.getParent().getId() != pane.getId()
					 ) {
				 System.out.println(draggingButton.toString());
				 System.out.println(pane.getId());
				 e.acceptTransferModes(TransferMode.COPY);
			 }
		 });
		 pane.setOnDragDropped(e -> {
			 Dragboard db = e.getDragboard();
			 if(db.hasContent(buttonFormat)) {
				 pane.getChildren().add(draggingButton);
				 e.setDropCompleted(true);
			}
		 });
		 
	 }
	 /**
	  * Pluto-explorer hilfsfunktionen
	  */
	 private void reset(ImageView imageView, double width, double height) {
	        imageView.setViewport(new Rectangle2D(0, 0, width, height));
	    }
	 // shift the viewport of the imageView by the specified delta, clamping so
	 // the viewport does not move off the actual image:
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

	    // convert mouse coordinates in the imageView to coordinates in the actual image:
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
	    }
	    
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
	    	//Event wenn Maus sich innerhalb ImageView bewwegt, sp�ter f�r Detailauswahl
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
	    
	    private void initRadioButton(String textButton1, String textButton2, String textButton3, String textButton4) {
	    	radioButton1.setVisible(true);
	    	radioButton1.setText(textButton1);
	    	radioButton2.setVisible(true);
	    	radioButton2.setText(textButton2);
	    	radioButton3.setVisible(true);
	    	radioButton3.setText(textButton3);
	    	radioButton4.setVisible(true);
	    	radioButton4.setText(textButton4);
	    }
}

