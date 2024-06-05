package white.floor.event.event;

import net.minecraft.entity.EntityLivingBase;
import white.floor.event.Event;

public class EventNameTags extends Event {
    private final EntityLivingBase entity;
    private String renderedName;

    public EventNameTags(EntityLivingBase entity, String renderedName) {
        this.entity = entity;
        this.renderedName = renderedName;
    }

    public EntityLivingBase getEntity() {
        return this.entity;
    }

    public String getRenderedName() {
        return this.renderedName;
    }

    public void setRenderedName(String renderedName) {
        this.renderedName = renderedName;
    }

}
