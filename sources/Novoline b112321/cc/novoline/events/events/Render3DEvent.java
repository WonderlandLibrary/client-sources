package cc.novoline.events.events;

import cc.novoline.events.EventManager;
import cc.novoline.events.events.Event;
import net.apu;

public class Render3DEvent implements Event {
   private final float partialTicks;

   public Render3DEvent(float var1) {
      apu var2 = new apu(var1);
      EventManager.call(var2);
      this.partialTicks = var2.getPartialTicks();
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }
}
