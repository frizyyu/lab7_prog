package helpers;

import java.util.Objects;


/**
 * Class for validate
 *
 * @author frizyy
 */
public class SimpleValidator {
    public boolean validate(String[] str) {
        if (str.length == 1 || (str.length == 2 && Objects.equals(str[1], "-h")))
            return true;
        System.out.println("Invalid argument");
        return false;
    }

    public String paramCheck(String[] str) {
        if (str.length == 1)
            return "";
        return str[1];
    }
}
