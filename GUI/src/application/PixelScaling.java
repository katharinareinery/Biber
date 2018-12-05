package application;

import java.awt.image.BufferedImage;

import org.opencv.core.Mat;

public class PixelScaling extends ImageMan{
	private Mat src;
	private Mat dst;
	private double coeffRed;
	private double coeffGreen;
	private double coeffBlue;
	
	
	public PixelScaling(Mat src, double coeffRed, double coeffGreen, double coeffBlue) {
		this.src = src;
		this.coeffRed = coeffRed;
		this.coeffGreen = coeffGreen;
		this.coeffBlue = coeffBlue;
	}
	
	
	
	
	public BufferedImage returnImage() {
		return matToBuffImage(dst);
	}
}
