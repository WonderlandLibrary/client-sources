/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.minecraft.client.Minecraft;

public class DiscordRPCUtils {
    private long created = 0L;
    private boolean running = false;
    public static Minecraft mc = Minecraft.getMinecraft();

    public void start() {
        this.running = true;
        this.created = System.currentTimeMillis();
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback(){

            @Override
            public void apply(DiscordUser discordUser) {
                DiscordRPCUtils.this.update("Playing On Drunk Client", "Server: " + (mc.isSingleplayer() ? "SinglePlayer" : DiscordRPCUtils.mc.getCurrentServerData().serverIP.toString()));
            }
        }).build();
        DiscordRPC.discordInitialize("934151221860765736", handlers, true);
        new Thread("RPC Callback"){

            @Override
            public void run() {
                while (DiscordRPCUtils.this.running) {
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }

    public void shutdown() {
        this.running = false;
        DiscordRPC.discordShutdown();
    }

    public void update(String fristLine, String secondLine) {
        DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(secondLine);
        b.setBigImage("large", "");
        b.setDetails(fristLine);
        b.setStartTimestamps(this.created);
        DiscordRPC.discordUpdatePresence(b.build());
    }
}

