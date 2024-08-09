/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.PolarBearEntity;

public class PolarBearModel<T extends PolarBearEntity>
extends QuadrupedModel<T> {
    public PolarBearModel() {
        super(12, 0.0f, true, 16.0f, 4.0f, 2.25f, 2.0f, 24);
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.headModel = new ModelRenderer(this, 0, 0);
        this.headModel.addBox(-3.5f, -3.0f, -3.0f, 7.0f, 7.0f, 7.0f, 0.0f);
        this.headModel.setRotationPoint(0.0f, 10.0f, -16.0f);
        this.headModel.setTextureOffset(0, 44).addBox(-2.5f, 1.0f, -6.0f, 5.0f, 3.0f, 3.0f, 0.0f);
        this.headModel.setTextureOffset(26, 0).addBox(-4.5f, -4.0f, -1.0f, 2.0f, 2.0f, 1.0f, 0.0f);
        ModelRenderer modelRenderer = this.headModel.setTextureOffset(26, 0);
        modelRenderer.mirror = true;
        modelRenderer.addBox(2.5f, -4.0f, -1.0f, 2.0f, 2.0f, 1.0f, 0.0f);
        this.body = new ModelRenderer(this);
        this.body.setTextureOffset(0, 19).addBox(-5.0f, -13.0f, -7.0f, 14.0f, 14.0f, 11.0f, 0.0f);
        this.body.setTextureOffset(39, 0).addBox(-4.0f, -25.0f, -7.0f, 12.0f, 12.0f, 10.0f, 0.0f);
        this.body.setRotationPoint(-2.0f, 9.0f, 12.0f);
        int n = 10;
        this.legBackRight = new ModelRenderer(this, 50, 22);
        this.legBackRight.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 10.0f, 8.0f, 0.0f);
        this.legBackRight.setRotationPoint(-3.5f, 14.0f, 6.0f);
        this.legBackLeft = new ModelRenderer(this, 50, 22);
        this.legBackLeft.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 10.0f, 8.0f, 0.0f);
        this.legBackLeft.setRotationPoint(3.5f, 14.0f, 6.0f);
        this.legFrontRight = new ModelRenderer(this, 50, 40);
        this.legFrontRight.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 10.0f, 6.0f, 0.0f);
        this.legFrontRight.setRotationPoint(-2.5f, 14.0f, -7.0f);
        this.legFrontLeft = new ModelRenderer(this, 50, 40);
        this.legFrontLeft.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 10.0f, 6.0f, 0.0f);
        this.legFrontLeft.setRotationPoint(2.5f, 14.0f, -7.0f);
        this.legBackRight.rotationPointX -= 1.0f;
        this.legBackLeft.rotationPointX += 1.0f;
        this.legBackRight.rotationPointZ += 0.0f;
        this.legBackLeft.rotationPointZ += 0.0f;
        this.legFrontRight.rotationPointX -= 1.0f;
        this.legFrontLeft.rotationPointX += 1.0f;
        this.legFrontRight.rotationPointZ -= 1.0f;
        this.legFrontLeft.rotationPointZ -= 1.0f;
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        super.setRotationAngles(t, f, f2, f3, f4, f5);
        float f6 = f3 - (float)((PolarBearEntity)t).ticksExisted;
        float f7 = ((PolarBearEntity)t).getStandingAnimationScale(f6);
        f7 *= f7;
        float f8 = 1.0f - f7;
        this.body.rotateAngleX = 1.5707964f - f7 * (float)Math.PI * 0.35f;
        this.body.rotationPointY = 9.0f * f8 + 11.0f * f7;
        this.legFrontRight.rotationPointY = 14.0f * f8 - 6.0f * f7;
        this.legFrontRight.rotationPointZ = -8.0f * f8 - 4.0f * f7;
        this.legFrontRight.rotateAngleX -= f7 * (float)Math.PI * 0.45f;
        this.legFrontLeft.rotationPointY = this.legFrontRight.rotationPointY;
        this.legFrontLeft.rotationPointZ = this.legFrontRight.rotationPointZ;
        this.legFrontLeft.rotateAngleX -= f7 * (float)Math.PI * 0.45f;
        if (this.isChild) {
            this.headModel.rotationPointY = 10.0f * f8 - 9.0f * f7;
            this.headModel.rotationPointZ = -16.0f * f8 - 7.0f * f7;
        } else {
            this.headModel.rotationPointY = 10.0f * f8 - 14.0f * f7;
            this.headModel.rotationPointZ = -16.0f * f8 - 3.0f * f7;
        }
        this.headModel.rotateAngleX += f7 * (float)Math.PI * 0.15f;
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((PolarBearEntity)entity2), f, f2, f3, f4, f5);
    }
}

