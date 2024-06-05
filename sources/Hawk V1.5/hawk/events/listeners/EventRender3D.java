package hawk.events.listeners;

import hawk.events.Event;

public class EventRender3D extends Event<EventRender3D> {
   private float partialTicks;

   public void setPartialTicks(float var1) {
      this.partialTicks = var1;
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public EventRender3D(float var1) {
      this.partialTicks = var1;
   }
}
