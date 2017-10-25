import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.FrequencyFilter;

import java.io.IOException;

/**
 * Created by Charlie on 24/10/2017.
 */
public class MainApplication {

    /**
     * Application entry point, change here the functions to call to apply filters.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        CustomImage imageFile = new CustomImage("C:\\Users\\Charlie\\IdeaProjects\\ImageAnalysisCW\\res\\PandaNoise.bmp",
                "C:\\Users\\Charlie\\IdeaProjects\\ImageAnalysisCW\\res\\"); //Load an Image.


        SpatialDomain.ApplyMedianFilter(imageFile);
//        FrequencyDomain.applyFourierFilter(imageFile, 0, 40);
        System.out.println("All tasks completed!");
    }


}
