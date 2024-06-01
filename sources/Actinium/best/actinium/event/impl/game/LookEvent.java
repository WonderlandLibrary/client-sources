package best.actinium.event.impl.game;

import best.actinium.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.lwjglx.util.vector.Vector2f;

@Getter
@Setter
@AllArgsConstructor
public class LookEvent extends Event {
    private Vector2f rotation;
}
