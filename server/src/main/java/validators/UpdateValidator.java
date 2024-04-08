package validators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import helpers.ElementValidCheck;
import supportive.MusicBand;
import helpers.ZonedDateTimeTypeAdapter;
import helpers.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

public class UpdateValidator implements ValidatorInterface{
    /**
     *
     *
     * @author frizyyu
     * @version 1.0
     */
    private final LinkedHashSet<MusicBand> collectionToCheck;

    public UpdateValidator(LinkedHashSet collection){
        this.collectionToCheck = collection;
    }
    @Override
    public boolean validationCheck(String[] sstr) {
        if (sstr.length == 2 && Objects.equals(sstr[1], "-h"))
            return true;
        else if (sstr.length == 2) {
                sstr[1] = sstr[1].replaceFirst(" ", "|");
                String[] str = sstr[1].split("\\|");

                /*if (str.length == 1) {
                    //System.out.println("Missing second argument");
                    //return false;
                }*/
                try{
                    Long.parseLong(str[0]);
                }
                catch (NumberFormatException e){
                    System.out.println("first argument is invalid");
                    return false;
                }
                FindElementWithId finder = new FindElementWithId();
                if (finder.findById(collectionToCheck, str) == null)
                    return false;
                else {
                    if (str.length != 1){
                        ElementValidCheck check = new ElementValidCheck();
                        return check.elementValidCheck(str);
                    }
                    else
                        return true;
                }
            }
        System.out.println("Missing arguments");
        return false;
    }

    @Override
    public String parameterCheck(String[] str) {
        return str[1];
    }
}
