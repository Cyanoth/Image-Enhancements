/**
 * Created by Charlie on 24/10/2017.
 */
import Catalano.Imaging.Filters.FrequencyFilter;

import java.io.IOException;

public class FrequencyDomain {

    /**
     * Apply a Fourier Filter transformation to the image.
     * @param img CustomImage file (a loaded bitmap file)
     * @param minFreq The minimum frequency, anything BELOW will be removed
     * @param maxFreq The maximum frequency, anything ABOVE will be removed.
     * @throws IOException On error.
     */
    public static void applyFourierFilter(CustomImage img, int minFreq, int maxFreq) throws IOException {
        System.out.println("Applying a Fourier Filter. minFreq: " + minFreq + " maxFreq: " + maxFreq);

        FrequencyFilter fFilter = new FrequencyFilter(minFreq, maxFreq);
        fFilter.ApplyInPlace(img.toFourierForm());
        img.openFilteredFourier();
        img.fourierToImage(true);

        System.out.println("The fourier filter was completed!");


    }

}
