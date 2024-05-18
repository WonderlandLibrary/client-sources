package wwe;

import java.net.*;
import java.io.*;
import net.minecraft.util.*;

public class cZoo
{
    public cZoo() {
        super();
    }
    
    public static String[] fxyp() {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL("https://www.dropbox.com/s/zpflq0l143k63ry/WWERandomMessage.txt?raw=1").openStream()));
            final StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                if (cZoo.\u2004\u2001\u2005\u2009) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (Util.getOSType() != Util.EnumOS.WINDOWS) {
                return sb.toString().substring(0, sb.toString().length()).split("\n");
            }
            return sb.toString().substring(0, sb.toString().length()).split("\r\n");
        }
        catch (Exception ex) {
            return new String[] { "missingno" };
        }
    }
}
