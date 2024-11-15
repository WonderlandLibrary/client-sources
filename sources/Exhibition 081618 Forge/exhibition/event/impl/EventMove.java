package exhibition.event.impl;

import exhibition.event.Event;

public class EventMove extends Event {
   private double x;
   private double y;
   private double z;

   public void fire(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
      super.fire();
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

   public EventMove setX(double x) {
      this.x = x;
      return this;
   }

   public EventMove setY(double y) {
      this.y = y;
      return this;
   }

   public EventMove setZ(double z) {
      this.z = z;
      return this;
   }
}
