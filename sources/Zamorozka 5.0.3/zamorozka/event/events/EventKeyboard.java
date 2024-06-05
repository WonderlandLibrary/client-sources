package zamorozka.event.events;

import zamorozka.event.Event;

/**
 * Created by Hexeption on 07/01/2017.
 */
public class EventKeyboard extends Event {

    public int key;

    public EventKeyboard(int key) {

        this.key = key;
    }

    public int getKey() {
    	
        return key;
    }
}
