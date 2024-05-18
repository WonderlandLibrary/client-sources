package wwe;

import java.net.*;
import java.io.*;

public class WzVI
{
    public WzVI() {
        super();
    }
    
    public static String Iean() {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL("https://www.dropbox.com/s/rdju4l5j9plyl1a/WWEImportantText.txt?raw=1").openStream()));
            final String line = bufferedReader.readLine();
            bufferedReader.close();
            return line;
        }
        catch (Exception ex) {
            return "";
        }
    }
}
