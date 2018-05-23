package application;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Grayscale extends ImageMan{
	private Mat dst;
	
	public Mat luminosity(Mat src) {
		Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2RGB);
		dst = new Mat(src.size(),CvType.CV_8U);
		
		/*The lightness method 
		 * averages the most prominent and least prominent colors:
		 *  (max(R, G, B) + min(R, G, B)) / 2.
		 * 
		*/
		
		/*The luminosity method 
		 * averages the values, but it forms a weighted average
		 * to account for human perception.
		 * We’re more sensitive to green than other colors, so green is weighted most heavily.
		 * he formula for luminosity is 0.21 R + 0.72 G + 0.07 B.
		 */
		double[] data;
		for(int i = 0; i<src.rows();i++) {
			for(int j = 0; j <src.cols();j++) {
				data = src.get(i, j);	
				//luminosity
				data[0]=(data[0]*0.21)+(data[1]*0.72)+(data[2]*0.07);
				//data[1]=(data[0]*0.21)+(data[1]*0.72)+(data[2]*0.07);
				//data[2]=(data[0]*0.21)+(data[1]*0.72)+(data[2]*0.07);
				dst.put(i, j, data[0]);
				//return dst;				
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
		dst = new Mat(src.size(),CvType.CV_8U);
		double[] data;
		for(int i = 0; i<src.rows(); i++) {
			for(int j = 0; j<src.cols(); j++) {
				data = src.get(i, j);
				data[0]=((data[0]+data[1]+data[2])/3);
				dst.put(i, j, data[0]);
			}
		}
		return dst;
	}
}
