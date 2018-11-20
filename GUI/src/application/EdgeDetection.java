package application;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


//https://homepages.inf.ed.ac.uk/rbf/HIPR2/edgdetct.htm
public class EdgeDetection extends ImageMan {

	private Mat src;
	private Mat dst;
	
	public Mat robertCross(Mat src) {
		this.src=src.clone();
		Mat dstX = new Mat(this.src.size(),this.src.type());
		Mat robertX = new Mat(2, 2, this.src.type());
		robertX.put(0, 0, 1);
		robertX.put(0, 1, 0);
		robertX.put(1, 0, 0);
		robertX.put(1, 1, -1);
		Mat dstY = new Mat(this.src.size(),this.src.type());
		Mat robertY = new Mat(2, 2, this.src.type());
		robertY.put(0, 0, 0);
		robertY.put(0, 1, 1);
		robertY.put(1, 0, -1);
		robertY.put(1, 1, 0);
		Imgproc.filter2D(this.src, dstX,-1, robertX);
		Imgproc.filter2D(this.src, dstY, -1, robertY);
		//dst wird aus dstX und dstY berechnet siehe https://homepages.inf.ed.ac.uk/rbf/HIPR2/roberts.htm
		return dst;
	}
	
	public Mat sobel() {
		return dst;
	}
	
	public Mat prewitt() {
		return dst;
	}
	
}
