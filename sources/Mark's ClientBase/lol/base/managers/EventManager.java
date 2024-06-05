package lol.base.managers;

import lol.base.addons.EventAddon;
import lol.base.radbus.PubSub;

public class EventManager {

    private final PubSub<EventAddon> eventPubSub = PubSub.newInstance(System.err::println);

    public void subscribe(Object object) {
        eventPubSub.subscribe(object);
    }

    public void unsubscribe(Object object) {
        eventPubSub.unsubscribe(object);
    }

    public void publish(EventAddon eventAddon) {
        eventPubSub.publish(eventAddon);
    }

}
