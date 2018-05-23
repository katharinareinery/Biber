package application;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public class Grayscale extends ImageMan{
	private Mat dst;
	
	/*The luminosity method 
	 * averages the values, but it forms a weighted average
	 * to account for human perception.
	 * We are more sensitive to green than other colors, so green is weighted most heavily.
	 * he formula for luminosity is 0.21 R + 0.72 G + 0.07 B.
	 */
	public Mat luminosity(Mat src) {
		Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2RGB);
		dst = new Mat(src.size(),CvType.CV_8UC1);
		double grayValue;
		double[] data;
		for(int i = 0; i<src.rows();i++) {
			for(int j = 0; j <src.cols();j++) {
				data = src.get(i, j);	
				grayValue=(data[0]*0.21)+(data[1]*0.72)+(data[2]*0.07);
				dst.put(i, j, grayValue);	
			}
		}
		return dst;
	}
	
	public Mat bWPixel(int row, int col, Mat mat) {
		double[] data = mat.get(row, col);
		//System.out.println(data.length);
		data[0]=(data[0]*0.21)+(data[1]*0.72)+(data[2]*0.07);
		data[1]=(data[0]*0.21)+(data[1]*0.72)+(data[2]*0.07);
		data[2]=(data[0]*0.21)+(data[1]*0.72)+(data[2]*0.07);
		//System.out.println("row: "+row+"\tcol: "+col);
		mat.put(row, col, data);
		return mat;
	}
	
	
	/*The average method
	 * simply averages the values: (R + G + B) / 3.
	*/
	public Mat average(Mat src) {
		Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2RGB);
		dst = new Mat(src.size(),CvType.CV_8UC1);
		double[] data;
		double grayValue;
		for(int i = 0; i<src.rows(); i++) {
			for(int j = 0; j<src.cols(); j++) {
				data = src.get(i, j);
				grayValue=((data[0]+data[1]+data[2])/3);
				dst.put(i, j, grayValue);
			}
		}
		return dst;
	}
	
	/*The lightness method 
	 * averages the most prominent and least prominent colors:
	 *  (max(R, G, B) + min(R, G, B)) / 2.
	 * 
	*/
	public Mat lightness(Mat src) {
		Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2RGB);
		dst = new Mat(src.size(),CvType.CV_8UC1);
		double[] data;
		double grayValue;
		for(int i = 0; i < src.rows(); i++) {
			for(int j = 0; j < src.cols(); j++) {
				data = src.get(i, j);
				double max,min;
				if(data[0]>data[1] && data[0]>data[2]) {
					max = data[0];
				}else {
					if(data[1]>data[0] && data[1]>data[2]) {
						max = data[1];
					}else {
						max = data[2];
					}
				}
				if(data[0]<data[1] && data[0]<data[2]) {
					min = data[0];
				}else {
					if(data[1]<data[0] && data[1]<data[2]) {
						min = data[1];
					}else {
						min = data[2];
					}
				}
				grayValue = ((max+min)/2);
				dst.put(i, j, grayValue);
			}
		}
		return dst;
	}
}
