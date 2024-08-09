package wtf.shiyeno.events.impl.game;

import wtf.shiyeno.events.Event;

public class EventKey extends Event {

    public int key;

    public EventKey(int key) {
        this.key = key;
    }
}
