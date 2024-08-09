/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class PufferFishSmallModel<T extends Entity>
extends SegmentedModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer rightEye;
    private final ModelRenderer leftEye;
    private final ModelRenderer rightFin;
    private final ModelRenderer leftFin;
    private final ModelRenderer tail;

    public PufferFishSmallModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        int n = 23;
        this.body = new ModelRenderer(this, 0, 27);
        this.body.addBox(-1.5f, -2.0f, -1.5f, 3.0f, 2.0f, 3.0f);
        this.body.setRotationPoint(0.0f, 23.0f, 0.0f);
        this.rightEye = new ModelRenderer(this, 24, 6);
        this.rightEye.addBox(-1.5f, 0.0f, -1.5f, 1.0f, 1.0f, 1.0f);
        this.rightEye.setRotationPoint(0.0f, 20.0f, 0.0f);
        this.leftEye = new ModelRenderer(this, 28, 6);
        this.leftEye.addBox(0.5f, 0.0f, -1.5f, 1.0f, 1.0f, 1.0f);
        this.leftEye.setRotationPoint(0.0f, 20.0f, 0.0f);
        this.tail = new ModelRenderer(this, -3, 0);
        this.tail.addBox(-1.5f, 0.0f, 0.0f, 3.0f, 0.0f, 3.0f);
        this.tail.setRotationPoint(0.0f, 22.0f, 1.5f);
        this.rightFin = new ModelRenderer(this, 25, 0);
        this.rightFin.addBox(-1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 2.0f);
        this.rightFin.setRotationPoint(-1.5f, 22.0f, -1.5f);
        this.leftFin = new ModelRenderer(this, 25, 0);
        this.leftFin.addBox(0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 2.0f);
        this.leftFin.setRotationPoint(1.5f, 22.0f, -1.5f);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.body, this.rightEye, this.leftEye, this.tail, this.rightFin, this.leftFin);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        this.rightFin.rotateAngleZ = -0.2f + 0.4f * MathHelper.sin(f3 * 0.2f);
        this.leftFin.rotateAngleZ = 0.2f - 0.4f * MathHelper.sin(f3 * 0.2f);
    }
}

