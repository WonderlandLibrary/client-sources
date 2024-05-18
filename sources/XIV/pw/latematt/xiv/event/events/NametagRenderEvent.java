package pw.latematt.xiv.event.events;

import net.minecraft.entity.EntityLivingBase;
import pw.latematt.xiv.event.Cancellable;
import pw.latematt.xiv.event.Event;

/**
 * @author Matthew
 */
public class NametagRenderEvent extends Event implements Cancellable {
    private final EntityLivingBase entity;
    private final double x, y, z;
    private boolean cancelled;

    public NametagRenderEvent(EntityLivingBase entity, double x, double y, double z) {
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public EntityLivingBase getEntity() {
        return entity;
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

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
