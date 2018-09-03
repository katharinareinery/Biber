package application;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Blur extends ImageMan{
	private Mat dst = new Mat();
	
	public Mat gaussianBlur(Mat src,int filterPower) {
		Imgproc.GaussianBlur(src, dst, new Size(filterPower,filterPower), 0,0);
		return dst;
	}
	
	public Mat medianBlur(Mat src,int filterPower) {
		Imgproc.medianBlur(src, dst, filterPower);
		return dst;
	}
		
	public Mat homogenBlur(Mat src,int filterPower) {
		Imgproc.blur(src, dst, new Size(filterPower,filterPower),new Point(-1,-1));
		return dst;
	}
	
	public Mat bilateralBlur(Mat src,int filterPower) {
		Imgproc.bilateralFilter(src, dst, filterPower, filterPower*2, filterPower/2);
		return dst;
	}
}
