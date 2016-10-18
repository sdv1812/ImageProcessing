package Wrinkle.Detection;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


public class WrinkleDetection {
	private Mat sourceImage;
	private Mat destinationImage;
	private Mat thresh;


	public WrinkleDetection() {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

	}


	public String detectEdgeUsingHoughLines(String filename) {


		preprocess(filename);
		Imgproc.Canny(destinationImage, destinationImage, 20, 20);
		Highgui.imwrite("sanskar_cannyedge.jpg", destinationImage);
//		List<MatOfPoint> contours = new ArrayList<> ();
//		Mat image32S = new Mat();
//
//		destinationImage.convertTo(image32S, CvType.CV_32SC1);
//
//		
//		Imgproc.findContours(image32S, contours, new Mat(), Imgproc.RETR_FLOODFILL, Imgproc.CHAIN_APPROX_SIMPLE);
//
//		// Draw all the contours such that they are filled in.
//		Mat contourImg = new Mat(image32S.size(), image32S.type());
//		for (int i = 0; i < contours.size(); i++) {
//		    Imgproc.drawContours(contourImg, contours, i, new Scalar(255, 255, 255), -1);
//		}
//
//		Highgui.imwrite("debug_image.jpg", contourImg); 
		Mat Himage = new Mat();
		Imgproc.HoughLinesP(destinationImage, Himage, 1, Math.PI / 180, 50, 50, 10);
		System.out.println(Himage.size());
		System.out.println();

		for(int i = 0; i < Himage.cols(); i++) {
			double[] val = Himage.get(0, i);
			System.out.println(val.length);
			System.out.println("val[0]= " + val[0]+ ", val[1] = "+ val[1]+ ", val[2]= "+ val[2]+ ", val[3] = "+ val[3] );
			Core.line(thresh, new Point(val[0], val[1]), new Point(val[2], val[3]), new Scalar(0, 0, 255), 2);
		}
		Highgui.imwrite("sanskar_hough.jpg", destinationImage);
		Highgui.imwrite("Himage.jpg", Himage);
		
		return "sanskar_houghline_edge.jpg";
	}

	public void preprocess(String filename) {
		sourceImage =  Highgui.imread(filename,
				Highgui.CV_LOAD_IMAGE_COLOR);
//		Mat cleanImage = sourceImage.clone();
		
//		for (int i = 1; i<sourceImage.rows(); i++) {
//			for (int j = 1; j<sourceImage.cols(); j++) {
//				//Vec3b sourceColor = sourceImage.at<Vec3b>(Point(i,j));
//				double[] val = sourceImage.get(i, j);
//				if (val[0]<130&& val[1]<130 && val[2]<130){
//					double[] previousVal = sourceImage.get(i, j-1);
//					val[0] = previousVal[0];
//					val[1] = previousVal[1];
//					val[2] = previousVal[2];
//					sourceImage.put(i, j, val);			
//				}
//			}
//		}
		
			Highgui.imwrite("clearImage.jpg", sourceImage);
		
		String contrastedImage = ImageUtilities.setContrast("clearImage.jpg");
		Mat furtherProcessing = Highgui.imread(contrastedImage,
				Highgui.CV_LOAD_IMAGE_COLOR);
		destinationImage = new Mat(furtherProcessing.rows(), furtherProcessing.cols(), furtherProcessing.type());
		thresh = new Mat(furtherProcessing.rows(), furtherProcessing.cols(), furtherProcessing.type());
		Imgproc.cvtColor(furtherProcessing, destinationImage, Imgproc.COLOR_RGB2GRAY);

		Highgui.imwrite("Wrinkle_gray.jpg", destinationImage);
		Imgproc.GaussianBlur(destinationImage, destinationImage,new Size(31,31), 0);
		Highgui.imwrite("Wrinkle_Blur.jpg", destinationImage);
		Imgproc.adaptiveThreshold(destinationImage, thresh, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 99, 10);
		Highgui.imwrite("Threshold.jpg", thresh);

}
}
