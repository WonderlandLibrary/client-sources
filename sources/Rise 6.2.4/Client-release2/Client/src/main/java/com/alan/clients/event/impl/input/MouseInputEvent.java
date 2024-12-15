package com.alan.clients.event.impl.input;

import com.alan.clients.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class MouseInputEvent implements Event {
    int mouseCode;
}
