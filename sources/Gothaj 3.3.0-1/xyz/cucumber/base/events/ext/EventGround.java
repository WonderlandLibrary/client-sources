package xyz.cucumber.base.events.ext;

import xyz.cucumber.base.events.Event;

public class EventGround extends Event {
   boolean onGround;

   public EventGround(boolean b) {
      this.onGround = b;
   }

   public boolean isOnGround() {
      return this.onGround;
   }

   public void setOnGround(boolean onGround) {
      this.onGround = onGround;
   }
}
