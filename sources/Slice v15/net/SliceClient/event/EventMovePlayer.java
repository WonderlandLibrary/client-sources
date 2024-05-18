package net.SliceClient.event;

import com.darkmagician6.eventapi.events.Event;

public class EventMovePlayer
  implements Event
{
  public double x;
  public double y;
  public double z;
  
  public EventMovePlayer(double x, double y, double z)
  {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  public double getX()
  {
    return x;
  }
  
  public double getY()
  {
    return y;
  }
  
  public double getZ()
  {
    return z;
  }
  
  public void setX(double x)
  {
    this.x = x;
  }
  
  public void setY(double y)
  {
    this.y = y;
  }
  
  public void setZ(double z)
  {
    this.z = z;
  }
  
  public double getMotionX()
  {
    return x;
  }
  
  public double getMotionY()
  {
    return y;
  }
  
  public double getMotionZ()
  {
    return z;
  }
  
  public void setMotionX(double motionX)
  {
    x = motionX;
  }
  
  public void setMotionY(double motionY)
  {
    y = motionY;
  }
  
  public void setMotionZ(double motionZ)
  {
    z = motionZ;
  }
}
