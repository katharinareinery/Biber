package application;

import javafx.scene.image.Image;

/**
 * The SharedObject contains all Elements that should be accessable within different classes.
 * To reduce the memory overhead this class is implemented as a singelton.
 */

public class SharedObject {
	
	//create an object of SingleObject
	private static SharedObject instance = new SharedObject();
	
	private Image originalImage;
	
	//Get the instance object available
	public static SharedObject getInstance() {
		return instance;
	}
	
	//make the constructor private so that this class cannot be instantiated
	private SharedObject() {}
	
	public Image getOriginalImage() {
		return originalImage;
	}

	public void setOriginalImage(Image originalImage) {
		this.originalImage = originalImage;
	}

}
