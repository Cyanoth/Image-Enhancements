import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Charles Knight on 16/10/2017.
 */
public class CustomImage {
    private int imgWidth;
    private int imgHeight;
    private int[][] pixelArray;
    private int[][] modPixelArray;

    public CustomImage(String imgPath)
    {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imgPath));
            imgWidth = img.getWidth();
            imgHeight = img.getHeight();
            pixelArray = new int[imgWidth][imgHeight]; //Instantiate two arrays WxH
            modPixelArray = new int[imgWidth][imgHeight];

            for (int x = 0; x < imgWidth; x++)
                for (int y = 0; y < imgHeight; y++) {
                    pixelArray[x][y] = img.getData().getSample(x, y, 0); //Read each pixel as grayscale and store in array.
                }

        }
        catch (IOException e) {
            System.out.println("An error occurred - Failed to load image!\n" + e);
        }
    }

    public int getWidth()
    {
        return imgWidth;
    } //Return the width of the image (px)
    public int getHeight()
    {
        return imgHeight;
    } //Return the height of the image (px)
    public int[][] getPixelArray() { return pixelArray; } //Return original, unmodified pixel array for read-only
    public int[][] getModifiedPixelArray() { return modPixelArray; } //Return modified pixel array

    public int getPixelGrayscaleVal(int x, int y, int[][] pixelArr)
    {
        return pixelArr[x][y]; //Get the grayscale value (0-255) in an array from the parameter pixelArr
    }

    public int getPixelRGBValue(int x, int y, int[][] pixelArr)
    {
        //Since all images for the coursework are grayscale, the value for RGB are all the same.
        //This simply sets each of the RGB Components to the same value (required for image output)...
        int rgb =  pixelArr[x][y];
        rgb = (rgb << 8) + pixelArr[x][y];
        rgb = (rgb << 8) +  pixelArr[x][y];

        return rgb; //Get the RGB value (0-255) in an array from the parameter pixelArr
    }

    public void setModifyPixel(int x, int y, int val)
    {
        if (val > 255)
            System.out.println("Warning! Modified pixel value is greater than 255! x: " + x + "  y: " + y + " val:" + val);

        modPixelArray[x][y] = val;
    }

    public void outputModifiedImage(boolean openFile) {
        try {
            File outputFile = new File("C:\\Users\\Charlie\\IdeaProjects\\ImageAnalysisCW\\res\\output.bmp");
            BufferedImage modImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < imgHeight; y++) {
                for (int x = 0; x < imgWidth; x++) {
                    modImage.setRGB(x, y, getPixelRGBValue(x, y, modPixelArray));
                }
            }
            ImageIO.write(modImage, "bmp", outputFile);

            if (openFile) {
                Desktop dt = Desktop.getDesktop();
                dt.open(outputFile);
            }

            System.out.println("The image file was written successfully!!");

        }
        catch (IOException e) {
            System.out.println("An error occurred during picture export... " + e);
        }
    }

    public void outToConsole(int[][] array)
    {
        int lineCounter = 0;

        for (int y = 0; y < imgHeight; y++)
            for (int x = 0; x < imgWidth; x++) {
                System.out.print(array[x][y]  + "  ");

                if (lineCounter == 10){
                    System.out.print("\n");
                    lineCounter = 0;
                }
                else
                    lineCounter++;
            }
    }
}
