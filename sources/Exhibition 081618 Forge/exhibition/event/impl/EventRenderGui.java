package exhibition.event.impl;

import exhibition.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class EventRenderGui extends Event {
   private ScaledResolution resolution;
   private float partialTicks;
   
   public void fire(ScaledResolution resolution,float partialTicks) {
      this.resolution = resolution;
      this.partialTicks = partialTicks;
      super.fire();
   }

   public ScaledResolution getResolution() {
      return this.resolution;
   }
   
   public float getPartialTicks() {
	      return this.partialTicks;
	   }
}
