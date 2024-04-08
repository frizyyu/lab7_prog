package validators;

import supportive.MusicBand;
import helpers.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

public class RemoveByIdValidator implements ValidatorInterface{
    /**
     *
     *
     * @author frizyyu
     * @version 1.0
     */
    private final LinkedHashSet<MusicBand> collectionToCheck;
    public RemoveByIdValidator (LinkedHashSet collection) {
        this.collectionToCheck = collection;
    }
    @Override
    public boolean validationCheck(String[] str) {
        if (str.length == 2 && Objects.equals(str[0], "-h"))
            return true;

        try {
                Long.parseLong(str[1]);
            }
        catch (NumberFormatException e){
            System.out.println("Argument is invalid");
            return false;
        }
        catch (ArrayIndexOutOfBoundsException er){
            System.out.println("Missing argument");
            return false;
        }
        FindElementWithId finder = new FindElementWithId();
        if (finder.findById(collectionToCheck, new String[]{str[1]}) == null)
            return false;
        else {
            return true;
        }
    }

    @Override
    public String parameterCheck(String[] str) {
        return str[1];
    }
}
