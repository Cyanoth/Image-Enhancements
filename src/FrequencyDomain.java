/**
 * Created by Charlie on 24/10/2017.
 */
import Catalano.Imaging.Filters.FrequencyFilter;

import java.io.IOException;

public class FrequencyDomain {

    public static void applyFourierFilter(CustomImage img, int minFreq, int maxFreq) throws IOException {
        System.out.println("Applying a Fourier Filter. minFreq: " + minFreq + " maxFreq: " + maxFreq);

        FrequencyFilter fFilter = new FrequencyFilter(minFreq, maxFreq);
        fFilter.ApplyInPlace(img.toFourierForm());
        img.openFilteredFourier();
        img.fourierToImage();

        System.out.println("The fourier filter was completed!");


    }

}
