import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.FrequencyFilter;

import java.io.IOException;

/**
 * Created by Charlie on 24/10/2017.
 */
public class MainApplication {

    public static void main(String[] args) throws IOException {

        CustomImage imageFile = new CustomImage("C:\\Users\\Charlie\\IdeaProjects\\ImageAnalysisCW\\res\\PandaNoise.bmp");
        //ApplyUnweightedMeanAverage(imageFile);
      //  SpatialDomain.ApplyBaudLimit(imageFile, 50, 190);

        FrequencyDomain.applyFourierFilter(imageFile, 50, 255);
        SpatialDomain.ApplyMedianFilter(imageFile);
        System.out.println("All tasks completed!");
    }


}
