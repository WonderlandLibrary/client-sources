package pw.latematt.xiv.event.events;

import net.minecraft.entity.item.EntityItem;
import pw.latematt.xiv.event.Cancellable;
import pw.latematt.xiv.event.Event;

/**
 * @author Matthew
 */
public class ItemRenderEvent extends Event implements Cancellable {
    private boolean cancelled;
    private final State state;
    private EntityItem item;

    public ItemRenderEvent(State state, EntityItem item) {
        this.state = state;
        this.item = item;
    }

    public State getState() {
        return state;
    }

    public EntityItem getItem() {
        return item;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public enum State {
        PRE,
        POST
    }
}
