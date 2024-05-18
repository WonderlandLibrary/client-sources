package events;

import darkmagician6.EventCancellable;
import me.protocol_client.Wrapper;

public class EventPrePlayerUpdate
  extends EventCancellable
{
  private boolean cancel;
  public float yaw;
  public float pitch;
  public double y;
  
  public EventPrePlayerUpdate(float yaw, float pitch, double y)
  {
    this.yaw = yaw;
    this.pitch = pitch;
    this.y = y;
  }
  public void setMotion(double motionX, double motionY, double motionZ)
  {
    Wrapper.getPlayer().motionX = motionX;
    Wrapper.getPlayer().motionY = motionY;
    Wrapper.getPlayer().motionZ = motionZ;
  }
  public float getPitch()
  {
    return this.pitch;
  }
  
  public float getYaw()
  {
    return this.yaw;
  }
  
  public double getY()
  {
    return this.y;
  }
  
  public void setPitch(float pitch)
  {
    this.pitch = pitch;
  }
  
  public void setYaw(float yaw)
  {
    this.yaw = yaw;
  }
  
  public void setY(double y)
  {
    this.y = y;
  }
  
  public boolean isCancelled()
  {
    return this.cancel;
  }
  
  public void setCancelled(boolean state)
  {
    this.cancel = state;
  }
}
