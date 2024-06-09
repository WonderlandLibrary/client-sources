package com.darkmagician6.eventapi.events.callables;

import com.darkmagician6.eventapi.events.Cancellable;
import com.darkmagician6.eventapi.events.Event;










public abstract class EventCancellable
  implements Event, Cancellable
{
  private boolean cancelled;
  
  protected EventCancellable() {}
  
  public boolean isCancelled()
  {
    return cancelled;
  }
  



  public void setCancelled(boolean state)
  {
    cancelled = state;
  }
}
