package commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import helpers.ContinueAction;
import helpers.CreateUsersMap;
import helpers.ServerToClient;
import helpers.ZonedDateTimeTypeAdapter;
import jsonHelper.ReadFromJson;
import jsonHelper.WriteToJson;
import org.apache.logging.log4j.Level;
import supportive.MusicBand;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;

/**
 * This class for exit
 *
 * @author frizyy
 */
public class Exit implements CommandInterface{

    private final LinkedHashSet<MusicBand> collection;
    org.apache.logging.log4j.Logger logger = new helpers.Logger().getLogger();

    /**
     *
     * @param collection our collection
     */
    public Exit(LinkedHashSet collection){
        this.collection = collection;
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
        //ServerToClient sender = new ServerToClient().send();
        /*ContinueAction cont = new ContinueAction();
        ReadFromJson reader = new ReadFromJson();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                .create();
        String json1;
        String json2;
        if (args.contains("saveit|")) {
            json1 = gson.toJson(CreateUsersMap.users.get(args.split("saveit\\|")[0]));
            json2 = gson.toJson(reader.read(args.split("saveit\\|")[0], args.split("saveit\\|")[1].replace(".json", "")));
        }
        else if (args.contains("exit|")) {
            json1 = gson.toJson(CreateUsersMap.users.get(args.split("exit\\|")[0]));
            json2 = gson.toJson(reader.read(args.split("exit\\|")[0], args.split("exit\\|")[1].replace(".json", "")));
            //System.out.println(json1);
            //System.out.println(json2);
        }
        else {
            json1 = gson.toJson(collection);
            json2 = gson.toJson(reader.read("", args.replace(".json", "")));
        }
        if (!Objects.equals(json1, json2)){
            if (!args.contains("saveit|")) {
                return "unsaved";
            }
            else {
                String path = args.split("saveit\\|")[0];
                args = args.split("saveit\\|")[1].replace(".json", "");
                //args = args.replace("saveit|", "");
                ReadFromJson.fileName = String.format("%s.json", args);
                logger.log(Level.WARN, "Save and exit");
                WriteToJson writer = new WriteToJson(collection, args, path);
                writer.write();
                Files.delete(Path.of(new File(path, "tmp.json").getAbsolutePath()));
                return "Saved. Exiting";
            }
            //}
            /*System.out.println("Collection unsaved. Save and exit? y/n");
            System.out.print(">>> ");
            int c = cont.continueAction("Saved. Exiting", "Not saved. Exit cancelled", "Action skipped. Invalid answer");
            if (c == 1) {
                Save saver = new Save(collection, ReadFromJson.fileName.replace(".json", ""));
                saver.execute(null);
                try {
                    Files.delete(Path.of(new File("tmp.json").getAbsolutePath()));
                } catch (Exception ignored) {
                }
                System.exit(0);
            }*/
        //return "Exit";
        //}
        //else {
            /*System.out.println("Are you sure to exit? y/n");
            System.out.print(">>> ");
            int c = cont.continueAction("Exiting", "Exit cancelled", "Action skipped. Invalid answer");
            if (c == 1) {
                try {
                    Files.delete(Path.of(new File("tmp.json").getAbsolutePath()));
                } catch (Exception ignored) {
                }
                System.exit(0);
            }*/
         //   return "saved";
        //}
        return "saved";
    }

    /**
     * Description of command
     *
     * @return
     */
    @Override
    public String description() {
        System.out.println("Exit program\nusage: exit");
        return null;
    }

    @Override
    public String executeWithObject(String args, MusicBand musicBand) throws IOException {
        return null;
    }
}
