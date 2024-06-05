package hawk.modules.movement;

import hawk.events.Event;
import hawk.events.listeners.EventUpdate;
import hawk.modules.Module;

public class BasicFly extends Module {
   public void onEvent(Event var1) {
      if (var1 instanceof EventUpdate && var1.isPre()) {
         this.mc.thePlayer.capabilities.isFlying = true;
      }

   }

   public void onEnable() {
   }

   public void onDisable() {
      this.mc.thePlayer.capabilities.isFlying = false;
   }

   public BasicFly() {
      super("BasicFly", 0, Module.Category.MOVEMENT);
   }
}
