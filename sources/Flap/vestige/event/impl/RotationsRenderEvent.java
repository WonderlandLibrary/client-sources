package vestige.event.impl;

import vestige.event.Event;

public class RotationsRenderEvent extends Event {
   private float yaw;
   private float bodyYaw;
   private float pitch;
   private float partialTicks;

   public float getYaw() {
      return this.yaw;
   }

   public float getBodyYaw() {
      return this.bodyYaw;
   }

   public float getPitch() {
      return this.pitch;
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public RotationsRenderEvent(float yaw, float bodyYaw, float pitch, float partialTicks) {
      this.yaw = yaw;
      this.bodyYaw = bodyYaw;
      this.pitch = pitch;
      this.partialTicks = partialTicks;
   }

   public void setYaw(float yaw) {
      this.yaw = yaw;
   }

   public void setBodyYaw(float bodyYaw) {
      this.bodyYaw = bodyYaw;
   }

   public void setPitch(float pitch) {
      this.pitch = pitch;
   }
}
