package me.vaziak.sensation.utils.client;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

import java.sql.PseudoColumnUsage;

public class DiscordRPCUtil {
    private long created = 0;

    private boolean running;

    public void start() {
        running = true;
        created = System.currentTimeMillis();

        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
            update("Starting Sensation...", "");
        }).build();

        DiscordRPC.discordInitialize("654472876208029696", handlers, true);

        new Thread("Discord RPC Callback") {
            @Override
            public void run() {
                while (running) {
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }

    public void shutdown() {
        running = false;
        DiscordRPC.discordShutdown();
    }

    public void update(String str, String str2) {
    	//NOT WORKING W/ AAL!
        DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder(str2);

        builder.setBigImage("large", "");
        builder.setDetails(str);
        builder.setStartTimestamps(created);

        DiscordRPC.discordUpdatePresence(builder.build());
    }
}
