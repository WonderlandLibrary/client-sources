package exhibition.module.impl.player;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventTick;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.Timer;

public class AutoEat extends Module {
   Timer timer = new Timer();

   public AutoEat(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventTick.class}
   )
   public void onEvent(Event event) {
      if (mc.thePlayer.getFoodStats().getFoodLevel() < 20 && this.timer.delay(2000.0F)) {
         mc.thePlayer.sendChatMessage("/eat");
         this.timer.reset();
      }

      if (this.timer.delay(60000.0F)) {
         mc.thePlayer.sendChatMessage("/eat");
         this.timer.reset();
      }

   }
}
