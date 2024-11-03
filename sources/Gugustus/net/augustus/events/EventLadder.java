package net.augustus.events;

public class EventLadder extends Event {
   private double motionYSpeed;

   public EventLadder(double motionYSpeed) {
      this.motionYSpeed = motionYSpeed;
   }

   public double getMotionYSpeed() {
      return this.motionYSpeed;
   }

   public void setMotionYSpeed(double motionYSpeed) {
      this.motionYSpeed = motionYSpeed;
   }
}
