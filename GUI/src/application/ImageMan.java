package application;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class ImageMan {
	Mat src;
	Mat dst;
	
	public BufferedImage matToBuffImage(Mat m) {
		 int type = BufferedImage.TYPE_BYTE_GRAY;
         if ( m.channels() > 1 ) {
             type = BufferedImage.TYPE_3BYTE_BGR;
         }
         int bufferSize = m.channels()*m.cols()*m.rows();
         byte [] b = new byte[bufferSize];
         m.get(0,0,b); // get all the pixels
         BufferedImage img = new BufferedImage(m.cols(),m.rows(), type);
         final byte[] targetPixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
         System.arraycopy(b, 0, targetPixels, 0, b.length);  
         return img;
	}
	
	public Mat buffImageToMat(BufferedImage img) {
		byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
		Mat m = new Mat(img.getHeight(),img.getWidth(),CvType.CV_8UC3);
		m.put(0, 0, pixels);
		return m;
	}
	public void useFilter() {	
	}
	
	public void setSrc(Mat src) {
		this.src=src;
	}
	
	public Mat getDst() {
		return dst;
	}
	
	public BufferedImage returnImage() {
		return matToBuffImage(dst);
	}
}

