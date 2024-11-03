package net.augustus.events;

public class EventRayCastPost extends Event {
   private float partialTicks;

   public EventRayCastPost(float partialTicks) {
      this.partialTicks = partialTicks;
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public void setPartialTicks(float partialTicks) {
      this.partialTicks = partialTicks;
   }
}
