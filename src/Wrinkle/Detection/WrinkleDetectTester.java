package Wrinkle.Detection;

import org.opencv.core.Core;

public class WrinkleDetectTester {

	public static void main(String[] args) {
		WrinkleDetection wd = new WrinkleDetection();
		BlobDetection bd = new BlobDetection();
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        String resultfile = "wrinkle_guo (1).bmp";
		wd.detectEdgeUsingHoughLines(resultfile);
		bd.detectBlob("skin_pore.jpg");
		
	}
}
