package application;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;


//https://homepages.inf.ed.ac.uk/rbf/HIPR2/edgdetct.htm
public class EdgeDetection extends ImageMan {

	private Mat src;
	private Mat dst;
	private Point anchor = new Point(-1,-1);
	private int ddepth = -1; 
	
	public Mat robertCross(Mat src) {
		this.src=src.clone();
		dst = new Mat(this.src.size(),this.src.type());
		Mat dstX = new Mat();
		Mat dstY = new Mat();
		double[] dataX;
		double[] dataY;
		double newValue;
		double[] newValueArray = new double[3];
		Mat robertX = new Mat(2,2,CvType.CV_32F);
		Mat robertY = new Mat(2, 2, CvType.CV_32F);
		robertX.put(0, 0, 1.0);
		robertX.put(0, 1, 0.0);
		robertX.put(1, 0, 0.0);
		robertX.put(1, 1, -1.0);
		robertY.put(0, 0, 0.0);
		robertY.put(0, 1, 1.0);
		robertY.put(1, 0, -1.0);
		robertY.put(1, 1, 0.0);
		Imgproc.filter2D(this.src, dstX, ddepth, robertX, anchor, 0, Core.BORDER_DEFAULT);
		Imgproc.filter2D(this.src, dstY, ddepth, robertY, anchor, 0, Core.BORDER_DEFAULT);
		if(this.src.channels()==3) {
			for(int i = 0; i < this.src.rows();i++) {
				for(int j = 0; j < this.src.cols();j++ ) {
					dataX=dstX.get(i, j);
					dataY=dstY.get(i, j);
					newValueArray[0] = Math.abs(dataX[0])+Math.abs(dataY[0]);
					newValueArray[1] = Math.abs(dataX[1])+Math.abs(dataY[1]);
					newValueArray[2] = Math.abs(dataX[2])+Math.abs(dataY[2]);
					dst.put(i, j, newValueArray);
				}
			}
		}else if(this.src.channels()==1) {
			for(int i = 0; i < this.src.rows();i++) {
				for(int j = 0; j < this.src.cols();j++ ) {
					dataX=dstX.get(i, j);
					dataY=dstY.get(i, j);
					newValue = Math.abs(dataX[0])+Math.abs(dataY[0]);
					dst.put(i, j, newValue);
				}
			}
		}
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
