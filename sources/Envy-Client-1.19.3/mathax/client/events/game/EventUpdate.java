package mathax.client.events.game;

import mathax.client.events.Event;

public class EventUpdate extends Event {

    private static final EventUpdate instance = new EventUpdate();

    public static EventUpdate get() {
        instance.setCancelled(false);
        return instance;
    }
}
