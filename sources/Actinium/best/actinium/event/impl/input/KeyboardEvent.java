package best.actinium.event.impl.input;

import best.actinium.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KeyboardEvent extends Event {
    private final int keyCode;
}
