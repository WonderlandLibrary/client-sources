package xyz.cucumber.base.events.ext;

import xyz.cucumber.base.events.Event;

public class EventMoveForward extends Event {
   boolean reset;

   public boolean isReset() {
      return this.reset;
   }

   public void setReset(boolean reset) {
      this.reset = reset;
   }

   public EventMoveForward(boolean reset) {
      this.reset = reset;
   }
}
