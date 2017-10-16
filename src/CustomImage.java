import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Charles Knight on 16/10/2017.
 */
public class CustomImage {
    private int imgWidth;
    private int imgHeight;
    private int[][] gsPixel;

    public CustomImage(String imgPath)
    {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imgPath));
            imgWidth = img.getWidth();
            imgHeight = img.getHeight();
            gsPixel = new int[imgWidth][imgHeight]; //Create an array which has the space of the image.

            for (int x = 0; x < imgWidth; x++)
                for (int y = 0; y < imgHeight; y++) {
                    gsPixel[x][y] = img.getData().getSample(x, y, 0);
                }

        }
        catch (IOException e) {
            System.out.println("An error occurred - Failed to load image!\n" + e);
        }
    }

    public int getWidth()
    {
        return imgWidth;
    }

    public int getHeight()
    {
        return imgHeight;
    }

    public int[][] gsPixels()
    {
        return gsPixel;
    }

    public int getGsPixelValue(int x, int y)
    {
        return gsPixel[x][y];
    }

    public int getRGBPixelValue(int x, int y)
    {
        int rgb =  gsPixel[x][y];
        rgb = (rgb << 8) +  gsPixel[x][y];
        rgb = (rgb << 8) +  gsPixel[x][y];

        return rgb;
    }

    public int getRGBPixelValue(int[][] array, int x, int y)
    {
        int rgb =  array[x][y];
        rgb = (rgb << 8) +  array[x][y];
        rgb = (rgb << 8) +  array[x][y];

        return rgb;
    }

    public void outputPixelArray()
    {
        int lineCounter = 0;

        for (int y = 0; y < imgHeight; y++)
            for (int x = 0; x < imgWidth; x++) {
                if (lineCounter == imgWidth)
                {
                    System.out.println(gsPixel[x][y]);
                    lineCounter = 0;
                }
                else
                {
                    System.out.print(gsPixel[x][y]  + "  ");
                    lineCounter++;
                }
            }
    }


    public void outputPixelArray(int[][] array)
    {
        int lineCounter = 0;

        for (int y = 0; y < imgHeight; y++)
            for (int x = 0; x < imgWidth; x++) {
                if (lineCounter == imgWidth)
                {
                    System.out.println(array[x][y]);
                    lineCounter = 0;
                }
                else
                {
                    System.out.print(array[x][y]  + "  ");
                    lineCounter++;
                }
            }
    }
}
