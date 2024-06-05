package hawk.modules.movement;

import hawk.events.Event;
import hawk.events.listeners.EventUpdate;
import hawk.modules.Module;

public class Airjump extends Module {
   public void onEvent(Event var1) {
      if (var1 instanceof EventUpdate && var1.isPre()) {
         this.mc.thePlayer.onGround = true;
      }

   }

   public Airjump() {
      super("Airjump", 0, Module.Category.MOVEMENT);
   }
}
