package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Passes the original image and the current image.
 * 
 * The FXController2 provides the original image and the currently edited image to be able 
 * to compare them in a separate window. The original image is obtained via the class SharedObject 
 * in which a singleton pattern was realized and the currently edited image from the FXController.
 * 
 */

public class FXController2 implements Initializable{
	@FXML
	private ImageView imageView2, imageView3;
	private Image img;
	private SharedObject so = SharedObject.getInstance();
	
	public void setImage(Image img) {
		this.img = img;
	}
	
	public void setImageInImageView() {
		imageView2.setImage(img);
		imageView3.setImage(so.getOriginalImage());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		imageView2.setImage(img);
		//System.out.println("FXController2.fxml");
	}
}
