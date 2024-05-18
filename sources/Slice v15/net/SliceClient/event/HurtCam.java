package net.SliceClient.event;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class HurtCam extends EventCancellable
{
  boolean cancel;
  
  public HurtCam() {}
  
  public boolean isCancelled() {
    return cancel;
  }
  
  public void setCancelled(boolean shouldCancel)
  {
    cancel = shouldCancel;
  }
}
