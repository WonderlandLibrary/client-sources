package cc.slack.events.impl.render;

import cc.slack.events.Event;
import net.minecraft.entity.Entity;

public class LivingLabelEvent extends Event {

    private final Entity entity;

    public LivingLabelEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }


}
