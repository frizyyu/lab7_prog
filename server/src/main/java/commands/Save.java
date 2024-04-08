package commands;

import helpers.CreateIfNotExist;
import jsonHelper.ReadFromJson;
import jsonHelper.WriteToJson;
import org.apache.logging.log4j.Level;
import supportive.MusicBand;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;

import static jsonHelper.ReadFromJson.fileName;

/**
 * This class for save collection in file
 *
 * @author frizyy
 */
public class Save implements CommandInterface {
    private final LinkedHashSet<MusicBand> collection;
    String fileName;
    public Save(LinkedHashSet collection, String fileName) {
        this.collection = collection;
        this.fileName = fileName;
    }
    /**
     * Execute command
     *
     * @param args null, because command hasn`t got arguments
     * @return
     * @throws IOException if happened some strange
     */
    @Override
    public String execute(String args) throws IOException {
        //System.out.println(args);
        /*String fName = ReadFromJson.fileName;
        ReadFromJson.fileName = "backup";
        CreateIfNotExist creator = new CreateIfNotExist();
        creator.create(true, "backup", "serverSave");
        WriteToJson writer = new WriteToJson(collection, "backup", "serverSave");
        writer.write();
        ReadFromJson.fileName = fName;
        //System.out.println("WWWWWWW");
        //System.out.println("QQQQQ");
        try {
            Files.delete(Path.of(new File("tmp.json").getAbsolutePath()));
            Files.delete(Path.of(new File("servtmp.json").getAbsolutePath()));
        }
        catch (Exception ignored){}
        org.apache.logging.log4j.Logger logger = new helpers.Logger().getLogger();
        //logger.log(Level.INFO, "Saved");
        return "";*/
        return "";
    }

    /**
     * Description of command
     *
     * @return
     */
    @Override
    public String description() {
        System.out.println("Save collection to .json file\nusage: save");
        return null;
    }

    @Override
    public String executeWithObject(String args, MusicBand musicBand) throws IOException {
        return null;
    }
}
