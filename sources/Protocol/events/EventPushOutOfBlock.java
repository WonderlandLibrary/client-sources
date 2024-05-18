package events;

import darkmagician6.EventCancellable;

public class EventPushOutOfBlock
extends EventCancellable
{
private boolean cancel;

public boolean isCancelled()
{
  return this.cancel;
}

public void setCancelled(boolean state)
{
  this.cancel = state;
}
}
