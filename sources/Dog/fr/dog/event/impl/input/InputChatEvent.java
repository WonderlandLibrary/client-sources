package fr.dog.event.impl.input;

import fr.dog.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class InputChatEvent extends CancellableEvent {
    private String message;
}