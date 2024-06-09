package lunadevs.luna.events.others;

import com.darkmagician6.eventapi.events.Event;

public class EventRender3D implements Event{

	  private float partialTicks;
	  
	  public EventRender3D(float ticks)
	  {
	    this.partialTicks = ticks;
	  }
	  
	  public float getPartialTicks()
	  {
	    return this.partialTicks;
	  }
	}
