package hawk.modules.player;

import hawk.events.Event;
import hawk.events.listeners.EventUpdate;
import hawk.modules.Module;
import hawk.settings.ModeSetting;
import hawk.settings.NumberSetting;
import hawk.settings.Setting;
import java.util.Random;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class Spammer extends Module {
   Random random = new Random();
   public ModeSetting mode = new ModeSetting("Mode", this, "Redesky", new String[]{"Redesky", "Developing hawk"});
   public NumberSetting delay = new NumberSetting("Delay (in ms)", 4500.0D, 50.0D, 60000.0D, 50.0D, this);
   String message;
   hawk.util.Timer timer = new hawk.util.Timer();

   public void onEvent(Event var1) {
      if (var1 instanceof EventUpdate) {
         if (this.timer.hasTimeElapsed(50L, true)) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(String.valueOf((new StringBuilder("/r spam moment")).append(this.random.nextInt(1000)))));
         }

         this.setDisplayname("Spammer");
         if (this.mode.is("Redesky")) {
            this.message = " Redesky anticheat best ww ";
         } else if (this.mode.is("Developing hawk")) {
            this.message = " Yescheatplus status : developing Hawk ";
         }

         if (this.timer.hasTimeElapsed((long)this.delay.getValue(), true)) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(String.valueOf((new StringBuilder(String.valueOf(this.random.nextInt(1000)))).append(this.message).append(this.random.nextInt(1000)))));
         }
      }

   }

   public Spammer() {
      super("Spammer", 0, Module.Category.PLAYER);
      this.addSettings(new Setting[]{this.delay});
   }
}
