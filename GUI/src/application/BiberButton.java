package application;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class BiberButton extends Button {
	
	private Image imageWithFilter;

	public BiberButton() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BiberButton(String text, Node graphic) {
		super(text, graphic);
		// TODO Auto-generated constructor stub
	}

	public BiberButton(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public Image getImageWithFilter() {
		return imageWithFilter;
	}

	public void setImageWithFilter(Image imageWithFilter) {
		this.imageWithFilter = imageWithFilter;
	}
	
	
	
	
	
	

}
