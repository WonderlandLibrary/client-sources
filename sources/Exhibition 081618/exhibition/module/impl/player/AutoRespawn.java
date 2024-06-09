package exhibition.module.impl.player;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventTick;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;

public class AutoRespawn extends Module {
   public AutoRespawn(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventTick.class}
   )
   public void onEvent(Event event) {
      if (mc.thePlayer.isDead) {
         mc.thePlayer.respawnPlayer();
      }

   }
}
