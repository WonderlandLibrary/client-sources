package net.SliceClient.event;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public abstract class PlayerUpdateEvent extends EventCancellable
{
  protected float yaw;
  protected float pitch;
  protected boolean onGround;
  protected boolean sneaking;
  
  public PlayerUpdateEvent() {}
  
  public float getYaw() {
    return yaw;
  }
  
  public void setYaw(float yaw)
  {
    this.yaw = yaw;
  }
  
  public float getPitch()
  {
    return pitch;
  }
  
  public void setPitch(float pitch)
  {
    this.pitch = pitch;
  }
  
  public boolean isOnGround()
  {
    return onGround;
  }
  
  public void setOnGround(boolean onGround)
  {
    this.onGround = onGround;
  }
  
  public boolean isSneaking()
  {
    return sneaking;
  }
  
  public void setSneaking(boolean sneaking)
  {
    this.sneaking = sneaking;
  }
}
