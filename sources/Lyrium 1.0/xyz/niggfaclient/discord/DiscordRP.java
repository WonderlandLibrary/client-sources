// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.discord;

import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordRPC;
import xyz.niggfaclient.Client;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.arikia.dev.drpc.DiscordEventHandlers;

public class DiscordRP
{
    private boolean running;
    private long created;
    
    public DiscordRP() {
        this.running = true;
        this.created = 0L;
    }
    
    public void start() {
        this.created = System.currentTimeMillis();
        final DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
            @Override
            public void apply(final DiscordUser discordUser) {
                DiscordRP.this.update("Launching " + Client.getInstance().clientName + "...", "");
                Client.getInstance().discordUsername = discordUser.username;
            }
        }).build();
        DiscordRPC.discordInitialize("1050507060623708190", handlers, true);
        new Thread("Discord RPC Callback") {
            @Override
            public void run() {
                while (DiscordRP.this.running) {
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }
    
    public void shutdown() {
        this.running = false;
        DiscordRPC.discordShutdown();
    }
    
    public void update(final String firstLine, final String secondLine) {
        final DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder(secondLine);
        builder.setBigImage("large", "");
        builder.setDetails(firstLine);
        builder.setStartTimestamps(this.created);
        DiscordRPC.discordUpdatePresence(builder.build());
    }
}
