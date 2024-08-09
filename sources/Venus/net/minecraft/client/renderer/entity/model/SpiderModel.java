/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class SpiderModel<T extends Entity>
extends SegmentedModel<T> {
    private final ModelRenderer spiderHead;
    private final ModelRenderer spiderNeck;
    private final ModelRenderer spiderBody;
    private final ModelRenderer spiderLeg1;
    private final ModelRenderer spiderLeg2;
    private final ModelRenderer spiderLeg3;
    private final ModelRenderer spiderLeg4;
    private final ModelRenderer spiderLeg5;
    private final ModelRenderer spiderLeg6;
    private final ModelRenderer spiderLeg7;
    private final ModelRenderer spiderLeg8;

    public SpiderModel() {
        float f = 0.0f;
        int n = 15;
        this.spiderHead = new ModelRenderer(this, 32, 4);
        this.spiderHead.addBox(-4.0f, -4.0f, -8.0f, 8.0f, 8.0f, 8.0f, 0.0f);
        this.spiderHead.setRotationPoint(0.0f, 15.0f, -3.0f);
        this.spiderNeck = new ModelRenderer(this, 0, 0);
        this.spiderNeck.addBox(-3.0f, -3.0f, -3.0f, 6.0f, 6.0f, 6.0f, 0.0f);
        this.spiderNeck.setRotationPoint(0.0f, 15.0f, 0.0f);
        this.spiderBody = new ModelRenderer(this, 0, 12);
        this.spiderBody.addBox(-5.0f, -4.0f, -6.0f, 10.0f, 8.0f, 12.0f, 0.0f);
        this.spiderBody.setRotationPoint(0.0f, 15.0f, 9.0f);
        this.spiderLeg1 = new ModelRenderer(this, 18, 0);
        this.spiderLeg1.addBox(-15.0f, -1.0f, -1.0f, 16.0f, 2.0f, 2.0f, 0.0f);
        this.spiderLeg1.setRotationPoint(-4.0f, 15.0f, 2.0f);
        this.spiderLeg2 = new ModelRenderer(this, 18, 0);
        this.spiderLeg2.addBox(-1.0f, -1.0f, -1.0f, 16.0f, 2.0f, 2.0f, 0.0f);
        this.spiderLeg2.setRotationPoint(4.0f, 15.0f, 2.0f);
        this.spiderLeg3 = new ModelRenderer(this, 18, 0);
        this.spiderLeg3.addBox(-15.0f, -1.0f, -1.0f, 16.0f, 2.0f, 2.0f, 0.0f);
        this.spiderLeg3.setRotationPoint(-4.0f, 15.0f, 1.0f);
        this.spiderLeg4 = new ModelRenderer(this, 18, 0);
        this.spiderLeg4.addBox(-1.0f, -1.0f, -1.0f, 16.0f, 2.0f, 2.0f, 0.0f);
        this.spiderLeg4.setRotationPoint(4.0f, 15.0f, 1.0f);
        this.spiderLeg5 = new ModelRenderer(this, 18, 0);
        this.spiderLeg5.addBox(-15.0f, -1.0f, -1.0f, 16.0f, 2.0f, 2.0f, 0.0f);
        this.spiderLeg5.setRotationPoint(-4.0f, 15.0f, 0.0f);
        this.spiderLeg6 = new ModelRenderer(this, 18, 0);
        this.spiderLeg6.addBox(-1.0f, -1.0f, -1.0f, 16.0f, 2.0f, 2.0f, 0.0f);
        this.spiderLeg6.setRotationPoint(4.0f, 15.0f, 0.0f);
        this.spiderLeg7 = new ModelRenderer(this, 18, 0);
        this.spiderLeg7.addBox(-15.0f, -1.0f, -1.0f, 16.0f, 2.0f, 2.0f, 0.0f);
        this.spiderLeg7.setRotationPoint(-4.0f, 15.0f, -1.0f);
        this.spiderLeg8 = new ModelRenderer(this, 18, 0);
        this.spiderLeg8.addBox(-1.0f, -1.0f, -1.0f, 16.0f, 2.0f, 2.0f, 0.0f);
        this.spiderLeg8.setRotationPoint(4.0f, 15.0f, -1.0f);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.spiderHead, this.spiderNeck, this.spiderBody, this.spiderLeg1, this.spiderLeg2, this.spiderLeg3, this.spiderLeg4, this.spiderLeg5, this.spiderLeg6, this.spiderLeg7, this.spiderLeg8);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        this.spiderHead.rotateAngleY = f4 * ((float)Math.PI / 180);
        this.spiderHead.rotateAngleX = f5 * ((float)Math.PI / 180);
        float f6 = 0.7853982f;
        this.spiderLeg1.rotateAngleZ = -0.7853982f;
        this.spiderLeg2.rotateAngleZ = 0.7853982f;
        this.spiderLeg3.rotateAngleZ = -0.58119464f;
        this.spiderLeg4.rotateAngleZ = 0.58119464f;
        this.spiderLeg5.rotateAngleZ = -0.58119464f;
        this.spiderLeg6.rotateAngleZ = 0.58119464f;
        this.spiderLeg7.rotateAngleZ = -0.7853982f;
        this.spiderLeg8.rotateAngleZ = 0.7853982f;
        float f7 = -0.0f;
        float f8 = 0.3926991f;
        this.spiderLeg1.rotateAngleY = 0.7853982f;
        this.spiderLeg2.rotateAngleY = -0.7853982f;
        this.spiderLeg3.rotateAngleY = 0.3926991f;
        this.spiderLeg4.rotateAngleY = -0.3926991f;
        this.spiderLeg5.rotateAngleY = -0.3926991f;
        this.spiderLeg6.rotateAngleY = 0.3926991f;
        this.spiderLeg7.rotateAngleY = -0.7853982f;
        this.spiderLeg8.rotateAngleY = 0.7853982f;
        float f9 = -(MathHelper.cos(f * 0.6662f * 2.0f + 0.0f) * 0.4f) * f2;
        float f10 = -(MathHelper.cos(f * 0.6662f * 2.0f + (float)Math.PI) * 0.4f) * f2;
        float f11 = -(MathHelper.cos(f * 0.6662f * 2.0f + 1.5707964f) * 0.4f) * f2;
        float f12 = -(MathHelper.cos(f * 0.6662f * 2.0f + 4.712389f) * 0.4f) * f2;
        float f13 = Math.abs(MathHelper.sin(f * 0.6662f + 0.0f) * 0.4f) * f2;
        float f14 = Math.abs(MathHelper.sin(f * 0.6662f + (float)Math.PI) * 0.4f) * f2;
        float f15 = Math.abs(MathHelper.sin(f * 0.6662f + 1.5707964f) * 0.4f) * f2;
        float f16 = Math.abs(MathHelper.sin(f * 0.6662f + 4.712389f) * 0.4f) * f2;
        this.spiderLeg1.rotateAngleY += f9;
        this.spiderLeg2.rotateAngleY += -f9;
        this.spiderLeg3.rotateAngleY += f10;
        this.spiderLeg4.rotateAngleY += -f10;
        this.spiderLeg5.rotateAngleY += f11;
        this.spiderLeg6.rotateAngleY += -f11;
        this.spiderLeg7.rotateAngleY += f12;
        this.spiderLeg8.rotateAngleY += -f12;
        this.spiderLeg1.rotateAngleZ += f13;
        this.spiderLeg2.rotateAngleZ += -f13;
        this.spiderLeg3.rotateAngleZ += f14;
        this.spiderLeg4.rotateAngleZ += -f14;
        this.spiderLeg5.rotateAngleZ += f15;
        this.spiderLeg6.rotateAngleZ += -f15;
        this.spiderLeg7.rotateAngleZ += f16;
        this.spiderLeg8.rotateAngleZ += -f16;
    }
}

