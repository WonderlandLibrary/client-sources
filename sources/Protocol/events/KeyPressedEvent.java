package events;

import darkmagician6.Event;

public final class KeyPressedEvent
implements Event
{
private final int eventKey;

public KeyPressedEvent(int eventKey)
{
  this.eventKey = eventKey;
}

public int getEventKey()
{
  return this.eventKey;
}
}