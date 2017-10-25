import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.FourierTransform;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Charles Knight on 16/10/2017.
 */
public class CustomImage {
    private int imgWidth; //Width of Image (px), set on image-load
    private int imgHeight; //Height of Image (px), set on image-load
    private int[][] pixelArray; //Original Image as a pixel array - not modified at all unless modified by a fourier transform
    private int[][] modPixelArray; //Modified Image from the spatial domain

    private String defaultOutputPath; //Path where image files should output to.

    private FastBitmap fourierBitmap; //Bitmap Object used by the FourierTransform Library
    private FourierTransform fourierTransform; //Fourier Transform version of Image is stored.

    /**
     * Constructor for CustomImage class, which contains IO operations & data about a particular image
     * @param imgPath - Load a bitmap file
     * @param defaultOutputPath - Path where modified image should output to.
     */
    public CustomImage(String imgPath, String defaultOutputPath)
    {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imgPath));
            imgWidth = img.getWidth();
            imgHeight = img.getHeight();
            pixelArray = new int[imgWidth][imgHeight]; //Instantiate two arrays WxH
            modPixelArray = new int[imgWidth][imgHeight];
            fourierBitmap = new FastBitmap(imgPath);
            this.defaultOutputPath = defaultOutputPath;

            for (int x = 0; x < imgWidth; x++) //Convert the image into an array of pixel values.
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
    public FastBitmap getFourierBitmap() { return fourierBitmap; } //Return the Fourier Bitmap Image.

    /**
     * @param x X corrd
     * @param y Y Coord
     * @param pixelArr Either pass in the getPixelArray() (original) or getModifiedPixelArray() (modified image, for multiple filters)
     * @return Grayscale value in an image array x, y
     */
    public int getPixelGrayscaleVal(int x, int y, int[][] pixelArr)
    {
        return pixelArr[x][y]; //Get the grayscale value (0-255) in an array from the parameter pixelArr
    }

    /**
     * Required to output images, the Java library requires the value as a RGB, this gets from an array into RGB value.
     * @param x X corrd
     * @param y Y Coord
     * @param pixelArr Either pass in the getPixelArray() (original) or getModifiedPixelArray() (modified image, for multiple filters)
     * @return RGB value in an image array x, y
     */
    public int getPixelRGBValue(int x, int y, int[][] pixelArr)
    {
        //Since all images for the coursework are grayscale, the value for RGB are all the same.
        //This simply sets each of the RGB Components to the same value (required for image output)...
        int rgb =  pixelArr[x][y];
        rgb = (rgb << 8) + pixelArr[x][y];
        rgb = (rgb << 8) +  pixelArr[x][y];

        return rgb; //Get the RGB value (0-255) in an array from the parameter pixelArr
    }

    /**
     * @param x X corrd
     * @param y Y Coord
     * @param val Value of the pixel to set in modified image (0 - 255)
     */
    public void setModifyPixel(int x, int y, int val)
    {
        if (val > 255)
            System.out.println("Warning! Modified pixel value is greater than 255! x: " + x + "  y: " + y + " val:" + val);

        modPixelArray[x][y] = val;
    }

    /**
     * Output the modified image and open it with the default image viewer.
     */
    public void outputModifiedImage() {
        try {
            File outputFile = new File(defaultOutputPath + "output.bmp");
            BufferedImage modImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < imgHeight; y++) {
                for (int x = 0; x < imgWidth; x++) {
                    modImage.setRGB(x, y, getPixelRGBValue(x, y, modPixelArray)); //Java library requires in value RGB, so in grayscale (RGB are all equal)
                }
            }
            ImageIO.write(modImage, "bmp", outputFile);

            Desktop dt = Desktop.getDesktop();
            dt.open(outputFile);
            System.out.println("The image file was written successfully!!");

        }
        catch (IOException e) {
            System.out.println("An error occurred during picture export... " + e);
        }
    }

    /**
     * @return Bitmap Image transformed into Fourier Form using the Catalano library, opens the result also in default image viewer
     */
    public FourierTransform toFourierForm() {
        System.out.println("Converting to a Fourier Transform... May take a few seconds...!");
        fourierTransform = new FourierTransform(getFourierBitmap());
        fourierTransform.Forward();
        System.out.println("Fourier Transform has completed");

        try {
            File outputFile = new File(defaultOutputPath + "fourierOutput.bmp");
            FastBitmap outputImg = fourierTransform.toFastBitmap();
            ImageIO.write(outputImg.toBufferedImage(), "bmp", outputFile);
            Desktop dt = Desktop.getDesktop();
            dt.open(outputFile);
        }
         catch (IOException e) {
            System.out.println("An error occurred during picture export... " + e);
        }

        return fourierTransform;
    }

    /**
     * Opens the fourier image in the desktop image viewer. Should be called after a filter has been applied to it.
     */
    public void openFilteredFourier() {
        try {
            File outputFile = new File(defaultOutputPath + "fourierFilter.bmp");
            FastBitmap outputImg = fourierTransform.toFastBitmap();
            ImageIO.write(outputImg.toBufferedImage(), "bmp", outputFile);
            Desktop dt = Desktop.getDesktop();
            dt.open(outputFile);
        }
        catch (IOException e) {
            System.out.println("An error occurred during picture export... " + e);
        }
    }

    /**
     * Converts the Filter Fourier Result into a Bitmap Image
     * @param setImgArray Whether the fourier result should be applied to the image array (for multiple filter in spatial domain)
     */
    public void fourierToImage(Boolean setImgArray)
    {
        System.out.println("Fourier Transform to Image... May take a few seconds...!");

        fourierTransform.Backward(); //Fourier to Bitmap, should add a check to see if its in fourier form?
        try {
            File outputFile = new File(defaultOutputPath + "fourierResult.bmp");
            FastBitmap outputImg = fourierTransform.toFastBitmap();
            ImageIO.write(outputImg.toBufferedImage(), "bmp", outputFile);
            Desktop dt = Desktop.getDesktop();
            dt.open(outputFile);

            if (setImgArray)
            {
                System.out.println("Overwriting the image array with fourier transform variant");
                for (int x = 0; x < imgWidth; x++)
                    for (int y = 0; y < imgHeight; y++) {
                        pixelArray[x][y] = outputImg.toBufferedImage().getData().getSample(x, y, 0); //Read each pixel as grayscale and store in array.
                    }
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred during picture export... " + e);
        }

        System.out.println("Fourier Transform to Image has completed");


    }

    /**
     * For debugging purposes, outputs the pixel array into the console
     * @param array Which pixel array to output to console.
     */
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
