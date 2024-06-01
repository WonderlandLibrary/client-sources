package best.actinium.event.impl.move;

import best.actinium.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MoveInputEvent extends Event {

    public float forward;
    public float strafe;
    public boolean jump;
    public boolean sneak;
    public double slowdown;

}
