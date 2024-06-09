package lunadevs.luna.events;

import com.darkmagician6.eventapi.events.Event;

public class KeyPressEvent implements Event{
	
	  private int key;
	  
	  public KeyPressEvent(int key)
	  {
	    this.key = key;
	  }
	  
	  public int getKey()
	  {
	    return this.key;
	  }

}
