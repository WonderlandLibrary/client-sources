package net.augustus.events;

public class EventMouseMoving extends Event {
   private int deltaX;
   private int deltaY;

   public EventMouseMoving(int deltaX, int deltaY) {
      this.deltaX = deltaX;
      this.deltaY = deltaY;
   }

   public int getDeltaX() {
      return this.deltaX;
   }

   public void setDeltaX(int deltaX) {
      this.deltaX = deltaX;
   }

   public int getDeltaY() {
      return this.deltaY;
   }

   public void setDeltaY(int deltaY) {
      this.deltaY = deltaY;
   }
}
