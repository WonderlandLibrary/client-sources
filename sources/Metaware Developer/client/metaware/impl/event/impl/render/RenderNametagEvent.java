package client.metaware.impl.event.impl.render;

import client.metaware.impl.event.Event;
import net.minecraft.entity.EntityLivingBase;

public class RenderNametagEvent extends Event {
    private final EntityLivingBase entity;
    private final String renderedName;

    public RenderNametagEvent(EntityLivingBase entity, String renderedName) {
        this.entity = entity;
        this.renderedName = renderedName;
    }

    public EntityLivingBase getEntity() {
        return this.entity;
    }

    public String getRenderedName() {
        return this.renderedName;
    }
}
