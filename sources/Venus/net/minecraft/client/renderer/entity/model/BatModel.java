/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.util.math.MathHelper;

public class BatModel
extends SegmentedModel<BatEntity> {
    private final ModelRenderer batHead;
    private final ModelRenderer batBody;
    private final ModelRenderer batRightWing;
    private final ModelRenderer batLeftWing;
    private final ModelRenderer batOuterRightWing;
    private final ModelRenderer batOuterLeftWing;

    public BatModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.batHead = new ModelRenderer(this, 0, 0);
        this.batHead.addBox(-3.0f, -3.0f, -3.0f, 6.0f, 6.0f, 6.0f);
        ModelRenderer modelRenderer = new ModelRenderer(this, 24, 0);
        modelRenderer.addBox(-4.0f, -6.0f, -2.0f, 3.0f, 4.0f, 1.0f);
        this.batHead.addChild(modelRenderer);
        ModelRenderer modelRenderer2 = new ModelRenderer(this, 24, 0);
        modelRenderer2.mirror = true;
        modelRenderer2.addBox(1.0f, -6.0f, -2.0f, 3.0f, 4.0f, 1.0f);
        this.batHead.addChild(modelRenderer2);
        this.batBody = new ModelRenderer(this, 0, 16);
        this.batBody.addBox(-3.0f, 4.0f, -3.0f, 6.0f, 12.0f, 6.0f);
        this.batBody.setTextureOffset(0, 34).addBox(-5.0f, 16.0f, 0.0f, 10.0f, 6.0f, 1.0f);
        this.batRightWing = new ModelRenderer(this, 42, 0);
        this.batRightWing.addBox(-12.0f, 1.0f, 1.5f, 10.0f, 16.0f, 1.0f);
        this.batOuterRightWing = new ModelRenderer(this, 24, 16);
        this.batOuterRightWing.setRotationPoint(-12.0f, 1.0f, 1.5f);
        this.batOuterRightWing.addBox(-8.0f, 1.0f, 0.0f, 8.0f, 12.0f, 1.0f);
        this.batLeftWing = new ModelRenderer(this, 42, 0);
        this.batLeftWing.mirror = true;
        this.batLeftWing.addBox(2.0f, 1.0f, 1.5f, 10.0f, 16.0f, 1.0f);
        this.batOuterLeftWing = new ModelRenderer(this, 24, 16);
        this.batOuterLeftWing.mirror = true;
        this.batOuterLeftWing.setRotationPoint(12.0f, 1.0f, 1.5f);
        this.batOuterLeftWing.addBox(0.0f, 1.0f, 0.0f, 8.0f, 12.0f, 1.0f);
        this.batBody.addChild(this.batRightWing);
        this.batBody.addChild(this.batLeftWing);
        this.batRightWing.addChild(this.batOuterRightWing);
        this.batLeftWing.addChild(this.batOuterLeftWing);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.batHead, this.batBody);
    }

    @Override
    public void setRotationAngles(BatEntity batEntity, float f, float f2, float f3, float f4, float f5) {
        if (batEntity.getIsBatHanging()) {
            this.batHead.rotateAngleX = f5 * ((float)Math.PI / 180);
            this.batHead.rotateAngleY = (float)Math.PI - f4 * ((float)Math.PI / 180);
            this.batHead.rotateAngleZ = (float)Math.PI;
            this.batHead.setRotationPoint(0.0f, -2.0f, 0.0f);
            this.batRightWing.setRotationPoint(-3.0f, 0.0f, 3.0f);
            this.batLeftWing.setRotationPoint(3.0f, 0.0f, 3.0f);
            this.batBody.rotateAngleX = (float)Math.PI;
            this.batRightWing.rotateAngleX = -0.15707964f;
            this.batRightWing.rotateAngleY = -1.2566371f;
            this.batOuterRightWing.rotateAngleY = -1.7278761f;
            this.batLeftWing.rotateAngleX = this.batRightWing.rotateAngleX;
            this.batLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY;
            this.batOuterLeftWing.rotateAngleY = -this.batOuterRightWing.rotateAngleY;
        } else {
            this.batHead.rotateAngleX = f5 * ((float)Math.PI / 180);
            this.batHead.rotateAngleY = f4 * ((float)Math.PI / 180);
            this.batHead.rotateAngleZ = 0.0f;
            this.batHead.setRotationPoint(0.0f, 0.0f, 0.0f);
            this.batRightWing.setRotationPoint(0.0f, 0.0f, 0.0f);
            this.batLeftWing.setRotationPoint(0.0f, 0.0f, 0.0f);
            this.batBody.rotateAngleX = 0.7853982f + MathHelper.cos(f3 * 0.1f) * 0.15f;
            this.batBody.rotateAngleY = 0.0f;
            this.batRightWing.rotateAngleY = MathHelper.cos(f3 * 1.3f) * (float)Math.PI * 0.25f;
            this.batLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY;
            this.batOuterRightWing.rotateAngleY = this.batRightWing.rotateAngleY * 0.5f;
            this.batOuterLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY * 0.5f;
        }
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((BatEntity)entity2, f, f2, f3, f4, f5);
    }
}

