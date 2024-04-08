package commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import helpers.ZonedDateTimeTypeAdapter;
import supportive.MusicBand;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * This class print elements of collection filtered by name
 */
public class FilterContainsName implements CommandInterface{
    private final LinkedHashSet<MusicBand> collection;
    public FilterContainsName(LinkedHashSet collection){
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
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                .create();
        /*int c=0;
        String resturnS = "";
        for (int i=0; i <= collection.size() - 1; i++){
            if (mb.get(i).getName().contains(args)){
                resturnS += String.format("%s\n", gson.toJson(mb.get(i)));
                c += 1;
            }
        }
        if (c == 0)
            System.out.println("No matches found");
        //return args;
        return resturnS.strip();*/
        //System.out.println(args);
        List<MusicBand> res = collection.stream().filter(x -> x.getName().contains(args)).toList();
        if (res.size() == 0)
            return "No matches found";
        else {
            List<String> re = new ArrayList<>();
            res.forEach(r -> re.add(gson.toJson(r)));
            return String.join("\n", re);
        }
    }

    /**
     * Description of command
     *
     * @return
     */
    @Override
    public String description() {
        return "Prints elements from collection with same name as input\nusage: filter_contains_name";
    }

    @Override
    public String executeWithObject(String args, MusicBand musicBand) throws IOException {
        return null;
    }
}
