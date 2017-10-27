import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.FrequencyFilter;

import java.io.IOException;

/**
 * Created by Charlie on 24/10/2017.
 */
public class MainApplication {

    /**
     * Application entry point, change here the functions to call to apply filters.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        //CustomImage originalImage = new CustomImage("C:\\Users\\Charlie\\IdeaProjects\\ImageAnalysisCW\\res\\PenguinOriginal.bmp",
        //      "C:\\Users\\Charlie\\IdeaProjects\\ImageAnalysisCW\\res\\"); //Load an Image.

        CustomImage noisyImage = new CustomImage("C:\\Users\\Charlie\\IdeaProjects\\ImageAnalysisCW\\res\\PenguinNoise.bmp",
                "C:\\Users\\Charlie\\IdeaProjects\\ImageAnalysisCW\\res\\"); //Load an Image.

        FrequencyDomain.applyFourierFilter(noisyImage, 0, 40);
        SpatialDomain.ApplyMedianFilter(noisyImage);

        //calculateRMSE(originalImage.getWidth(), originalImage.getHeight(), originalImage.getPixelArray(), noisyImage.getModifiedPixelArray());

        System.out.println("All tasks completed!");
    }

    /**
     * Calcuates the root mean square error between two images
     * @param imgWidth Width of the image
     * @param imgHeight Height of the image
     * @param originalImage Original Image Greyscale pixel array.
     * @param noisyImage Noisy Image greyscale Pixel Array
     */
    public static void calculateRMSE(int imgWidth, int imgHeight, int[][] originalImage, int[][] noisyImage)
    {
        /* Implments the mathmatical formula: http://math.tutorvista.com/statistics/mean-squared-error.html */
        System.out.println("Calculating the root mean square error....");

        int squareSumDifference = 0;

        for (int y = 0; y < imgHeight; y++) //For each pixel in the Image (Height then Row)
            for (int x = 0; x < imgWidth; x++)
                squareSumDifference += (noisyImage[x][y] - originalImage[x][y]) ^2; //Get square sum of error between two pixel points.


        System.out.println("Square Sum Difference equals:" + squareSumDifference);

        double rmse = Math.round((((double)1/(imgHeight * imgWidth)) * squareSumDifference) * 100); // Calculate (1/w*h) * squareSumDifference
        rmse = (rmse / 100); //Rounding it into two decimal places.

        System.out.println("The RMSE is equal to:" + rmse + "%");
    }


}
