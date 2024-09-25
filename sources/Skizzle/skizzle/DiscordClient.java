/*
 * Decompiled with CFR 0.150.
 */
package skizzle;

import skizzle.DiscordRP;

public class DiscordClient {
    public DiscordRP discordRP = new DiscordRP();
    public static DiscordClient INSTANCE = new DiscordClient();

    public DiscordRP getDiscordRP() {
        DiscordClient Nigga;
        return Nigga.discordRP;
    }

    public void init() {
        DiscordClient Nigga;
        Nigga.discordRP.start();
    }

    public static DiscordClient getInstance() {
        return INSTANCE;
    }

    public void shutdown() {
        DiscordClient Nigga;
        Nigga.discordRP.shutdown();
    }

    public DiscordClient() {
        DiscordClient Nigga;
    }
}

