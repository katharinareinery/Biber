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
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
	@FXML
	private MenuItem ueber;
	
	private FileChooser fileChooser;
	private Stage stage;
	private Image image;
	private BufferedImage bufferedImage;
	private Mat mat;
	
	Blur blur = new Blur();
	BlackAndWhite blackAndwhite = new BlackAndWhite();
	String filepath;
	
	/**
	 * Blur.
	 */
	public void handleButton(ActionEvent event) {
		try {
			BufferedImage neu = toBufferedImage(blur.imageMan(filepath));
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
			BufferedImage neu = toBufferedImage(blackAndwhite.imageMan(filepath));
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
		weichzeichnen.setOnAction(this::handleButton);
		//schwellwerte.setOnAction(this::handleButtonSch);
		schwarzweiss.setOnAction(this::handleButtonSchWe);
		ueber.setOnAction(this::handleAbout);
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
			bufferedImage = ImageIO.read(file);
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
	
	/*helper*/


    public BufferedImage toBufferedImage(Mat m){
          int type = BufferedImage.TYPE_BYTE_GRAY;
          if ( m.channels() > 1 ) {
              type = BufferedImage.TYPE_3BYTE_BGR;
          }
          int bufferSize = m.channels()*m.cols()*m.rows();
          byte [] b = new byte[bufferSize];
          m.get(0,0,b); // get all the pixels
          BufferedImage img = new BufferedImage(m.cols(),m.rows(), type);
          final byte[] targetPixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
          System.arraycopy(b, 0, targetPixels, 0, b.length);  
          return img;
      }
    
   public Mat bufferedImageToMat(BufferedImage image) throws IOException{
		    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		    ImageIO.write(image, "jpg", byteArrayOutputStream);
		    byteArrayOutputStream.flush();
		    return Imgcodecs.imdecode(new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
   }
	
}
