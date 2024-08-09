/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class EndermanModel<T extends LivingEntity>
extends BipedModel<T> {
    public boolean isCarrying;
    public boolean isAttacking;

    public EndermanModel(float f) {
        super(0.0f, -14.0f, 64, 32);
        float f2 = -14.0f;
        this.bipedHeadwear = new ModelRenderer(this, 0, 16);
        this.bipedHeadwear.addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, f - 0.5f);
        this.bipedHeadwear.setRotationPoint(0.0f, -14.0f, 0.0f);
        this.bipedBody = new ModelRenderer(this, 32, 16);
        this.bipedBody.addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, f);
        this.bipedBody.setRotationPoint(0.0f, -14.0f, 0.0f);
        this.bipedRightArm = new ModelRenderer(this, 56, 0);
        this.bipedRightArm.addBox(-1.0f, -2.0f, -1.0f, 2.0f, 30.0f, 2.0f, f);
        this.bipedRightArm.setRotationPoint(-3.0f, -12.0f, 0.0f);
        this.bipedLeftArm = new ModelRenderer(this, 56, 0);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -1.0f, 2.0f, 30.0f, 2.0f, f);
        this.bipedLeftArm.setRotationPoint(5.0f, -12.0f, 0.0f);
        this.bipedRightLeg = new ModelRenderer(this, 56, 0);
        this.bipedRightLeg.addBox(-1.0f, 0.0f, -1.0f, 2.0f, 30.0f, 2.0f, f);
        this.bipedRightLeg.setRotationPoint(-2.0f, -2.0f, 0.0f);
        this.bipedLeftLeg = new ModelRenderer(this, 56, 0);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-1.0f, 0.0f, -1.0f, 2.0f, 30.0f, 2.0f, f);
        this.bipedLeftLeg.setRotationPoint(2.0f, -2.0f, 0.0f);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        float f6;
        super.setRotationAngles(t, f, f2, f3, f4, f5);
        this.bipedHead.showModel = true;
        float f7 = -14.0f;
        this.bipedBody.rotateAngleX = 0.0f;
        this.bipedBody.rotationPointY = -14.0f;
        this.bipedBody.rotationPointZ = -0.0f;
        this.bipedRightLeg.rotateAngleX -= 0.0f;
        this.bipedLeftLeg.rotateAngleX -= 0.0f;
        this.bipedRightArm.rotateAngleX = (float)((double)this.bipedRightArm.rotateAngleX * 0.5);
        this.bipedLeftArm.rotateAngleX = (float)((double)this.bipedLeftArm.rotateAngleX * 0.5);
        this.bipedRightLeg.rotateAngleX = (float)((double)this.bipedRightLeg.rotateAngleX * 0.5);
        this.bipedLeftLeg.rotateAngleX = (float)((double)this.bipedLeftLeg.rotateAngleX * 0.5);
        float f8 = 0.4f;
        if (this.bipedRightArm.rotateAngleX > 0.4f) {
            this.bipedRightArm.rotateAngleX = 0.4f;
        }
        if (this.bipedLeftArm.rotateAngleX > 0.4f) {
            this.bipedLeftArm.rotateAngleX = 0.4f;
        }
        if (this.bipedRightArm.rotateAngleX < -0.4f) {
            this.bipedRightArm.rotateAngleX = -0.4f;
        }
        if (this.bipedLeftArm.rotateAngleX < -0.4f) {
            this.bipedLeftArm.rotateAngleX = -0.4f;
        }
        if (this.bipedRightLeg.rotateAngleX > 0.4f) {
            this.bipedRightLeg.rotateAngleX = 0.4f;
        }
        if (this.bipedLeftLeg.rotateAngleX > 0.4f) {
            this.bipedLeftLeg.rotateAngleX = 0.4f;
        }
        if (this.bipedRightLeg.rotateAngleX < -0.4f) {
            this.bipedRightLeg.rotateAngleX = -0.4f;
        }
        if (this.bipedLeftLeg.rotateAngleX < -0.4f) {
            this.bipedLeftLeg.rotateAngleX = -0.4f;
        }
        if (this.isCarrying) {
            this.bipedRightArm.rotateAngleX = -0.5f;
            this.bipedLeftArm.rotateAngleX = -0.5f;
            this.bipedRightArm.rotateAngleZ = 0.05f;
            this.bipedLeftArm.rotateAngleZ = -0.05f;
        }
        this.bipedRightArm.rotationPointZ = 0.0f;
        this.bipedLeftArm.rotationPointZ = 0.0f;
        this.bipedRightLeg.rotationPointZ = 0.0f;
        this.bipedLeftLeg.rotationPointZ = 0.0f;
        this.bipedRightLeg.rotationPointY = -5.0f;
        this.bipedLeftLeg.rotationPointY = -5.0f;
        this.bipedHead.rotationPointZ = -0.0f;
        this.bipedHead.rotationPointY = -13.0f;
        this.bipedHeadwear.rotationPointX = this.bipedHead.rotationPointX;
        this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
        this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;
        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;
        if (this.isAttacking) {
            f6 = 1.0f;
            this.bipedHead.rotationPointY -= 5.0f;
        }
        f6 = -14.0f;
        this.bipedRightArm.setRotationPoint(-5.0f, -12.0f, 0.0f);
        this.bipedLeftArm.setRotationPoint(5.0f, -12.0f, 0.0f);
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((LivingEntity)entity2), f, f2, f3, f4, f5);
    }
}

