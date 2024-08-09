package dev.excellent.api.event.impl.input;

import dev.excellent.api.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ChatInputEvent extends CancellableEvent {
    private String message;
}