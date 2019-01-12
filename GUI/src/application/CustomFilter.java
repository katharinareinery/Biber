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
		Mat kernel;
		double coef;
		double delta;
		
		Mat dst = new Mat();
	
		public Point getAnchor() {
			return anchor;
		}
		public void setAnchor(Point anchor) {
			this.anchor = anchor;
		}
		public int getDdepth() {
			return ddepth;
		}
		public void setDdepth(int ddepth) {
			this.ddepth = ddepth;
		}
		public Mat getKernel() {
			return kernel;
		}
		public void setKernel(Mat kernel) {
			this.kernel = kernel;
		}
		public double getCoef() {
			return coef;
		}
		public void setCoef(double coef) {
			this.coef = coef;
		}
		public double getDelta() {
			return delta;
		}
		public void setDelta(double delta) {
			this.delta = delta;
		}
		public void setDst(Mat dst) {
			this.dst = dst;
		}
		public CustomFilter(Mat src,int t){
			super();
		}
		public CustomFilter(Mat kernel, double coef,double delta){
			super();
			this.kernel = kernel;
			this.coef = coef;
			this.delta=delta;
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
		public Mat customKernel(Mat src,Mat kernel,double coef,double delta) {
			Scalar scalar = new Scalar(coef);
			Core.multiply(kernel, scalar, kernel);
			Imgproc.filter2D(src, dst, ddepth, kernel, anchor, delta,Core.BORDER_DEFAULT);
			return dst;
		}
		//method to use within timeline q
		public void useFilter() {
			Scalar scalar = new Scalar(coef);
			Core.multiply(kernel, scalar, kernel);
			Imgproc.filter2D(src, dst, ddepth, kernel,anchor,delta,Core.BORDER_DEFAULT);
		}
		

		
		public BufferedImage returnImage() {
			return matToBuffImage(dst);
		}
		
		//overriden toString for debugging purpose
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Custom Filter:\tMat:["+src.rows()+","+src.cols()+":"+src.channels()+"]";
		}
}
