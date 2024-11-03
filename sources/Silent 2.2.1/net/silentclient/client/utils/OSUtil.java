package net.silentclient.client.utils;

public class OSUtil {
    public static boolean isMac() {
        return OSUtil.getOS().toLowerCase().startsWith("mac");
    }

    public static boolean isWindows() {
        return OSUtil.getOS().toLowerCase().startsWith("win");
    }

    public static String getOS() {
        return System.getProperty("os.name").toLowerCase();
    }
}
