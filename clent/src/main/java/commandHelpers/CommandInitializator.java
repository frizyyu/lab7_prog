package commandHelpers;
import helpers.ClientToServer;
import supportive.MusicBand;
import validators.*;

import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

/**
 * This class for find command from input string
 *
 * @author frizyy
 */
public class CommandInitializator {

    private final HashMap<String, Object> commands;
    public static List<String> scripts = new ArrayList<>();
    public LinkedHashSet<MusicBand> collection;
    ArrayList notNeedToErrorOut = new ArrayList();

    //private Help help;

    public CommandInitializator(LinkedHashSet collection) throws IOException {
        //this.collection = collection;
        commands = new HashMap<String, Object>();
        commands.put("help", new HelpValidator());
        commands.put("add", new AddValidator());
        commands.put("show", new ShowValidator());
        //commands.put("save", Arrays.asList(new Save(collection, fileName), new SaveValidator()));
        commands.put("info", new InfoValidator());
        commands.put("clear", new ClearValidator());
        commands.put("update", new UpdateValidator(collection));
        commands.put("exit", new ExitValidator(collection));
        commands.put("remove_by_id", new RemoveByIdValidator(collection));
        commands.put("add_if_max", new AddIfMaxValidator(collection));
        commands.put("remove_greater", new RemoveGreaterValidator(collection));
        commands.put("remove_lower", new RemoveLowerValidator(collection));
        commands.put("average_of_albums_count", new AverageOfAlbumsCountValidator());
        commands.put("filter_by_albums_count", new FilterByAlbumsCountValidator());
        commands.put("filter_contains_name", new FilterContainsNameValidator());
        commands.put("execute_script", new ExecuteScriptValidator());
        notNeedToErrorOut.add("clear");
        notNeedToErrorOut.add("exit");
        notNeedToErrorOut.add("remove_by_id");
        notNeedToErrorOut.add("remove_greater");
        notNeedToErrorOut.add("remove_lower");
    }

    /**
     *
     * @param inp string for parsing
     * @return boolean
     * @throws IOException if happened some strange
     */
    public boolean validate(String inp) throws IOException { // returns boolean
        inp = inp.strip().replaceFirst(" ", "|");
        String[] inpArr = inp.split("\\|");
        try {
            ValidatorInterface validate = (ValidatorInterface) commands.get(inpArr[0].toLowerCase());
            if (validate.validationCheck(inpArr)){
                //System.out.println("VALIDATE");
                return true;
            }
            else{
                if (!notNeedToErrorOut.contains(inpArr[0].toLowerCase()))
                    System.out.println("Error. Type help to get list of commands");
                return false;
            }
        }
        catch (NullPointerException error){
            System.out.printf("Unknown command %s\n", inpArr[0]);
            return false;
        }
    }
}
