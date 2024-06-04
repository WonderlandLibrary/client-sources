package yung.purity.api.events;

import yung.purity.api.Event;






public class EventMove
  extends Event
{
  public static double x;
  public static double y;
  public static double z;
  private double motionX;
  private double motionY;
  private double motionZ;
  
  public EventMove(double x, double y, double z)
  {
    x = x;
    y = y;
    z = z;
    motionX = x;
    motionY = y;
    motionZ = z;
  }
  
  public double getX()
  {
    return x;
  }
  
  public void setX(double x) {
    x = x;
  }
  
  public double getY()
  {
    return y;
  }
  
  public void setY(double y) {
    y = y;
  }
  
  public double getZ()
  {
    return z;
  }
  
  public void setZ(double z) {
    z = z;
  }
}
