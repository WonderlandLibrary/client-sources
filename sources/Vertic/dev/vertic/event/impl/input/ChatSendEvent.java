package dev.vertic.event.impl.input;

import dev.vertic.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatSendEvent extends Event {
    private String message;
}
