package dev.darkmoon.client.event.misc;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventMessage extends EventCancellable {
    private String message;
}

