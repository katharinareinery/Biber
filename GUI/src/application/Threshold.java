package application;

import java.awt.image.BufferedImage;

//import java.awt.image.BufferedImage;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Thresholding is a method of image segmentation, 
 * in general it is used to create binary images.
 */

public class Threshold extends ImageMan{
	private Mat bwsrc = new Mat();
	// destination Matrix for the picture
	private Mat dst;		
	//source Matrix for init
	private Mat src;
	//threshold value
	private int t = 0;
	/*public Threshold(){
		super();
	}*/
	//constructor
		public Threshold(Mat src,int t){
			super();
			this.src = src;
			this.t = t;
		}
		public Threshold(int t){
			super();
			this.t = t;
		}
		public Threshold() {
			super();
		}
		
		//getter/setter for private vars
		public void setThreshold(int t) {
			this.t = t;
		}
		public int getThreshold() {
			return t;
		}
		public void setSrc(Mat src) {
			this.src = src;
		}
		public Mat getSrc() {
			return src;
		}
		public Mat getDst() {
			return dst;
		}
		//thresholding method
		public Mat binarisieren(int t, Mat src) {
			if(src.channels() > 1) {
				Imgproc.cvtColor(src, bwsrc, Imgproc.COLOR_BGR2GRAY);
				dst = new Mat(bwsrc.size(),CvType.CV_8U);
				for(int i = 0; i<bwsrc.rows();i++) {
					for(int j = 0; j <bwsrc.cols();j++) {
//						System.out.print("row: "+i+"\t");
//						System.out.println("col: "+j);
						if(bwsrc.get(i,j)[0]>=t) {
							dst.put(i, j, 255);
						}else dst.put(i, j, 0);
					}
				}
			}else {
				dst = new Mat(src.size(),CvType.CV_8U);
				for(int i = 0; i<src.rows();i++) {
					for(int j = 0; j <src.cols();j++) {
						if(src.get(i,j)[0]>=t) {
							dst.put(i, j, 255);
						}else dst.put(i, j, 0);
					}
				}
			}
			return dst;
		}
		//method to use within timeline q
		public void useFilter() {
			if(src.channels() > 1) {
				Imgproc.cvtColor(src, bwsrc, Imgproc.COLOR_BGR2GRAY);
				dst = new Mat(bwsrc.size(),CvType.CV_8U);
				for(int i = 0; i<bwsrc.rows();i++) {
					for(int j = 0; j <bwsrc.cols();j++) {
						if(bwsrc.get(i,j)[0]>=t) {
							dst.put(i, j, 255);
						}else dst.put(i, j, 0);
					}
				}
			}else {
				dst = new Mat(src.size(),CvType.CV_8U);
				for(int i = 0; i<src.rows();i++) {
					for(int j = 0; j <src.cols();j++) {
						if(src.get(i,j)[0]>=t) {
							dst.put(i, j, 255);
						}else dst.put(i, j, 0);
					}
				}
			}
		}
		

		
		public BufferedImage returnImage() {
			return matToBuffImage(dst);
		}
		
		//overriden toString for debugging purpose
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Threshold:"+t+"\tMat:["+src.rows()+","+src.cols()+":"+src.channels()+"]";
		}
	}