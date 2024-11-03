package net.lenni0451.eventapi.events.premade;

import net.lenni0451.eventapi.events.IEvent;
import net.lenni0451.eventapi.events.types.IStoppable;

public class EventStoppable implements IEvent, IStoppable {
   private boolean stopped = false;

   @Override
   public boolean isStopped() {
      return this.stopped;
   }

   @Override
   public void setStopped(boolean stopped) {
      this.stopped = stopped;
   }
}
