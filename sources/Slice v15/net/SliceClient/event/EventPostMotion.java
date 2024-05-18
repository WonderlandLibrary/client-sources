package net.SliceClient.event;

import com.darkmagician6.eventapi.events.Event;

public class EventPostMotion implements Event {
  private double motionX;
  private double motionY;
  private double motionZ;
  
  public EventPostMotion() {}
  
  public double getMotionX() { return motionX; }
  
  public double getMotionY()
  {
    return motionY;
  }
  
  public double getMotionZ() {
    return motionZ;
  }
  
  public void setMotion(double motionX, double motionY, double motionZ) {
    this.motionX = motionX;
    this.motionY = motionY;
    this.motionZ = motionZ;
  }
}
