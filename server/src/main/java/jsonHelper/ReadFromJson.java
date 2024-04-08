package jsonHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import supportive.MusicBand;
import helpers.*;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.Arrays;
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
    
    public Integer readNum(String path, String fileName) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(path, fileName).getAbsolutePath());
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line);
            }
        }
        return Integer.parseInt(resultStringBuilder.toString());
    }
    
    public LinkedHashSet<MusicBand> read(String path, String fileName) throws IOException {


        ReadFromJson.fileName =fileName;
        Scanner input = new Scanner(System.in);
        LinkedHashSet<MusicBand> res;
        FileInputStream inputStream;

        while (true) {
            ReadFromJson.fileName += ".json";
            try {
                //System.out.println(new File(path, ReadFromJson.fileName).getAbsolutePath());
                inputStream = new FileInputStream(new File(path, ReadFromJson.fileName).getAbsolutePath());
                break;
            } catch (FileNotFoundException e) {
                return null;
                /*System.out.printf("File not found\nCrate file \"%s\"? y/n\n", ReadFromJson.fileName);
                ContinueAction cont = new ContinueAction();
                System.out.print(">>> ");
                int c = cont.continueAction(String.format("File \"%s\" has been created", ReadFromJson.fileName), "File has not been created", "Action skipped. Invalid answer");
                if (c == 1) {
                    FileOutputStream out = new FileOutputStream(ReadFromJson.fileName);
                    out.write("".getBytes());
                    out.close();
                    inputStream = new FileInputStream(new File(ReadFromJson.fileName).getAbsolutePath());
                    break;
                } else {
                    System.out.println("Enter file name");
                    System.out.print(">>> ");
                    ReadFromJson.fileName = input.nextLine();
                }*/
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
                    for (int i = 0; i < this.readedFile.split("\"id\":").length; i++){
                        this.readedFile = this.readedFile.replace(",,", ",").replace(String.format("{%s: ", i), "{1: ").replace(String.format("}, %s: ", i), "}, 1: ");
                    }
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
                    if (!this.readedFile.equals("")) {
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
                res = new LinkedHashSet<MusicBand>();
                MusicBand mb = new MusicBand();
                mb.setId(-1);
                res.add(mb);
                return res;
            }
        }
    }

    public String getFileName(){
        return ReadFromJson.fileName;
    }

}
