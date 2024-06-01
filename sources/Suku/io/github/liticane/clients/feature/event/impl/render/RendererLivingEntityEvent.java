package io.github.liticane.clients.feature.event.impl.render;

import io.github.liticane.clients.feature.event.Event;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;

public class RendererLivingEntityEvent extends Event {
    private final EntityLivingBase entity;
    private final RendererLivingEntity<?> renderer;
    private final float partialTicks;
    private final double x,y,z;
    private Type type;

    public RendererLivingEntityEvent(EntityLivingBase entity, RendererLivingEntity<?> renderer, float partialTicks, double x, double y, double z, Type type) {
        this.entity = entity;
        this.renderer = renderer;
        this.partialTicks = partialTicks;
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public EntityLivingBase getEntity() {
        return entity;
    }

    public RendererLivingEntity<?> getRenderer() {
        return renderer;
    }

    public float getPartialTicks() {
        return partialTicks;
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

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        PRE,
        POST
    }
}