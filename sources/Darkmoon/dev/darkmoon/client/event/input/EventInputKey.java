package dev.darkmoon.client.event.input;

import com.darkmagician6.eventapi.events.Event;
import lombok.Getter;
import lombok.Setter;

public class EventInputKey implements Event {
    @Getter
    @Setter
    private int key;
    public EventInputKey(int key) {
        this.key = key;
    }
}
