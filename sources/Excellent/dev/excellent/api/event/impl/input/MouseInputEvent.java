package dev.excellent.api.event.impl.input;

import dev.excellent.api.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MouseInputEvent extends CancellableEvent {
    private final int mouseButton;

}