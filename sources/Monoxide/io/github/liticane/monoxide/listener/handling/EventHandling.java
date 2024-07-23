package io.github.liticane.monoxide.listener.handling;

import io.github.liticane.monoxide.listener.event.Event;
import io.github.liticane.monoxide.listener.radbus.PubSub;
import lombok.Getter;

public class EventHandling {

    private final PubSub<Event> eventPubSub = PubSub.newInstance(System.err::println);

    @Getter
    private static EventHandling instance;

    public void publishEvent(Event event) {
        eventPubSub.publish(event);
    }

    public void registerListener(Object object) {
        eventPubSub.subscribe(object);
    }

    public void unregisterListener(Object object) {
        eventPubSub.unsubscribe(object);
    }

    public static void setInstance(EventHandling instance) {
        EventHandling.instance = instance;
    }
}
