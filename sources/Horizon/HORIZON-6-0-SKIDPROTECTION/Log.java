package HORIZON-6-0-SKIDPROTECTION;

import java.security.AccessController;
import java.security.PrivilegedAction;

public final class Log
{
    private static boolean HorizonCode_Horizon_È;
    private static boolean Â;
    private static final String Ý = "org.newdawn.slick.forceVerboseLog";
    private static final String Ø­áŒŠá = "true";
    private static LogSystem Âµá€;
    
    static {
        Log.HorizonCode_Horizon_È = true;
        Log.Â = false;
        Log.Âµá€ = new DefaultLogSystem();
    }
    
    public static void HorizonCode_Horizon_È(final LogSystem system) {
        Log.Âµá€ = system;
    }
    
    public static void HorizonCode_Horizon_È(final boolean v) {
        if (Log.Â) {
            return;
        }
        Log.HorizonCode_Horizon_È = v;
    }
    
    public static void HorizonCode_Horizon_È() {
        try {
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
                @Override
                public Object run() {
                    final String val = System.getProperty("org.newdawn.slick.forceVerboseLog");
                    if (val != null && val.equalsIgnoreCase("true")) {
                        Log.Â();
                    }
                    return null;
                }
            });
        }
        catch (Throwable t) {}
    }
    
    public static void Â() {
        Log.Â = true;
        Log.HorizonCode_Horizon_È = true;
    }
    
    public static void HorizonCode_Horizon_È(final String message, final Throwable e) {
        Log.Âµá€.HorizonCode_Horizon_È(message, e);
    }
    
    public static void HorizonCode_Horizon_È(final Throwable e) {
        Log.Âµá€.HorizonCode_Horizon_È(e);
    }
    
    public static void HorizonCode_Horizon_È(final String message) {
        Log.Âµá€.HorizonCode_Horizon_È(message);
    }
    
    public static void Â(final String message) {
        Log.Âµá€.Â(message);
    }
    
    public static void Â(final String message, final Throwable e) {
        Log.Âµá€.Â(message, e);
    }
    
    public static void Ý(final String message) {
        if (Log.HorizonCode_Horizon_È || Log.Â) {
            Log.Âµá€.Ý(message);
        }
    }
    
    public static void Ø­áŒŠá(final String message) {
        if (Log.HorizonCode_Horizon_È || Log.Â) {
            Log.Âµá€.Ø­áŒŠá(message);
        }
    }
}
