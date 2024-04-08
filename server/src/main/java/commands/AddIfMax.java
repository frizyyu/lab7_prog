package commands;

import DBHelper.ReadFromDB;
import helpers.*;
import supportive.MusicBand;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class for add element if his value more then value of another element
 *
 * @author frizyy
 */
public class AddIfMax implements CommandInterface {

    private final LinkedHashSet<MusicBand> collection;
    MusicBandDbManipulator mbdbm;
    UserDB udb;

    /**
     *
     * @param collection our collection
     */
    public AddIfMax(LinkedHashSet collection, MusicBandDbManipulator mbdbm, UserDB udb){
        this.collection = collection;
        this.udb = udb;
        this.mbdbm = mbdbm;
    }

    /**
     * Execute method
     *
     * @param args input string with element
     * @return
     * @throws IOException if happened some strange
     */
    @Override
    public String execute(String args) throws IOException {
        SortCollection sorted = new SortCollection(collection);
        sorted.sort(null);
        List<MusicBand> mb = new ArrayList<>(collection);
        MusicBand maxMusicBandInstance;
        if (collection.size() > 0) {
            maxMusicBandInstance = mb.get(collection.size() - 1);
        }
        else maxMusicBandInstance = null;

        ClassObjectCreator creator = new ClassObjectCreator(collection);
        MusicBand myMap = creator.create(args);

        FindMax maxer = new FindMax();
        MusicBand maxElement;
        if (maxMusicBandInstance != null)
            maxElement = maxer.getMax(maxMusicBandInstance, myMap);
        else maxElement = myMap;
        /*LinkedHashSet<MusicBand> coll = new LinkedHashSet<>();
        coll.add(maxMusicBandInstance);
        coll.add(myMap);
        MusicBand maxElement = coll.stream().max(new AlbumsCountComparator()).orElse(null);*/

        if (maxElement == myMap){
            if (new TheQueryFormer().add(myMap, mbdbm)) {
                collection.add(myMap);
                SortCollection sorter = new SortCollection(collection);
                sorter.sort(null);
                return "Element has been added to collection";
            }
            else
                return "Element hasn`t been added to collection";
        }
        else
            return "Element not added. His value is not more than value of maximum element of this collection";
        //System.out.println(args);
        //return args;
    }
    /**
     * Method for print description of command
     *
     * @return
     */
    @Override
    public String description() {
        return"Add element to collection, if his value is more than value of maximum element of this collection\nusage: add_if_max {element} or add_if_max";
    }

    public String executeWithObject(String arg, MusicBand mbElement) throws IOException {
        SortCollection sorted = new SortCollection(collection);
        sorted.sort(null);
        List<MusicBand> mb = new ArrayList<>(collection);
        MusicBand maxMusicBandInstance;
        if (collection.size() > 0) {
             maxMusicBandInstance = mb.get(collection.size() - 1);
        }
        else
             maxMusicBandInstance = null;
        ClassObjectCreator creator = new ClassObjectCreator(collection);
        MusicBand myMap;
        myMap = creator.addIdAndDate(mbElement);
        collection.add(myMap);
        FindMax maxer = new FindMax();
        MusicBand maxElement;
        if (maxMusicBandInstance != null)
            maxElement = maxer.getMax(maxMusicBandInstance, myMap);
        else maxElement = myMap;
        /*LinkedHashSet<MusicBand> coll = new LinkedHashSet<>();
        coll.add(maxMusicBandInstance);
        coll.add(mbElement);
        MusicBand maxElement = coll.stream().max(SortCollection.c).orElse(null);*/

        if (maxElement == myMap){
            if (new TheQueryFormer().add(myMap, mbdbm)) {
                collection.add(myMap);
                SortCollection sorter = new SortCollection(collection);
                sorter.sort(null);
                String[] x = udb.getUserName(ReadFromDB.fileName).split("\\|");
                if (x.length == 1) {
                    CreateUsersMap.users.put(x[0], collection);
                } else {
                    for (int i = 0; i < x.length; i++) {
                        CreateUsersMap.users.put(x[i], collection);
                    }
                }
                return "Element has been added to collection";
            }
            else
                return "Element hasn`t been added to collection";
        }
        else
            return "Element not added. His value is not more than value of maximum element of this collection";
    }
}
