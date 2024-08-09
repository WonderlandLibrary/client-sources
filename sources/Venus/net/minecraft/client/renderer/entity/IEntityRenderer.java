/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public interface IEntityRenderer<T extends Entity, M extends EntityModel<T>> {
    public M getEntityModel();

    public ResourceLocation getEntityTexture(T var1);
}

