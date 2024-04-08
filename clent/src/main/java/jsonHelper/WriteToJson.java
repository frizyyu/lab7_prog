package jsonHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    public WriteToJson(LinkedHashSet<MusicBand> collection, String fileName){
        this.fileName = fileName;
        this.collection = collection;
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
            json = json.substring(0, indexes.get(i)+indexrange) + String.format(", %s: ", id) + json.substring(indexes.get(i)+1+indexrange);
            indexrange += 4;
            id += 1;
        }

        FileOutputStream fileOutputStream = null;
        if (ReadFromJson.fileName == null && this.fileName == null){
            System.out.println("Enter file name to save:");
            System.out.printf("%s >>> ", CreateUser.userName);
            Scanner input = new Scanner(System.in);
            ReadFromJson.fileName = String.format("%s.json", input.nextLine());
            fileOutputStream = new FileOutputStream(new File(ReadFromJson.fileName).getAbsolutePath());
        }
        if (!Objects.equals(this.fileName, "tmp.json")){
            fileOutputStream = new FileOutputStream(new File(ReadFromJson.fileName).getAbsolutePath());
            fileOutputStream.write(json.getBytes());
            fileOutputStream.close();
            System.out.printf("Collection saved in \"%s\"%n", ReadFromJson.fileName);
        }
        else {
            try {

                if (!Files.exists(Path.of(new File("tmp.json").getAbsolutePath())))
                    Files.createFile(new File("tmp.json").toPath());
            }catch (NoSuchFileException e){
            }
            fileOutputStream = new FileOutputStream(new File("tmp.json").getAbsolutePath());
            fileOutputStream.write(json.getBytes());
            fileOutputStream.close();
        }
    }
}
