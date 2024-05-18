package HORIZON-6-0-SKIDPROTECTION;

import java.net.URLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class HWIDManager
{
    public static String HorizonCode_Horizon_È(final byte[] data) {
        final StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; ++i) {
            int halfbyte = data[i] >>> 4 & 0xF;
            int two_halfs = 0;
            do {
                if (halfbyte >= 0 && halfbyte <= 9) {
                    buf.append((char)(48 + halfbyte));
                }
                else {
                    buf.append((char)(97 + (halfbyte - 10)));
                }
                halfbyte = (data[i] & 0xF);
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
    
    public static String HorizonCode_Horizon_È(final String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return HorizonCode_Horizon_È(sha1hash);
    }
    
    public static boolean Â(final String hwid) {
        try {
            final URL url = new URL("http://horizonco.de/horizonclient/hwid.php");
            final URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            connection.setRequestProperty("User-Agent", "Horizon Client");
            final BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(":")) {
                    final String[] split = line.split(":");
                    if (split[0].equalsIgnoreCase(hwid)) {
                        final String s = split[1];
                        return true;
                    }
                    continue;
                }
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        return false;
    }
}
