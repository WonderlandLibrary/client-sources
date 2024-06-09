package lunadevs.luna.events;

import com.darkmagician6.eventapi.events.Event;

public class RenderWorldEvent implements Event {

	  private float ticks;
	  
	  public RenderWorldEvent(float ticks)
	  {
	    setTicks(ticks);
	  }
	  
	  public float getTicks()
	  {
	    return this.ticks;
	  }
	  
	  public void setTicks(float ticks)
	  {
	    this.ticks = ticks;
	  }
	
}
