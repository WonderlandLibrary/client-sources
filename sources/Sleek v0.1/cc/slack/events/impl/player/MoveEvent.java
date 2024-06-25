package cc.slack.events.impl.player;

import cc.slack.events.Event;
import cc.slack.utils.client.mc;

public class MoveEvent extends Event {
   private double x;
   private double y;
   private double z;
   public boolean safewalk = false;

   public void setZero() {
      this.setX(0.0D);
      this.setZ(0.0D);
      this.setY(0.0D);
   }

   public void setZeroXZ() {
      this.setX(0.0D);
      this.setZ(0.0D);
   }

   public void setX(double x) {
      mc.getPlayer().motionX = x;
      this.x = x;
   }

   public void setY(double y) {
      mc.getPlayer().motionY = y;
      this.y = y;
   }

   public void setZ(double z) {
      mc.getPlayer().motionZ = z;
      this.z = z;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public boolean isSafewalk() {
      return this.safewalk;
   }

   public MoveEvent(double x, double y, double z, boolean safewalk) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.safewalk = safewalk;
   }
}
