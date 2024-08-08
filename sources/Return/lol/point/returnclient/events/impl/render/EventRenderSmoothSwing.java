package lol.point.returnclient.events.impl.render;

import lol.point.returnclient.events.Event;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EventRenderSmoothSwing extends Event {
    public float renderSwingProgress;
}
