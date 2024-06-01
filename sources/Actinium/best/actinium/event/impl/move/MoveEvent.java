package best.actinium.event.impl.move;

import best.actinium.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MoveEvent extends Event {
    private double x, y, z;
}
