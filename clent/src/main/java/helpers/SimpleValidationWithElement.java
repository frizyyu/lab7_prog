package helpers;

import java.util.Objects;

/**
 * Class for simple validation of command
 *
 * @author frizyy
 */
public class SimpleValidationWithElement {
    /**
     * Execute method
     * @param str string eith element
     * @return true if element is valid, else false
     */
    public boolean validate(String[] str){
        if (str.length == 2 && Objects.equals(str[1], "-h"))
            return true;
        else if (str.length == 2){
            ElementValidCheck check = new ElementValidCheck();
            return check.elementValidCheck(str);
        }
        System.out.println("Missing argument");
        return false;
    }
}
