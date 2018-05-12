package application;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class BlackAndWhite extends ImageMan{
	private Mat dst = new Mat();
	
	public Mat imageMan(Mat src) {
		Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2RGB);
		
		
		/*The lightness method 
		 * averages the most prominent and least prominent colors:
		 *  (max(R, G, B) + min(R, G, B)) / 2.
		 * 
		*/
		
		/*The average method
		 * simply averages the values: (R + G + B) / 3.
		*/
		
		/*The luminosity method 
		 * averages the values, but it forms a weighted average
		 * to account for human perception.
		 * We’re more sensitive to green than other colors, so green is weighted most heavily.
		 * he formula for luminosity is 0.21 R + 0.72 G + 0.07 B.
		 */
		double[] data;
		for(int i = 0; i<dst.rows();i++) {
			for(int j = 0; j <dst.cols();j++) {
				//System.out.print("row: "+i+"\t");
				//System.out.println("col: "+j);
				data = dst.get(i, j);
				System.out.println("data[0]: "+data[0]);
				//System.out.println("data[1]: "+data[1]);
				//System.out.println("data[2]: "+data[2]);
				
				//luminosity
				data[0]=(data[0]*0.21)+(data[1]*0.72)+(data[2]*0.07);
				data[1]=(data[0]*0.21)+(data[1]*0.72)+(data[2]*0.07);
				data[2]=(data[0]*0.21)+(data[1]*0.72)+(data[2]*0.07);

				dst.put(i, j, data);
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
}
