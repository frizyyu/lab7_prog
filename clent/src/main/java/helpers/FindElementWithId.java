package helpers;

import supportive.MusicBand;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Class for check element in collection
 *
 * @author frizyy
 */
public class FindElementWithId {
    /**
     *
     * @param collectionToCheck collection
     * @param str string with id to check
     * @return true if element exists, else false
     */
    public MusicBand findById(LinkedHashSet collectionToCheck, String[] str){
        MusicBand checkForId = new MusicBand();
        //System.out.println(collectionToCheck);
        List<MusicBand> mb = new ArrayList<>(collectionToCheck);
        for (int i=0; i <= collectionToCheck.size()-1; i++){
            //System.out.println(mb);
            //System.out.println(mb.get(i).getId());
            if (mb.get(i).getId() == Integer.parseInt(str[0])){
                checkForId.setId(mb.get(i).getId());
                checkForId.setCreationDate(mb.get(i).getCreationDate());
                return mb.get(i);
            }
        }
        if (checkForId.getId() == 0){
            //System.out.printf("Element with id %s not found\n", str[0]);
            return null;
        }
        return null;
    }
}
