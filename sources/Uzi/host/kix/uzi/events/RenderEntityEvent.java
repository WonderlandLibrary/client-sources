package host.kix.uzi.events;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.entity.Entity;

/**
 * Created by k1x on 4/23/17.
 */
public class RenderEntityEvent implements Event {

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
