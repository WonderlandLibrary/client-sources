package best.actinium.event.impl.input;

import best.actinium.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatInputEvent extends Event {
    private String message;
}
