package io.github.nevalackin.radium.utils;

import java.util.HashMap;
import java.util.Map;

public final class ServerUtils {

    private static final Map<String, Long> serverIpPingCache = new HashMap<>();

    private static final String HYPIXEL = "hypixel.net";

    private ServerUtils() {
    }

    public static void update(String ip, long ping) {
        serverIpPingCache.put(ip, ping);
    }

    public static long getPingToServer(String ip) {
        return serverIpPingCache.get(ip);
    }

    public static boolean isOnServer(String ip) {
        if (Wrapper.getMinecraft().isSingleplayer())
            return false;

        return getCurrentServerIP().endsWith(ip);
    }

    public static String getCurrentServerIP() {
        if (Wrapper.getMinecraft().isSingleplayer())
            return "Singleplayer";

        return Wrapper.getMinecraft().getCurrentServerData().serverIP;
    }

    public static boolean isOnHypixel() {
        return isOnServer(HYPIXEL);
    }

    public static long getPingToCurrentServer() {
        if (Wrapper.getMinecraft().isSingleplayer())
            return 0;

        return getPingToServer(getCurrentServerIP());
    }
}
