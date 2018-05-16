package application;

import java.awt.image.BufferedImage;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Threshold extends ImageMan{
	Mat bwsrc = new Mat();
	Mat dst;		//Matrix fürs Bild
	
	/*public Threshold(){
		super();
	}*/
	public Mat binarisieren(int t,Mat src) {
		int maxval = 255;
		Imgproc.cvtColor(src, bwsrc, Imgproc.COLOR_BGR2GRAY);
		dst = new Mat(bwsrc.size(),CvType.CV_8U);
		for(int i = 0; i<bwsrc.rows();i++) {
			for(int j = 0; j <bwsrc.cols();j++) {
//				System.out.print("row: "+i+"\t");
//				System.out.println("col: "+j);
				if(bwsrc.get(i,j)[0]>=t) {
					dst.put(i, j, maxval);
				}else dst.put(i, j, 0);
			}
		}
		return dst;
		
	}
}
