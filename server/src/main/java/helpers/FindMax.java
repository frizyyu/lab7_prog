package helpers;

import supportive.Coordinates;
import supportive.MusicBand;
import supportive.Studio;

import java.io.IOException;
import java.util.*;

/**
 * Class for find max of 2 elements
 *
 * @author frizyy
 */
public class FindMax {
    /**
     *
     * @param el1 first element
     * @param el2 second element
     * @return max element
     * @throws IOException if happened some strange
     */
    public MusicBand getMax(MusicBand el1, MusicBand el2) throws IOException {
        LinkedHashSet<MusicBand> forCheckMax = new LinkedHashSet<>();
        forCheckMax.add(el1);
        forCheckMax.add(el2);

        SortCollection sorterForCheck = new SortCollection(forCheckMax);
        sorterForCheck.sort(null);
        List<MusicBand> checkMaxList = new ArrayList<>(forCheckMax);
        return checkMaxList.get(forCheckMax.size() - 1);
    }
}
