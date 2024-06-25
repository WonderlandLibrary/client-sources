package cc.slack.events.impl.player;

import cc.slack.events.Event;

public class JumpEvent extends Event {
   private float yaw;

   public float getYaw() {
      return this.yaw;
   }

   public void setYaw(float yaw) {
      this.yaw = yaw;
   }

   public JumpEvent(float yaw) {
      this.yaw = yaw;
   }
}
