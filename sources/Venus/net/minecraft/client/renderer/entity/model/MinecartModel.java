/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import java.util.Arrays;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class MinecartModel<T extends Entity>
extends SegmentedModel<T> {
    private final ModelRenderer[] sideModels = new ModelRenderer[6];

    public MinecartModel() {
        this.sideModels[0] = new ModelRenderer(this, 0, 10);
        this.sideModels[1] = new ModelRenderer(this, 0, 0);
        this.sideModels[2] = new ModelRenderer(this, 0, 0);
        this.sideModels[3] = new ModelRenderer(this, 0, 0);
        this.sideModels[4] = new ModelRenderer(this, 0, 0);
        this.sideModels[5] = new ModelRenderer(this, 44, 10);
        int n = 20;
        int n2 = 8;
        int n3 = 16;
        int n4 = 4;
        this.sideModels[0].addBox(-10.0f, -8.0f, -1.0f, 20.0f, 16.0f, 2.0f, 0.0f);
        this.sideModels[0].setRotationPoint(0.0f, 4.0f, 0.0f);
        this.sideModels[5].addBox(-9.0f, -7.0f, -1.0f, 18.0f, 14.0f, 1.0f, 0.0f);
        this.sideModels[5].setRotationPoint(0.0f, 4.0f, 0.0f);
        this.sideModels[1].addBox(-8.0f, -9.0f, -1.0f, 16.0f, 8.0f, 2.0f, 0.0f);
        this.sideModels[1].setRotationPoint(-9.0f, 4.0f, 0.0f);
        this.sideModels[2].addBox(-8.0f, -9.0f, -1.0f, 16.0f, 8.0f, 2.0f, 0.0f);
        this.sideModels[2].setRotationPoint(9.0f, 4.0f, 0.0f);
        this.sideModels[3].addBox(-8.0f, -9.0f, -1.0f, 16.0f, 8.0f, 2.0f, 0.0f);
        this.sideModels[3].setRotationPoint(0.0f, 4.0f, -7.0f);
        this.sideModels[4].addBox(-8.0f, -9.0f, -1.0f, 16.0f, 8.0f, 2.0f, 0.0f);
        this.sideModels[4].setRotationPoint(0.0f, 4.0f, 7.0f);
        this.sideModels[0].rotateAngleX = 1.5707964f;
        this.sideModels[1].rotateAngleY = 4.712389f;
        this.sideModels[2].rotateAngleY = 1.5707964f;
        this.sideModels[3].rotateAngleY = (float)Math.PI;
        this.sideModels[5].rotateAngleX = -1.5707964f;
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        this.sideModels[5].rotationPointY = 4.0f - f3;
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return Arrays.asList(this.sideModels);
    }
}

