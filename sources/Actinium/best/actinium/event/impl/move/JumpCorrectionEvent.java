package best.actinium.event.impl.move;

import best.actinium.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JumpCorrectionEvent extends Event {
    private float yaw;
}
