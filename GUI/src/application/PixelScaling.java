package application;

import java.awt.image.BufferedImage;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class PixelScaling extends ImageMan{
	private Mat src;
	private Mat dst;
	private int channel;
	private double coeffGray;
	private double coeffRed;
	private double coeffGreen;
	private double coeffBlue;
	
	
	public PixelScaling(int channel, double coeffRed, double coeffGreen, double coeffBlue) {
		this.channel = channel;
		this.coeffRed = coeffRed;
		this.coeffGreen = coeffGreen;
		this.coeffBlue = coeffBlue;
	}
	
	public PixelScaling(int channel, double coeffGray) {
		this.channel = channel;
		this.coeffGray = coeffGray;
	}
	
	public void setSrc(Mat src) {
		this.src= src;
	}
	
	public Mat getDst() {
		return dst;
	}
	
	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public double getCoeffGray() {
		return coeffGray;
	}

	public void setCoeffGray(double coeffGray) {
		this.coeffGray = coeffGray;
	}

	public double getCoeffRed() {
		return coeffRed;
	}

	public void setCoeffRed(double coeffRed) {
		this.coeffRed = coeffRed;
	}

	public double getCoeffGreen() {
		return coeffGreen;
	}

	public void setCoeffGreen(double coeffGreen) {
		this.coeffGreen = coeffGreen;
	}

	public double getCoeffBlue() {
		return coeffBlue;
	}

	public void setCoeffBlue(double coeffBlue) {
		this.coeffBlue = coeffBlue;
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
	
	public Mat scalingGray(Mat src, double coeffGray) {
		this.src=src.clone();
		dst = new Mat(this.src.size(),this.src.type());
		double value[];
		for(int i = 0; i < this.src.rows(); i++) {
			for(int j = 0; j < this.src.cols(); j++) {
				value=this.src.get(i, j);
				value[0] = value[0]*coeffGray;
				if(value[0] > 255) {
					value[0] = 255;
				}
				dst.put(i, j, value);
			}
		}
		
		return dst;
	}
	
	public void useFilter() {
		if(channel == 3) {
			dst = scaling(src,coeffRed,coeffGreen,coeffBlue);
		}
		if(channel == 1) {
			dst = scalingGray(src, coeffGray);
		}
	}
	
	public BufferedImage returnImage() {
		return matToBuffImage(dst);
	}
}
