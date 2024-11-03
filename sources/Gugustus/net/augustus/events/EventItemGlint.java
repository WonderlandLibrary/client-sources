package net.augustus.events;

public class EventItemGlint extends Event {
   private int color;
   private double speed;

   public EventItemGlint(int color, double speed) {
      this.color = color;
      this.speed = speed;
   }

   public double getSpeed() {
      return this.speed;
   }

   public void setSpeed(double speed) {
      this.speed = speed;
   }

   public int getColor() {
      return this.color;
   }

   public void setColor(int color) {
      this.color = color;
   }
}
