// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.events.impl;

import net.minecraft.entity.Entity;
import xyz.niggfaclient.events.CancellableEvent;

public class StepEvent extends CancellableEvent
{
    private Entity entity;
    private float height;
    public boolean pre;
    
    public StepEvent(final Entity entity) {
        this.pre = true;
        this.entity = entity;
        this.height = entity.stepHeight;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public void setHeight(final float height) {
        this.height = height;
    }
    
    public boolean isPre() {
        return this.pre;
    }
    
    public void setPost() {
        this.pre = false;
    }
}
