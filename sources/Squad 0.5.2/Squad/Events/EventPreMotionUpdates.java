package Squad.Events;

import com.darkmagician6.eventapi.events.Event;

public class EventPreMotionUpdates implements Event{
	private double motionX;
	  public double motionY;
	  private double motionZ;
	  
	  
	  
	  
	  
	  public double getMotionX()
	  {
	    return this.motionX;
	  }
	  
	  public double getMotionY()
	  {
	    return this.motionY;
	  }
	  
	  public double getMotionZ()
	  {
	    return this.motionZ;
	  }
	  
	  public void setMotion(double motionX, double motionY, double motionZ)
	  {
	    this.motionX = motionX;
	    this.motionY = motionY;
	    this.motionZ = motionZ;
	  }
	}
