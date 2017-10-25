/**
 * Created by Charlie on 24/10/2017.
 */
import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.FourierTransform;
import Catalano.Imaging.Filters.FrequencyFilter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FrequencyDomain {

    public static FourierTransform generateFourier(FastBitmap fbImg, Boolean openResult) throws IOException {
        System.out.println("Converting Bitmap to Fourier Transform... May take a few seconds...!");
        File outputFile = new File("C:\\Users\\Charlie\\IdeaProjects\\ImageAnalysisCW\\res\\fourierOutput.bmp");


        FourierTransform fTransform = new FourierTransform(fbImg);
        fTransform.Forward();
        FastBitmap outputImg = fTransform.toFastBitmap();
        ImageIO.write(outputImg.toBufferedImage(), "bmp", outputFile);

        if (openResult) {
            Desktop dt = Desktop.getDesktop();
            dt.open(outputFile);
        }

        System.out.println("The fourier transform was completed!");

        return fTransform;
    }


    public static void applyFourierFilter(FourierTransform fourierImg, int minFreq, int maxFreq) throws IOException {
        System.out.println("Applying a Fourier Filter. minFreq: " + minFreq + " maxFreq: " + maxFreq);
        File filterOutput = new File("C:\\Users\\Charlie\\IdeaProjects\\ImageAnalysisCW\\res\\fourierFilterOutput.bmp");
        File resultOutput = new File("C:\\Users\\Charlie\\IdeaProjects\\ImageAnalysisCW\\res\\fourierImageResult.bmp");

        FrequencyFilter fFilter = new FrequencyFilter(minFreq, maxFreq);

        fFilter.ApplyInPlace(fourierImg);
        FastBitmap fourierOutput = fourierImg.toFastBitmap();
        ImageIO.write(fourierOutput.toBufferedImage(), "bmp", filterOutput);

        fourierImg.Backward();
        FastBitmap fourierResult = fourierImg.toFastBitmap();
        ImageIO.write(fourierResult.toBufferedImage(), "bmp", resultOutput);

        if (true) {
            Desktop dt = Desktop.getDesktop();
            dt.open(filterOutput);
            dt.open(resultOutput);
        }

        System.out.println("The fourier filter was completed!");


    }

}
