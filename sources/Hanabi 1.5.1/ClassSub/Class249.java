package ClassSub;

import java.util.*;
import java.io.*;

public class Class249
{
    public static final String I = "::";
    String data;
    Class298 type;
    
    
    public Class249(final String data, final Class298 type) {
        this.data = data;
        this.type = type;
    }
    
    public String getData() {
        return this.data;
    }
    
    public void sendPacketToAllClient() {
        for (final Class194 class194 : Class194.userList) {
            if (class194.getPrintWriter() != null) {
                class194.getPrintWriter().println(this.getData());
            }
        }
    }
    
    public void sendPacketToClient(final Class194 class194) {
        if (class194 != null) {
            class194.getPrintWriter().println(this.getData());
        }
        else {
            Class158.LOGGER.log(this.getData());
        }
    }
    
    public void sendPacketToServer(final PrintWriter printWriter) {
        printWriter.println(this.getData());
    }
}
