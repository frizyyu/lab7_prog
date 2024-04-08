package validators;

/**
 * Interface for create validators
 *
 * @author frizyy
 */
public interface ValidatorInterface {
    /**
     * Check command
     * @param str input string
     * @return true if correct, else false
     */
    boolean validationCheck(String[] str);
    /**
     * Check params
     * @param str input string
     * @return true if correct, else false
     */
    String parameterCheck(String[] str);
}
