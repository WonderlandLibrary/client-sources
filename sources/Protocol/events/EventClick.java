package events;

import darkmagician6.EventCancellable;

public class EventClick
extends EventCancellable
{
private boolean canceled;

public boolean isCancelled()
{
  return this.canceled;
}

public void setCancelled(boolean state)
{
  this.canceled = state;
}
}