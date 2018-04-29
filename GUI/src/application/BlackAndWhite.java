package application;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class BlackAndWhite implements ImageManipulation{
	private Mat src = new Mat();
	private Mat dst = new Mat();
	
	public Mat imageMan(String filepath) {
		//lädt das Bild mit RGB
		src = Imgcodecs.imread(filepath, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		if(src.empty()) {
			System.out.println("Error opening image");
			System.out.println("Usage: filechooserPath");
			System.exit(-1);
		}
		//Umwandeln 
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
			
			for(int j = 0; j <dst.cols();i++) {
				data = dst.get(i, j);
				//luminosity
				data[0]=data[0]*0.21;
				data[1]=data[1]*0.72;
				data[2]=data[2]*0.07;
				dst.put(i, j, data);
				
			}
		}
		
		System.out.println(dst.channels());
	
		return dst;
	}
}
