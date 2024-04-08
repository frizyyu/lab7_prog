package helpers;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {
    public String hashing(String pswd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-224");
        byte[] messageDigest = md.digest((pswd).getBytes("UTF-8"));
        BigInteger no = new BigInteger(1, messageDigest);

        return no.toString(16);
    }
}
