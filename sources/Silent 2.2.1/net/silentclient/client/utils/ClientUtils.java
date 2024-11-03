package net.silentclient.client.utils;

public class ClientUtils {
    public static boolean isDevelopment() {
        for(StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if(element.getClassName().equals("GradleStart")) {
                return true;
            }
        }

        return false;
    }
}
