import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class NoiseRemoval {

    public static void main(String[] args) throws IOException {

        CustomImage imageFile = new CustomImage("C:\\Users\\Charlie\\IdeaProjects\\ImageAnalysisCW\\res\\PandaNoise.bmp");
        //ApplyUnweightedMeanAverage(imageFile);
        ApplyMedianFilter(imageFile);
    }

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

                img.setModifyPixel(iX, iY, Math.round(pixelSum / neighbourCount));
            }

        //img.outToConsole(img.getModifiedPixelArray());
        img.outputModifiedImage(true);
        System.out.println("\n\nx: 137, y:76 equal " + img.getPixelGrayscaleVal(137, 76, img.getModifiedPixelArray()));
        System.out.println("Completed: Apply Unweighted Mean Average");
    }

    public static void ApplyWeightedMeanAverage(CustomImage img) {
        //TODO: each neighbour has an associated weight

    }

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

                //Sort the array (bubble-sort) to prepare to calculate a median
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
                //If odd count
                if (neighbourCount %2 == 1)
                    selectValue = neighbourCount / 2;
                else //If even amount of neighbours (elements)
                    selectValue = (neighbourCount + 1) /2;

                medianValue = neighboursValues[selectValue];


                img.setModifyPixel(iX, iY, medianValue);

                if (iX == 137 && iY == 76)
                    System.out.println("\n\nx 176, y 76 neighbours:" + Arrays.toString(neighboursValues) + " median is... " + medianValue);
            }
            img.outputModifiedImage(true);

    }

    public static void ApplyPrewittOperator(CustomImage img) {
        //TODO
    }
}