package validators;
import helpers.*;

import java.util.Objects;

public class FilterByAlbumsCountValidator implements ValidatorInterface{
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
        else if (str.length == 2){
            try {
                Integer.parseInt(str[1]);
                return true;
            }
            catch (IndexOutOfBoundsException er){
                System.out.println("Missing argument");
                return false;
            }
            catch (NumberFormatException e){
                System.out.println("Argument invalid");
                return false;
            }
        }
        System.out.println("Missing argument");
        return false;
    }

    @Override
    public String parameterCheck(String[] str) {
        return sv.paramCheck(str);
    }
}
