package Snipeware;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordRP {

    private boolean running = true;
    public long created = 0;

    public void start(){

        this.created = System.currentTimeMillis();

        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
            System.out.println("Welcome " + user.username + "#" + user.discriminator + ".");
            DiscordRichPresence.Builder presence = new DiscordRichPresence.Builder("");
            DiscordRPC.discordUpdatePresence(presence.build());
            Client.DID = user.userId.toString();
        }).build();
        DiscordRPC.discordInitialize("861739582290788432", handlers, false);
        DiscordRPC.discordRegister("861739582290788432", "");

        
        new Thread("Discord RPC Callback"){
            @Override
            public void run() {
                while(running){
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();

    }

    public void shutdown(){
        running = false;
        DiscordRPC.discordShutdown();
    }

    public void update(String firstLine, String secondLine){
        DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(secondLine);
        b.setBigImage("ddd", "Bhoping on kids");
        b.setDetails(firstLine);
        b.setStartTimestamps(created);

        DiscordRPC.discordUpdatePresence(b.build());
    }
}
