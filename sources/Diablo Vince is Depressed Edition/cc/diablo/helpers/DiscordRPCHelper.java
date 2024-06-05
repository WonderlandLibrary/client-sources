/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.arikia.dev.drpc.DiscordEventHandlers
 *  net.arikia.dev.drpc.DiscordRPC
 *  net.arikia.dev.drpc.DiscordRichPresence
 *  net.arikia.dev.drpc.DiscordRichPresence$Builder
 */
package cc.diablo.helpers;

import cc.diablo.Main;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class DiscordRPCHelper {
    public static int killCount = 0;
    public static String serverIp = "none";
    public static long timestamp = System.currentTimeMillis() / 1000L;

    public static void updateRPC() {
        try {
            DiscordRPC.discordInitialize((String)"916790950322376704", (DiscordEventHandlers)new DiscordEventHandlers(), (boolean)true);
            DiscordRichPresence rich = new DiscordRichPresence.Builder("intent.store").setDetails("Server: " + serverIp + " | Kills : " + killCount).setStartTimestamps(timestamp).setBigImage("logo", Main.version + " (" + Main.buildType + ")").build();
            DiscordRPC.discordUpdatePresence((DiscordRichPresence)rich);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

