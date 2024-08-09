package dev.excellent.api.event.impl.other;

import dev.excellent.api.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvCloseEvent extends CancellableEvent {
    private int windowId;
}
