package validators;
import helpers.*;

/**
 * Class for validate command Add
 *
 * @author frizyy
 */
public class AddValidator implements ValidatorInterface {
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
