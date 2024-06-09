package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.EventListener;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventRender3D;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;

public class SurvivalNuker extends Module {
   public SurvivalNuker(ModuleData data) {
      super(data);
   }

   public EventListener.Priority getPriority() {
      return EventListener.Priority.MEDIUM;
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class, EventRender3D.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventMotionUpdate) {
         EventMotionUpdate var2 = (EventMotionUpdate)event.cast();
      }

   }
}
