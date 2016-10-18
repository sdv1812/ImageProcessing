package Wrinkle.Detection;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


public class BlobDetection {

	public BlobDetection() {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

	}


	public void detectBlob(String filename) {

		Mat orig = Highgui.imread(filename,Highgui.IMREAD_GRAYSCALE);
		Mat orig_copy= orig.clone();
		Imgproc.GaussianBlur(orig, orig,new Size(45,45), 0);
		Mat MatOut= new Mat();

		FeatureDetector blobDetector;

		blobDetector = FeatureDetector.create(FeatureDetector.SIFT);

		MatOfKeyPoint keypoints1 = new MatOfKeyPoint();

		blobDetector.detect(orig,keypoints1);

		org.opencv.core.Scalar cores = new org.opencv.core.Scalar(0,0,255);
//		List<KeyPoint> list = keypoints1.toList();
//		
//		for(KeyPoint point: list){
//			//System.out.println("hsdgkfjdsghfsadf" +point);
//			Point xy = point.pt;
//			//double[] intensity  = orig.get(xy.x, xy.y);
//			System.out.println("intensity value of 1st point>>>" +intensity[0]);
//			System.out.println("X coordinate of Key Point: " +xy.x);
//			System.out.println("Y coordinate of Key Point: " +xy.y);
//		}
			


		

		org.opencv.features2d.Features2d.drawKeypoints(orig_copy,keypoints1,MatOut,cores,2);

		Highgui.imwrite("PhotoOut.jpg", MatOut);
	}
}
