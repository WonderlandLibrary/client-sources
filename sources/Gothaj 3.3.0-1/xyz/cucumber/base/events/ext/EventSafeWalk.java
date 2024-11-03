package xyz.cucumber.base.events.ext;

import xyz.cucumber.base.events.Event;

public class EventSafeWalk extends Event {
   private boolean safe;

   public EventSafeWalk(boolean flag) {
      this.safe = flag;
   }

   public boolean isSafe() {
      return this.safe;
   }

   public void setSafe(boolean safe) {
      this.safe = safe;
   }
}
