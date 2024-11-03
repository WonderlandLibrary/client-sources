package xyz.cucumber.base.events.ext;

import xyz.cucumber.base.events.Event;

public class EventHit extends Event {
   boolean forced;

   public EventHit(boolean forced) {
      this.forced = forced;
   }

   public void setForced(boolean forced) {
      this.forced = forced;
   }

   public boolean isForced() {
      return this.forced;
   }
}
