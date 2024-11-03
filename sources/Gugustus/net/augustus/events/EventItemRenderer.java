package net.augustus.events;

public class EventItemRenderer extends Event {
   double x;
   double y;
   double z;
   double scale;
   double blockX;
   double blockY;
   double blockZ;

   public EventItemRenderer(double x, double y, double z, double scale, double blockX, double blockY, double blockZ) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.scale = scale;
      this.blockX = blockX;
      this.blockY = blockY;
      this.blockZ = blockZ;
   }

   public double getBlockX() {
      return this.blockX;
   }

   public void setBlockX(double blockX) {
      this.blockX = blockX;
   }

   public double getBlockY() {
      return this.blockY;
   }

   public void setBlockY(double blockY) {
      this.blockY = blockY;
   }

   public double getBlockZ() {
      return this.blockZ;
   }

   public void setBlockZ(double blockZ) {
      this.blockZ = blockZ;
   }

   public double getX() {
      return this.x;
   }

   public void setX(double x) {
      this.x = x;
   }

   public double getY() {
      return this.y;
   }

   public void setY(double y) {
      this.y = y;
   }

   public double getZ() {
      return this.z;
   }

   public void setZ(double z) {
      this.z = z;
   }

   public double getScale() {
      return this.scale;
   }

   public void setScale(double scale) {
      this.scale = scale;
   }
}
