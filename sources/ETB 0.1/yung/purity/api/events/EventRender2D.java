package yung.purity.api.events;

import yung.purity.api.Event;









public class EventRender2D
  extends Event
{
  private float partialTicks;
  
  public EventRender2D(float partialTicks)
  {
    this.partialTicks = partialTicks;
  }
  
  public float getPartialTicks()
  {
    return partialTicks;
  }
  
  public void setPartialTicks(float partialTicks)
  {
    this.partialTicks = partialTicks;
  }
}
