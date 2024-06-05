package cc.novoline.events.events;

import cc.novoline.events.EventManager;
import cc.novoline.events.events.Event;
import net.a2G;
import net.minecraft.client.gui.ScaledResolution;

public class Render2DEvent implements Event {
   private final ScaledResolution resolution;
   private final float partialTicks;

   public Render2DEvent(ScaledResolution var1, float var2) {
      a2G var3 = new a2G(var1, var2);
      EventManager.call(var3);
      this.resolution = var3.getResolution();
      this.partialTicks = var3.getPartialTicks();
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public ScaledResolution getResolution() {
      return this.resolution;
   }
}
