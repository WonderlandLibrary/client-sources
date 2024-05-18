package net.SliceClient.event;

import com.darkmagician6.eventapi.events.Event;

public class EventRenderWorld
  implements Event
{
  private float ticks;
  
  public EventRenderWorld(float ticks)
  {
    setTicks(ticks);
  }
  
  public float getTicks()
  {
    return ticks;
  }
  
  public void setTicks(float ticks)
  {
    this.ticks = ticks;
  }
}
