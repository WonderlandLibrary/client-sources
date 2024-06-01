package best.actinium.event.impl.render;

import best.actinium.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Render3DEvent extends Event {
    private final float partialTicks;
}
