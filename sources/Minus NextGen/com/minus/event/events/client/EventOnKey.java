package com.minus.event.events.client;

import com.minus.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EventOnKey implements Event {
    private final int key, action;
}
