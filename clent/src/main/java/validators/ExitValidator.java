package validators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import helpers.*;
import jsonHelper.ReadFromJson;
import jsonHelper.WriteToJson;
import supportive.MusicBand;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;

public class ExitValidator implements ValidatorInterface{
    /**
     *
     *
     * @author frizyyu
     * @version 1.0
     */
    SimpleValidator valid = new SimpleValidator();
    LinkedHashSet<MusicBand> collection;
    public ExitValidator(LinkedHashSet collection){
        this.collection = collection;
    }
    @Override
    public boolean validationCheck(String[] str) throws IOException {
        if (this.valid.validate(str)) {
            /*ContinueAction cont = new ContinueAction();
            ReadFromJson reader = new ReadFromJson();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                    .create();
            String json1 = gson.toJson(collection);
            String json2 = gson.toJson(reader.read(ReadFromJson.fileName.replace(".json", "")));
            if (!Objects.equals(json1, json2)){
                System.out.println("Collection unsaved. Save and exit? y/n");
                System.out.printf("%s >>> ", CreateUser.userName);
                int c = cont.continueAction("Saved. Exiting", "Not saved. Exit cancelled", "Action skipped. Invalid answer");
                if (c == 1) {
                    WriteToJson writer = new WriteToJson(collection, null);
                    writer.write();
                    Files.delete(Path.of(new File("tmp.json").getAbsolutePath()));
                    //Save saver = new Save(collection, ReadFromJson.fileName.replace(".json", ""));
                    //saver.execute(null);
                    try {
                        Files.delete(Path.of(new File("tmp.json").getAbsolutePath()));
                    } catch (Exception ignored) {
                    }
                    return true;
                }
            }
            else {
                System.out.print("Are you sure to exit? y/n\n>>> ");
                cont = new ContinueAction();
                int c = cont.continueAction("Bye bye", "Exit cancelled", "Action skipped. Invalid answer");
                if (c == 1) {
                    return true;
                } else if (c == -1) {
                    return false;
                } else
                    return false;
            }
        }*/
            return true;
        }
        return false;

    }

    @Override
    public String parameterCheck(String[] str) {
        return this.valid.paramCheck(str);
    }
}
