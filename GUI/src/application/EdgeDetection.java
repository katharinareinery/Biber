package application;

import java.awt.image.BufferedImage;

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
	private String fType = "";
	
	public EdgeDetection(String fType, Mat src) {
		super();
		this.fType=fType;
		this.src=src.clone();
	}
	
	public Mat getSrc() {
		return src;
	}

	public void setSrc(Mat src) {
		this.src = src;
	}

	public Mat getDst() {
		return dst;
	}

	public void setDst(Mat dst) {
		this.dst = dst;
	}

	public String getfType() {
		return fType;
	}

	public void setfType(String fType) {
		this.fType = fType;
	}

	public EdgeDetection() {
		super();
	}
	
	public BufferedImage returnImage() {
		return matToBuffImage(dst);
	}

	public Mat robertCross(Mat src) {
		this.src=src.clone();
		dst = new Mat(this.src.size(),this.src.type());
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
		
		dst = doIt(robertX, robertY);
		
		//dst wird aus dstX und dstY berechnet siehe https://homepages.inf.ed.ac.uk/rbf/HIPR2/roberts.htm
		return dst;
	}
	
	public Mat sobel(Mat src) {
		this.src=src.clone();
		dst = new Mat(this.src.size(),this.src.type());
		Mat sobelX = new Mat(3, 3, CvType.CV_32F);
		Mat sobelY = new Mat(3, 3, CvType.CV_32F);
		sobelX.put(0, 0, -3.0);
		sobelX.put(0, 1, 0.0);
		sobelX.put(0, 2, 3.0);
		
		sobelX.put(1, 0, -10.0);
		sobelX.put(1, 1, 0.0);
		sobelX.put(1, 2, 10.0);
		
		sobelX.put(2, 0, -3.0);
		sobelX.put(2, 1, 0.0);
		sobelX.put(2, 2, 3.0);
		
		sobelY.put(0, 0, -3.0);
		sobelY.put(0, 1, -10.0);
		sobelY.put(0, 2, -3.0);
		
		sobelY.put(1, 0, 0.0);
		sobelY.put(1, 1, 0.0);
		sobelY.put(1, 2, 0.0);
		
		sobelY.put(2, 0, 3.0);
		sobelY.put(2, 1, 10.0);
		sobelY.put(2, 2, 3.0);
		
		dst = doIt(sobelX, sobelY);

		//dst wird aus dstX und dstY berechnet siehe https://homepages.inf.ed.ac.uk/rbf/HIPR2/roberts.htm
		return dst;
	}
	
	public Mat prewitt(Mat src) {
		this.src=src.clone();
		dst = new Mat(this.src.size(),this.src.type());
		Mat prewittX = new Mat(3, 3, CvType.CV_32F);
		Mat prewittY = new Mat(3, 3, CvType.CV_32F);
		prewittX.put(0, 0, -1.0);
		prewittX.put(0, 1, 0.0);
		prewittX.put(0, 2, 1.0);
		
		prewittX.put(1, 0, -1.0);
		prewittX.put(1, 1, 0.0);
		prewittX.put(1, 2, 1.0);
		
		prewittX.put(2, 0, -1.0);
		prewittX.put(2, 1, 0.0);
		prewittX.put(2, 2, 1.0);
		
		prewittY.put(0, 0, -1.0);
		prewittY.put(0, 1, -1.0);
		prewittY.put(0, 2, -1.0);
		
		prewittY.put(1, 0, 0.0);
		prewittY.put(1, 1, 0.0);
		prewittY.put(1, 2, 0.0);
		
		prewittY.put(2, 0, 1.0);
		prewittY.put(2, 1, 1.0);
		prewittY.put(2, 2, 1.0);
		dst = doIt(prewittX, prewittY);
		
		return dst;
	}
	
	private Mat doIt(Mat filterX, Mat filterY) {
		Mat dstX = new Mat();
		Mat dstY = new Mat();
		double[] dataX;
		double[] dataY;
		double newValue;
		double[] newValueArray = new double[3];
		Imgproc.filter2D(this.src, dstX, ddepth, filterX, anchor, 0, Core.BORDER_DEFAULT);
		Imgproc.filter2D(this.src, dstY, ddepth, filterY, anchor, 0, Core.BORDER_DEFAULT);
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
		return dst;
	}
	
	public void useFilter() {
		if(fType.equals("Roberts Cross")){
			dst = robertCross(src);
		}else if(fType.equals("Sobel")) {
			dst = sobel(src);
		}else if(fType.equals("Prewitt")) {
			dst = prewitt(src);
		}
	}
	
}
