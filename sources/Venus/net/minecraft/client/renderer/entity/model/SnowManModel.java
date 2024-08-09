/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class SnowManModel<T extends Entity>
extends SegmentedModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer bottomBody;
    private final ModelRenderer head;
    private final ModelRenderer rightHand;
    private final ModelRenderer leftHand;

    public SnowManModel() {
        float f = 4.0f;
        float f2 = 0.0f;
        this.head = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
        this.head.addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, -0.5f);
        this.head.setRotationPoint(0.0f, 4.0f, 0.0f);
        this.rightHand = new ModelRenderer(this, 32, 0).setTextureSize(64, 64);
        this.rightHand.addBox(-1.0f, 0.0f, -1.0f, 12.0f, 2.0f, 2.0f, -0.5f);
        this.rightHand.setRotationPoint(0.0f, 6.0f, 0.0f);
        this.leftHand = new ModelRenderer(this, 32, 0).setTextureSize(64, 64);
        this.leftHand.addBox(-1.0f, 0.0f, -1.0f, 12.0f, 2.0f, 2.0f, -0.5f);
        this.leftHand.setRotationPoint(0.0f, 6.0f, 0.0f);
        this.body = new ModelRenderer(this, 0, 16).setTextureSize(64, 64);
        this.body.addBox(-5.0f, -10.0f, -5.0f, 10.0f, 10.0f, 10.0f, -0.5f);
        this.body.setRotationPoint(0.0f, 13.0f, 0.0f);
        this.bottomBody = new ModelRenderer(this, 0, 36).setTextureSize(64, 64);
        this.bottomBody.addBox(-6.0f, -12.0f, -6.0f, 12.0f, 12.0f, 12.0f, -0.5f);
        this.bottomBody.setRotationPoint(0.0f, 24.0f, 0.0f);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        this.head.rotateAngleY = f4 * ((float)Math.PI / 180);
        this.head.rotateAngleX = f5 * ((float)Math.PI / 180);
        this.body.rotateAngleY = f4 * ((float)Math.PI / 180) * 0.25f;
        float f6 = MathHelper.sin(this.body.rotateAngleY);
        float f7 = MathHelper.cos(this.body.rotateAngleY);
        this.rightHand.rotateAngleZ = 1.0f;
        this.leftHand.rotateAngleZ = -1.0f;
        this.rightHand.rotateAngleY = 0.0f + this.body.rotateAngleY;
        this.leftHand.rotateAngleY = (float)Math.PI + this.body.rotateAngleY;
        this.rightHand.rotationPointX = f7 * 5.0f;
        this.rightHand.rotationPointZ = -f6 * 5.0f;
        this.leftHand.rotationPointX = -f7 * 5.0f;
        this.leftHand.rotationPointZ = f6 * 5.0f;
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.body, this.bottomBody, this.head, this.rightHand, this.leftHand);
    }

    public ModelRenderer func_205070_a() {
        return this.head;
    }
}

