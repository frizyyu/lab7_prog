package helpers;

import org.apache.commons.lang3.ArrayUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class PasswordHasher {
    String pswd;

    String encrypt(byte[] sequence) {
        return Arrays.toString(sequence);
    }

    public PasswordHasher(String pswd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.pswd = pswd;
    }
    /*public String petersburgSalt() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return Arrays.toString(bytes);

    }*/

    public String hashing() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-224");

        //String salt = petersburgSalt();
        String pepper = "*63&^mVLC(#";
        byte[] messageDigest = md.digest((this.pswd + pepper).getBytes("UTF-8"));
        //System.out.println((this.pswd + pepper + salt).getBytes("UTF-8").length);
        //System.out.println(messageDigest.length);
        //System.out.println(encrypt(ArrayUtils.addAll(md.digest((pepper).getBytes("UTF-8")), ArrayUtils.addAll(md.digest((this.pswd).getBytes("UTF-8")), md.digest((salt).getBytes("UTF-8"))))));
        //System.out.println(Arrays.toString(ArrayUtils.addAll(md.digest((pepper).getBytes("UTF-8")), ArrayUtils.addAll(md.digest((this.pswd).getBytes("UTF-8")), md.digest((salt).getBytes("UTF-8"))))));
        //System.out.println(Arrays.toString(messageDigest));
        BigInteger no = new BigInteger(1, messageDigest);

        return no.toString(16);
    }
}
