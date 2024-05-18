package net.SliceClient.event;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class PreSendMotion
  extends EventCancellable
{
  private boolean cancel;
  public float yaw;
  public float pitch;
  public double y;
  public boolean ground;
  
  public PreSendMotion(double y, float yaw, float pitch, boolean ground)
  {
    this.yaw = yaw;
    this.pitch = pitch;
    this.y = y;
    this.ground = ground;
  }
  
  public boolean isCancelled()
  {
    return cancel;
  }
  
  public void setCancelled(boolean cancel)
  {
    this.cancel = cancel;
  }
}
