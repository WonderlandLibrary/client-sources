package me.xatzdevelopments.util;

public class Timer2 {

    public static long lastMs;
    
    static {
        Timer2.lastMs = System.currentTimeMillis();
    }
    
    public static void Reset() {
        Timer2.lastMs = System.currentTimeMillis();
    }
    
    public static boolean hasTimeElapsed(final long time, final boolean reset) {
        if (System.currentTimeMillis() - Timer2.lastMs > time) {
            if (reset) {
                Reset();
            }
            return true;
        }
        return false;
    }
}
