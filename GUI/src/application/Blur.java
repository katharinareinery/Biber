package application;
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
