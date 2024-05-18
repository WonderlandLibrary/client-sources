package net.SliceClient.event;

import com.darkmagician6.eventapi.events.Event;

public class EventRender3D implements Event
{
  public static float partialTicks;
  
  public EventRender3D() {}
  
  public float getPartialTicks() {
    return partialTicks;
  }
  
  public void setPartialTicks(float partialTicks)
  {
    partialTicks = partialTicks;
  }
}
