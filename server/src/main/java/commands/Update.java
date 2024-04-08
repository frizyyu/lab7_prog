package commands;

import DBHelper.ReadFromDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import supportive.MusicBand;
import helpers.*;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * This class for update element of collection
 *
 * @author frizyy
 */
public class Update implements CommandInterface {
    private final LinkedHashSet<MusicBand> collection;
    MusicBandDbManipulator mbdbm;
    UserDB udb;
    public Update(LinkedHashSet collection, MusicBandDbManipulator mbdbm, UserDB udb){
        this.collection = collection;
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
        //System.out.println(args);
        String[] argsList = args.replaceFirst(" ", "|").split("\\|");
        MusicBand myMap;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                .create();
        if (argsList.length != 1){
            myMap = gson.fromJson(argsList[1], MusicBand.class);
        }
        else{
            myMap = new MusicBand();
            UserFriendlyCreateObject creator = new UserFriendlyCreateObject();
            myMap = creator.create(myMap);
        }

        FindElementWithId finder = new FindElementWithId();
        MusicBand res = finder.findById(collection, argsList);
        if (res == null){
            return "Element doesn`t exist";
        }

        myMap.setId(res.getId());
        myMap.setCreationDate(res.getCreationDate());
        if (new TheQueryFormer().update(myMap, mbdbm)) {
            collection.remove(res);
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
            return String.format("Collection element with id %s has been updated\n", argsList[0]);
        }
        return String.format("Collection element with id %s hasn`t been updated\n", argsList[0]);
        //return args;
    }

    /**
     * Description of command
     *
     * @return
     */
    @Override
    public String description() {
        return "Update element by id\nusage: update id {element} or update id";
        //return null;
    }

    @Override
    public String executeWithObject(String args, MusicBand mb) throws IOException {
        String[] argsList = args.replaceFirst(" ", "|").split("\\|");
        ClassObjectCreator creator = new ClassObjectCreator(collection);
        MusicBand myMap;
        myMap = creator.addIdAndDate(mb);

        FindElementWithId finder = new FindElementWithId();
        MusicBand res = finder.findById(collection, argsList);
        if (res == null){
            return "Element doesn`t exist";
        }

        myMap.setId(res.getId());
        myMap.setCreationDate(res.getCreationDate());
        if (new TheQueryFormer().update(myMap, mbdbm)) {
            collection.remove(res);
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
            return String.format("Collection element with id %s has been updated\n", argsList[0]);
        }
        return String.format("Collection element with id %s hasn`t been updated\n", argsList[0]);
    }
}
