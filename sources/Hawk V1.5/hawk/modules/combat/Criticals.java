package hawk.modules.combat;

import hawk.events.Event;
import hawk.events.listeners.EventSendPacket;
import hawk.modules.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Criticals extends Module {
   public void onEnable() {
      this.mc.thePlayer.jump();
   }

   public Criticals() {
      super("Criticals", 0, Module.Category.COMBAT);
   }

   public void onEvent(Event var1) {
      if (var1 instanceof EventSendPacket) {
         EventSendPacket var2 = (EventSendPacket)var1;
         if (var2.getPacket() instanceof C03PacketPlayer && Killaura.HasTarget && this.mc.thePlayer.fallDistance < 3.0F) {
            C03PacketPlayer var3 = (C03PacketPlayer)var2.getPacket();
            var3.onground = false;
         }
      }

   }

   public void onDisable() {
   }
}
