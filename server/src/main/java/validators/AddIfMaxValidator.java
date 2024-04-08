package validators;

import helpers.ElementValidCheck;
import helpers.SimpleValidationWithElement;

import java.util.Objects;

/**
 * Class for validate command AddIfMax
 *
 * @author frizyy
 */
public class AddIfMaxValidator implements ValidatorInterface{
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
