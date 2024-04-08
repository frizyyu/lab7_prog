package validators;

import helpers.SimpleValidator;

import java.util.Objects;

public class ExitValidator implements ValidatorInterface{
    /**
     *
     *
     * @author frizyyu
     * @version 1.0
     */
    SimpleValidator valid = new SimpleValidator();
    @Override
    public boolean validationCheck(String[] str) {
        return this.valid.validate(str);
    }

    @Override
    public String parameterCheck(String[] str) {
        return this.valid.paramCheck(str);
    }
}
