package pw.latematt.xiv.event.events;

import net.minecraft.entity.Entity;
import pw.latematt.xiv.event.Cancellable;
import pw.latematt.xiv.event.Event;

/**
 * @author Matthew
 */
public class EntityStepEvent extends Event implements Cancellable {
    private final Entity entity;
    private final boolean canStep;
    private boolean cancelled;
    private double x, y, z;

    public EntityStepEvent(Entity entity, boolean canStep, double x, double y, double z) {
        this.entity = entity;
        this.canStep = canStep;

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Entity getEntity() {
        return entity;
    }

    public boolean canStep() {
        return canStep;
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
