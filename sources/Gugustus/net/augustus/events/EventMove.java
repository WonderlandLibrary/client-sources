package net.augustus.events;

public class EventMove extends Event {
   private float yaw;
   private float friction;

   public EventMove(float yaw, float friction) {
      this.yaw = yaw;
      this.friction = friction;
   }

   public float getYaw() {
      return this.yaw;
   }

   public void setYaw(float yaw) {
      this.yaw = yaw;
   }

   public float getFriction() {
      return this.friction;
   }

   public void setFriction(float friction) {
      this.friction = friction;
   }
}
