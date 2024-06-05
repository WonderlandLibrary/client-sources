package hawk.modules.player;

import hawk.events.Event;
import hawk.events.listeners.EventMotion;
import hawk.modules.Module;

public class Fastplace extends Module {
   public void onEvent(Event var1) {
      if (var1 instanceof EventMotion && var1.isPre()) {
         this.mc.rightClickDelayTimer = 0;
      }

   }

   public Fastplace() {
      super("Fastplace", 0, Module.Category.PLAYER);
   }

   public void onDisable() {
      this.mc.rightClickDelayTimer = 6;
   }
}
