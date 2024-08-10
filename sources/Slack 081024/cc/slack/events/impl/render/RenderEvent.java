package cc.slack.events.impl.render;

import cc.slack.events.Event;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RenderEvent extends Event {

    public State state;
    public float partialTicks, width, height;

    public RenderEvent(State state, float partialTicks) {
        this.state = state;
        this.partialTicks = partialTicks;
    }

    public RenderEvent(State state, float partialTicks, float width, float height) {
        this.state = state;
        this.partialTicks = partialTicks;
        this.width = width;
        this.height = height;
    }

    public enum State {
        RENDER_3D, RENDER_2D
    }
}
