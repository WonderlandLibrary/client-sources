package lunadevs.luna.events;

import com.darkmagician6.eventapi.events.Event;

public class EventMotion
  implements Event
{
  public double x;
  private boolean cancelled;
  public double y;
  public double z;
  private Location location;
  private float yaw;
  private float pitch;
  private boolean onGround;
  private EventType type;
  
  public EventMotion(Location location, boolean onGround, float yaw, float pitch, EventType type)
  {
    this.location = location;
    this.onGround = onGround;
    this.cancelled = false;
    this.type = type;
    this.yaw = yaw;
    this.pitch = pitch;
  }
  
  public boolean isCancelled() {
      return this.cancelled;
  }
  
  
  public void setCancelled(final boolean state) {
      this.cancelled = state;
  }
  public Location getLocation()
  {
    return this.location;
  }
  
  public void setLocation(Location location)
  {
    this.location = location;
  }
  
  public boolean isOnGround()
  {
    return this.onGround;
  }
  
  public void setOnGround(boolean onGround)
  {
    this.onGround = onGround;
  }
  
  public EventType getType()
  {
    return this.type;
  }
  
  public float getYaw()
  {
    return this.yaw;
  }
  
  public void setYaw(float yaw)
  {
    this.yaw = yaw;
  }
  
  public float getPitch()
  {
    return this.pitch;
  }
  
  public void setPitch(float pitch)
  {
    this.pitch = pitch;
  }
}
