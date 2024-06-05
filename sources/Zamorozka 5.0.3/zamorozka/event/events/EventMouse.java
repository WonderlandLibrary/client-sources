package zamorozka.event.events;

import zamorozka.event.Event;

public class EventMouse extends Event {
	
    public int key;

    public EventMouse(int key) {
        this.key = key;
    }

}
