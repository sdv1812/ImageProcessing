package Wrinkle.Detection;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class ImageUtilities {
	private static String resultfile = "sanskar_contrast.jpg";
	
	public static String setContrast(String filename) {
		try {
	         System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	         Mat source = Highgui.imread(filename, 
	         Highgui.CV_LOAD_IMAGE_GRAYSCALE);
	         Mat destination = new Mat(source.rows(),source.cols(),source.type());
	         
	         Imgproc.equalizeHist(source, destination);
	         Highgui.imwrite(resultfile, destination);
	         
	      }catch (Exception e) {
	         System.out.println("error: " + e.getMessage());
	      }
		return resultfile;
	}
	
	public static String setBrightness() {
		return resultfile;
		
	}
	
	public static String setThreshold(String filename) {
		try {
	         System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	         Mat source = Highgui.imread(filename, 
	         Highgui.CV_LOAD_IMAGE_GRAYSCALE);
	         Mat destination = new Mat(source.rows(),source.cols(),source.type());
	         
	         Imgproc.threshold(source,destination,127,255,Imgproc.THRESH_BINARY_INV);
	         Highgui.imwrite("Sanskar_Thresh.jpg", destination);
	         return "Sanskar_Thresh.jpg";
	         
	      }catch (Exception e) {
	         System.out.println("error: " + e.getMessage());
	      }
		return resultfile;
	}
	
	}
