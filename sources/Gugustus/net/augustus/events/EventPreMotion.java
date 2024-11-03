package net.augustus.events;

public class EventPreMotion extends Event {
   public float yaw;
   public float pitch;
   public boolean ground;
   public double x;
   public double y;
   public double z;

   public EventPreMotion(float yaw, float pitch, boolean ground, double x, double y, double z) {
      this.yaw = yaw;
      this.pitch = pitch;
      this.ground = ground;
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public void setX(double x) {
      this.x = x;
   }

   public void setY(double y) {
      this.y = y;
   }

   public void setZ(double z) {
      this.z = z;
   }

   public float getYaw() {
      return this.yaw;
   }

   public void setYaw(float yaw) {
      this.yaw = yaw;
   }

   public float getPitch() {
      return this.pitch;
   }

   public void setPitch(float pitch) {
      this.pitch = pitch;
   }

   public boolean onGround() {
      return this.ground;
   }

   public void setGround(boolean ground) {
      this.ground = ground;
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
}
