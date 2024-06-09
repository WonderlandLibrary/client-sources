// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.discord;

import net.arikia.dev.drpc.DiscordRichPresence;
import net.aql.Lib;
import net.minecraft.client.Minecraft;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.arikia.dev.drpc.DiscordEventHandlers;

public class DiscordRPC
{
    private boolean running;
    private long created;
    
    public DiscordRPC() {
        this.running = true;
        this.created = 0L;
    }
    
    public void start() {
        this.created = System.currentTimeMillis();
        final DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
            @Override
            public void apply(final DiscordUser user) {
                final Minecraft mc = Minecraft.getMinecraft();
                final String s = String.valueOf(Lib.getUID());
                DiscordRPC.this.update("a Client for everything", "https://discord.gg/qnzcdrCx7Q");
                if (user.userId.equalsIgnoreCase("1000123485529583747")) {}
            }
        }).build();
        net.arikia.dev.drpc.DiscordRPC.discordInitialize("1000123485529583747", handlers, true);
        new Thread("Discord RPC Callback") {
            @Override
            public void run() {
                while (DiscordRPC.this.running) {
                    net.arikia.dev.drpc.DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }
    
    public void shutdown() {
        this.running = false;
        net.arikia.dev.drpc.DiscordRPC.discordShutdown();
    }
    
    public void update(final String first, final String second) {
        final DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(second);
        b.setBigImage("dd8vlps-4e4d6575-308f-442b-a317-d85460b1e35f", "");
        b.setDetails(first);
        b.setStartTimestamps(this.created);
        net.arikia.dev.drpc.DiscordRPC.discordUpdatePresence(b.build());
    }
}
