package application;
import java.awt.image.BufferedImage;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
//import org.opencv.highgui.HighGui;
//import org.opencv.imgcodecs.Imgcodecs;

/**
 * Blurring (smoothing) is the commonly used image processing operation for reducing the image noise. 
 * The process removes high-frequency content, like edges, from the image and makes it smooth.
 * In general blurring is achieved by convolving (each element of the image is added to its local neighbors, 
 * weighted by the kernel) the image through a low pass filter kernel.
 * 
 * src: The source (input image) for this operation.
 * dst: The destination (output image) for this operation.
 * sigmaColor: Filter sigma in the color space.
 * sigmaSpace: Filter sigma in the coordinate space.
 * Size( w, h ): Defines the size of the kernel to be used ( of width w pixels and height h pixels).
 * Point(-1, -1): Indicates where the anchor point (the pixel evaluated) is located with respect to the 
 * neighborhood. If there is a negative value, then the center of the kernel is considered the anchor point.
 */

public class Blur extends ImageMan{
	private Mat dst = new Mat();
	private Mat src;
	private String ftype = "";
	private int filterpower;
	private double sigmaColour;
	private double sigmaSpace;
	
	//constructor
	public Blur() {
		
	}
	public Blur(String ftype,int filterpower) {
		this.ftype = ftype;
		this.filterpower = filterpower;
	}
	public Blur(String ftype,int filterpower,double sigmaColour,double sigmaSpace) {
		this.ftype=ftype;
		this.filterpower=filterpower;
		this.sigmaColour=sigmaColour;
		this.sigmaSpace = sigmaSpace;
	}
	public Blur(Mat src,String ftype,int filterpower,double sigmaColour,double sigmaSpace) {
		this.src = src;
		this.ftype = ftype;
		this.filterpower = filterpower;
		this.sigmaColour = sigmaColour;
		this.sigmaSpace = sigmaSpace;
	}
	//useFilter MEthod to use within Timeline q
	@Override
	public void useFilter() {
		if(ftype.equals("homogen")) {
			Imgproc.blur(src, dst, new Size(filterpower,filterpower),new Point(-1,-1));
		}
		else if(ftype.equals("gaussian")) {
			Imgproc.GaussianBlur(src, dst, new Size(filterpower,filterpower), 0,0);
		}
		else if(ftype.equals("median")) {
			Imgproc.medianBlur(src, dst, filterpower);
		}
		else if(ftype.equals("bilateral")) {
			Imgproc.bilateralFilter(src, dst, filterpower, sigmaColour, sigmaSpace);
		}
	}
	//toString() override
	public String toString() {
		// TODO Auto-generated method stub
		return "BlurMethod:"+ftype+"\tMat:["+src.rows()+","+src.cols()+":"+src.channels()+"]";
	}
	//returns buffered Image
	public BufferedImage returnImage() {
		return matToBuffImage(dst);
	}

	//getter/setter
	public void setSrc(Mat src) {
		this.src=src;
	}

	public Mat getDst() {
		return dst;
	}
	public String getFtype() {
		return ftype;
	}
	public Mat getSrc() {
		return src;
	}
	public void setDst(Mat dst) {
		this.dst = dst;
	}
	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	
	/**
	 * Gaussian filtering is done by convolving each point in the input array with a Gaussian kernel 
	 * and then summing them all to produce the output array.
	 * The Gaussian filter is a low-pass filter that removes the high-frequency components are reduced.
	 */
	public Mat gaussianBlur(Mat src,int filterPower) {
		Imgproc.GaussianBlur(src, dst, new Size(filterPower,filterPower), 0,0);
		return dst;
	}
	
	/**
	 * The median filter run through each element of the signal (in this case the image) 
	 * and replace each pixel with the median of its neighboring pixels 
	 * (located in a square neighborhood around the evaluated pixel).
	 */
	public Mat medianBlur(Mat src,int filterPower) {
		Imgproc.medianBlur(src, dst, filterPower);
		return dst;
	}
		
	/**
	 * OpenCV offers the function blur to perform smoothing with this filter.
	 */
	public Mat homogenBlur(Mat src,int filterPower) {
		Imgproc.blur(src, dst, new Size(filterPower,filterPower),new Point(-1,-1));
		return dst;
	}
	
	/**
	 * The Bilateral Filter operation applies a bilateral image to a filter.
	 */
	public Mat bilateralBlur(Mat src,int filterPower, double sigmaColour,double sigmaSpace) {
		Imgproc.bilateralFilter(src, dst, filterPower, sigmaColour, sigmaSpace);
		return dst;
	}
}
