package validators;

import helpers.ElementValidCheck;
import helpers.SimpleValidationWithElement;

import java.util.Objects;

public class RemoveLowerValidator implements ValidatorInterface{
    /**
     *
     *
     * @author frizyyu
     * @version 1.0
     */
    @Override
    public boolean validationCheck(String[] str) {
        if (str.length == 1){
            return true;
        }

        SimpleValidationWithElement check = new SimpleValidationWithElement();
        return check.validate(str);
    }

    @Override
    public String parameterCheck(String[] str) {
        if (str.length != 1)
            return str[1];
        return "";
    }
}
