package net.SliceClient.event;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class EventUpdatePreMotion
  extends EventCancellable
{
  public float yaw;
  public float pitch;
  
  public EventUpdatePreMotion() {}
}
