package dev.darkmoon.client.event.input;

import com.darkmagician6.eventapi.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventMouse implements Event {
    private int button;
}
