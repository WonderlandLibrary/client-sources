// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds;

import club.minnced.discord.rpc.DiscordEventHandlers;
import me.perry.mcdonalds.features.modules.client.RPC;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.perry.mcdonalds.features.modules.Module;

public class Discord extends Module
{
    public static DiscordRichPresence presence;
    private static final DiscordRPC rpc;
    private static RPC discordrpc;
    private static Thread thread;
    
    public Discord(final String name, final String description, final Category category, final boolean hasListener, final boolean hidden, final boolean alwaysListening) {
        super(name, description, category, hasListener, hidden, alwaysListening);
    }
    
    public static void start() {
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        Discord.rpc.Discord_Initialize("833348373708275712", handlers, true, "");
        Discord.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        Discord.presence.details = "Smoking childs on the McDonalds playground";
        Discord.presence.state = "Smoking childs";
        Discord.presence.largeImageKey = "large";
        Discord.presence.largeImageText = "https://discord.gg/snDa88Vjfz";
        Discord.rpc.Discord_UpdatePresence(Discord.presence);
        (Discord.thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                Discord.rpc.Discord_RunCallbacks();
                Discord.presence.details = "Smoking childs on the McDonalds playground";
                Discord.presence.state = "https://discord.gg/snDa88Vjfz";
                Discord.rpc.Discord_UpdatePresence(Discord.presence);
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException ex) {}
            }
        }, "RPC-Callback-Handler")).start();
    }
    
    public static void stop() {
        if (Discord.thread != null && !Discord.thread.isInterrupted()) {
            Discord.thread.interrupt();
        }
        Discord.rpc.Discord_Shutdown();
    }
    
    static {
        rpc = DiscordRPC.INSTANCE;
        Discord.presence = new DiscordRichPresence();
    }
}
