package net.lenni0451.eventapi.events.premade;

import net.lenni0451.eventapi.events.IEvent;
import net.lenni0451.eventapi.events.types.ICancellable;

public class EventCancellable implements IEvent, ICancellable {
   private boolean cancelled = false;

   @Override
   public boolean isCancelled() {
      return this.cancelled;
   }

   @Override
   public void setCancelled(boolean cancelled) {
      this.cancelled = cancelled;
   }
}
