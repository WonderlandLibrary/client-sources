package hawk.modules.player;

import hawk.events.Event;
import hawk.events.listeners.EventUpdate;
import hawk.modules.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class FastEat extends Module {
   hawk.util.Timer timer = new hawk.util.Timer();
   boolean PlayerEat = false;

   public void onEvent(Event var1) {
      if (var1 instanceof EventUpdate && var1.isPre() && !this.mc.thePlayer.isBlocking() && this.mc.thePlayer.isEating()) {
         for(int var2 = 0; var2 < 10; ++var2) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
         }
      }

   }

   public FastEat() {
      super("FastEat", 0, Module.Category.PLAYER);
   }
}
