/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS;

import drunkclient.beta.IMPL.Module.Module;
import net.minecraft.client.multiplayer.ServerData;

public class ServerUtils {
    private static boolean hypixel;
    private static boolean fakeHypixel;

    public static void checkHypixel(ServerData var0) {
        if (var0.serverIP.toLowerCase().contains("hypixel.net") && !fakeHypixel) {
            hypixel = true;
        }
        hypixel = false;
    }

    public static boolean isHypixel() {
        if (hypixel) return true;
        if (Module.mc.isSingleplayer()) return true;
        return false;
    }
}

