package commands;

import supportive.MusicBand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * this class for count average of Albums count
 *
 * @author frizyy
 */
public class AverageOfAlbumsCount implements CommandInterface{
    private final LinkedHashSet<MusicBand> collection;

    /**
     *
     * @param collection our collection
     */
    public AverageOfAlbumsCount(LinkedHashSet collection){
        this.collection = collection;
    }

    /**
     * Execute method
     *
     * @param args null, because command hasn`t got arguments
     * @return
     * @throws IOException if happened some strange
     */
    @Override
    public String execute(String args) throws IOException {
        List<MusicBand> mb = new ArrayList<>(collection);
        int c=0;
        int counter = 0;
        for (int i=0; i <= collection.size() - 1; i++){
            c += mb.get(i).getAlbumsCount();
            counter += 1;
        }
        return String.format("Average of albumsCount: %s\n", c/counter);
        //return args;
    }
    /**
     * Method for print description of command
     *
     * @return
     */
    @Override
    public String description() {
        return"Prints average of albumsCount\nusage: average_of_albums_count";
        //return null;
    }

    @Override
    public String executeWithObject(String args, MusicBand musicBand) throws IOException {
        return null;
    }
}
