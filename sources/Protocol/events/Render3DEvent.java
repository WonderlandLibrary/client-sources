package events;

import darkmagician6.Event;
import darkmagician6.EventCancellable;

public class Render3DEvent extends EventCancellable{
	  public static float partialTicks;
	  
	  public Render3DEvent(float partialTicks)
	  {
	    this.partialTicks = partialTicks;
	  }
	  
	  public float getPartialTicks()
	  {
	    return this.partialTicks;
	  }
	}
