package yung.purity.api.events;

import net.minecraft.entity.Entity;
import yung.purity.api.Event;












public class EventUpdate
  extends Event
{
  public double y;
  public static float yaw;
  public static float pitch;
  private boolean onGround;
  private Entity entity;
  
  public EventUpdate(double y, float yaw, float pitch, boolean onGround)
  {
    this.y = y;
    yaw = yaw;
    pitch = pitch;
    this.onGround = onGround;
    setType((byte)0);
  }
  
  public EventUpdate()
  {
    setType((byte)1);
  }
  
  public double getY()
  {
    return y;
  }
  
  public void setY(double y) {
    this.y = y;
  }
  
  public float getYaw()
  {
    return yaw;
  }
  
  public void setYaw(float yaw) {
    yaw = yaw;
  }
  
  public float getPitch()
  {
    return pitch;
  }
  
  public void setPitch(float pitch) {
    pitch = pitch;
  }
  
  public boolean isOnGround()
  {
    return onGround;
  }
  
  public void setOnGround(boolean onGround) {
    this.onGround = onGround;
  }
  
  public Entity getEntity()
  {
    return entity;
  }
}
