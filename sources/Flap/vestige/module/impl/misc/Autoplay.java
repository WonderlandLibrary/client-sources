package vestige.module.impl.misc;

import net.minecraft.network.play.server.S02PacketChat;
import vestige.event.Listener;
import vestige.event.impl.PacketReceiveEvent;
import vestige.event.impl.TickEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.client.NotificationManager;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.misc.TimerUtil;

public class Autoplay extends Module {
   private final ModeSetting mode = new ModeSetting("Mode", "Solo insane", new String[]{"Solo normal", "Solo insane", "Solo MushMC"});
   private final IntegerSetting delay = new IntegerSetting("Delay", 1500, 0, 4000, 50);
   private final String messagemush = "Deseja jogar novamente? CLIQUE AQUI!";
   private final String winMessage = "You won! Want to play again? Click here!";
   private final String loseMessage = "You died! Want to play again? Click here!";
   private final TimerUtil timer = new TimerUtil();
   private boolean waiting;

   public Autoplay() {
      super("Autoplay", Category.ULTILITY);
      this.addSettings(new AbstractSetting[]{this.mode, this.delay});
   }

   public void onEnable() {
      this.waiting = false;
      this.timer.reset();
   }

   @Listener
   public void onTick(TickEvent event) {
      if (this.waiting && this.timer.getTimeElapsed() >= (long)this.delay.getValue()) {
         String command = "";
         String var3 = this.mode.getMode();
         byte var4 = -1;
         switch(var3.hashCode()) {
         case -263033164:
            if (var3.equals("Solo MushMC")) {
               var4 = 2;
            }
            break;
         case 532112747:
            if (var3.equals("Solo insane")) {
               var4 = 1;
            }
            break;
         case 676163368:
            if (var3.equals("Solo normal")) {
               var4 = 0;
            }
         }

         switch(var4) {
         case 0:
            command = "/play solo_normal";
            break;
         case 1:
            command = "/play solo_insane";
            break;
         case 2:
            command = "/play skywars_solo";
         }

         mc.thePlayer.sendChatMessage(command);
         this.timer.reset();
         this.waiting = false;
      }

   }

   @Listener
   public void onReceive(PacketReceiveEvent event) {
      if (event.getPacket() instanceof S02PacketChat) {
         S02PacketChat packet = (S02PacketChat)event.getPacket();
         String message = packet.getChatComponent().getUnformattedText();
         if (message.contains("You won! Want to play again? Click here!") && message.length() < "You won! Want to play again? Click here!".length() + 3 || message.contains("You died! Want to play again? Click here!") && message.length() < "You died! Want to play again? Click here!".length() + 3 || message.contains("Deseja jogar novamente? CLIQUE AQUI!")) {
            this.waiting = true;
            NotificationManager.showNotification("AutoPlay", "Sending in " + this.delay.getValue() / 1000 + "s", NotificationManager.NotificationType.SUCESS, 3000L);
            this.timer.reset();
         }
      }

   }
}
