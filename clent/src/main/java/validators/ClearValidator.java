package validators;

import helpers.*;

import java.util.Objects;

public class ClearValidator implements ValidatorInterface{
    SimpleValidator valid = new SimpleValidator();
    @Override
    public boolean validationCheck(String[] str) {
        if (this.valid.validate(str)){
            System.out.print("Are you sure about deleting the collection? y/n\n>>> ");
            ContinueAction cont = new ContinueAction();
            int c = cont.continueAction("Clear collection..", "The collection has not been cleared", "Action skipped. Invalid answer");
            if (c == 1){
                return true;
            } else if (c == -1) {
                return false;
            }
            else
                return false;
        }
        else
            return false;
    }

    @Override
    public String parameterCheck(String[] str) {
        return this.valid.paramCheck(str);
    }
}
