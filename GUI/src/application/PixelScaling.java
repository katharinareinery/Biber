package application;

import java.awt.image.BufferedImage;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class PixelScaling extends ImageMan{
	private Mat src;
	private Mat dst;
	private double coeffRed;
	private double coeffGreen;
	private double coeffBlue;
	
	
	public PixelScaling(double coeffRed, double coeffGreen, double coeffBlue) {
		this.coeffRed = coeffRed;
		this.coeffGreen = coeffGreen;
		this.coeffBlue = coeffBlue;
	}
	
	public void setSrc(Mat src) {
		this.src= src;
	}
	
	public Mat getDst() {
		return dst;
	}
	
	public PixelScaling() {
		super();
	}
	
	
	public Mat scaling(Mat src, double coeffRed, double coeffGreen, double coeffBlue) {
		this.src=src.clone();
		dst = new Mat(this.src.size(),this.src.type());
		//Imgproc.cvtColor(this.src, this.src, Imgproc.COLOR_BGR2RGB);
		double value[];
		for(int i = 0; i < this.src.rows(); i++) {
			for(int j = 0 ; j < this.src.cols(); j++) {
				value=this.src.get(i, j);
				value[2] = value[2]*coeffRed;
				value[1] = value[1]*coeffGreen;
				value[0] = value[0]*coeffBlue;
				if(value[0]>255) {
					value[0]=255;
				}
				if(value[1]>255) {
					value[1] = 255;
				}
				if(value[2]>255) {
					value[2] = 255;
				}
				dst.put(i, j, value);
			}
		}
		return dst;
	}
	
	public void useFilter() {
		dst = scaling(src,coeffRed,coeffGreen,coeffBlue);
	}
	
	public BufferedImage returnImage() {
		return matToBuffImage(dst);
	}
}
