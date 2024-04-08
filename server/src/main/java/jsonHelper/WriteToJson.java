package jsonHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import supportive.MusicBand;
import helpers.*;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.*;

public class WriteToJson {
    /**
     *
     *
     * @author frizyyu
     * @version 1.0
     */
    LinkedHashSet<MusicBand> collection;
    String fileName;
    String path;
    public void writeNum(String path, String fileName) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(path, fileName).getAbsolutePath());
        fileOutputStream.write("0".getBytes());
        fileOutputStream.close();
    }
    public WriteToJson(LinkedHashSet<MusicBand> collection, String fileName, String path){
        this.fileName = fileName;
        this.collection = collection;
        this.path = path;
    }
    public void write() throws IOException {
        SortCollection sorter = new SortCollection(collection);
        sorter.sort(null);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                .create();
        String json = gson.toJson(collection);
        json = json.replace("[", "{1: ").replace("]", "}");
        List<Integer> indexes = new ArrayList<>();
        for (int i=2; i <= json.length()-7; i++){
            if (("}},{\"id\"").equals(json.substring(i - 2, i+6))){
                indexes.add(i);
            }
        }
        int indexrange = 0;
        int id = 2;
        for (int i=0; i <= indexes.size()-1; i++){
            json = json.substring(0, indexes.get(i)+indexrange) + ", 1: " + json.substring(indexes.get(i)+1+indexrange);
            indexrange += 4;
            id += 1;
        }
        for (int i = 2; i <= collection.size(); i++){
            json = json.replaceFirst(", 1: ", String.format(", %s: ", i));
        }

        FileOutputStream fileOutputStream = null;
        /*if (Objects.equals(this.fileName, "servtmp.json")){
            try {

                if (!Files.exists(Path.of(new File("servtmp.json").getAbsolutePath())))
                    Files.createFile(new File("servtmp.json").toPath());
            }catch (NoSuchFileException e){
            }
            fileOutputStream = new FileOutputStream(new File("servtmp.json").getAbsolutePath());
            fileOutputStream.write(json.getBytes());
            fileOutputStream.close();
        }*/
        //else {
        //System.out.println(ReadFromJson.fileName);
            if (ReadFromJson.fileName == null && this.fileName == null) {
                System.out.println("Enter file name to save:");
                System.out.print(">>> ");
                Scanner input = new Scanner(System.in);
                ReadFromJson.fileName = String.format("%s.json", input.nextLine());
                fileOutputStream = new FileOutputStream(new File(path, ReadFromJson.fileName).getAbsolutePath());
            }
            if (!Objects.equals(this.fileName, "tmp.json")) {
                /*if (json.equals("{1: }"))
                    json = "";*/
                //if (ReadFromJson.fileName == null)
                    //fileOutputStream = new FileOutputStream(new File(path, "ServerSave").getAbsolutePath());
                fileOutputStream = new FileOutputStream(new File(path, ReadFromJson.fileName).getAbsolutePath());
                fileOutputStream.write(json.getBytes());
                fileOutputStream.close();
                //System.out.println(new File(ReadFromJson.fileName).getAbsolutePath());
                //System.out.printf("Collection saved in \"%s\"%n", ReadFromJson.fileName);
                Logger logger = new helpers.Logger().getLogger();
                logger.log(Level.INFO, String.format("Collection saved in \"%s\"%n", ReadFromJson.fileName));
            } else {
                try {

                    if (!Files.exists(Path.of(new File(path, "tmp.json").getAbsolutePath())))
                        Files.createFile(new File(path, "tmp.json").toPath());
                } catch (NoSuchFileException e) {
                }
                fileOutputStream = new FileOutputStream(new File(path, "tmp.json").getAbsolutePath());
                fileOutputStream.write(json.getBytes());
                fileOutputStream.close();
            }
        //}
    }

    public void toServerBackup(HashMap map, String fileName) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                .create();
        String json = "";
        HashMap<String, String> resHM = new HashMap<>();
        for (Object key: map.keySet()){
            json = gson.toJson(map.get(key));
            resHM.put((String) key, json);
        }
        String res = String.format("%s", resHM);
        //OUTPUT LIKE: {null{}=[], artem=[{"id":1,"name":"asd","coordinates":{"x":12,"y":21.0},"creationDate":"13.03.2024 17:35:00","numberOfParticipants":123,"albumsCount":312,"genre":"JAZZ","studio":{"name":"adssad","address":"sdasd"}}]}
        FileOutputStream fileOutputStream = new FileOutputStream(new File("serverSave", fileName).getAbsolutePath());
        fileOutputStream.write(res.getBytes());
        fileOutputStream.close();
    }
}
