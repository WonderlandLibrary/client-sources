package exhibition.module.impl.player;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventPacket;
import exhibition.event.impl.EventTick;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.PlayerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

public class PotionSaver extends Module {
   public PotionSaver(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventPacket.class, EventTick.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventPacket) {
         EventPacket ep = (EventPacket)event.cast();
         if (ep.getPacket() instanceof C03PacketPlayer && !PlayerUtil.isMoving()) {
            ep.setCancelled(true);
         }
      }

   }
}
