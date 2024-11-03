package xyz.cucumber.base.events.ext;

import xyz.cucumber.base.events.Event;

public class EventRender3D extends Event {
   private float partialTicks;

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public void setPartialTicks(float partialTicks) {
      this.partialTicks = partialTicks;
   }

   public EventRender3D(float partialTicks) {
      this.partialTicks = partialTicks;
   }
}
