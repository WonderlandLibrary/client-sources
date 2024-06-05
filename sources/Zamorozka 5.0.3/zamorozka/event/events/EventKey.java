package zamorozka.event.events;

import zamorozka.event.Event;

public class EventKey extends Event {

    public int key;
    
    public EventKey(int key)
    {
	this.key = key;
    }
}