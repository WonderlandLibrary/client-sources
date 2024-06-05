/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.events.render;

import net.minecraft.entity.EntityLivingBase;
import digital.rbq.events.Cancellable;
import digital.rbq.events.Event;

public final class RenderNametagEvent
extends Cancellable
implements Event {
    private final EntityLivingBase entity;
    private String renderedName;

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

    public void setRenderedName(String renderedName) {
        this.renderedName = renderedName;
    }
}

