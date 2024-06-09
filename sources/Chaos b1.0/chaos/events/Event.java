package chaos.events;


import com.darkmagician6.eventapi.EventManager;

import net.minecraft.client.Minecraft;

public abstract class Event {

	public static enum State
	  {
	    PRE,  POST;
	    
	    private State() {}
	  }
	 

}
