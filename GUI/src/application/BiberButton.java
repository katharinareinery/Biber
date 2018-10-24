package application;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class BiberButton extends Button {
	
	private Image imageWithFilter;
	private String filter;
	private double sigmaColour;
	private double sigmaSpace;
	private int filterPower;
	private int threshold;
	private String grayScale;
	private String blurOption;
	//Filter object
	private ImageMan filterobject; 



	public BiberButton() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BiberButton(String text, Node graphic) {
		super(text, graphic);
		// TODO Auto-generated constructor stub
	}
	
	public BiberButton(String text,ImageMan filterobject) {
		super(text);
		this.filterobject=filterobject;
	}
	
	public void setFilterobject(ImageMan filterobject) {
		this.filterobject = filterobject;
	}
	
	public ImageMan getFilterobject() {
		return filterobject;
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
	
	public void setFilter(String filter) {
		this.filter=filter;
	}
	
	public String getFilter() {
		return this.filter;
	}

	public double getSigmaColour() {
		return sigmaColour;
	}

	public void setSigmaColour(double sigmaColour) {
		this.sigmaColour = sigmaColour;
	}

	public double getSigmaSpace() {
		return sigmaSpace;
	}

	public void setSigmaSpace(double sigmaSpace) {
		this.sigmaSpace = sigmaSpace;
	}

	public int getFilterPower() {
		return filterPower;
	}

	public void setFilterPower(int filterPower) {
		this.filterPower = filterPower;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public String getGrayScale() {
		return grayScale;
	}

	public void setGrayScale(String grayScale) {
		this.grayScale = grayScale;
	}

	public String getBlurOption() {
		return blurOption;
	}

	public void setBlurOption(String blurOption) {
		this.blurOption = blurOption;
	}
}
