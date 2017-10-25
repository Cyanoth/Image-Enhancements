import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class SpatialDomain {

    /**
     * Neighbourhood fixed radius of 1, calculates the mean unweighted average of a pixel.
     * @param img Custom Bitmap Image Object
     * @throws IOException
     */
    public static void ApplyUnweightedMeanAverage(CustomImage img) throws IOException {
        System.out.println("Processing: Apply Unweighted Mean Average");
        //iY = Image Y, iX = Image X, bY = Neighbour-Box Y, bX = Neighbour-Box X.
        for (int iY = 0; iY < img.getHeight(); iY++) //For each pixel in the Image (Height then Row)
            for (int iX = 0; iX < img.getWidth(); iX++)
            {
                int pixelSum = 0;
                int neighbourCount= 0; //Keep sum and count the neighbours

                //Use Math Max/Min functions to ensure it doesn't no out of bounds...
                for(int bY = Math.max(0, iY - 1); bY < Math.min(img.getHeight(), iY + 2); bY++) //might be iY+1+1
                    for(int bX = Math.max(0, iX -1); bX < Math.min(img.getWidth(), + iX + 2); bX++)
                    {
                        pixelSum += img.getPixelGrayscaleVal(bX, bY, img.getPixelArray());
                        neighbourCount++;
                    }

                img.setModifyPixel(iX, iY, Math.round(pixelSum / neighbourCount)); //Set modified Pixel to the mean value.
            }

        //img.outToConsole(img.getModifiedPixelArray());
        img.outputModifiedImage();
        System.out.println("Completed: Apply Unweighted Mean Average");
    }

    /**
     * Neighbourhood fixed radius of 1, calculates the mean (fixed) weighted average of a pixel.
     * @param img Custom Bitmap Image Object
     * @throws IOException
     */
    public static void ApplyWeightedMeanAverage(CustomImage img) {
        System.out.println("Processing: Apply Weighted-Mean Filter");
        int[] weights = { 1, 2, 1, 2, 4, 2, 1, 2, 1 }; //Weights going from top-left to bottom-right (see documentation)

        //iY = Image Y, iX = Image X, bY = Neighbour-Box Y, bX = Neighbour-Box X.
        for (int iY = 0; iY < img.getHeight(); iY++) //For each pixel in the Image (Height then Row)
            for (int iX = 0; iX < img.getWidth(); iX++)
             {
                int pixelSum = 0;
                int totalCount = 0;
                int weightCounter = 0;

                //Use Math Max/Min functions to ensure it doesn't no out of bounds...
                for(int bY = Math.max(0, iY - 1); bY < Math.min(img.getHeight(), iY + 2); bY++) //might be iY+1+1
                    for(int bX = Math.max(0, iX -1); bX < Math.min(img.getWidth(), + iX + 2); bX++)
                    {
                        pixelSum += img.getPixelGrayscaleVal(bX, bY, img.getPixelArray()) * weights[weightCounter];
                        totalCount += weights[weightCounter];
                        weightCounter++;
                    }
                pixelSum = Math.min(255, Math.round(pixelSum / totalCount)); //Set modified Pixel to the mean value.

                img.setModifyPixel(iX, iY, pixelSum);
            }

        img.outputModifiedImage();
        System.out.println("Completed: Weighted-Mean Filter");
    }

    /**
     * Neighbourhood fixed radius of 1, calculates the median of a pixel.
     * @param img Custom Bitmap Image Object
     */
    public static void ApplyMedianFilter(CustomImage img)  {
        System.out.println("Processing: Apply Median Filter");

        for (int iY = 0; iY < img.getHeight(); iY++) //For each pixel in the Image (Height then Row)
            for (int iX = 0; iX < img.getWidth(); iX++)
            {
                int[] neighboursValues = new int[9]; //Maximum neighbours can be up to 9
                int neighbourCount = 0; //Keep sum and count the neighbours

                //Use Math Max/Min functions to ensure it doesn't no out of bounds...
                for(int bY = Math.max(0, iY - 1); bY < Math.min(img.getHeight(), iY + 2); bY++) //might be iY+1+1
                    for(int bX = Math.max(0, iX -1); bX < Math.min(img.getWidth(), + iX + 2); bX++)
                    {
                        neighboursValues[neighbourCount] = img.getPixelGrayscaleVal(bX, bY, img.getPixelArray());
                        neighbourCount++;
                    }

                //Sort the array (using bubble-sort) to prepare to calculate a median
                for (int i = neighbourCount; i >= 0; i--) {
                    for (int j = 1; j < i; j++) {
                        if (neighboursValues[j - 1] > neighboursValues[j]) {
                            int temp = neighboursValues[j - 1];
                            neighboursValues[j - 1] = neighboursValues[j];
                            neighboursValues[j] = temp;
                        }
                    }
                }

                int medianValue = -1;
                int selectValue = -1;

                //If odd count, Calculate Median
                if (neighbourCount %2 == 1)
                    selectValue = neighbourCount / 2;
                else //If even amount of neighbours (elements)
                    selectValue = (neighbourCount + 1) /2;

                medianValue = neighboursValues[selectValue];
                img.setModifyPixel(iX, iY, medianValue);
            }

        img.outputModifiedImage();
        System.out.println("Completed: Apply Median Filter");
    }

    /**
     * Applies a Limit Filter to the image (min & max filter)
     * @param img Custom Bitmap Image Object
     * @param minValue Minimum value of pixel. Anything BELOW will be set to this value.
     * @param maxValue Maximum value of a pixel. Anything ABOVE will be set to this value.
     */
    public static void ApplyBaudLimit(CustomImage img, int minValue, int maxValue) {
        System.out.println("Processing: Apply Baud-Limit Filter");
        if (minValue < 0 || maxValue > 255){ //Min & Max must be between 0-255
            System.out.println("Invalid number assignment, please try again!");
            return;
        }

        //iY = Image Y, iX = Image X, bY = Neighbour-Box Y, bX = Neighbour-Box X.
        for (int iY = 0; iY < img.getHeight(); iY++) //For each pixel in the Image (Height then Row)
            for (int iX = 0; iX < img.getWidth(); iX++)
            {
                 //Use Math Max/Min functions to ensure it doesn't no out of bounds...
                for(int bY = Math.max(0, iY - 1); bY < Math.min(img.getHeight(), iY + 2); bY++) //might be iY+1+1
                    for(int bX = Math.max(0, iX -1); bX < Math.min(img.getWidth(), + iX + 2); bX++)
                    {
                        int pixelValue = img.getPixelGrayscaleVal(bX, bY, img.getPixelArray());
                        img.setModifyPixel(bX, bY, pixelValue); //Set modified pixel as origianl pixel (changed if required below)

                        if (pixelValue < minValue) //If below min val, set this pixel as min value
                            img.setModifyPixel(bX, bY, minValue);

                        if (img.getPixelGrayscaleVal(bX, bY, img.getPixelArray()) > maxValue) //If above max val, set this pixel as max value.
                            img.setModifyPixel(bX, bY, maxValue);
                    }
              }

        img.outputModifiedImage();
        System.out.println("Completed: Apply Baud-Limit Filter");

    }
}