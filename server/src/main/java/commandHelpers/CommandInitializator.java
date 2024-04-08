package commandHelpers;
import DBHelper.ReadFromDB;
import com.sun.tools.javac.Main;
import helpers.*;
import commands.*;
import jsonHelper.ReadFromJson;
import jsonHelper.WriteToJson;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import supportive.MusicBand;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.*;

import static jsonHelper.ReadFromJson.fileName;

/**
 * This class for find command from input string
 *
 * @author frizyy
 */
public class CommandInitializator implements Runnable{
    private final HashMap<String, Object> commands;
    public static List<String> scripts = new ArrayList<>();
    public LinkedHashSet<MusicBand> collection;
    List<String> needToParse = new ArrayList<>();
    MusicBandDbManipulator mbdbm;
    String n;
    DBManipulator dbm;
    //boolean isCreated = false;
    CreateIfNotExist creator = new CreateIfNotExist();
    Logger logger = new helpers.Logger().getLogger();
    //boolean isPrintedAboutConnectionToDB = false;
    public CommandInitializator(LinkedHashSet<MusicBand> collection, String fileName, DBManipulator dbm, UserDB udb){
        this.collection = collection;
        this.dbm = dbm;
        this.mbdbm = new MusicBandDbManipulator(dbm);
        needToParse.add("add");
        needToParse.add("update");
        needToParse.add("remove_greater");
        needToParse.add("remove_lower");
        needToParse.add("add_if_max");
        needToParse.add("remove_by_id");
        needToParse.add("clear");
        needToParse.add("execute_script");

        commands = new HashMap<>();
        commands.put("help", new Help());
        commands.put("add", new Add(collection, mbdbm, udb));
        commands.put("show", new Show(collection));
        commands.put("save", new Save(collection, fileName));
        commands.put("info", new Info(collection));
        commands.put("clear", new Clear(collection, mbdbm, udb));
        commands.put("update", new Update(collection, mbdbm, udb));
        commands.put("exit", new Exit(collection));
        commands.put("remove_by_id", new RemoveById(collection, mbdbm, udb));
        commands.put("add_if_max", new AddIfMax(collection, mbdbm, udb));
        commands.put("remove_greater", new RemoveGreater(collection, mbdbm, udb));
        commands.put("remove_lower", new RemoveLower(collection, mbdbm, udb));
        commands.put("average_of_albums_count", new AverageOfAlbumsCount(collection));
        commands.put("filter_by_albums_count", new FilterByAlbumsCount(collection));
        commands.put("filter_contains_name", new FilterContainsName(collection));
        commands.put("execute_script", new ExecuteScript(collection, fileName));
        commands.put("CONNECTCLIENT", new ClienConnect(collection));
    }

    /**
     * @param inp       string for parsing
     * @param onlyCheck in order to know if it is necessary to output text to the console
     * @return
     * @throws IOException if happened some strange
     */
    public String validateAndExecute(Request inp, boolean onlyCheck, UserDB udb) throws IOException, SQLException { // returns String
                if (Objects.equals(inp.getCommand(), "testing connection")){
                    //logger.log(Level.INFO, String"Client sending request");
                    return "|";
                }
                if (Objects.equals(inp.getCommand(), "test server status")){
                    logger.log(Level.INFO, "Client connected");
                    return "CONNECTED\n";
                }
                else if (inp.getArgs().equals("SENDPLS")){
                    return "SENDPLS";
                }
                else if (inp.getCommand().equals("CHECKUSEREXIST")){
                    if (udb.checkUserByUsernameAndPassword(inp.getUser(), inp.getArgs()))
                        return "UserExist|";
                    return String.format("UserDoesntExist|%s", inp.getArgs());
                }
                else if (inp.getCommand().equals("REGISTERUSER")){
                    udb.insertUser(inp.getUser(), inp.getArgs());
                    return "New user has been registered\n";
                }
                else if (inp.getArgs().equals("STOPSENDING")){
                    //System.out.println("SADASD");
                    return "STOPSENDING";
                }
                /*else if (Objects.equals(inp.getUser(), "NEEDCOLLECTION")){
                    logger.log(Level.INFO, "Server has been restarted. Load data");
                    return String.format("NEEDCOLLECTION|%s", inp.getArgs());
                }*/
                else if (Objects.equals(inp.getCommand(), "send collection")){
                    logger.log(Level.INFO, "Client connected");
                    collection = inp.getElement();
                    fileName = inp.getArgs();
                    CreateUsersMap.users.put(inp.getUser(), collection);
                    return "CONNECTED";
                }
                else if (Objects.equals(inp.getCommand(), "CONNECTCLIENT")){ //if client connect
                    /////// load collection from file ////////
                    logger.log(Level.INFO, "Load data from file to client");
                    LinkedHashSet<MusicBand> res;
                    String fname = null;
                    File tmpFile = new File(new File(inp.getUser(), "tmp.json").getAbsolutePath());
                    if (inp.getArgs().length() != 0)
                        fileName = inp.getArgs().replace(".json", "");
                    else
                        fileName = "";
                    if (tmpFile.exists() && !inp.getArgs().contains("|Y")) {
                        if (inp.getArgs().contains("LOAD|")) {
                            fileName = "tmp";
                            mbdbm = new MusicBandDbManipulator(dbm);

                            collection = mbdbm.OnAwake(ReadFromDB.fileName);
                            CreateUsersMap.users.put(inp.getUser(), collection);
                            fname = null;
                            ReadFromDB.fileName = inp.getArgs().replace("LOAD|", "");
                        }
                        else if (!inp.getArgs().contains("|Y")){
                            return String.format("%s|FILENAME %s", false, fileName);
                        }
                    } else if (inp.getArgs().contains("|Y")) {
                        fileName = inp.getArgs().replace("|Y", "");
                        try {
                            Files.delete(Path.of(new File(inp.getUser(), "tmp.json").getAbsolutePath()));
                        }
                        catch (Exception ignored){}
                        while (true) {
                            if (Objects.equals(fileName, "tmp")) {
                                return String.format("%s|FILENAME %s", false, fileName);
                            } else
                                break;
                        }
                        mbdbm = new MusicBandDbManipulator(dbm);

                        if (fileName.contains("|CREATEFILE")) {
                            fileName = fileName.replace("|CREATEFILE", "");
                            //create new table
                            if (fileName.equals(""))
                                return String.format("%s|NEEDCREATE", fileName);
                            mbdbm.tableCreator(fileName);
                            creator.create(true, ReadFromDB.fileName, "fileChecker");

                        }
                        else if (fileName.contains("|NCREATEFILE")) {
                            fileName = fileName.replace("|NCREATEFILE", "");
                            fname = fileName;
                        }
                        if (Objects.equals(fileName, ""))
                            return String.format("%s|NEEDCREATE", fileName);
                        ReadFromDB.fileName = inp.getArgs().replace("|NCREATEFILE", "").replace("|CREATEFILE", "");
                        res = mbdbm.OnAwake(ReadFromDB.fileName);
                        creator.create(true, ReadFromDB.fileName, "fileChecker");
                        //System.out.println(creator.create(false, fileName, "fileChecker"));
                        if (!creator.create(false, fileName, "fileChecker")){
                            return String.format("%s|NEEDCREATE", fileName);
                        }
                        //res = new LinkedHashSet<>();
                        /*else if (res.size() == 1) {
                            List<MusicBand> mb = new ArrayList<>(res);
                            if (mb.get(0).getId() == -1)
                                return "ERRORINFILE";
                        }*/
                        if (res == null){
                            res = new LinkedHashSet<>();
                        }
                        collection = res;
                        CreateUsersMap.users.put(inp.getUser(), collection);
                        udb.updatelastFile(ReadFromDB.fileName, inp.getUser());
                        logger.log(Level.INFO, "File loaded");
                    }
                    else{
                        mbdbm = new MusicBandDbManipulator(dbm);

                        if (fileName.contains("|CREATEFILE")) {
                            fileName = fileName.replace("|CREATEFILE", "");
                            if (fileName.equals(""))
                                return String.format("%s|NEEDCREATE", fileName);
                            mbdbm.tableCreator(fileName);
                            creator.create(true, ReadFromDB.fileName, "fileChecker");
                        }
                        else if (fileName.contains("|NCREATEFILE")) {
                            fileName = fileName.replace("|NCREATEFILE", "");
                            fname = fileName;
                        }
                        if (Objects.equals(fileName, ""))
                            return String.format("%s|NEEDCREATE", fileName);
                        ReadFromDB.fileName = inp.getArgs().replace("|NCREATEFILE", "").replace("|CREATEFILE", "");
                        res = mbdbm.OnAwake(ReadFromDB.fileName);
                        //System.out.println(creator.create(false, fileName, "fileChecker"));
                        if (!creator.create(false, fileName, "fileChecker")){

                            return String.format("%s|NEEDCREATE", fileName);
                        }
                        //res = new LinkedHashSet<>();
                        /*else if (res.size() == 1) {
                            List<MusicBand> mb = new ArrayList<>(res);
                            if (mb.get(0).getId() == -1)
                                return "ERRORINFILE";
                        }*/
                        if (res == null){
                            res = new LinkedHashSet<>();
                        }
                        collection = res;
                        CreateUsersMap.users.put(inp.getUser(), collection);
                        udb.updatelastFile(ReadFromDB.fileName, inp.getUser());
                        logger.log(Level.INFO, "File loaded");
                    }

                    return String.format("%s|FILENAME %s", true, fileName);
                }
                else {
                    CommandInterface command = (CommandInterface) commands.get(inp.getCommand().toLowerCase());
                    if (inp.getmbElement() != null && !inp.getArgs().contains("-h"))
                    {
                        n = command.executeWithObject(inp.getArgs(), inp.getmbElement());
                    }
                    else {
                        switch (inp.getArgs()) {
                            case "" -> n = command.execute(null);
                            case "-h" -> n = command.description();
                            default -> n = command.execute(inp.getArgs());
                        }
                    }
                    //WriteToJson savertmp = new WriteToJson(collection, "tmp.json", inp.getUser());
                    //String currCommand;
                    //boolean needSaveTmp = false;
                    /*if (inp.getCommand().length() != 0) {
                        currCommand = inp.getCommand();
                        if (needToParse.contains(currCommand.split(" ")[0].strip().toLowerCase())) {
                            needSaveTmp = true;
                        }
                        if (needSaveTmp) {
                            savertmp.write();
                        }
                    }*/
                }
        //WriteToJson toBackUp = new WriteToJson(collection, "backup.json", "serverSave");
        //System.out.println(CreateUsersMap.users);
        //toBackUp.writeNum("fileChecker", ReadFromDB.fileName + ".json");
        n += "\n";
        return n;
    }

    @Override
    public void run() {

    }
}
