package vestige.event.impl;

import vestige.event.type.CancellableEvent;

public class JumpEvent extends CancellableEvent {
   private double motionY;
   private float yaw;
   private boolean boosting;

   public double getMotionY() {
      return this.motionY;
   }

   public float getYaw() {
      return this.yaw;
   }

   public boolean isBoosting() {
      return this.boosting;
   }

   public void setMotionY(double motionY) {
      this.motionY = motionY;
   }

   public void setYaw(float yaw) {
      this.yaw = yaw;
   }

   public void setBoosting(boolean boosting) {
      this.boosting = boosting;
   }

   public JumpEvent(double motionY, float yaw, boolean boosting) {
      this.motionY = motionY;
      this.yaw = yaw;
      this.boosting = boosting;
   }
}
