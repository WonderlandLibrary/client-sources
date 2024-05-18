package com.darkmagician6.eventapi.events;






public abstract class EventStoppable
  implements Event
{
  private boolean stopped;
  




  protected EventStoppable() {}
  




  public void stop()
  {
    stopped = true;
  }
  





  public boolean isStopped()
  {
    return stopped;
  }
}
