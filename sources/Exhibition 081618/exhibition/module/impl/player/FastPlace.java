package exhibition.module.impl.player;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventTick;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;

public class FastPlace extends Module {
   private static final String KEY_TIMES = "CLICKSPEED";

   public FastPlace(ModuleData data) {
      super(data);
      this.settings.put("CLICKSPEED", new Setting("CLICKSPEED", Integer.valueOf(4), "Tick delay between clicks.", 1.0D, 0.0D, 20.0D));
   }

   @RegisterEvent(
      events = {EventTick.class}
   )
   public void onEvent(Event event) {
      mc.rightClickDelayTimer = Math.min(mc.rightClickDelayTimer, 1);
   }
}
