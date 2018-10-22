package application;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class BiberButton extends Button {
	
	private Image imageWithFilter;
	private String filter;
	private int thresValue;
	private int filterPower;
	private int sigmaColour;

	public BiberButton() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BiberButton(String text, Node graphic) {
		super(text, graphic);
		// TODO Auto-generated constructor stub
	}

	public BiberButton(String text,String filter) {
		super(text);
		this.filter=filter;
		// TODO Auto-generated constructor stub
	}

	//Only for ThresButton
	public BiberButton(String text,String filter,int thresValue) {
		super(text);
		this.filter=filter;
		this.thresValue=thresValue;
	}
	//Für jeden Typen ein Kontruktor? oder nur zwei Kontruktor und dann darin überprüfen welche dinge man bekommen hat und braucht?
	
	public Image getImageWithFilter() {
		return imageWithFilter;
	}

	public void setImageWithFilter(Image imageWithFilter) {
		this.imageWithFilter = imageWithFilter;
	}
	
	public String getFilter() {
		return this.filter;
	}
	
	public int getThresValue() {
		return this.thresValue;
	}
	
	
	
	

}
