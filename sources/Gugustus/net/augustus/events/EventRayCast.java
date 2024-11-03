package net.augustus.events;

public class EventRayCast extends Event {
   private float partialTicks;

   public EventRayCast(float partialTicks) {
      this.partialTicks = partialTicks;
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public void setPartialTicks(float partialTicks) {
      this.partialTicks = partialTicks;
   }
}
