package com.darkcart.xdolf.event;

public abstract class EventCancellable
implements Event, Cancellable
{
private boolean cancelled;

public boolean isCancelled()
{
  return this.cancelled;
}

public void setCancelled(boolean state)
{
  this.cancelled = state;
}
}
