package exhibition.module.impl.render;

import exhibition.event.Event;
import exhibition.event.EventListener;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventRender3D;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;

public class HitIndicator extends Module {
   public HitIndicator(ModuleData data) {
      super(data);
   }

   public EventListener.Priority getPriority() {
      return EventListener.Priority.HIGH;
   }

   @RegisterEvent(
      events = {EventRender3D.class}
   )
   public void onEvent(Event event) {
      EventRender3D er = (EventRender3D)event;
   }
}
