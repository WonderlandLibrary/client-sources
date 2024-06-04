package yung.purity.api;


public abstract class Event
{
  private boolean cancelled;
  public byte type;
  
  public Event() {}
  
  public boolean isCancelled()
  {
    return cancelled;
  }
  


  public void setCancelled(boolean cancelled)
  {
    this.cancelled = cancelled;
  }
  


  public byte getType()
  {
    return type;
  }
  


  public void setType(byte type)
  {
    this.type = type;
  }
}
