package exhibition.module.impl.movement;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventRenderGui;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;

public class Debug extends Module {
   public Debug(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventRenderGui.class}
   )
   public void onEvent(Event event) {
   }
}
