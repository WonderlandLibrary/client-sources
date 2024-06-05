package tk.rektsky;

import java.util.logging.*;

public class RektLogger
{
    public static void log(final Object... message) {
        final int key = 0;
        String output = "[RektSky] ";
        for (final Object o : message) {
            output += o.toString();
        }
        Logger.getGlobal().log(Level.INFO, output);
    }
    
    public static void error(final Object... message) {
        final int key = 0;
        String output = "[RektSky] ";
        for (final Object o : message) {
            output += o.toString();
        }
        Logger.getGlobal().log(Level.WARNING, output);
    }
}
