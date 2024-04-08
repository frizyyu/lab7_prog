package commandHelpers;
import commands.*;
import helpers.DBManipulator;
import helpers.MusicBandDbManipulator;
import helpers.UserDB;
import validators.*;

import java.io.IOException;
import java.util.*;

/**
 * This class for find command from input string
 *
 * @author frizyy
 */
public class CommandInitializatorForScript {
    /**
     *
     */

    private final HashMap<String, List<?>> commands;
    public static List<String> scripts = new ArrayList<>();
    //public LinkedHashSet<LinkedHashMap<?, ?>> collection;

    //private Help help;

    public CommandInitializatorForScript(LinkedHashSet collection, String fileName, DBManipulator dbm, MusicBandDbManipulator mbdbm, UserDB udb){
        //this.collection = collection;
        commands = new HashMap<>();
        commands.put("help", Arrays.asList(new Help(), new HelpValidator()));
        commands.put("add", Arrays.asList(new Add(collection, mbdbm, udb), new AddValidator()));
        commands.put("show", Arrays.asList(new Show(collection), new ShowValidator()));
        commands.put("save", Arrays.asList(new Save(collection, fileName), new SaveValidator()));
        commands.put("info", Arrays.asList(new Info(collection), new InfoValidator()));
        commands.put("clear", Arrays.asList(new Clear(collection, mbdbm, udb), new ClearValidator()));
        commands.put("update", Arrays.asList(new Update(collection, mbdbm, udb), new UpdateValidator(collection)));
        commands.put("exit", Arrays.asList(new Exit(collection), new ExitValidator()));
        commands.put("remove_by_id", Arrays.asList(new RemoveById(collection, mbdbm, udb), new RemoveByIdValidator(collection)));
        commands.put("add_if_max", Arrays.asList(new AddIfMax(collection, mbdbm, udb), new AddIfMaxValidator()));
        commands.put("remove_greater", Arrays.asList(new RemoveGreater(collection, mbdbm, udb), new RemoveGreaterValidator()));
        commands.put("remove_lower", Arrays.asList(new RemoveLower(collection, mbdbm, udb), new RemoveLowerValidator()));
        commands.put("average_of_albums_count", Arrays.asList(new AverageOfAlbumsCount(collection), new AverageOfAlbumsCountValidator()));
        commands.put("filter_by_albums_count", Arrays.asList(new FilterByAlbumsCount(collection), new FilterByAlbumsCountValidator()));
        commands.put("filter_contains_name", Arrays.asList(new FilterContainsName(collection), new FilterContainsNameValidator()));
        commands.put("execute_script", Arrays.asList(new ExecuteScript(collection, fileName), new ExecuteScriptValidator()));
    }

    /**
     *
     * @param inp string for parsing
     * @param onlyCheck in order to know if it is necessary to output text to the console
     * @return boolean
     * @throws IOException if happened some strange
     */
    public boolean validateAndExecute(String inp, boolean onlyCheck) throws IOException { // returns boolean
        inp = inp.strip().replaceFirst(" ", "|");
        String[] inpArr = inp.split("\\|");
        try {
            ValidatorInterface validate = (ValidatorInterface) commands.get(inpArr[0].toLowerCase()).get(1);
            if (validate.validationCheck(inpArr)){
                CommandInterface command = (CommandInterface) commands.get(inpArr[0].toLowerCase()).get(0);
                if (!onlyCheck){
                    switch (validate.parameterCheck(inpArr)) {
                        case "" -> command.execute(null);
                        case "-h" -> command.description();
                        default -> command.execute(inpArr[1]);
                    }
                }
                return true;
            }
            else{
                //System.out.println("Error. Type help to get list of commands");
                return false;
            }
        }
        catch (NullPointerException error){
            //System.out.printf("Unknown command %s\n", inpArr[0]);
            return false;
        }
    }
}
