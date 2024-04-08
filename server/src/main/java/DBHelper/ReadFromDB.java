package DBHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import helpers.DBManipulator;
import helpers.MusicBandDbManipulator;
import helpers.SortCollection;
import helpers.ZonedDateTimeTypeAdapter;
import jsonHelper.ReadFromJson;
import supportive.MusicBand;

import java.io.*;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Scanner;

public class ReadFromDB {
    public String readedFile = "";
    public static String fileName;
    public LinkedHashSet<MusicBand> read(String path, String fileName, DBManipulator dbm) throws IOException {


        ReadFromDB.fileName = fileName;
        LinkedHashSet<MusicBand> res = new LinkedHashSet<>();
        MusicBandDbManipulator mbdbm = new MusicBandDbManipulator(dbm);
        String fromDB;
        String jsonTypeStr = "";

        while (true) {
            //ReadFromJson.fileName += ".json";
            fromDB = mbdbm.selectAll(fileName);
            if (fromDB != null)
                break;
            else
                return null;
        }
        jsonTypeStr += fromDB.replace(": ", "\":").replace("\" ", "\",\"").replace("} ", "},\"")
                .replace(" numberOfParticipants", ",\"numberOfParticipants").replace(" albumsCount", ",\"albumsCount")
                .replace(" genre", ",\"genre");
        //System.out.println(jsonTypeStr);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                .create();
        for (String elem: jsonTypeStr.split(",,,")){
            MusicBand myMap = gson.fromJson(elem, MusicBand.class);
            res.add(myMap);
            res.remove(null);
        }
        SortCollection sorter = new SortCollection(res);
        sorter.sort(null);
        return res;

        /*while (true) {
            BufferedInputStream buffInputStr = null;
            try {
                buffInputStr
                        = new BufferedInputStream(
                        inputStream);

                while (buffInputStr.available() > 0) {


                    char c = (char) buffInputStr.read();

                    this.readedFile += c;
                }
                res = new LinkedHashSet<MusicBand>();
                //System.out.println(this.readedFile);
                int id = 1;
                String toConvert = null;
                if (!Objects.equals(this.readedFile, "{}")) {
                    for (int i = 0; i < this.readedFile.split("\"id\":").length; i++){
                        this.readedFile = this.readedFile.replace(",,", ",").replace(String.format("{%s: ", i), "{1: ").replace(String.format("}, %s: ", i), "}, 1: ");
                    }*/
                    //System.out.println(this.readedFile);
                    /*for (int i = 4; i <= this.readedFile.length() - 2; i++) {
                        //System.out.println(this.readedFile);
                        //if (i >= 6)
                            //System.out.println(this.readedFile.substring(i - 6, i));
                        if ("{%s: ".equals(this.readedFile.substring(i - 4, i)) || (i >= 6 && "}, %s: ".equals(this.readedFile.substring(i - 6, i)))) {
                            id += 1;
                            //System.out.printf("}, %d: %n", id);
                            //System.out.println(this.readedFile.substring(i - 8, i));
                            if (toConvert != null) {
                                Gson gson = new GsonBuilder()
                                        .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                                        .create();
                                MusicBand myMap = gson.fromJson(toConvert.substring(0, toConvert.length() - 5), MusicBand.class);
                                res.add(myMap);
                            }
                            toConvert = "";
                        }
                        toConvert += this.readedFile.substring(i, i + 1);
                        //System.out.println(toConvert);
                    }*/
                    //System.out.println(this.readedFile.substring(4, this.readedFile.length() - 1));
                    /*if (!this.readedFile.equals("")) {
                        String[] r = this.readedFile.substring(4, this.readedFile.length() - 1).split(", 1: ");
                        //System.out.println(Arrays.toString(r));
                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                                .create();
                        for (String elem: r){
                            MusicBand myMap = gson.fromJson(elem, MusicBand.class);
                            res.add(myMap);
                            res.remove(null);
                        }
                        //System.out.println(res);
                    }
                    //System.out.println(toConvert);
                    SortCollection sorter = new SortCollection(res);
                    sorter.sort(null);
                    inputStream.close();
                    buffInputStr.close();
                    //System.out.println(res);
                    return res;
                }
            } catch (JsonSyntaxException ee) { //if invalid, disconnect user from server and fix file
                /*System.out.println("Invalid file data. Fix file and restart the program");
                inputStream.close();
                buffInputStr.close();
                System.exit(0);*/
                /*res = new LinkedHashSet<MusicBand>();
                MusicBand mb = new MusicBand();
                mb.setId(-1);
                res.add(mb);
                return res;
            }
        }*/
    }

    public String getFileName(){
        return ReadFromDB.fileName;
    }

}
