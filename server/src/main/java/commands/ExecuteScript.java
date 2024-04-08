package commands;

import commandHelpers.CommandInitializatorForScript;
import supportive.MusicBand;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * This class for execute script feom file
 *
 * @author frizyy
 */
public class ExecuteScript implements CommandInterface{
    public String readedFile = "";
    private final LinkedHashSet<MusicBand> collection;
    private final String fileName;
    public List<String> needToParse = new ArrayList<>();

    /**
     *
     * @param collection our collection
     * @param fileName filename for find script
     */
    public ExecuteScript(LinkedHashSet collection, String fileName){
        this.collection = collection;
        this.fileName = fileName;
        this.needToParse.add("add");
        this.needToParse.add("update");
        this.needToParse.add("remove_greater");
        this.needToParse.add("remove_lower");
        this.needToParse.add("add_if_max");
    }

    /**
     * Execute method
     *
     * @param args string with arguments
     * @return
     * @throws IOException if happened some strange
     */
    @Override
    public String execute(String args) throws IOException {
        /*//System.out.println("ASDASDASDASDASDEXECUTE");
        //System.out.println(args);
        String fileName = args.split("\\|")[0];
        args = args.split("\\|")[1];
        String result = "";
        //FileInputStream inputStream = new FileInputStream(new File(args).getAbsolutePath());
        //CommandInitializator.scripts.add(String.format("execute_script %s", args));
        this.readedFile = args;
        if (Objects.equals(this.readedFile, "")) {
            result = "Script file is empty\n";
            return result;
        }
        else {
        result += String.format("Executing file %s\n", fileName);
        String[] script = this.readedFile.replace("\n", "|").split("\\|");
        System.out.println(Arrays.toString(script));
        CommandInitializatorForScript exec = new CommandInitializatorForScript(collection, fileName);
        String scripti;
        for(int i=0; i <= script.length - 1; i++){
            CommandInitializatorForScript.scripts.add(String.format("execute_script %s", fileName));
            if (!(CommandInitializatorForScript.scripts.contains(script[i]) || (CommandInitializatorForScript.scripts.contains(script[i]) && (CommandInitializatorForScript.scripts.get(CommandInitializatorForScript.scripts.toArray().length - 2) == String.format("execute_script %s", args) && CommandInitializatorForScript.scripts.get(CommandInitializatorForScript.scripts.toArray().length - 1) != String.format("execute_script %s", args))))) {
                if (!Objects.equals(script[i], "skip") && !exec.validateAndExecute(script[i], true)) {
                    break;
                }
                try {
                    if (needToParse.contains(script[i].replaceFirst(" ", "|").split("\\|")[0])) {
                        scripti = script[i];
                        if (!(scripti.contains("{") && scripti.contains("}"))){
                            scripti = String.format("%s {\"name\":\"%s\",\"coordinates\":{\"x\":%s,\"y\":%s},\"numberOfParticipants\":%s,\"albumsCount\":%s,\"genre\":\"%s\",\"studio\":{\"name\":\"%s\",\"address\":\"%s\"}}", script[i].strip(), script[i + 1].strip(), script[i + 2].strip(), script[i + 3].strip(), script[i + 4].strip(), script[i + 5].strip(), script[i + 6].strip().toUpperCase(), script[i + 7].strip(), script[i + 8].strip());
                            for (int j = 1; j <= 8; j++){
                                script[i + j] = "skip";
                            }
                        }
                        if (!exec.validateAndExecute(scripti, true))
                            break;
                    }
                    else
                        scripti = script[i];
                }catch (IndexOutOfBoundsException e){
                    result += "Not enough elements\n";
                    break;
                }
                if (!Objects.equals(scripti, "skip")) {
                    if (i == 0)
                        result += "-------------------\n";
                    result += String.format("Execute command: %s\n\n", script[i]);
                    result += String.format("%s\n", exec.validateAndExecute(scripti, false));
                    result += "-------------------\n";
                }
            }
            else{
                result += "Error in script file. Recursion found\n";
                break;
            }
        }
        result += String.format("File \"%s\" has been executed\n", fileName); //CommandInitializator.scripts.removeAll(new ArrayList<>(CommandInitializator.scripts));
        this.readedFile = "";
        CommandInitializatorForScript.scripts.removeAll(new ArrayList<>(CommandInitializatorForScript.scripts));
        }*/

        return "";
    }

    /**
     * Description of command
     *
     * @return
     */
    @Override
    public String description() {
        return "Executes commands from input file\nusage: execute_script file_name";
    }

    @Override
    public String executeWithObject(String args, MusicBand musicBand) throws IOException {
        return null;
    }
}
