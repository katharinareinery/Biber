package application;

import java.awt.image.BufferedImage;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import application.Threshold;
/*
*Zhang Suen thinning algorithm
*@link https://www.programcreek.com/java-api-examples/index.php?source_dir=MIME-master/app/src/main/java/de/lmu/ifi/medien/mime/OpenCVUtil.java
*/
public class ZhangSuen extends ImageMan{
	private Mat bwsrc = new Mat();
	// destination Matrix for the picture
	private Mat dst;		
	//source Matrix for init
	private Mat src;
	//threshold value
	private int t = 0;
	/*public Threshold(){
		super();
	}*/
	//constructor
		public ZhangSuen(Mat src,int t){
			super();
			this.src = src;
			this.t = t;
		}
		public ZhangSuen(int t){
			super();
			this.t = t;
		}
		public ZhangSuen() {
			super();
		}
		//gett/setter for priv vars
		public void setSrc(Mat src) {
			this.src = src;
		}
		public Mat getSrc() {
			return src;
		}
		public Mat getDst() {
			return dst;
		}
		//zhang suen thinning algorith
		public Mat zhangSuen(Mat src,int t) {
			dst = new Mat(src.size(),CvType.CV_8UC1);
			src = Threshold.binarisierenInvers(t, src);
			Mat prev = Mat.zeros(src.size(), CvType.CV_8UC1);
			Mat diff = new Mat();
			do {
				zhangSuenIterations(src,0);
				zhangSuenIterations(src, 1);
				Core.absdiff(src, prev, diff);
				src.copyTo(prev);
			}
			while(Core.countNonZero(diff)>0);
			Core.bitwise_not(prev,dst);
			return dst;
		}
		
		private void zhangSuenIterations(Mat img,int step) {
			//get image pixels
			byte[] buffer = new byte[(int) img.total() * img.channels()];
			img.get(0, 0,buffer);
			
			byte[] markerBuffer = new byte[buffer.length];
			
			int rows = img.rows();
			int cols = img.cols();
			
			//process all pixels
			for(int i = 1;i<rows-1;++i) {
				for(int j=1;j<cols-1;++j) {
					//precalculate offsets
					int prev = cols*(i-1)+j;
					int cur = cols*i+j;
					int next = cols*(i+1)+j;
					
					//getting 8neighborhood of current pixel(cur pixel = p1(center),p2 north, counting clockwise)
					byte p2 = buffer[prev];
					byte p3 = buffer[prev+1];
					byte p4 = buffer[cur+1];
					byte p5 = buffer[next+1];
					byte p6 = buffer[next];
					byte p7 = buffer[next-1];
					byte p8 = buffer[cur-1];
					byte p9 = buffer[prev-1];
					
					int a = 0;
					if(p2==0 && p3==-1) {
						++a;
					}
					if(p3==0 && p4==-1) {
						++a;
					}
					if(p4==0 && p5==-1) {
						++a;
					}
					if(p5==0 && p6==-1) {
						++a;
					}
					if(p6==0 && p7==-1) {
						++a;
					}
					if(p7==0 && p8==-1) {
						++a;
					}
					if(p8==0 && p9==-1) {
						++a;
					}
					if(p9==0 && p2==-1) {
						++a;
					}
					
					//Number of filled pixels in the 8neighborhood
					int b = Math.abs(p2+p3+p4+p5+p6+p7+p8+p9);
					
					//Condition 3 & 4
					int c3 = step == 0 ? (p2*p4*p6) : (p2*p4*p8);
					int c4 = step == 0 ? (p4*p6*p8) : (p2*p6*p8);
					
					//Determine if the current pixel has to be eliminated; 0 = delete, -1 = keep
					markerBuffer[cur] = (byte) ((a==1 && b>=2 && b<=6 &&c3==0&&c4==0)?0:-1);
				}
			}
			for(int i = 0;i<buffer.length;++i) {
				buffer[i] = (byte)((buffer[i] == -1 && markerBuffer[i] == -1) ? -1 : 0);
			}
			img.put(0, 0, buffer);
		}
		
		//method to use within timeline q
		public void useFilter() {
			dst = new Mat(src.size(),CvType.CV_8UC1);
			src = Threshold.binarisierenInvers(t, src);
			Mat prev = Mat.zeros(src.size(), CvType.CV_8UC1);
			Mat diff = new Mat();
			do {
				zhangSuenIterations(src,0);
				zhangSuenIterations(src, 1);
				Core.absdiff(src, prev, diff);
				src.copyTo(prev);
			}
			while(Core.countNonZero(diff)>0);
			Core.bitwise_not(prev,dst);
		}
		

		
		public BufferedImage returnImage() {
			return matToBuffImage(dst);
		}
		
		//overriden toString for debugging purpose
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Zhang Suen:"+t+"\tMat:["+src.rows()+","+src.cols()+":"+src.channels()+"]";
		}
}
