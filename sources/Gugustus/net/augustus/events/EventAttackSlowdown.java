package net.augustus.events;

public class EventAttackSlowdown extends Event {
   private boolean sprint;
   private double slowDown = 0.6;

   public EventAttackSlowdown(boolean sprint, double slowDown) {
      this.sprint = sprint;
      this.slowDown = slowDown;
   }

   public boolean isSprint() {
      return this.sprint;
   }

   public void setSprint(boolean sprint) {
      this.sprint = sprint;
   }

   public double getSlowDown() {
      return this.slowDown;
   }

   public void setSlowDown(double slowDown) {
      this.slowDown = slowDown;
   }
}
