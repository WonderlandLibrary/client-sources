/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API.events.render;

import drunkclient.beta.API.events.render.Cancellable;
import net.minecraft.entity.EntityLivingBase;

public final class RenderNametagEvent
extends Cancellable {
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

