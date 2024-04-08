package validators;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;

public class ExecuteScriptValidator implements ValidatorInterface{
    /**
     *
     *
     * @author frizyyu
     * @version 1.0
     */
    @Override
    public boolean validationCheck(String[] str) {
        if (str.length == 2 && Objects.equals(str[1], "-h"))
            return true;
        else if (str.length == 2){
            File f = new File(new File(str[1]).getAbsolutePath());
            if(f.exists() && !f.isDirectory()) {
                return true;
            }
            System.out.println("File not exist");
            return false;
        }
        System.out.println("Missing argument");
        return false;
    }

    @Override
    public String parameterCheck(String[] str) {
        return str[1];
    }
}
