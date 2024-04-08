package commands;

import DBHelper.ReadFromDB;
import supportive.*;
import helpers.*;

import java.io.IOException;
import java.util.*;

/**
 * This class for add element to collection
 *
 * @author frizyy
 */
public class Add implements CommandInterface {
    private final LinkedHashSet<MusicBand> collection;
    MusicBandDbManipulator mbdbm;
    UserDB udb;

    public Add(LinkedHashSet collection, MusicBandDbManipulator mbdbm, UserDB udb) {
        this.collection = collection;
        this.udb = udb;
        this.mbdbm = mbdbm;
    }

    /**
     * Execute method
     *
     * @param args json-type string for create class instance
     * @return
     * @throws IOException if happened some strange
     */
    @Override
    public String execute(String args) throws IOException {
        //System.out.println(args);
        ClassObjectCreator creator = new ClassObjectCreator(collection);
        //add to db
        MusicBand myMap = creator.create(args);
        myMap = creator.addIdAndDate(myMap);
        if (new TheQueryFormer().add(myMap, mbdbm)) {
            myMap.setId(Long.parseLong(mbdbm.selectIds(ReadFromDB.fileName)));
            collection.add(myMap);

            //System.out.println(collection.size());
            sorter = new SortCollection(collection);
            sorter.sort(null);

            String[] x = udb.getUserName(ReadFromDB.fileName).split("\\|");
            if (x.length == 1) {
                CreateUsersMap.users.put(x[0], collection);
            } else {
                for (int i = 0; i < x.length; i++) {
                    CreateUsersMap.users.put(x[i], collection);
                }
            }

            //long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
            //System.out.println(afterUsedMem);
            return "Element added to collection";
        }
        return "Element hsn`t added to collection";
    }

    /**
     * Method for print description of command
     *
     * @return
     */
    @Override
    public String description() {
        return "This command adds an item to the collection\nusage: add {element} or add";
    }
    ClassObjectCreator creator;
    SortCollection sorter;
    @Override
    public String executeWithObject(String args, MusicBand mb) throws IOException {
        //System.out.println(args);
        //System.out.println(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory());
        creator = new ClassObjectCreator(collection);
        MusicBand myMap = creator.addIdAndDate(mb);
        if (new TheQueryFormer().add(myMap, mbdbm)) {
            myMap.setId(Long.parseLong(mbdbm.selectIds(ReadFromDB.fileName)));
            collection.add(myMap);

            //System.out.println(collection.size());
            sorter = new SortCollection(collection);
            sorter.sort(null);

            String[] x = udb.getUserName(ReadFromDB.fileName).split("\\|");
            if (x.length == 1) {
                CreateUsersMap.users.put(x[0], collection);
            } else {
                for (int i = 0; i < x.length; i++) {
                    CreateUsersMap.users.put(x[i], collection);
                }
            }

            //long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
            //System.out.println(afterUsedMem);
            return "Element added to collection";
        }
        return "Element hsn`t added to collection";
    }
}
