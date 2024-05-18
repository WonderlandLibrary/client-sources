package pw.latematt.xiv.event.events;

import net.minecraft.entity.Entity;
import pw.latematt.xiv.event.Event;

/**
 * @author Matthew
 */
public class FovModifierEvent extends Event {
    private final Entity entity;
    private float fov;

    public FovModifierEvent(Entity entity, float fov) {
        this.entity = entity;
        this.fov = fov;
    }

    public Entity getEntity() {
        return entity;
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }
}
