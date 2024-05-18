package pw.latematt.xiv.event.events;

import net.minecraft.entity.Entity;
import pw.latematt.xiv.event.Cancellable;
import pw.latematt.xiv.event.Event;

/**
 * @author Jack
 */
public class AttackEntityEvent extends Event implements Cancellable {
    private final Entity entity;
    private boolean cancelled;

    public AttackEntityEvent(final Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
