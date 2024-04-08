package validators;

import helpers.*;
import supportive.MusicBand;

import java.util.LinkedHashSet;
import java.util.Objects;

/**
 * Class for validate command AddIfMax
 *
 * @author frizyy
 */
public class AddIfMaxValidator implements ValidatorInterface{
    private final LinkedHashSet<MusicBand> collectionToCheck;

    public AddIfMaxValidator(LinkedHashSet collectionToCheck) {
        this.collectionToCheck = collectionToCheck;
    }

    /**
     * Check command
     * @param str input string
     * @return true if correct, else false
     */
    @Override
    public boolean validationCheck(String[] str) {
        if (str.length == 1){
            return true;
        }
        if (collectionToCheck.size() == 0) {
            //System.out.println("Collection is empty. There is nothing to compare it with, add an item to the collection using \"add\"");
            return true;
        }
        SimpleValidationWithElement check = new SimpleValidationWithElement();
        return check.validate(str);
    }

    /**
     * Check params
     * @param str input string
     * @return true if correct, else false
     */
    @Override
    public String parameterCheck(String[] str) {
        if (str.length != 1)
            return str[1];
        return "";
    }
}
