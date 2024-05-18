package events;

import darkmagician6.EventCancellable;

public class EventBlockOpacity
  extends EventCancellable
{
  private int blockOpacity;
  
  public EventBlockOpacity(int blockOpacity)
  {
    this.blockOpacity = blockOpacity;
  }
  
  public int getBlockOpacity()
  {
    return this.blockOpacity;
  }
  
  public void setBlockOpacity(int blockOpacity)
  {
    this.blockOpacity = blockOpacity;
  }
}
