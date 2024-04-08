package validators;

import helpers.SimpleValidator;
/**
 * Class for validate command AverageOfAlbumsCount
 *
 * @author frizyy
 */
public class AverageOfAlbumsCountValidator implements ValidatorInterface {
    SimpleValidator valid = new SimpleValidator();
    /**
     * Check command
     * @param str input string
     * @return true if correct, else false
     */
    @Override
    public boolean validationCheck(String[] str) {
        return this.valid.validate(str);
    }
    /**
     * Check params
     * @param str input string
     * @return true if correct, else false
     */
    @Override
    public String parameterCheck(String[] str) {
        return this.valid.paramCheck(str);
    }
}
