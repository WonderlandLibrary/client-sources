/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderEntity
extends Render<Entity> {
    public RenderEntity(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        RenderEntity.renderOffsetAABB(entity.getEntityBoundingBox(), x - entity.lastTickPosX, y - entity.lastTickPosY, z - entity.lastTickPosZ);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    @Nullable
    public ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }
}

