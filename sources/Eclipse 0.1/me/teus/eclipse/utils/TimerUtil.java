package me.teus.eclipse.utils;

import org.lwjgl.Sys;

public class TimerUtil {

    public static long lastMS = System.currentTimeMillis();
    public static void reset() {
        lastMS = System.currentTimeMillis();
    }

    public static boolean hasTimeElapsed(long time) {
        if (System.currentTimeMillis() - lastMS > time) {
            return true;
        }

        return false;
    }

}
