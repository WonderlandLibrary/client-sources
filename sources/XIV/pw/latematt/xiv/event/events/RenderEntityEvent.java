package pw.latematt.xiv.event.events;

import net.minecraft.entity.Entity;
import pw.latematt.xiv.event.Event;

/**
 * @author Rederpz
 */
public class RenderEntityEvent extends Event {
    private final State state;
    private final Entity entity;

    public RenderEntityEvent(State state, Entity entity) {
        this.state = state;
        this.entity = entity;
    }

    public State getState() {
        return state;
    }

    public Entity getEntity() {
        return entity;
    }

    public enum State {
        PRE,
        POST
    }
}
