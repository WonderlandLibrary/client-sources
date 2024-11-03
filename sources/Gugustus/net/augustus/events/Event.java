package net.augustus.events;

import net.lenni0451.eventapi.events.IEvent;

public class Event implements IEvent {
   private boolean canceled;

   public boolean isCanceled() {
      return this.canceled;
   }

   public void setCanceled(boolean canceled) {
      this.canceled = canceled;
   }
}
