package com.minus.event.events.client;

import com.minus.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventOnChatSend extends CancellableEvent {
    private String input;
}
