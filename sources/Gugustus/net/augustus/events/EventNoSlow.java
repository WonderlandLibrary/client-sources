package net.augustus.events;

public class EventNoSlow extends Event {
   private float moveStrafe;
   private float moveForward;
   private boolean sprint;

   public EventNoSlow(float moveStrafe, float moveForward) {
      this.moveStrafe = moveStrafe;
      this.moveForward = moveForward;
   }

   public float getMoveStrafe() {
      return this.moveStrafe;
   }

   public void setMoveStrafe(float moveStrafe) {
      this.moveStrafe = moveStrafe;
   }

   public float getMoveForward() {
      return this.moveForward;
   }

   public void setMoveForward(float moveForward) {
      this.moveForward = moveForward;
   }

   public boolean isSprint() {
      return this.sprint;
   }

   public void setSprint(boolean sprint) {
      this.sprint = sprint;
   }
}
