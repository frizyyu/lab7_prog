package helpers;

import DBHelper.ReadFromDB;
import supportive.MusicBand;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TheQueryFormer {
    public boolean add(MusicBand myMap, MusicBandDbManipulator mbdbm){
        ArrayList<Object> itms = new ArrayList<>();
        itms.add(myMap.getName());
        itms.add((myMap.getCoordinates()).getX());
        itms.add((myMap.getCoordinates()).getY());
        itms.add(DateTimeFormatter.ofPattern("dd.MM.uuuu-kk`mm`ss").format(myMap.getCreationDate())); //не
        itms.add(myMap.getNumberOfParticipants());
        itms.add(myMap.getAlbumsCount());
        itms.add(String.format("%s", myMap.getGenre()));
        itms.add((myMap.getStudio()).getName());
        itms.add((myMap.getStudio()).getAddress());
        return mbdbm.insertElement(itms, ReadFromDB.fileName);
    }

    public boolean remove(MusicBand myMap, MusicBandDbManipulator mbdbm){
        ArrayList<Object> itms = new ArrayList<>();
        if (myMap != null) {
            itms.add(myMap.getId());
            //System.out.println(ReadFromDB.fileName);
            return mbdbm.deleteElement(itms, ReadFromDB.fileName);
        }
        return false;
    }

    public boolean removeAll(MusicBandDbManipulator mbdbm){
        return mbdbm.deleteAll(ReadFromDB.fileName);
    }

    public boolean update(MusicBand myMap, MusicBandDbManipulator mbdbm){
        ArrayList<Object> itms = new ArrayList<>();
        if (myMap != null) {
            itms.add(myMap.getName());
            itms.add((myMap.getCoordinates()).getX());
            itms.add((myMap.getCoordinates()).getY());
            itms.add(DateTimeFormatter.ofPattern("dd.MM.uuuu-kk`mm`ss").format(myMap.getCreationDate())); //не
            itms.add(myMap.getNumberOfParticipants());
            itms.add(myMap.getAlbumsCount());
            itms.add(String.format("%s", myMap.getGenre()));
            itms.add((myMap.getStudio()).getName());
            itms.add((myMap.getStudio()).getAddress());
            return mbdbm.updateElement(itms, ReadFromDB.fileName);
        }
        return false;
    }
}
