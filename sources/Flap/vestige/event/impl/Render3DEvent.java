package vestige.event.impl;

import vestige.event.Event;

public class Render3DEvent extends Event {
   private float partialTicks;

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public Render3DEvent(float partialTicks) {
      this.partialTicks = partialTicks;
   }
}
