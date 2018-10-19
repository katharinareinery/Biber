package application;

//import java.awt.image.BufferedImage;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Thresholding is a method of image segmentation, 
 * in general it is used to create binary images.
 */

public class Threshold extends ImageMan{
	Mat bwsrc = new Mat();
	// Matrix for the picture
	Mat dst;		
	
	/*public Threshold(){
		super();
	}*/
	
	public Mat binarisieren(int t, Mat src) {
		Imgproc.cvtColor(src, bwsrc, Imgproc.COLOR_BGR2GRAY);
		dst = new Mat(bwsrc.size(),CvType.CV_8U);
		for(int i = 0; i<bwsrc.rows();i++) {
			for(int j = 0; j <bwsrc.cols();j++) {
//				System.out.print("row: "+i+"\t");
//				System.out.println("col: "+j);
				if(bwsrc.get(i,j)[0]>=t) {
					dst.put(i, j, 255);
				}else dst.put(i, j, 0);
			}
		}
		return dst;
	}
}
