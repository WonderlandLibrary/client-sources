package dev.lvstrng.argon.utils;

public class MemoryUtils {
    public static byte[] byteArr = null;

    public static void occupy() {
        byteArr = new byte[Math.abs((int) Runtime.getRuntime().freeMemory())];
    }

    public static void free() {
        byteArr = new byte[0];
        System.gc();
        System.runFinalization();
    }
}
