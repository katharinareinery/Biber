package application;

import java.awt.image.BufferedImage;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import application.Threshold;
import javafx.geometry.Point2D;
/*
*Own FilterObject
*@link https://docs.opencv.org/2.4/doc/tutorials/imgproc/imgtrans/filter_2d/filter_2d.html
*/
public class CustomFilter extends ImageMan{
		Point anchor = new Point(-1,-1);
		int ddepth = -1;
		
		Mat dst = new Mat();
	
		public CustomFilter(Mat src,int t){
			super();
		}
		public CustomFilter(int t){
			super();
		}
		public CustomFilter() {
			super();
		}
		//gett/setter for priv vars
		public void setSrc(Mat src) {
			this.src = src;
		}
		public Mat getSrc() {
			return src;
		}
		public Mat getDst() {
			return dst;
		}
		public Mat customKernel(Mat src,Mat kernel,double coef) {
			Scalar scalar = new Scalar(coef);
			Core.multiply(kernel, scalar, kernel);
			Imgproc.filter2D(src, dst, ddepth, kernel, anchor, 0,Core.BORDER_DEFAULT);
			return dst;
		}
		//method to use within timeline q
		public void useFilter() {

		}
		

		
		public BufferedImage returnImage() {
			return matToBuffImage(dst);
		}
		
		//overriden toString for debugging purpose
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "OwnFilter:\tMat:["+src.rows()+","+src.cols()+":"+src.channels()+"]";
		}
}
