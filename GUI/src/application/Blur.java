package application;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Blur extends ImageMan{
	int DELAY_CAPTION = 1500;
	int DELAY_BLUR = 100;
	int MAX_KERNEL_LENGHT = 31;
	private Mat dst = new Mat();
	
	public Mat imageMan(Mat src) {		
			for(int i = 1; i < MAX_KERNEL_LENGHT; i = i + 2) {
				Imgproc.GaussianBlur(src, dst, new Size(i , i), 0, 0);
			}
		return dst;
	}
	
	public Mat medianBlur(Mat src) {
		for(int i = 1; i<MAX_KERNEL_LENGHT; i=i+2) {
			Imgproc.medianBlur(src, dst, i);
		}
		return dst;
	}
	
	public Mat homogenBlur(Mat src) {
		for(int i = 1; i < MAX_KERNEL_LENGHT; i=i+2) {
			Imgproc.blur(src, dst, new Size( i, i ), new Point(-1,-1));
		}
		return dst;
	}
	
	public Mat biliteralBlur(Mat src) {
		for(int i = 1; i < MAX_KERNEL_LENGHT; i=i+2) {
			Imgproc.bilateralFilter(src, dst, i, i*2, i/2);
		}
		return dst;
	}
}
