package helpers;

import DBHelper.ReadFromDB;
import supportive.MusicBand;

import java.io.*;
import java.util.*;

public class CreateUsersMap {
    public static HashMap<String, LinkedHashSet<MusicBand>> users = new HashMap<>();
    String readedFile;
    String username;
    DBManipulator dbm;
    String fileName;

    public CreateUsersMap(DBManipulator dbm, String username, String fileName) throws IOException {
        //users = new HashMap<String, LinkedHashSet<MusicBand>>();
        //System.out.println("CREATING");
        this.dbm = dbm;
        this.username = username;
        this.fileName = fileName;
        //String s = "SALES:0,SALE_PRODUCTS:1,EXPENSES:2,EXPENSES_ITEMS:3";
        /*CreateIfNotExist creator = new CreateIfNotExist();
        creator.create(true, "backup", "serverSave");
        FileInputStream inputStream = new FileInputStream(new File("serverSave", "backup.json").getAbsolutePath());
        BufferedInputStream buffInputStr = null;
        buffInputStr
                = new BufferedInputStream(
                inputStream);

        while (buffInputStr.available() > 0) {


            char c = (char) buffInputStr.read();

            readedFile += c;
        }
        LinkedHashSet<MusicBand> resLHS = new LinkedHashSet<>();
        try {
            readedFile = readedFile.replaceFirst("null\\{", "{");
            //System.out.println(readedFile);
            //System.out.println(readedFile.replaceFirst("\\{", "").substring(0, readedFile.length() - 2).replace("},{", "}},{{"));
            String[] pairs = readedFile.replaceFirst("\\{", "").substring(0, readedFile.length() - 2).replace("},{", "}},{{").split(", "); // будет сплитить всё, не только по элементам
            //System.out.println(Arrays.toString(pairs));
            for (int i = 0; i < pairs.length; i++) {
                String pair = pairs[i];
                String[] keyValue = pair.split("=");
                //System.out.println(Arrays.toString(keyValue[1].substring(1, keyValue[1].length() - 1).split("},\\{")));
                String[] valueList = keyValue[1].substring(1, keyValue[1].length() - 1).split("},\\{");
                resLHS = new LinkedHashSet<>();
                for (String elem: valueList) {
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                            .create();
                    //System.out.println(elem);
                    MusicBand myMap = gson.fromJson(elem, MusicBand.class);
                    resLHS.add(myMap);
                }
                users.put(keyValue[0], resLHS); //протестить
                //System.out.println(users);
            }
        } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException | NullPointerException ignored){

        }
        //System.out.println(users);
        /*FileInputStream inputStream = new FileInputStream(new File("users.json").getAbsolutePath());
        BufferedInputStream buffInputStr = null;
        buffInputStr
                = new BufferedInputStream(
                inputStream);

        while (buffInputStr.available() > 0) {


            char c = (char) buffInputStr.read();

            readedFile += c;
        }
        System.out.println(readedFile.replace("null", ""));
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        JsonMappingException.Reference reference = mapper.readValue(readedFile, JsonMappingException.Reference.class);
        System.out.println(reference + " ASDADS");
        HashMap car = mapper.readValue(new File("users.json"), HashMap.class);
        System.out.println(car);*/
        /*HashMap map = new ObjectMapper().readValue(readedFile, HashMap.class);
        //System.out.println(map.);
        for (int i=0; i<map.size(); i++){
            LinkedHashSet sett = LinkedHashSet.class.cast(map.get(map.keySet().toArray()[i].toString()));
            ArrayList mb = new ArrayList<>(sett);
            LinkedHashSet<MusicBand> rs = new LinkedHashSet<>();
            for (int j=0; j < mb.size(); j++){
                rs.add((MusicBand) mb.get(j));
            }
            users.put(map.keySet().toArray()[i].toString(), rs);
        }
        System.out.println(users);*/
    }

    public void create() throws IOException {
        //System.out.println(fileName);
        if (fileName != null){
            ReadFromDB DBreader = new ReadFromDB();
            LinkedHashSet<MusicBand> res = DBreader.read("", fileName, dbm);
            if (res != null)
                users.put(username, DBreader.read("", fileName, dbm));
            else users.put(username, new LinkedHashSet<>());
        }
        else
            users.put(username, new LinkedHashSet<>());
    }
}
