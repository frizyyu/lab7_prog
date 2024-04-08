package helpers;

import helpers.ContinueAction;
import jsonHelper.ReadFromJson;
import supportive.MusicBand;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class CreateIfNotExist {
    public boolean create(boolean createOrNot, String fileName, String path) throws IOException {
        //fileName =fileName;
            fileName += ".json";
            //System.out.println(new File(path, fileName).getAbsolutePath());
            File f = new File(new File("fileChecker", fileName).getAbsolutePath());
            //System.out.println(createOrNot);
            if (!f.exists()){
                if (createOrNot) {
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                    return true;
                }
                return false;
            }
            if (createOrNot)
                f.createNewFile();
            return true;
            /*FileOutputStream out = new FileOutputStream(String.format("%s", new File(path, fileName).getAbsolutePath()).replace("\\", "/"));
            out.write("".getBytes());
            out.close();*/
            //inputStream = new FileInputStream(new File(fileName).getAbsolutePath());
    }
}
