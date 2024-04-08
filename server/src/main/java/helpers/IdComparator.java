package helpers;

import supportive.MusicBand;

import java.util.Comparator;

public class IdComparator implements Comparator<MusicBand> {
    @Override
    public int compare(MusicBand o1, MusicBand o2) {
        return Long.compare(o1.getId(), o2.getId());
    }
}
