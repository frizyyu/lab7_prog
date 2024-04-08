package helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.checkerframework.checker.units.qual.C;
import supportive.Coordinates;
import supportive.MusicBand;
import supportive.MusicGenre;
import supportive.Studio;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        MusicBand myMap;
        if (args != null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                    .create();
            myMap = gson.fromJson(args, MusicBand.class);
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
        //myMap = addIdAndDate(myMap);
        return myMap;
    }

    public MusicBand addIdAndDate(MusicBand myMap) throws IOException {
        //SortCollection sorter = new SortCollection(collection);
        //sorter.sortById(null);
        //collection = collection.stream().sorted(SortCollection.cId).collect(Collectors.toCollection(LinkedHashSet::new));
        //System.out.println(collection.stream().sorted(new IdComparator()).distinct().toList());
        List<MusicBand> mb = collection.stream().sorted(new IdComparator()).distinct().toList();
        if (mb.size() == 0)
            myMap.setId(1);
        else {
            if (myMap.getId() == 0)
                myMap.setId(mb.get(mb.size() - 1).getId() + 1);
        }
        //System.out.println(myMap.getCreationDate());
        if (myMap.getCreationDate() == null)
            myMap.setCreationDate(ZonedDateTime.now());
        return myMap;
    }
}
