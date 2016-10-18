package Wrinkle.Detection;


import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


public class CircleDetection {
	private Mat sourceImage;
	private Mat destinationImage;
	private List<MatOfPoint> contours;
	private Mat threshImage;


	public CircleDetection() {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		contours = new ArrayList<MatOfPoint>();
		threshImage = new Mat();

	}


	public void detectCircleUsingHough() {

		sourceImage =  Highgui.imread("download.png",
				Highgui.CV_LOAD_IMAGE_COLOR);
		destinationImage = new Mat(sourceImage.rows(),sourceImage.cols(),sourceImage.type());
		Imgproc.cvtColor(sourceImage, destinationImage, Imgproc.COLOR_RGB2GRAY);
		//Highgui.imwrite("test_hough_gray.jpg", destinationImage);
		//		Imgproc.GaussianBlur(destinationImage, destinationImage,new Size(7,7), 0);
		//		Highgui.imwrite("sanskar_blur.jpg", destinationImage);
		//Imgproc.Canny(destinationImage, destinationImage, 50, 50);
		//Highgui.imwrite("sanskar_edge.jpg", destinationImage);
		//	Vector circles;
		Mat Himage = new Mat();
		Imgproc.HoughCircles(destinationImage,Himage , Imgproc.CV_HOUGH_GRADIENT, 1,destinationImage.rows()/8, 200, 50, 0, 0);
		for (int i = 0; i < Himage.cols(); i++) {
			double[] vCircle = Himage.get(0, i);

			Point pt = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
			int radius = (int)Math.round(vCircle[2]);


			Core.circle(sourceImage , pt, radius, new Scalar(255, 0, 0), 2);
		}

		Highgui.imwrite("test_hough.jpg", sourceImage);
	}
	public void detectCircleUsingContour() {
		sourceImage =  Highgui.imread("download.png",
				Highgui.CV_LOAD_IMAGE_COLOR);
		destinationImage = new Mat(sourceImage.rows(),sourceImage.cols(),sourceImage.type());
		Imgproc.cvtColor(sourceImage, destinationImage, Imgproc.COLOR_RGB2GRAY);
		Core.inRange(destinationImage, new Scalar(50, 100, 50), new Scalar(95, 255, 255), threshImage);
		Imgproc.blur(threshImage, threshImage, new Size(10, 10));
		Imgproc.threshold(threshImage, threshImage, 150, 255, Imgproc.THRESH_BINARY);

		System.out.println(threshImage.rows());
		Imgproc.findContours(threshImage, contours,  new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		double maxArea = 0;
		float[] radius = new float[1];
		Point center = new Point();
		for (int i = 0; i < contours.size(); i++) {
			MatOfPoint c = contours.get(i);
			if (Imgproc.contourArea(c) > maxArea) {
				MatOfPoint2f c2f = new MatOfPoint2f(c.toArray());
				Imgproc.minEnclosingCircle(c2f, center, radius);
			}
		}
		Core.circle(sourceImage, center, (int)radius[0], new Scalar(255, 0, 0), 2);

		Highgui.imwrite("test_contour.jpg", sourceImage);
	}

}

