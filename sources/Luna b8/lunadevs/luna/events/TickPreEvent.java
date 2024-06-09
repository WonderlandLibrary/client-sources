package lunadevs.luna.events;

import com.darkmagician6.eventapi.events.Event;
import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class TickPreEvent extends EventCancellable implements Event{
	
	  public double posX;
	  public double minY;
	  public double posY;
	  public double posZ;
	  
	  public TickPreEvent(double posX, double minY, double posY, double posZ)
	  {
	    this.posX = posX;
	    this.minY = minY;
	    this.posY = posY;
	    this.posZ = posZ;
	  }
	
	
	
}
