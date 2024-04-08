package helpers;

import supportive.MusicBand;

import java.util.Comparator;

public class AlbumsCountComparator implements Comparator<MusicBand> {
    @Override
    public int compare(MusicBand o1, MusicBand o2) {
        return o1.getAlbumsCount() - o2.getAlbumsCount();
    }
}
