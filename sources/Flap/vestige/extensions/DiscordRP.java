package vestige.extensions;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.DiscordEventHandlers.Builder;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordRP {
   private boolean running = true;
   private long created = 0L;

   public void start() {
      this.created = System.currentTimeMillis();
      DiscordEventHandlers handlers = (new Builder()).setReadyEventHandler(new ReadyCallback() {
         public void apply(DiscordUser user) {
            System.out.println("Welcome " + user.username);
            DiscordRP.this.update("Loading Flap Client", "");
         }
      }).build();
      DiscordRPC.discordInitialize("1272639148544622683", handlers, true);
      (new Thread("Discord RPC CallBack") {
         public void run() {
            while(DiscordRP.this.running) {
               DiscordRPC.discordRunCallbacks();
            }

         }
      }).start();
   }

   public void stop() {
      this.running = false;
      DiscordRPC.discordShutdown();
   }

   public void update(String message, String prefix) {
      net.arikia.dev.drpc.DiscordRichPresence.Builder b = new net.arikia.dev.drpc.DiscordRichPresence.Builder(prefix);
      b.setBigImage("blaco", "");
      b.setDetails(message);
      b.setStartTimestamps(this.created);
      DiscordRPC.discordUpdatePresence(b.build());
   }
}
