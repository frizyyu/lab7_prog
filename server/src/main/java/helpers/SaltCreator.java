package helpers;

import java.security.SecureRandom;
import java.util.Arrays;

public class SaltCreator {
    public String petersburgSalt() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return Arrays.toString(bytes);
    }
}
