/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.util.math.MathHelper;

public class RavagerModel
extends SegmentedModel<RavagerEntity> {
    private final ModelRenderer head;
    private final ModelRenderer jaw;
    private final ModelRenderer body;
    private final ModelRenderer legBackRight;
    private final ModelRenderer legBackLeft;
    private final ModelRenderer legFrontRight;
    private final ModelRenderer legFrontLeft;
    private final ModelRenderer neck;

    public RavagerModel() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        int n = 16;
        float f = 0.0f;
        this.neck = new ModelRenderer(this);
        this.neck.setRotationPoint(0.0f, -7.0f, -1.5f);
        this.neck.setTextureOffset(68, 73).addBox(-5.0f, -1.0f, -18.0f, 10.0f, 10.0f, 18.0f, 0.0f);
        this.head = new ModelRenderer(this);
        this.head.setRotationPoint(0.0f, 16.0f, -17.0f);
        this.head.setTextureOffset(0, 0).addBox(-8.0f, -20.0f, -14.0f, 16.0f, 20.0f, 16.0f, 0.0f);
        this.head.setTextureOffset(0, 0).addBox(-2.0f, -6.0f, -18.0f, 4.0f, 8.0f, 4.0f, 0.0f);
        ModelRenderer modelRenderer = new ModelRenderer(this);
        modelRenderer.setRotationPoint(-10.0f, -14.0f, -8.0f);
        modelRenderer.setTextureOffset(74, 55).addBox(0.0f, -14.0f, -2.0f, 2.0f, 14.0f, 4.0f, 0.0f);
        modelRenderer.rotateAngleX = 1.0995574f;
        this.head.addChild(modelRenderer);
        ModelRenderer modelRenderer2 = new ModelRenderer(this);
        modelRenderer2.mirror = true;
        modelRenderer2.setRotationPoint(8.0f, -14.0f, -8.0f);
        modelRenderer2.setTextureOffset(74, 55).addBox(0.0f, -14.0f, -2.0f, 2.0f, 14.0f, 4.0f, 0.0f);
        modelRenderer2.rotateAngleX = 1.0995574f;
        this.head.addChild(modelRenderer2);
        this.jaw = new ModelRenderer(this);
        this.jaw.setRotationPoint(0.0f, -2.0f, 2.0f);
        this.jaw.setTextureOffset(0, 36).addBox(-8.0f, 0.0f, -16.0f, 16.0f, 3.0f, 16.0f, 0.0f);
        this.head.addChild(this.jaw);
        this.neck.addChild(this.head);
        this.body = new ModelRenderer(this);
        this.body.setTextureOffset(0, 55).addBox(-7.0f, -10.0f, -7.0f, 14.0f, 16.0f, 20.0f, 0.0f);
        this.body.setTextureOffset(0, 91).addBox(-6.0f, 6.0f, -7.0f, 12.0f, 13.0f, 18.0f, 0.0f);
        this.body.setRotationPoint(0.0f, 1.0f, 2.0f);
        this.legBackRight = new ModelRenderer(this, 96, 0);
        this.legBackRight.addBox(-4.0f, 0.0f, -4.0f, 8.0f, 37.0f, 8.0f, 0.0f);
        this.legBackRight.setRotationPoint(-8.0f, -13.0f, 18.0f);
        this.legBackLeft = new ModelRenderer(this, 96, 0);
        this.legBackLeft.mirror = true;
        this.legBackLeft.addBox(-4.0f, 0.0f, -4.0f, 8.0f, 37.0f, 8.0f, 0.0f);
        this.legBackLeft.setRotationPoint(8.0f, -13.0f, 18.0f);
        this.legFrontRight = new ModelRenderer(this, 64, 0);
        this.legFrontRight.addBox(-4.0f, 0.0f, -4.0f, 8.0f, 37.0f, 8.0f, 0.0f);
        this.legFrontRight.setRotationPoint(-8.0f, -13.0f, -5.0f);
        this.legFrontLeft = new ModelRenderer(this, 64, 0);
        this.legFrontLeft.mirror = true;
        this.legFrontLeft.addBox(-4.0f, 0.0f, -4.0f, 8.0f, 37.0f, 8.0f, 0.0f);
        this.legFrontLeft.setRotationPoint(8.0f, -13.0f, -5.0f);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.neck, this.body, this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft);
    }

    @Override
    public void setRotationAngles(RavagerEntity ravagerEntity, float f, float f2, float f3, float f4, float f5) {
        this.head.rotateAngleX = f5 * ((float)Math.PI / 180);
        this.head.rotateAngleY = f4 * ((float)Math.PI / 180);
        this.body.rotateAngleX = 1.5707964f;
        float f6 = 0.4f * f2;
        this.legBackRight.rotateAngleX = MathHelper.cos(f * 0.6662f) * f6;
        this.legBackLeft.rotateAngleX = MathHelper.cos(f * 0.6662f + (float)Math.PI) * f6;
        this.legFrontRight.rotateAngleX = MathHelper.cos(f * 0.6662f + (float)Math.PI) * f6;
        this.legFrontLeft.rotateAngleX = MathHelper.cos(f * 0.6662f) * f6;
    }

    @Override
    public void setLivingAnimations(RavagerEntity ravagerEntity, float f, float f2, float f3) {
        super.setLivingAnimations(ravagerEntity, f, f2, f3);
        int n = ravagerEntity.func_213684_dX();
        int n2 = ravagerEntity.func_213687_eg();
        int n3 = 20;
        int n4 = ravagerEntity.func_213683_l();
        int n5 = 10;
        if (n4 > 0) {
            float f4 = MathHelper.func_233021_e_((float)n4 - f3, 10.0f);
            float f5 = (1.0f + f4) * 0.5f;
            float f6 = f5 * f5 * f5 * 12.0f;
            float f7 = f6 * MathHelper.sin(this.neck.rotateAngleX);
            this.neck.rotationPointZ = -6.5f + f6;
            this.neck.rotationPointY = -7.0f - f7;
            float f8 = MathHelper.sin(((float)n4 - f3) / 10.0f * (float)Math.PI * 0.25f);
            this.jaw.rotateAngleX = 1.5707964f * f8;
            this.jaw.rotateAngleX = n4 > 5 ? MathHelper.sin(((float)(-4 + n4) - f3) / 4.0f) * (float)Math.PI * 0.4f : 0.15707964f * MathHelper.sin((float)Math.PI * ((float)n4 - f3) / 10.0f);
        } else {
            float f9 = -1.0f;
            float f10 = -1.0f * MathHelper.sin(this.neck.rotateAngleX);
            this.neck.rotationPointX = 0.0f;
            this.neck.rotationPointY = -7.0f - f10;
            this.neck.rotationPointZ = 5.5f;
            boolean bl = n > 0;
            this.neck.rotateAngleX = bl ? 0.2199115f : 0.0f;
            this.jaw.rotateAngleX = (float)Math.PI * (bl ? 0.05f : 0.01f);
            if (bl) {
                double d = (double)n / 40.0;
                this.neck.rotationPointX = (float)Math.sin(d * 10.0) * 3.0f;
            } else if (n2 > 0) {
                float f11 = MathHelper.sin(((float)(20 - n2) - f3) / 20.0f * (float)Math.PI * 0.25f);
                this.jaw.rotateAngleX = 1.5707964f * f11;
            }
        }
    }

    @Override
    public void setLivingAnimations(Entity entity2, float f, float f2, float f3) {
        this.setLivingAnimations((RavagerEntity)entity2, f, f2, f3);
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((RavagerEntity)entity2, f, f2, f3, f4, f5);
    }
}

