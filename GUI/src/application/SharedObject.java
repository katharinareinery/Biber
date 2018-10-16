package application;

import javafx.scene.image.Image;

public class SharedObject {
	
	/**
	 * Singleton Pattern
	 * Gibt das ursprüngliche Bild zurück
	 */
	
	private static SharedObject instance = new SharedObject();
	
	private Image originalImage;
	
	public static SharedObject getInstance() {
		return instance;
	}
	
	private SharedObject() {
		
	}
	
	public Image getOriginalImage() {
		return originalImage;
	}

	public void setOriginalImage(Image originalImage) {
		this.originalImage = originalImage;
	}

}
