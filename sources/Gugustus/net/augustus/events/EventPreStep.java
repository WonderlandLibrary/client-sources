package net.augustus.events;

public class EventPreStep extends Event {
   private float stepHeight = 0.6F;

   public EventPreStep(float stepHeight) {
      this.stepHeight = stepHeight;
   }

   public float getStepHeight() {
      return this.stepHeight;
   }

   public void setStepHeight(float stepHeight) {
      this.stepHeight = stepHeight;
   }
}
