/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.util.ResourceLocation;

public interface IEntityRenderer {
    public Class getEntityClass();

    public void setEntityClass(Class var1);

    public ResourceLocation getLocationTextureCustom();

    public void setLocationTextureCustom(ResourceLocation var1);
}

