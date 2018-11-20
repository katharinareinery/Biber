package application;

import java.awt.image.BufferedImage;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

/**
 * Converting Colored Images to Grayscale.
 * The class named Imgproc of the package org.opencv.imgproc 
 * provides methods to convert an image from one color to another.
 * A method named cvtColor() is used to convert colored images to grayscale.
 */

public class Grayscale extends ImageMan{
	private Mat dst = new Mat();
	private Mat srcCopy = new Mat();
	
	private Mat src;
	private String ftype = "";
	private double red;
	private double green;
	private double blue;
	
	//getter/setter
	public void setSrc(Mat src) {
		this.src = src;
	}
	public Mat getSrc() {
		return src;
	}
	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	public String getFtype() {
		return ftype;
	}
	
	public void setRed(double red) {
		this.red=red;
	}
	
	public double getRed() {
		return red;
	}
	
	public double getGreen() {
		return green;
	}
	public void setGreen(double green) {
		this.green = green;
	}
	public double getBlue() {
		return blue;
	}
	public void setBlue(double blue) {
		this.blue = blue;
	}
	//constructor
	public Grayscale() {
		super();
	}
	public Grayscale(String ftype) {
		super();
		this.ftype = ftype;
	}
	public Grayscale(Mat src,String ftype) {
		super();
		this.src = src;
		this.ftype = ftype;
	}
	
	public Grayscale(Mat src, String ftype, double red, double green, double blue) {
		super();
		this.src=src;
		this.ftype=ftype;
		this.red=red;
		this.green=green;
		this.blue=blue;
	}
	
	public Grayscale(String ftype, double red, double green, double blue) {
		super();
		this.ftype=ftype;
		this.red=red;
		this.green=green;
		this.blue=blue;
	}
	//toString() override
	public String toString() {
			// TODO Auto-generated method stub
			return "BWMethod:"+ftype+"\tMat:["+src.rows()+","+src.cols()+":"+src.channels()+"]";
	}
		//useFilter() method to call from within timelines filterlist
	public void useFilter() {
		if(ftype.equals("") || ftype.equals(null)) {
			System.out.println("Grayscale::useFilter::ftype equals NULLSTRING");
		}
		else if(ftype.equals("luminosity")) {
			srcCopy = src.clone();
			if(srcCopy.channels()>1)
				Imgproc.cvtColor(srcCopy, srcCopy, Imgproc.COLOR_BGR2RGB);
			dst = new Mat(srcCopy.size(),CvType.CV_8UC1);
			double grayValue;
			double[] data;
			for(int i = 0; i<srcCopy.rows();i++) {
				for(int j = 0; j <srcCopy.cols();j++) {
					data = srcCopy.get(i, j);	
					grayValue=(data[0]*0.21)+(data[1]*0.72)+(data[2]*0.07);
					dst.put(i, j, grayValue);	
				}
			}
		}
		else if(ftype.equals("average")) {
			srcCopy=src.clone();
			if(srcCopy.channels()>1)
				Imgproc.cvtColor(srcCopy, srcCopy, Imgproc.COLOR_BGR2RGB);
			dst = new Mat(srcCopy.size(),CvType.CV_8UC1);
			double[] data;
			double grayValue;
			for(int i = 0; i<srcCopy.rows(); i++) {
				for(int j = 0; j<srcCopy.cols(); j++) {
					data = srcCopy.get(i, j);
					grayValue=((data[0]+data[1]+data[2])/3);
					dst.put(i, j, grayValue);
				}
			}
		}
		else if(ftype.equals("customized")) {
			srcCopy=src.clone();
			dst = new Mat(srcCopy.size(),CvType.CV_8UC1);
			double[] data;
			double grayValue;
			Imgproc.cvtColor(srcCopy,srcCopy,Imgproc.COLOR_BGR2RGB);
			for(int i = 0; i < srcCopy.rows();i++) {
				for(int j = 0; j < srcCopy.cols();j++ ) {
					data=srcCopy.get(i, j);
					grayValue = (data[0]*red)+(data[1]*green)+(data[2]*blue);
					dst.put(i, j, grayValue);
				}
			}
		}
		else if(ftype.equals("lightness")) {
			srcCopy=src.clone();
			if(srcCopy.channels()>1)
				Imgproc.cvtColor(srcCopy, srcCopy, Imgproc.COLOR_BGR2RGB);
			dst = new Mat(srcCopy.size(),CvType.CV_8UC1);
			double[] data;
			double grayValue;
			double max,min;
			for(int i = 0; i < srcCopy.rows(); i++) {
				for(int j = 0; j < srcCopy.cols(); j++) {
					data = srcCopy.get(i, j);
					if(data[0]>data[1] && data[0]>data[2]) {
						max = data[0];
					}else {
						if(data[1]>data[0] && data[1]>data[2]) {
							max = data[1];
						}else {
							max = data[2];
						}
					}
					if(data[0]<data[1] && data[0]<data[2]) {
						min = data[0];
					}else {
						if(data[1]<data[0] && data[1]<data[2]) {
							min = data[1];
						}else {
							min = data[2];
						}
					}
					grayValue = ((max+min)/2);
					dst.put(i, j, grayValue);
					}
				}
			}
		}
		
		public Mat getDst() {
			return dst;
		}
		
		public BufferedImage returnImage() {
			return matToBuffImage(dst);
		}
		
	/** 
	 * The luminosity method 
	 * averages the values, but it forms a weighted average
	 * to account for human perception.
	 * We are more sensitive to green than other colors, so green is weighted most heavily.
	 * The formula for luminosity is 0.21 R + 0.72 G + 0.07 B.
	 */
	public Mat luminosity(Mat src) {
		//Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2RGB);
		srcCopy = src.clone();
		Imgproc.cvtColor(srcCopy, srcCopy, Imgproc.COLOR_BGR2RGB);
		dst = new Mat(srcCopy.size(),CvType.CV_8UC1);
		double grayValue;
		double[] data;
		for(int i = 0; i<srcCopy.rows();i++) {
			for(int j = 0; j <srcCopy.cols();j++) {
				data = srcCopy.get(i, j);	
				grayValue=(data[0]*0.21)+(data[1]*0.72)+(data[2]*0.07);
				dst.put(i, j, grayValue);	
			}
		}
		return dst;
	}
	
	public Mat grayOwn(Mat src,double red, double green, double blue) {
		srcCopy=src.clone();
		dst = new Mat(srcCopy.size(),CvType.CV_8UC1);
		double[] data;
		double grayValue;
		Imgproc.cvtColor(srcCopy,srcCopy,Imgproc.COLOR_BGR2RGB);
		for(int i = 0; i < srcCopy.rows();i++) {
			for(int j = 0; j < srcCopy.cols();j++ ) {
				data=srcCopy.get(i, j);
				grayValue = (data[0]*red)+(data[1]*green)+(data[2]*blue);
				dst.put(i, j, grayValue);
			}
		}
		//System.out.println("row: "+row+"\tcol: "+col)
		return dst;
	}
	
	public Mat grayPixelFor(Mat src) {
		srcCopy=src.clone();
		double[] data;
		dst = new Mat(srcCopy.size(),CvType.CV_8UC1);
		double grayValue;
		Imgproc.cvtColor(srcCopy, srcCopy, Imgproc.COLOR_BGR2RGB);
		for(int i = 0; i <srcCopy.rows();i++) {
			for(int j = 0; j < srcCopy.cols(); j++) {
				data=srcCopy.get(i,j);
				grayValue =  (data[0]*0.21)+(data[1]*0.72)+(data[2]*0.07);
				dst.put(i, j, grayValue);
			}
		}
		return dst;
	}
		
	/** 
	 * The average method
	 * simply averages the values: (R + G + B) / 3.
	*/
	public Mat average(Mat src) {
		srcCopy=src.clone();
		Imgproc.cvtColor(srcCopy, srcCopy, Imgproc.COLOR_BGR2RGB);
		dst = new Mat(srcCopy.size(),CvType.CV_8UC1);
		double[] data;
		double grayValue;
		for(int i = 0; i<srcCopy.rows(); i++) {
			for(int j = 0; j<srcCopy.cols(); j++) {
				data = srcCopy.get(i, j);
				grayValue=((data[0]+data[1]+data[2])/3);
				dst.put(i, j, grayValue);
			}
		}
		return dst;
	}
	
	/** 
	 * The lightness method 
	 * averages the most prominent and least prominent colors:
	 * (max(R, G, B) + min(R, G, B)) / 2.
	 * 
	*/
	public Mat lightness(Mat src) {
		srcCopy=src.clone();
		//Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2RGB);
		Imgproc.cvtColor(srcCopy, srcCopy, Imgproc.COLOR_BGR2RGB);
		dst = new Mat(srcCopy.size(),CvType.CV_8UC1);
		double[] data;
		double grayValue;
		double max,min;
		for(int i = 0; i < srcCopy.rows(); i++) {
			for(int j = 0; j < srcCopy.cols(); j++) {
				data = srcCopy.get(i, j);
				if(data[0]>data[1] && data[0]>data[2]) {
					max = data[0];
				}else {
					if(data[1]>data[0] && data[1]>data[2]) {
						max = data[1];
					}else {
						max = data[2];
					}
				}
				if(data[0]<data[1] && data[0]<data[2]) {
					min = data[0];
				}else {
					if(data[1]<data[0] && data[1]<data[2]) {
						min = data[1];
					}else {
						min = data[2];
					}
				}
				grayValue = ((max+min)/2);
				dst.put(i, j, grayValue);
			}
		}
		return dst;
	}
}
