package exhibition.module.impl.movement;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventRenderGui;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;

public class Sprint extends Module {
   public Sprint(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventRenderGui.class}
   )
   public void onEvent(Event event) {
      if (this.canSprint()) {
         mc.thePlayer.setSprinting(true);
      }

   }

   private boolean canSprint() {
      return mc.thePlayer.moveForward != 0.0F && !mc.thePlayer.isSneaking() && mc.thePlayer.getFoodStats().getFoodLevel() >= 6 && !mc.thePlayer.isCollidedHorizontally;
   }
}
