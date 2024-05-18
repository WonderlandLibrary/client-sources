package wwe;

import java.net.*;
import java.io.*;

public class RXVB
{
    public RXVB() {
        super();
    }
    
    public static boolean XSfe() {
        boolean b = false;
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL("https://www.dropbox.com/s/b93na80slsgxwbw/WWEVersion.txt?raw=1").openStream()));
            if (!"2.7.2".equals(bufferedReader.readLine())) {
                b = true;
            }
            bufferedReader.close();
            if (RXVB.\u200a\u2006\u2002\u200f\u200e\u2006) {
                throw null;
            }
        }
        catch (Exception ex) {}
        return b;
    }
}
