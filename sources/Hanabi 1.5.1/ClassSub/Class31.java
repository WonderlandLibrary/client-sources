package ClassSub;

import java.net.*;
import java.io.*;

public class Class31
{
    
    
    public static String getSocketMessage(final Socket socket) {
        try {
            return new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK")).readLine();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static String getInputStreamMessage(final InputStream inputStream) {
        try {
            return new BufferedReader(new InputStreamReader(inputStream, "GBK")).readLine();
        }
        catch (Exception ex) {
            Class158.LOGGER.log(ex.getMessage());
            return null;
        }
    }
}
