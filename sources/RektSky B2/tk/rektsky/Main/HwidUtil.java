package tk.rektsky.Main;

import java.security.*;
import java.io.*;

public class HwidUtil
{
    public static String getHwid() throws Exception {
        final String hwid = SHA1(System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("COMPUTERNAME") + System.getProperty("user.name"));
        return hwid;
    }
    
    private static String SHA1(final String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }
    
    private static String convertToHex(final byte[] data) {
        final StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; ++i) {
            int halfbyte = data[i] >>> 4 & 0xF;
            int var4 = 0;
            do {
                if (halfbyte >= 0 && halfbyte <= 9) {
                    buf.append((char)(48 + halfbyte));
                }
                else {
                    buf.append((char)(97 + (halfbyte - 10)));
                }
                halfbyte = (data[i] & 0xF);
            } while (var4++ < 1);
        }
        return buf.toString();
    }
}
