/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LeashKnotModel<T extends Entity>
extends SegmentedModel<T> {
    private final ModelRenderer knotRenderer;

    public LeashKnotModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.knotRenderer = new ModelRenderer(this, 0, 0);
        this.knotRenderer.addBox(-3.0f, -6.0f, -3.0f, 6.0f, 8.0f, 6.0f, 0.0f);
        this.knotRenderer.setRotationPoint(0.0f, 0.0f, 0.0f);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.knotRenderer);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        this.knotRenderer.rotateAngleY = f4 * ((float)Math.PI / 180);
        this.knotRenderer.rotateAngleX = f5 * ((float)Math.PI / 180);
    }
}

