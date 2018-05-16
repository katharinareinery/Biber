package application;

import java.awt.image.BufferedImage;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Threshold extends ImageMan{
	int t = 0; 					// Threshold
	Mat bwsrc = new Mat();
	Mat dst = new Mat();		//Matrix fürs Bild
	
	/*public Threshold(){
		super();
	}*/
	public Mat binarisieren(int t,Mat src) {
		this.t = t;
		int maxval = 255;
		Imgproc.cvtColor(src, bwsrc, Imgproc.COLOR_BGR2GRAY);
		for(int i = 0; i<bwsrc.rows();i++) {
			for(int j = 0; j <bwsrc.cols();j++) {
				if(bwsrc.get(i,j)[0]>=t) {
					dst.put(i, j, maxval);
				}else dst.put(i, j, 0);
			}
		}
		return dst;
		
	}
}
