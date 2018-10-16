package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Übergibt das Originalbild und das aktuell
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
