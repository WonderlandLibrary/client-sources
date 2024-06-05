/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API.events.world;

import drunkclient.beta.API.Event;
import net.minecraft.entity.Entity;

public class EventStep
extends Event {
    private Entity entity;
    private float stepHeight;
    private boolean pre;
    private double blockHeight;

    public EventStep(Entity entity, float stepHeight, boolean pre) {
        this.entity = entity;
        this.stepHeight = stepHeight;
        this.pre = pre;
    }

    public EventStep(Entity entity, float stepHeight, boolean pre, double blockHeight) {
        this.blockHeight = blockHeight;
        this.stepHeight = stepHeight;
        this.entity = entity;
        this.pre = pre;
    }

    public double getHeightStepped() {
        return this.blockHeight;
    }

    public void setStepHeight(float stepHeight) {
        this.stepHeight = stepHeight;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public float getStepHeight() {
        return this.stepHeight;
    }

    public boolean isPre() {
        return this.pre;
    }

    public void setStage(boolean pre) {
        this.pre = pre;
    }
}

