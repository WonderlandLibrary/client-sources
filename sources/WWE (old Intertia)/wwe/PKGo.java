package wwe;

import java.util.*;
import java.net.*;
import java.io.*;

public class PKGo
{
    public static ArrayList<UUID> PtUQ;
    
    public PKGo() {
        super();
    }
    
    public static void OyGy() {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL("https://www.dropbox.com/s/souy17bj9no3q65/WWECapeList.txt?raw=1").openStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                PKGo.PtUQ.add(UUID.fromString(line));
                if (PKGo.\u200e) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (PKGo.\u200e) {
                throw null;
            }
        }
        catch (Exception ex) {}
    }
    
    public static boolean UBRO(final UUID uuid) {
        return PKGo.PtUQ.contains(uuid);
    }
    
    static {
        PKGo.PtUQ = new ArrayList<UUID>();
    }
}
