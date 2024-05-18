package pw.latematt.xiv.event.events;

import net.minecraft.entity.Entity;
import pw.latematt.xiv.event.Event;

/**
 * @author Matthew
 */
public class AddWeatherEvent extends Event {
    private final Entity entity;

    public AddWeatherEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
