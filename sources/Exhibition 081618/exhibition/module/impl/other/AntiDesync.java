package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventDamageBlock;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;

public class AntiDesync extends Module {
   public AntiDesync(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventDamageBlock.class}
   )
   public void onEvent(Event event) {
   }
}
