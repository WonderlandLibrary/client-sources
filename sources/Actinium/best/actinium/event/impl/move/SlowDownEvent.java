package best.actinium.event.impl.move;

import best.actinium.event.Event;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlowDownEvent extends Event {
    private float slowdownAmount;
}
