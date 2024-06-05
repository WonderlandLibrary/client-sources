package white.floor.event.event;

import white.floor.event.Event;

public class EventMouseKey extends Event {

    public int key;

    public EventMouseKey(int key) {
        this.key = key;
    }

}