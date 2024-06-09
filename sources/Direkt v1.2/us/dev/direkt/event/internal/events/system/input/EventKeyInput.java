package us.dev.direkt.event.internal.events.system.input;

import us.dev.direkt.event.Event;

/**
 * @author Foundry
 */
public class EventKeyInput implements Event {
    private int eventKey;

    public EventKeyInput(int key) {
        this.eventKey = key;
    }

    public int getEventKey() {
        return this.eventKey;
    }
}
