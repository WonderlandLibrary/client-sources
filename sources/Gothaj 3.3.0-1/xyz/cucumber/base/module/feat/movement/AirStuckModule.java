package xyz.cucumber.base.module.feat.movement;

import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventMove;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;

@ModuleInfo(
   category = Category.MOVEMENT,
   description = "Allows you to freeze your self in air",
   name = "Air Stuck",
   key = 0
)
public class AirStuckModule extends Mod {
   @EventListener
   public void onMove(EventMove e) {
      e.setCancelled(true);
      this.mc.thePlayer.motionY = 0.0;
   }

   @EventListener
   public void onSendPacket(EventSendPacket e) {
      if (e.getPacket() instanceof C03PacketPlayer) {
         e.setCancelled(true);
      }
   }
}
