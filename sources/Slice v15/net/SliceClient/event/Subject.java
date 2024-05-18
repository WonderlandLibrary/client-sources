package net.SliceClient.event;

public class Subject
{
  private boolean canceled = false;
  
  public Subject() {}
  
  public boolean isCancelled() { return canceled; }
  

  public void setCancelled(boolean canceled)
  {
    this.canceled = canceled;
  }
}
