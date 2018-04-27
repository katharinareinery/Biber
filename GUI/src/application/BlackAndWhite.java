package application;
 import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

public class BlackAndWhite implements ImageManipulation{
	private Mat src,dst;
	
	public Mat imageMan(String filepath) {
		src = Imgcodecs.imread(filepath, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
		if(src.empty()) {
			System.out.println("Error opening image");
			System.out.println("Usage: filechooserPath");
			System.exit(-1);
		}
	
		dst = src.clone();
		return dst;
	}
}
