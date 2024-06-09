package exhibition.module.impl.render;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventRenderGui;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;

public class ESP extends Module {
   public ESP(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventRenderGui.class, EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventRenderGui) {
         EventRenderGui var2 = (EventRenderGui)event;
      }

   }
}
