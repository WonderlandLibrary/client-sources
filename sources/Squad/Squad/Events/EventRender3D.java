package Squad.Events;

import com.darkmagician6.eventapi.events.Event;

public class EventRender3D implements Event{
	
		  public float partialTicks;
		  
		  public float getPartialTicks()
		  {
		    return this.partialTicks;
		  }
		  
		  public void setPartialTicks(float partialTicks)
		  {
		    this.partialTicks = partialTicks;
		  }
		}


