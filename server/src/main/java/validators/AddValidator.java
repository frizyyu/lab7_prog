package validators;

import com.google.common.base.Splitter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import supportive.*;
import helpers.*;

import java.time.ZonedDateTime;
import java.util.*;
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
