package jsonHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import supportive.MusicBand;
import helpers.*;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Scanner;

public class ReadFromJson {
    /**
     *
     *
     * @author frizyyu
     * @version 1.0
     */
    public String readedFile = "";
    public static String fileName;
    public LinkedHashSet<MusicBand> read(String fileName) throws IOException {

        ReadFromJson.fileName =fileName;
        Scanner input = new Scanner(System.in);
        LinkedHashSet<MusicBand> res;
        FileInputStream inputStream;

        while (true) {
            ReadFromJson.fileName += ".json";
            try {
                inputStream = new FileInputStream(new File(ReadFromJson.fileName).getAbsolutePath());
                break;
            } catch (FileNotFoundException e) {
                System.out.printf("File not found\nCrate file \"%s\"? y/n\n", ReadFromJson.fileName);
                ContinueAction cont = new ContinueAction();
                System.out.printf("%s >>> ", CreateUser.userName);
                int c = cont.continueAction(String.format("File \"%s\" has been created", ReadFromJson.fileName), "File has not been created", "Action skipped. Invalid answer");
                if (c == 1) {
                    FileOutputStream out = new FileOutputStream(ReadFromJson.fileName);
                    out.write("".getBytes());
                    out.close();
                    inputStream = new FileInputStream(new File(ReadFromJson.fileName).getAbsolutePath());
                    break;
                } else {
                    System.out.println("Enter file name");
                    System.out.printf("%s >>> ", CreateUser.userName);
                    ReadFromJson.fileName = input.nextLine();
                }
            }
        }


        while (true) {
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
                    for (int i = 4; i <= this.readedFile.length() - 2; i++) {
                        //System.out.println(i);
                        if (String.format("{%s: ", id).equals(this.readedFile.substring(i - 4, i)) || (i >= 6 && String.format("}, %s: ", id).equals(this.readedFile.substring(i - 6, i)))) {
                            id += 1;
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
                    }
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                            .create();
                    MusicBand myMap = gson.fromJson(toConvert, MusicBand.class);
                    res.add(myMap);
                    res.remove(null);
                    SortCollection sorter = new SortCollection(res);
                    sorter.sort(null);
                    inputStream.close();
                    buffInputStr.close();
                    return res;
                }
            } catch (JsonSyntaxException ee) {
                System.out.println("Invalid file data. Fix file and restart the program");
                inputStream.close();
                buffInputStr.close();
                System.exit(0);
            }
        }
    }

    public String getFileName(){
        return ReadFromJson.fileName;
    }

}
