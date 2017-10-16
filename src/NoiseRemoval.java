import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class NoiseRemoval {

    public static void main(String[] args) throws IOException {

        CustomImage imageFile = new CustomImage("C:\\Users\\Charlie\\IdeaProjects\\ImageAnalysisCW\\res\\PandaNoise.bmp");
        ApplyUnweightedMeanAverage(imageFile);
       // img.outputPixelArray();

        //testCreateImage(img);

    }


    public static void ApplyUnweightedMeanAverage(CustomImage img) throws IOException {
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

            img.outToConsole(img.getModifiedPixelArray());
            img.outputModifiedImage(true);

            System.out.println("\n\nx: 137, y:76 equal " + img.getPixelGrayscaleVal(137, 76, img.getModifiedPixelArray()));
    }

}