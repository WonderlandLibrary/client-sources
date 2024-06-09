package lunadevs.luna.events;

import com.darkmagician6.eventapi.events.Event;

public class EventMiddleClick implements Event {
	
  public int key;
  
  public EventMiddleClick(int key)
  {
    this.key = key;
  }
}
