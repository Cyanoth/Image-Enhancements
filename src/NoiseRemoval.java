import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class NoiseRemoval {

    public static void main(String[] args) throws IOException {


        System.out.println("Applying a Low-Pass Filter (Basic Mean-Neighbour) ");
        CustomImage img = new CustomImage("C:\\Users\\Charlie\\IdeaProjects\\ImageAnalysisCW\\res\\PandaNoise.bmp");

        System.out.println("Applying a Low-Pass Filter (Local Neighbourhood with weighted-pixes");
       // img.outputPixelArray();

        testCreateImage(img);

    }

    public static void testCreateImage(CustomImage img) throws IOException {
        BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                int gsValue = img.getGsPixelValue(x, y);
                int simpleMod = gsValue * 2;

                image.setRGB(x, y, img.gsToRGB(simpleMod));
            }
        }

        File outputFile = new File("C:\\Users\\Charlie\\IdeaProjects\\ImageAnalysisCW\\res\\output.bmp");
        ImageIO.write(image, "bmp", outputFile);

        Desktop dt = Desktop.getDesktop();
        dt.open(outputFile);

        System.out.println("Image was written successfully!!");
    }

    public void ApplyLowPassFilter(CustomImage img)
    {
    }

}