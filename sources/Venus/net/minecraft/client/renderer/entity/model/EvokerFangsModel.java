/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class EvokerFangsModel<T extends Entity>
extends SegmentedModel<T> {
    private final ModelRenderer base = new ModelRenderer(this, 0, 0);
    private final ModelRenderer upperJaw;
    private final ModelRenderer lowerJaw;

    public EvokerFangsModel() {
        this.base.setRotationPoint(-5.0f, 22.0f, -5.0f);
        this.base.addBox(0.0f, 0.0f, 0.0f, 10.0f, 12.0f, 10.0f);
        this.upperJaw = new ModelRenderer(this, 40, 0);
        this.upperJaw.setRotationPoint(1.5f, 22.0f, -4.0f);
        this.upperJaw.addBox(0.0f, 0.0f, 0.0f, 4.0f, 14.0f, 8.0f);
        this.lowerJaw = new ModelRenderer(this, 40, 0);
        this.lowerJaw.setRotationPoint(-1.5f, 22.0f, 4.0f);
        this.lowerJaw.addBox(0.0f, 0.0f, 0.0f, 4.0f, 14.0f, 8.0f);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        float f6 = f * 2.0f;
        if (f6 > 1.0f) {
            f6 = 1.0f;
        }
        f6 = 1.0f - f6 * f6 * f6;
        this.upperJaw.rotateAngleZ = (float)Math.PI - f6 * 0.35f * (float)Math.PI;
        this.lowerJaw.rotateAngleZ = (float)Math.PI + f6 * 0.35f * (float)Math.PI;
        this.lowerJaw.rotateAngleY = (float)Math.PI;
        float f7 = (f + MathHelper.sin(f * 2.7f)) * 0.6f * 12.0f;
        this.lowerJaw.rotationPointY = this.upperJaw.rotationPointY = 24.0f - f7;
        this.base.rotationPointY = this.upperJaw.rotationPointY;
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.base, this.upperJaw, this.lowerJaw);
    }
}

