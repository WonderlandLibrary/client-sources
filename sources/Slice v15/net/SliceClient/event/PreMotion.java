package net.SliceClient.event;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class PreMotion
  extends EventCancellable
{
  public float yaw;
  public float pitch;
  
  public PreMotion(float yaw, float pitch)
  {
    this.yaw = yaw;
    this.pitch = pitch;
  }
  
  public float getPitch()
  {
    return pitch;
  }
  
  public float getYaw()
  {
    return yaw;
  }
  
  public void setPitch(float pitch)
  {
    this.pitch = pitch;
  }
  
  public void setYaw(float yaw)
  {
    this.yaw = yaw;
  }
}
