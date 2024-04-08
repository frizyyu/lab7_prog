package commands;

import DBHelper.ReadFromDB;
import helpers.*;
import supportive.MusicBand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * This class for remove greater elements than input
 *
 * @author frizyy
 */
public class RemoveGreater implements CommandInterface{
    private final LinkedHashSet<MusicBand> collection;
    MusicBandDbManipulator mbdbm;
    UserDB udb;
    public RemoveGreater(LinkedHashSet collection, MusicBandDbManipulator mbdbm, UserDB udb){
        this.collection = collection;
        this.udb = udb;
        this.mbdbm = mbdbm;
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
        ClassObjectCreator creator = new ClassObjectCreator(collection);
        MusicBand myMap = creator.create(args);
        List<MusicBand> mb = new ArrayList<>(collection);
        //FindMax maxer = new FindMax();
        boolean isRemoved = false;
        int ind=collection.size();
        for (int i=0; i <= collection.size() - 1; i++){
            //MusicBand maxElement = maxer.getMax(mb.get(i), myMap);
            LinkedHashSet<MusicBand> coll = new LinkedHashSet<>();
            coll.add(mb.get(i));
            coll.add(myMap);
            MusicBand maxElement = coll.stream().max(new AlbumsCountComparator()).orElse(null);
            if (maxElement == mb.get(i) && ind == collection.size()) {
                ind = i+1;
                break;
            }

        }
        for (int i = ind; i < collection.size(); i++) {
            boolean is_removed = new TheQueryFormer().remove(mb.get(i), mbdbm);
            if (is_removed) {
                collection.remove(mb.get(i));
                SortCollection sorter = new SortCollection(collection);
                sorter.sort(null);
                isRemoved = true;
                //return "Element has been removed";
            }
            //System.out.printf("Element with id %s has been removed\n", mb.get(i).getId());
        }
        if (isRemoved)
            return "Elements has been removed";
        else
            return "Elements has not been removed";
    }

    /**
     * Description of command
     *
     * @return
     */
    @Override
    public String description() {
        return "Remove elements that are greater than input\nusage remove_greater {element} or remove_greater";
    }

    @Override
    public String executeWithObject(String args, MusicBand mbElement) throws IOException {

        /*ClassObjectCreator creator = new ClassObjectCreator(collection);
        MusicBand myMap;
        myMap = creator.addIdAndDate(mbElement);*/

        List<MusicBand> mb = new ArrayList<>(collection);
        //FindMax maxer = new FindMax();
        boolean isRemoved = false;
        int ind=collection.size();
        for (int i=0; i <= collection.size() - 1; i++){
            //MusicBand maxElement = maxer.getMax(mb.get(i), mbElement);
            LinkedHashSet<MusicBand> coll = new LinkedHashSet<>();
            coll.add(mb.get(i));
            coll.add(mbElement);
            MusicBand maxElement = coll.stream().max(new AlbumsCountComparator()).orElse(null);
            //System.out.println(maxElement);
            if (maxElement == mb.get(i) && ind == collection.size()) {
                ind = i+1;
                break;
            }

        }
        for (int i = ind; i < collection.size(); i++) {
            boolean is_removed = new TheQueryFormer().remove(mb.get(i), mbdbm);
            if (is_removed) {
                collection.remove(mb.get(i));
                SortCollection sorter = new SortCollection(collection);
                sorter.sort(null);
                isRemoved = true;
                //return "Element has been removed";
            }
            //System.out.printf("Element with id %s has been removed\n", mb.get(i).getId());
        }
        if (isRemoved) {
            String[] x = udb.getUserName(ReadFromDB.fileName).split("\\|");
            if (x.length == 1){
                CreateUsersMap.users.put(x[0], collection);
            }
            else {
                for (int i = 0; i < x.length; i++) {
                    CreateUsersMap.users.put(x[i], collection);
                }
            }
            return "Elements has been removed";
        }
        else
            return "Elements has not been removed";
    }
}
