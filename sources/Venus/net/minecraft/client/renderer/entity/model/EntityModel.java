/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public abstract class EntityModel<T extends Entity>
extends Model {
    public float swingProgress;
    public boolean isSitting;
    public boolean isChild = true;

    protected EntityModel() {
        this(RenderType::getEntityCutoutNoCull);
    }

    protected EntityModel(Function<ResourceLocation, RenderType> function) {
        super(function);
    }

    public abstract void setRotationAngles(T var1, float var2, float var3, float var4, float var5, float var6);

    public void setLivingAnimations(T t, float f, float f2, float f3) {
    }

    public void copyModelAttributesTo(EntityModel<T> entityModel) {
        entityModel.swingProgress = this.swingProgress;
        entityModel.isSitting = this.isSitting;
        entityModel.isChild = this.isChild;
    }
}

