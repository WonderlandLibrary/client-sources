package best.actinium.event.impl.move;

import best.actinium.event.Event;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebSlowDownEvent extends Event {
    private float slowDown,motionSpeed,motionY;
    private boolean noMotion = false;
}
