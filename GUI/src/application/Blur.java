package application;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Blur{
	int DELAY_CAPTION = 1500;
	int DELAY_BLUR = 100;
	int MAX_KERNEL_LENGHT = 31;
	Mat src = new Mat();
	Mat dst = new Mat();
	
	public Mat imageMan(String filename) {
		src = Imgcodecs.imread(filename, Imgcodecs.IMREAD_COLOR);
		if(src.empty()) {
			System.out.println("Error opening image");
			System.out.println("Usage: filechooserPath");
			System.exit(-1);
		}
	
		dst = src.clone();
		
			for(int i = 1; i < MAX_KERNEL_LENGHT; i = i + 2) {
				Imgproc.GaussianBlur(src, dst, new Size(i , i), 0, 0);
				displayDst(DELAY_BLUR);
			}
		return dst;
	}


	private boolean displayCaption(String caption) {
		dst = Mat.zeros(src.size(), src.type());
		//Imgproc.putText(dst, caption, new Point(src.cols()/4, src.rows()/2, Core.FONT_HERSHEY_COMPLEX, 1, new Scalar(255,255,255));
		return false;
	}
	
	private void displayDst(int delay) {
		// TODO Auto-generated method stub
		
	}
	
}
