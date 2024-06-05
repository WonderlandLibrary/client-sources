package hawk.events.listeners;

import hawk.events.Event;

public class EventMotion extends Event<EventMotion> {
   public float pitch;
   public boolean onGround;
   public double z;
   public float yaw;
   public double x;
   public double y;

   public double getZ() {
      return this.z;
   }

   public EventMotion(double var1, double var3, double var5, float var7, float var8, boolean var9) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
      this.yaw = var7;
      this.pitch = var8;
      this.onGround = var9;
   }

   public void setY(double var1) {
      this.y = var1;
   }

   public double getY() {
      return this.y;
   }

   public void setYaw(float var1) {
      this.yaw = var1;
   }

   public void setOnGround(boolean var1) {
      this.onGround = var1;
   }

   public float getPitch() {
      return this.pitch;
   }

   public boolean isOnGround() {
      return this.onGround;
   }

   public void setPitch(float var1) {
      this.pitch = var1;
   }

   public float getYaw() {
      return this.yaw;
   }

   public void setZ(double var1) {
      this.z = var1;
   }

   public void setX(double var1) {
      this.x = var1;
   }

   public double getX() {
      return this.x;
   }
}
