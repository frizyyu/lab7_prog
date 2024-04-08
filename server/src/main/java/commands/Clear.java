package commands;

import DBHelper.ReadFromDB;
import helpers.*;
import supportive.MusicBand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * This class for clear collection
 *
 * @author frizyy
 */
public class Clear implements CommandInterface{
    private final LinkedHashSet<MusicBand> collection;
    MusicBandDbManipulator mbdbm;
    UserDB udb;

    /**
     *
     * @param collection our collection
     */
    public Clear(LinkedHashSet collection, MusicBandDbManipulator mbdbm, UserDB udb){
        this.collection = collection;
        this.mbdbm = mbdbm;
        this.udb = udb;
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
        String forRet = "Collection hasn`t been cleared";
        List<MusicBand> mb = new ArrayList<>(collection);
        for(MusicBand it:mb) {
            if (new TheQueryFormer().remove(it, mbdbm)) {
                collection.remove(it);
                forRet = "Collection has been cleared";
                String[] x = udb.getUserName(ReadFromDB.fileName).split("\\|");
                if (x.length == 1) {
                    CreateUsersMap.users.put(x[0], collection);
                } else {
                    for (int i = 0; i < x.length; i++) {
                        CreateUsersMap.users.put(x[i], collection);
                    }
                }
            }
        }
        //return "Are you sure about deleting the collection? y/n\n>>> ";
        //System.out.print(">>> ");
        /*ContinueAction cont = new ContinueAction();
        int c = cont.continueAction("Collection has been cleared", "The collection has not been cleared", "Action skipped. Invalid answer");
        if (c == 1){
            collection.clear();
            return "Collection has been cleared";
        } else if (c == -1) {
            return "The collection has not been cleared";
        }
        else
            return "Action skipped. Invalid answer";
        //return args;*/
        return forRet;
    }
    /**
     * Method for print description of command
     *
     * @return
     */
    @Override
    public String description() {
        return "Clear the collection\nusage: clear";
    }

    @Override
    public String executeWithObject(String args, MusicBand musicBand) throws IOException {
        return null;
    }
}
