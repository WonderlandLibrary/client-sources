/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.util.math.MathHelper;

public class HorseModel<T extends AbstractHorseEntity>
extends AgeableModel<T> {
    protected final ModelRenderer body;
    protected final ModelRenderer head;
    private final ModelRenderer field_228262_f_;
    private final ModelRenderer field_228263_g_;
    private final ModelRenderer field_228264_h_;
    private final ModelRenderer field_228265_i_;
    private final ModelRenderer field_228266_j_;
    private final ModelRenderer field_228267_k_;
    private final ModelRenderer field_228268_l_;
    private final ModelRenderer field_228269_m_;
    private final ModelRenderer field_217133_j;
    private final ModelRenderer[] field_217134_k;
    private final ModelRenderer[] field_217135_l;

    public HorseModel(float f) {
        super(true, 16.2f, 1.36f, 2.7272f, 2.0f, 20.0f);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.body = new ModelRenderer(this, 0, 32);
        this.body.addBox(-5.0f, -8.0f, -17.0f, 10.0f, 10.0f, 22.0f, 0.05f);
        this.body.setRotationPoint(0.0f, 11.0f, 5.0f);
        this.head = new ModelRenderer(this, 0, 35);
        this.head.addBox(-2.05f, -6.0f, -2.0f, 4.0f, 12.0f, 7.0f);
        this.head.rotateAngleX = 0.5235988f;
        ModelRenderer modelRenderer = new ModelRenderer(this, 0, 13);
        modelRenderer.addBox(-3.0f, -11.0f, -2.0f, 6.0f, 5.0f, 7.0f, f);
        ModelRenderer modelRenderer2 = new ModelRenderer(this, 56, 36);
        modelRenderer2.addBox(-1.0f, -11.0f, 5.01f, 2.0f, 16.0f, 2.0f, f);
        ModelRenderer modelRenderer3 = new ModelRenderer(this, 0, 25);
        modelRenderer3.addBox(-2.0f, -11.0f, -7.0f, 4.0f, 5.0f, 5.0f, f);
        this.head.addChild(modelRenderer);
        this.head.addChild(modelRenderer2);
        this.head.addChild(modelRenderer3);
        this.func_199047_a(this.head);
        this.field_228262_f_ = new ModelRenderer(this, 48, 21);
        this.field_228262_f_.mirror = true;
        this.field_228262_f_.addBox(-3.0f, -1.01f, -1.0f, 4.0f, 11.0f, 4.0f, f);
        this.field_228262_f_.setRotationPoint(4.0f, 14.0f, 7.0f);
        this.field_228263_g_ = new ModelRenderer(this, 48, 21);
        this.field_228263_g_.addBox(-1.0f, -1.01f, -1.0f, 4.0f, 11.0f, 4.0f, f);
        this.field_228263_g_.setRotationPoint(-4.0f, 14.0f, 7.0f);
        this.field_228264_h_ = new ModelRenderer(this, 48, 21);
        this.field_228264_h_.mirror = true;
        this.field_228264_h_.addBox(-3.0f, -1.01f, -1.9f, 4.0f, 11.0f, 4.0f, f);
        this.field_228264_h_.setRotationPoint(4.0f, 6.0f, -12.0f);
        this.field_228265_i_ = new ModelRenderer(this, 48, 21);
        this.field_228265_i_.addBox(-1.0f, -1.01f, -1.9f, 4.0f, 11.0f, 4.0f, f);
        this.field_228265_i_.setRotationPoint(-4.0f, 6.0f, -12.0f);
        float f2 = 5.5f;
        this.field_228266_j_ = new ModelRenderer(this, 48, 21);
        this.field_228266_j_.mirror = true;
        this.field_228266_j_.addBox(-3.0f, -1.01f, -1.0f, 4.0f, 11.0f, 4.0f, f, f + 5.5f, f);
        this.field_228266_j_.setRotationPoint(4.0f, 14.0f, 7.0f);
        this.field_228267_k_ = new ModelRenderer(this, 48, 21);
        this.field_228267_k_.addBox(-1.0f, -1.01f, -1.0f, 4.0f, 11.0f, 4.0f, f, f + 5.5f, f);
        this.field_228267_k_.setRotationPoint(-4.0f, 14.0f, 7.0f);
        this.field_228268_l_ = new ModelRenderer(this, 48, 21);
        this.field_228268_l_.mirror = true;
        this.field_228268_l_.addBox(-3.0f, -1.01f, -1.9f, 4.0f, 11.0f, 4.0f, f, f + 5.5f, f);
        this.field_228268_l_.setRotationPoint(4.0f, 6.0f, -12.0f);
        this.field_228269_m_ = new ModelRenderer(this, 48, 21);
        this.field_228269_m_.addBox(-1.0f, -1.01f, -1.9f, 4.0f, 11.0f, 4.0f, f, f + 5.5f, f);
        this.field_228269_m_.setRotationPoint(-4.0f, 6.0f, -12.0f);
        this.field_217133_j = new ModelRenderer(this, 42, 36);
        this.field_217133_j.addBox(-1.5f, 0.0f, 0.0f, 3.0f, 14.0f, 4.0f, f);
        this.field_217133_j.setRotationPoint(0.0f, -5.0f, 2.0f);
        this.field_217133_j.rotateAngleX = 0.5235988f;
        this.body.addChild(this.field_217133_j);
        ModelRenderer modelRenderer4 = new ModelRenderer(this, 26, 0);
        modelRenderer4.addBox(-5.0f, -8.0f, -9.0f, 10.0f, 9.0f, 9.0f, 0.5f);
        this.body.addChild(modelRenderer4);
        ModelRenderer modelRenderer5 = new ModelRenderer(this, 29, 5);
        modelRenderer5.addBox(2.0f, -9.0f, -6.0f, 1.0f, 2.0f, 2.0f, f);
        this.head.addChild(modelRenderer5);
        ModelRenderer modelRenderer6 = new ModelRenderer(this, 29, 5);
        modelRenderer6.addBox(-3.0f, -9.0f, -6.0f, 1.0f, 2.0f, 2.0f, f);
        this.head.addChild(modelRenderer6);
        ModelRenderer modelRenderer7 = new ModelRenderer(this, 32, 2);
        modelRenderer7.addBox(3.1f, -6.0f, -8.0f, 0.0f, 3.0f, 16.0f, f);
        modelRenderer7.rotateAngleX = -0.5235988f;
        this.head.addChild(modelRenderer7);
        ModelRenderer modelRenderer8 = new ModelRenderer(this, 32, 2);
        modelRenderer8.addBox(-3.1f, -6.0f, -8.0f, 0.0f, 3.0f, 16.0f, f);
        modelRenderer8.rotateAngleX = -0.5235988f;
        this.head.addChild(modelRenderer8);
        ModelRenderer modelRenderer9 = new ModelRenderer(this, 1, 1);
        modelRenderer9.addBox(-3.0f, -11.0f, -1.9f, 6.0f, 5.0f, 6.0f, 0.2f);
        this.head.addChild(modelRenderer9);
        ModelRenderer modelRenderer10 = new ModelRenderer(this, 19, 0);
        modelRenderer10.addBox(-2.0f, -11.0f, -4.0f, 4.0f, 5.0f, 2.0f, 0.2f);
        this.head.addChild(modelRenderer10);
        this.field_217134_k = new ModelRenderer[]{modelRenderer4, modelRenderer5, modelRenderer6, modelRenderer9, modelRenderer10};
        this.field_217135_l = new ModelRenderer[]{modelRenderer7, modelRenderer8};
    }

    protected void func_199047_a(ModelRenderer modelRenderer) {
        ModelRenderer modelRenderer2 = new ModelRenderer(this, 19, 16);
        modelRenderer2.addBox(0.55f, -13.0f, 4.0f, 2.0f, 3.0f, 1.0f, -0.001f);
        ModelRenderer modelRenderer3 = new ModelRenderer(this, 19, 16);
        modelRenderer3.addBox(-2.55f, -13.0f, 4.0f, 2.0f, 3.0f, 1.0f, -0.001f);
        modelRenderer.addChild(modelRenderer2);
        modelRenderer.addChild(modelRenderer3);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        boolean bl = ((AbstractHorseEntity)t).isHorseSaddled();
        boolean bl2 = ((Entity)t).isBeingRidden();
        for (ModelRenderer modelRenderer : this.field_217134_k) {
            modelRenderer.showModel = bl;
        }
        for (ModelRenderer modelRenderer : this.field_217135_l) {
            modelRenderer.showModel = bl2 && bl;
        }
        this.body.rotationPointY = 11.0f;
    }

    @Override
    public Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(this.body, this.field_228262_f_, this.field_228263_g_, this.field_228264_h_, this.field_228265_i_, this.field_228266_j_, this.field_228267_k_, this.field_228268_l_, this.field_228269_m_);
    }

    @Override
    public void setLivingAnimations(T t, float f, float f2, float f3) {
        super.setLivingAnimations(t, f, f2, f3);
        float f4 = MathHelper.rotLerp(((AbstractHorseEntity)t).prevRenderYawOffset, ((AbstractHorseEntity)t).renderYawOffset, f3);
        float f5 = MathHelper.rotLerp(((AbstractHorseEntity)t).prevRotationYawHead, ((AbstractHorseEntity)t).rotationYawHead, f3);
        float f6 = MathHelper.lerp(f3, ((AbstractHorseEntity)t).prevRotationPitch, ((AbstractHorseEntity)t).rotationPitch);
        float f7 = f5 - f4;
        float f8 = f6 * ((float)Math.PI / 180);
        if (f7 > 20.0f) {
            f7 = 20.0f;
        }
        if (f7 < -20.0f) {
            f7 = -20.0f;
        }
        if (f2 > 0.2f) {
            f8 += MathHelper.cos(f * 0.4f) * 0.15f * f2;
        }
        float f9 = ((AbstractHorseEntity)t).getGrassEatingAmount(f3);
        float f10 = ((AbstractHorseEntity)t).getRearingAmount(f3);
        float f11 = 1.0f - f10;
        float f12 = ((AbstractHorseEntity)t).getMouthOpennessAngle(f3);
        boolean bl = ((AbstractHorseEntity)t).tailCounter != 0;
        float f13 = (float)((AbstractHorseEntity)t).ticksExisted + f3;
        this.head.rotationPointY = 4.0f;
        this.head.rotationPointZ = -12.0f;
        this.body.rotateAngleX = 0.0f;
        this.head.rotateAngleX = 0.5235988f + f8;
        this.head.rotateAngleY = f7 * ((float)Math.PI / 180);
        float f14 = ((Entity)t).isInWater() ? 0.2f : 1.0f;
        float f15 = MathHelper.cos(f14 * f * 0.6662f + (float)Math.PI);
        float f16 = f15 * 0.8f * f2;
        float f17 = (1.0f - Math.max(f10, f9)) * (0.5235988f + f8 + f12 * MathHelper.sin(f13) * 0.05f);
        this.head.rotateAngleX = f10 * (0.2617994f + f8) + f9 * (2.1816616f + MathHelper.sin(f13) * 0.05f) + f17;
        this.head.rotateAngleY = f10 * f7 * ((float)Math.PI / 180) + (1.0f - Math.max(f10, f9)) * this.head.rotateAngleY;
        this.head.rotationPointY = f10 * -4.0f + f9 * 11.0f + (1.0f - Math.max(f10, f9)) * this.head.rotationPointY;
        this.head.rotationPointZ = f10 * -4.0f + f9 * -12.0f + (1.0f - Math.max(f10, f9)) * this.head.rotationPointZ;
        this.body.rotateAngleX = f10 * -0.7853982f + f11 * this.body.rotateAngleX;
        float f18 = 0.2617994f * f10;
        float f19 = MathHelper.cos(f13 * 0.6f + (float)Math.PI);
        this.field_228264_h_.rotationPointY = 2.0f * f10 + 14.0f * f11;
        this.field_228264_h_.rotationPointZ = -6.0f * f10 - 10.0f * f11;
        this.field_228265_i_.rotationPointY = this.field_228264_h_.rotationPointY;
        this.field_228265_i_.rotationPointZ = this.field_228264_h_.rotationPointZ;
        float f20 = (-1.0471976f + f19) * f10 + f16 * f11;
        float f21 = (-1.0471976f - f19) * f10 - f16 * f11;
        this.field_228262_f_.rotateAngleX = f18 - f15 * 0.5f * f2 * f11;
        this.field_228263_g_.rotateAngleX = f18 + f15 * 0.5f * f2 * f11;
        this.field_228264_h_.rotateAngleX = f20;
        this.field_228265_i_.rotateAngleX = f21;
        this.field_217133_j.rotateAngleX = 0.5235988f + f2 * 0.75f;
        this.field_217133_j.rotationPointY = -5.0f + f2;
        this.field_217133_j.rotationPointZ = 2.0f + f2 * 2.0f;
        this.field_217133_j.rotateAngleY = bl ? MathHelper.cos(f13 * 0.7f) : 0.0f;
        this.field_228266_j_.rotationPointY = this.field_228262_f_.rotationPointY;
        this.field_228266_j_.rotationPointZ = this.field_228262_f_.rotationPointZ;
        this.field_228266_j_.rotateAngleX = this.field_228262_f_.rotateAngleX;
        this.field_228267_k_.rotationPointY = this.field_228263_g_.rotationPointY;
        this.field_228267_k_.rotationPointZ = this.field_228263_g_.rotationPointZ;
        this.field_228267_k_.rotateAngleX = this.field_228263_g_.rotateAngleX;
        this.field_228268_l_.rotationPointY = this.field_228264_h_.rotationPointY;
        this.field_228268_l_.rotationPointZ = this.field_228264_h_.rotationPointZ;
        this.field_228268_l_.rotateAngleX = this.field_228264_h_.rotateAngleX;
        this.field_228269_m_.rotationPointY = this.field_228265_i_.rotationPointY;
        this.field_228269_m_.rotationPointZ = this.field_228265_i_.rotationPointZ;
        this.field_228269_m_.rotateAngleX = this.field_228265_i_.rotateAngleX;
        boolean bl2 = ((AgeableEntity)t).isChild();
        this.field_228262_f_.showModel = !bl2;
        this.field_228263_g_.showModel = !bl2;
        this.field_228264_h_.showModel = !bl2;
        this.field_228265_i_.showModel = !bl2;
        this.field_228266_j_.showModel = bl2;
        this.field_228267_k_.showModel = bl2;
        this.field_228268_l_.showModel = bl2;
        this.field_228269_m_.showModel = bl2;
        this.body.rotationPointY = bl2 ? 10.8f : 0.0f;
    }

    @Override
    public void setLivingAnimations(Entity entity2, float f, float f2, float f3) {
        this.setLivingAnimations((T)((AbstractHorseEntity)entity2), f, f2, f3);
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((AbstractHorseEntity)entity2), f, f2, f3, f4, f5);
    }
}

