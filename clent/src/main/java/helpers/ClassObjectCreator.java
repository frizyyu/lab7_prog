package helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.checkerframework.checker.units.qual.C;
import supportive.Coordinates;
import supportive.MusicBand;
import supportive.MusicGenre;
import supportive.Studio;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

/**
 * This class for create object of MusicBand
 *
 * @author frizyy
 */
public class ClassObjectCreator {
    private final LinkedHashSet<MusicBand> collection;
    public ClassObjectCreator(LinkedHashSet collection){
        this.collection = collection;
    }

    /**
     * Execute method
     * @param args json-type string for create instance
     * @return MusicBand instance
     * @throws IOException if happened some strange
     */
    public MusicBand create(String args) throws IOException {
        if (args != null && !args.contains("{\"id\":0,")){
            args = args.replaceFirst("\\{", "{\"id\":0,");
        }
        MusicBand myMap;
        if (args != null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                    .create();
            //try {
                myMap = gson.fromJson(args, MusicBand.class);
            /*} catch (JsonSyntaxException q){
                return null;
            }*/
        }
        else {
                myMap = new MusicBand();
                UserFriendlyCreateObject creator = new UserFriendlyCreateObject();
                myMap = creator.create(myMap);
        }
        try {
            myMap.setGenre(MusicGenre.valueOf(args.split("\"genre\":")[1].replace(" ", "").replaceFirst("\"", "").split("\",")[0].toUpperCase()));
        }catch (Exception ignored){
        }
        SortCollection sorter = new SortCollection(collection);
        sorter.sortById(null);
        //List<MusicBand> mb = new ArrayList<>(collection);
        /*if (mb.size() == 0)
            myMap.setId(1);
        else {
            if (myMap.getId() == 0)
                myMap.setId(mb.get(mb.size() - 1).getId() + 1);
        }
        if (myMap.getCreationDate() == null)
            myMap.setCreationDate(ZonedDateTime.now());*/
        //sorter.sort(null);
        return myMap;
    }

    public MusicBand setId(String id, MusicBand myMap){
        //if (myMap == null)
          //  return null;
        myMap.setId(Long.parseLong(id));
        return myMap;
    }
}
