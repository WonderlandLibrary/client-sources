package Ascii4UwUWareClient.API.Auth;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hwid {

    public static String getHWID() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final String s = textToSHA1(String.valueOf(System.getenv("PROCESSOR_IDENTIFIER")) + System.getenv("COMPUTERNAME") + System.getProperty("user.name"));;
        return s;
    }

    public static String GetPcName(){
        final String s = System.getProperty("user.name").trim();

        return s;
    }

    public static String GetUserName(){
        final String s = System.getProperty("user.name").trim();

        return s;
    }

    public static String textToSHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return bytesToHex(sha1hash);
    }

    private static String bytesToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        int i = 0;
        while (i < data.length) {
            int halfbyte = data[i] >>> 4 & 15;
            int two_halfs = 0;
            do {
                if (halfbyte >= 0 && halfbyte <= 9) {
                    buf.append((char)(48 + halfbyte));
                } else {
                    buf.append((char)(97 + (halfbyte - 10)));
                }
                halfbyte = data[i] & 15;
            } while (two_halfs++ < 1);
            ++i;
        }
        return buf.toString();
    }


}
