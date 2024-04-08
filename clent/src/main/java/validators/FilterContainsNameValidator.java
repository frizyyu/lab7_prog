package validators;

import helpers.*;

import java.util.Objects;

public class FilterContainsNameValidator implements ValidatorInterface {
    /**
     *
     *
     * @author frizyyu
     * @version 1.0
     */
    SimpleValidator sv = new SimpleValidator();

    @Override
    public boolean validationCheck(String[] str) {
        if (str.length == 2 && Objects.equals(str[1], "-h"))
            return true;
        else if (str.length == 2) {
            return true;
        }
        System.out.println("Missing argument");
        return false;
    }

    @Override
    public String parameterCheck(String[] str) {
        return sv.paramCheck(str);
    }
}
