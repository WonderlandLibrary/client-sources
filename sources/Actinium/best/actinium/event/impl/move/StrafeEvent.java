package best.actinium.event.impl.move;

import best.actinium.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StrafeEvent extends Event {

    public float forward;
    public float strafe;
    public float friction;
    public float yaw;

}
