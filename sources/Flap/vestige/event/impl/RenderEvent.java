package vestige.event.impl;

import vestige.event.Event;

public class RenderEvent extends Event {
   private float partialTicks;

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public RenderEvent(float partialTicks) {
      this.partialTicks = partialTicks;
   }
}
