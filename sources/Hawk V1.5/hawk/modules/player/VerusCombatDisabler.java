package hawk.modules.player;

import hawk.events.Event;
import hawk.events.listeners.EventPacket;
import hawk.modules.Module;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;

public class VerusCombatDisabler extends Module {
   public void onEvent(Event var1) {
      if (var1 instanceof EventPacket && var1.isIncoming()) {
         EventPacket var2 = (EventPacket)var1;
         if (EventPacket.getPacket() instanceof C0FPacketConfirmTransaction) {
            var2.setCancelled(true);
         }
      }

   }

   public VerusCombatDisabler() {
      super("FakeVerusCombatDisabler", 0, Module.Category.PLAYER);
   }
}
