package commands;

import DBHelper.ReadFromDB;
import helpers.*;
import supportive.MusicBand;

import java.io.IOException;
import java.util.LinkedHashSet;

/**
 * This class for remove element from collection by id
 *
 * @author frizyy
 */
public class RemoveById implements CommandInterface{
    private final LinkedHashSet<MusicBand> collection;
    UserDB udb;
    MusicBandDbManipulator mbdbm;
    public RemoveById(LinkedHashSet collection, MusicBandDbManipulator mbdbm, UserDB udb){
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
        args = args.replaceFirst(" ", "|");
        String [] argsList = args.split("\\|");
        /*ContinueAction cont = new ContinueAction();
        System.out.printf("Are you sure to delete element with id %s? y/n\n", argsList[0]);
        System.out.print(">>> ");
        int contAction = cont.continueAction(String.format("Element from id %s deleted", argsList[0]), "Element not deleted", "Action skipped. Invalid answer");
        if (contAction == 1){*/
            FindElementWithId finder = new FindElementWithId();
            MusicBand myMap = finder.findById(collection, argsList);
            //check author
            boolean is_removed = new TheQueryFormer().remove(myMap, mbdbm);
            if (is_removed) {
                collection.remove(myMap);
                SortCollection sorter = new SortCollection(collection);
                sorter.sort(null);
                String[] x = udb.getUserName(ReadFromDB.fileName).split("\\|");
                if (x.length == 1){
                    CreateUsersMap.users.put(x[0], collection);
                }
                else {
                    for (int i = 0; i < x.length; i++) {
                        CreateUsersMap.users.put(x[i], collection);
                    }
                }
                return "Element has been removed";
            }

        //}
        return "Element hasn`t been removed.\nIf element exists, you`re not author";
        //return args;
    }

    /**
     * Description of command
     *
     * @return
     */
    @Override
    public String description() {
        System.out.println("Remove element by id\nusage: remove_by_id id");
        return null;
    }

    @Override
    public String executeWithObject(String args, MusicBand musicBand) throws IOException {
        return null;
    }
}
