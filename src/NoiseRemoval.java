import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class NoiseRemoval {

    public static void main(String[] args) throws IOException {

        System.out.println("Applying a Low-Pass Filter (Local Neighbourhood with weighted-pixes");
        CustomImage img = new CustomImage("C:\\Users\\Charlie\\IdeaProjects\\ImageAnalysisCW\\res\\PandaNoise.bmp");
        ApplyUnweightedMeanAverage(img);
       // img.outputPixelArray();

        //testCreateImage(img);

    }

    public static void writeImage(CustomImage img, int[][] array) throws IOException {
        BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                int gsValue = img.getRGBPixelValue(array, x, y);
                image.setRGB(x, y, gsValue);
            }
        }

        File outputFile = new File("C:\\Users\\Charlie\\IdeaProjects\\ImageAnalysisCW\\res\\output.bmp");
        ImageIO.write(image, "bmp", outputFile);

        Desktop dt = Desktop.getDesktop();
        dt.open(outputFile);

        System.out.println("Image was written successfully!!");
    }

    public static void ApplyUnweightedMeanAverage(CustomImage img) throws IOException {
        int[][] modifiedImage = new int[img.getWidth()][img.getHeight()];

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
                        pixelSum += img.getGsPixelValue(bX, bY);
                        neighbourCount++;
                    }

                modifiedImage[iX][iY] = Math.round(pixelSum / neighbourCount);
            }

            img.outputPixelArray(modifiedImage);
            writeImage(img, modifiedImage);
            System.out.println("\n\nx: 137, y:76 equal " + modifiedImage[137][76]);
    }

}