package cc.novoline.events.events;

import cc.novoline.events.events.Event;

public abstract class StoppableEvent implements Event {
   private boolean stopped;

   public void stop() {
      this.stopped = true;
   }

   public boolean isStopped() {
      return this.stopped;
   }
}
