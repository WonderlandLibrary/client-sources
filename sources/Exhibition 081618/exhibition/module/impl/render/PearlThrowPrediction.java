package exhibition.module.impl.render;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventPacket;
import exhibition.event.impl.EventRender3D;
import exhibition.event.impl.EventTick;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;

public class PearlThrowPrediction extends Module {
   public PearlThrowPrediction(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventPacket.class, EventTick.class, EventRender3D.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventTick) {
         ;
      }

      if (event instanceof EventPacket) {
         ;
      }

      if (event instanceof EventRender3D) {
         ;
      }

   }
}
