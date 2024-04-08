package commandHelpers;

import helpers.*;
import jsonHelper.ReadFromJson;
import supportive.MusicBand;

import java.io.*;
import java.util.*;

/**
 * This class for execute script feom file
 *
 * @author frizyy
 */
public class ExecuteScript{
    public String readedFile = "";
    private final LinkedHashSet<MusicBand> collection;
    private final String fileName;
    public List<String> needToParse = new ArrayList<>();

    /**
     *
     * @param collection our collection
     * @param fileName filename for find script
     */
    public ExecuteScript(LinkedHashSet collection, String fileName) throws IOException {
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

    public String execute(String args, ClientToServer cts, String userName, ArrayList<String> needCrateMbObject) throws IOException, ClassNotFoundException {
        //System.out.println("ASDASDASDASDASDEXECUTE");
        //System.out.println(args);
        String fileName = args.split("\\|")[0];
        args = args.split("\\|")[1];
        String result = "";
        //FileInputStream inputStream = new FileInputStream(new File(args).getAbsolutePath());
        //CommandInitializator.scripts.add(String.format("execute_script %s", args));
        this.readedFile = args;
        if (Objects.equals(this.readedFile, "")) {
            System.out.print("Script file is empty\n");
            //return result;
        }
        else {
            System.out.printf("Executing file %s\n", fileName);
        String[] script = this.readedFile.replace("\n", "|").split("\\|");
        //System.out.println(Arrays.toString(script));
        CommandInitializator.scripts.add(String.format("execute_script %s", fileName));
        CommandInitializator exec = new CommandInitializator(collection);
        String scripti = null;
        boolean isRecursion = false;
        for(int i=0; i <= script.length - 1; i++){
            /*System.out.println("AAA" + CommandInitializator.scripts);
            System.out.println(script[i]);
            System.out.println("AAA" + CommandInitializator.scripts.indexOf(script[i].strip()));*/
            if (CommandInitializator.scripts.size() > 1 && CommandInitializator.scripts.indexOf(script[i].strip()) != CommandInitializator.scripts.size() - 1 && CommandInitializator.scripts.contains(script[i].strip())) {
                isRecursion = true;
            }
            if (isRecursion) {
                break;
            }
            else {
                if (!Objects.equals(script[i], "skip") && !exec.validate(script[i])) {
                    break;
                }
                try {
                    if (script[i].split(" ")[0].equals("execute_script")) {
                        try {
                            ExecuteScriptReader esr = new ExecuteScriptReader();
                            //for с отправкой команд из execute script
                            ExecuteScript es = new ExecuteScript(collection, null);
                            es.execute(esr.read(script[i].split(" ")[1].strip()), cts, CreateUser.userName, needCrateMbObject);
                        } catch (FileNotFoundException er) {
                            System.out.println("File not found");
                            break;
                        }
                    }
                    else if (script[i].equals("exit"))
                        cts.send(new Request(script[i], String.format("%sexit|%s", CreateUser.userName, ReadFromJson.fileName), collection, userName, "password", null));
                    //req.setArgs(esr.read(currCommand[1]));
                    //System.out.println(needToParse.contains(script[i].replaceFirst(" ", "|").split("\\|")[0].strip()));
                    else {
                        if (needToParse.contains(script[i].replaceFirst(" ", "|").split("\\|")[0].strip())) {
                            scripti = script[i];
                            if (!(scripti.contains("{") && scripti.contains("}"))) {
                                scripti = String.format("%s {\"name\":\"%s\",\"coordinates\":{\"x\":%s,\"y\":%s},\"numberOfParticipants\":%s,\"albumsCount\":%s,\"genre\":\"%s\",\"studio\":{\"name\":\"%s\",\"address\":\"%s\"}}", script[i].strip(), script[i + 1].strip(), script[i + 2].strip(), script[i + 3].strip(), script[i + 4].strip(), script[i + 5].strip(), script[i + 6].strip().toUpperCase(), script[i + 7].strip(), script[i + 8].strip());
                                for (int j = 1; j <= 8; j++) {
                                    script[i + j] = "skip";
                                }
                            }
                            if (!exec.validate(scripti))
                                break;
                            //System.out.println(Arrays.toString(script));
                        } else
                            scripti = script[i];
                        if (!Objects.equals(scripti, "skip")) {
                            assert scripti != null;
                            String[] commandAndArg;
                            if (scripti.contains(" "))
                                commandAndArg = scripti.replaceFirst(" ", "|").split("\\|");
                            else
                                commandAndArg = new String[]{scripti.strip()};
                            MusicBand mb = null;
                            String cmd;
                            String arg;
                            if (needCrateMbObject.contains(commandAndArg[0].strip())) {
                                //System.out.println(Arrays.toString(commandAndArg));
                                ClassObjectCreator coc = new ClassObjectCreator(collection);
                                //System.out.println(req.getArgs());
                                cmd = commandAndArg[0];
                                arg = commandAndArg[1].strip();
                                //System.out.println(cmd);
                                commandAndArg = new String[]{commandAndArg[1].strip()};
                                if (cmd.equals("update")) {
                                    mb = coc.create(arg);
                                    mb = coc.setId(cmd, mb);
                                } else
                                    mb = coc.create(arg);
                                //arg = commandAndArg[1];
                                //System.out.println(mb.getName());
                            } else {
                                if (commandAndArg.length > 1)
                                    arg = commandAndArg[1];
                                else
                                    arg = "";
                                cmd = commandAndArg[0].strip();
                            }
                            if (i == 0)
                                System.out.print("-------------------\n");
                            System.out.printf("Execute command: %s\n\n", script[i]);
                            //System.out.println(cts.send(new Request(cmd, arg, collection, userName, mb)));
                            cts.send(new Request(cmd, arg, collection, userName, "password", mb));
                            System.out.print("-------------------\n");
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.print("Not enough elements\n");
                    //break;
                }
            }
            /*else{
                System.out.print("Error in script file. Recursion found\n");
                break;
            }*/
        }
        System.out.printf("File \"%s\" has been executed\n", fileName); //CommandInitializator.scripts.removeAll(new ArrayList<>(CommandInitializator.scripts));
        this.readedFile = "";
        CommandInitializator.scripts.removeAll(new ArrayList<>(CommandInitializator.scripts));
        if (isRecursion){
            System.out.print("Error in script file. Recursion found\n");
        }
        }

        return result;
    }

    /**
     * Description of command
     *
     * @return
     */

    public String description() {
        return "Executes commands from input file\nusage: execute_script file_name";
    }

}
