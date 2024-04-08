package commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import helpers.ZonedDateTimeTypeAdapter;
import org.checkerframework.checker.units.qual.A;
import supportive.MusicBand;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * This class for print elements from collection which filtered by albums count
 *
 * @author frizyy
 */
public class FilterByAlbumsCount implements CommandInterface{
    private final LinkedHashSet<MusicBand> collection;
    public FilterByAlbumsCount(LinkedHashSet collection){
        this.collection = collection;
    }

    /**
     * Execute command
     *
     * @param args string with arguments
     * @return
     * @throws IOException if happened some strange
     */
    @Override
    public String execute(String args) throws IOException {
        List<MusicBand> mb = new ArrayList<>(collection);
        //System.out.println(collection.stream().filter(x -> x.getAlbumsCount() == Integer.parseInt(args)).findAny());
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                .create();
        /*int c=0;
        String returnS = "";
        for (int i=0; i <= collection.size() - 1; i++){
            if (mb.get(i).getAlbumsCount() == Integer.parseInt(args)){
                returnS += String.format("%s\n", gson.toJson(mb.get(i)));
                c += 1;
            }
        }*/
        List<MusicBand> res = collection.stream().filter(x -> x.getAlbumsCount() == Integer.parseInt(args)).toList();
        if (res.size() == 0)
            return "No matches found";
        else {
            List<String> re = new ArrayList<>();
            res.forEach(r -> re.add(gson.toJson(r)));
            return String.join("\n", re);
        }
        /*if (c == 0)
            return "No matches found";
        return returnS.strip();*/
        //return args;
    }

    /**
     * Description of command
     *
     * @return
     */
    @Override
    public String description() {
        return "Prints elements from collection with same albumsCount as input\nusage: filter_by_albums_count";
        //return null;
    }

    @Override
    public String executeWithObject(String args, MusicBand musicBand) throws IOException {
        return null;
    }
}
