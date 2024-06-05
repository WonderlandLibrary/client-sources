package Focus.Beta.UTILS;

import Focus.Beta.IMPL.Module.impl.focus.DiscordRP;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordRPCUtils {
    private long created = 0;
    private boolean running = false;

    public void start(){
        running = true;
        this.created = System.currentTimeMillis();

        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
            @Override
            public void apply(DiscordUser discordUser) {
                update("Playing on", "Focus Client");
                System.out.println("Connected to " + discordUser.username + "#" + discordUser.discriminator + " ID: " + discordUser.userId);
            }
        }).build();

        DiscordRPC.discordInitialize("881950400213823559", handlers, true);

        new Thread("RPC Callback"){

            @Override
            public void run() {

                while (running){
                    DiscordRPC.discordRunCallbacks();
                }

            }

        }.start();
    }

    public void shutdown(){
        running = false;
        DiscordRPC.discordShutdown();
    }

    public void update(String fristLine, String secondLine){
        DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(secondLine);
        b.setBigImage("large", "");
        b.setDetails(fristLine);
        b.setStartTimestamps(created);

        DiscordRPC.discordUpdatePresence(b.build());
    }
}
