/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.ModelUtils;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.util.math.MathHelper;

public class PandaModel<T extends PandaEntity>
extends QuadrupedModel<T> {
    private float field_217164_l;
    private float field_217165_m;
    private float field_217166_n;

    public PandaModel(int n, float f) {
        super(n, f, true, 23.0f, 4.8f, 2.7f, 3.0f, 49);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.headModel = new ModelRenderer(this, 0, 6);
        this.headModel.addBox(-6.5f, -5.0f, -4.0f, 13.0f, 10.0f, 9.0f);
        this.headModel.setRotationPoint(0.0f, 11.5f, -17.0f);
        this.headModel.setTextureOffset(45, 16).addBox(-3.5f, 0.0f, -6.0f, 7.0f, 5.0f, 2.0f);
        this.headModel.setTextureOffset(52, 25).addBox(-8.5f, -8.0f, -1.0f, 5.0f, 4.0f, 1.0f);
        this.headModel.setTextureOffset(52, 25).addBox(3.5f, -8.0f, -1.0f, 5.0f, 4.0f, 1.0f);
        this.body = new ModelRenderer(this, 0, 25);
        this.body.addBox(-9.5f, -13.0f, -6.5f, 19.0f, 26.0f, 13.0f);
        this.body.setRotationPoint(0.0f, 10.0f, 0.0f);
        int n2 = 9;
        int n3 = 6;
        this.legBackRight = new ModelRenderer(this, 40, 0);
        this.legBackRight.addBox(-3.0f, 0.0f, -3.0f, 6.0f, 9.0f, 6.0f);
        this.legBackRight.setRotationPoint(-5.5f, 15.0f, 9.0f);
        this.legBackLeft = new ModelRenderer(this, 40, 0);
        this.legBackLeft.addBox(-3.0f, 0.0f, -3.0f, 6.0f, 9.0f, 6.0f);
        this.legBackLeft.setRotationPoint(5.5f, 15.0f, 9.0f);
        this.legFrontRight = new ModelRenderer(this, 40, 0);
        this.legFrontRight.addBox(-3.0f, 0.0f, -3.0f, 6.0f, 9.0f, 6.0f);
        this.legFrontRight.setRotationPoint(-5.5f, 15.0f, -9.0f);
        this.legFrontLeft = new ModelRenderer(this, 40, 0);
        this.legFrontLeft.addBox(-3.0f, 0.0f, -3.0f, 6.0f, 9.0f, 6.0f);
        this.legFrontLeft.setRotationPoint(5.5f, 15.0f, -9.0f);
    }

    @Override
    public void setLivingAnimations(T t, float f, float f2, float f3) {
        super.setLivingAnimations(t, f, f2, f3);
        this.field_217164_l = ((PandaEntity)t).func_213561_v(f3);
        this.field_217165_m = ((PandaEntity)t).func_213583_w(f3);
        this.field_217166_n = ((AgeableEntity)t).isChild() ? 0.0f : ((PandaEntity)t).func_213591_x(f3);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        super.setRotationAngles(t, f, f2, f3, f4, f5);
        boolean bl = ((PandaEntity)t).getUnhappyCounter() > 0;
        boolean bl2 = ((PandaEntity)t).func_213539_dW();
        int n = ((PandaEntity)t).getSneezeCounter();
        boolean bl3 = ((PandaEntity)t).func_213578_dZ();
        boolean bl4 = ((PandaEntity)t).func_213566_eo();
        if (bl) {
            this.headModel.rotateAngleY = 0.35f * MathHelper.sin(0.6f * f3);
            this.headModel.rotateAngleZ = 0.35f * MathHelper.sin(0.6f * f3);
            this.legFrontRight.rotateAngleX = -0.75f * MathHelper.sin(0.3f * f3);
            this.legFrontLeft.rotateAngleX = 0.75f * MathHelper.sin(0.3f * f3);
        } else {
            this.headModel.rotateAngleZ = 0.0f;
        }
        if (bl2) {
            if (n < 15) {
                this.headModel.rotateAngleX = -0.7853982f * (float)n / 14.0f;
            } else if (n < 20) {
                float f6 = (n - 15) / 5;
                this.headModel.rotateAngleX = -0.7853982f + 0.7853982f * f6;
            }
        }
        if (this.field_217164_l > 0.0f) {
            this.body.rotateAngleX = ModelUtils.func_228283_a_(this.body.rotateAngleX, 1.7407963f, this.field_217164_l);
            this.headModel.rotateAngleX = ModelUtils.func_228283_a_(this.headModel.rotateAngleX, 1.5707964f, this.field_217164_l);
            this.legFrontRight.rotateAngleZ = -0.27079642f;
            this.legFrontLeft.rotateAngleZ = 0.27079642f;
            this.legBackRight.rotateAngleZ = 0.5707964f;
            this.legBackLeft.rotateAngleZ = -0.5707964f;
            if (bl3) {
                this.headModel.rotateAngleX = 1.5707964f + 0.2f * MathHelper.sin(f3 * 0.6f);
                this.legFrontRight.rotateAngleX = -0.4f - 0.2f * MathHelper.sin(f3 * 0.6f);
                this.legFrontLeft.rotateAngleX = -0.4f - 0.2f * MathHelper.sin(f3 * 0.6f);
            }
            if (bl4) {
                this.headModel.rotateAngleX = 2.1707964f;
                this.legFrontRight.rotateAngleX = -0.9f;
                this.legFrontLeft.rotateAngleX = -0.9f;
            }
        } else {
            this.legBackRight.rotateAngleZ = 0.0f;
            this.legBackLeft.rotateAngleZ = 0.0f;
            this.legFrontRight.rotateAngleZ = 0.0f;
            this.legFrontLeft.rotateAngleZ = 0.0f;
        }
        if (this.field_217165_m > 0.0f) {
            this.legBackRight.rotateAngleX = -0.6f * MathHelper.sin(f3 * 0.15f);
            this.legBackLeft.rotateAngleX = 0.6f * MathHelper.sin(f3 * 0.15f);
            this.legFrontRight.rotateAngleX = 0.3f * MathHelper.sin(f3 * 0.25f);
            this.legFrontLeft.rotateAngleX = -0.3f * MathHelper.sin(f3 * 0.25f);
            this.headModel.rotateAngleX = ModelUtils.func_228283_a_(this.headModel.rotateAngleX, 1.5707964f, this.field_217165_m);
        }
        if (this.field_217166_n > 0.0f) {
            this.headModel.rotateAngleX = ModelUtils.func_228283_a_(this.headModel.rotateAngleX, 2.0561945f, this.field_217166_n);
            this.legBackRight.rotateAngleX = -0.5f * MathHelper.sin(f3 * 0.5f);
            this.legBackLeft.rotateAngleX = 0.5f * MathHelper.sin(f3 * 0.5f);
            this.legFrontRight.rotateAngleX = 0.5f * MathHelper.sin(f3 * 0.5f);
            this.legFrontLeft.rotateAngleX = -0.5f * MathHelper.sin(f3 * 0.5f);
        }
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((PandaEntity)entity2), f, f2, f3, f4, f5);
    }

    @Override
    public void setLivingAnimations(Entity entity2, float f, float f2, float f3) {
        this.setLivingAnimations((T)((PandaEntity)entity2), f, f2, f3);
    }
}

