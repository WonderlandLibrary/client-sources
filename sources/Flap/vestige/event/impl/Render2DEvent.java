package vestige.event.impl;

import vestige.event.Event;

public class Render2DEvent extends Event {
   private float partialTicks;

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public Render2DEvent(float partialTicks) {
      this.partialTicks = partialTicks;
   }
}
