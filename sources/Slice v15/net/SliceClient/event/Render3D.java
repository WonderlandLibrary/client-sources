package net.SliceClient.event;

import com.darkmagician6.eventapi.events.Event;

public class Render3D implements Event
{
  private float partialTicks;
  
  public Render3D() {}
  
  public float getPartialTicks() {
    return partialTicks;
  }
  
  public void setPartialTicks(float partialTicks)
  {
    this.partialTicks = partialTicks;
  }
}
