/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.math.MathHelper;

public class FoxModel<T extends FoxEntity>
extends AgeableModel<T> {
    public final ModelRenderer head;
    private final ModelRenderer rightEar;
    private final ModelRenderer leftEar;
    private final ModelRenderer snout;
    private final ModelRenderer body;
    private final ModelRenderer legBackRight;
    private final ModelRenderer legBackLeft;
    private final ModelRenderer legFrontRight;
    private final ModelRenderer legFrontLeft;
    private final ModelRenderer tail;
    private float field_217125_n;

    public FoxModel() {
        super(true, 8.0f, 3.35f);
        this.textureWidth = 48;
        this.textureHeight = 32;
        this.head = new ModelRenderer(this, 1, 5);
        this.head.addBox(-3.0f, -2.0f, -5.0f, 8.0f, 6.0f, 6.0f);
        this.head.setRotationPoint(-1.0f, 16.5f, -3.0f);
        this.rightEar = new ModelRenderer(this, 8, 1);
        this.rightEar.addBox(-3.0f, -4.0f, -4.0f, 2.0f, 2.0f, 1.0f);
        this.leftEar = new ModelRenderer(this, 15, 1);
        this.leftEar.addBox(3.0f, -4.0f, -4.0f, 2.0f, 2.0f, 1.0f);
        this.snout = new ModelRenderer(this, 6, 18);
        this.snout.addBox(-1.0f, 2.01f, -8.0f, 4.0f, 2.0f, 3.0f);
        this.head.addChild(this.rightEar);
        this.head.addChild(this.leftEar);
        this.head.addChild(this.snout);
        this.body = new ModelRenderer(this, 24, 15);
        this.body.addBox(-3.0f, 3.999f, -3.5f, 6.0f, 11.0f, 6.0f);
        this.body.setRotationPoint(0.0f, 16.0f, -6.0f);
        float f = 0.001f;
        this.legBackRight = new ModelRenderer(this, 13, 24);
        this.legBackRight.addBox(2.0f, 0.5f, -1.0f, 2.0f, 6.0f, 2.0f, 0.001f);
        this.legBackRight.setRotationPoint(-5.0f, 17.5f, 7.0f);
        this.legBackLeft = new ModelRenderer(this, 4, 24);
        this.legBackLeft.addBox(2.0f, 0.5f, -1.0f, 2.0f, 6.0f, 2.0f, 0.001f);
        this.legBackLeft.setRotationPoint(-1.0f, 17.5f, 7.0f);
        this.legFrontRight = new ModelRenderer(this, 13, 24);
        this.legFrontRight.addBox(2.0f, 0.5f, -1.0f, 2.0f, 6.0f, 2.0f, 0.001f);
        this.legFrontRight.setRotationPoint(-5.0f, 17.5f, 0.0f);
        this.legFrontLeft = new ModelRenderer(this, 4, 24);
        this.legFrontLeft.addBox(2.0f, 0.5f, -1.0f, 2.0f, 6.0f, 2.0f, 0.001f);
        this.legFrontLeft.setRotationPoint(-1.0f, 17.5f, 0.0f);
        this.tail = new ModelRenderer(this, 30, 0);
        this.tail.addBox(2.0f, 0.0f, -1.0f, 4.0f, 9.0f, 5.0f);
        this.tail.setRotationPoint(-4.0f, 15.0f, -1.0f);
        this.body.addChild(this.tail);
    }

    @Override
    public void setLivingAnimations(T t, float f, float f2, float f3) {
        this.body.rotateAngleX = 1.5707964f;
        this.tail.rotateAngleX = -0.05235988f;
        this.legBackRight.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f2;
        this.legBackLeft.rotateAngleX = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * f2;
        this.legFrontRight.rotateAngleX = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * f2;
        this.legFrontLeft.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f2;
        this.head.setRotationPoint(-1.0f, 16.5f, -3.0f);
        this.head.rotateAngleY = 0.0f;
        this.head.rotateAngleZ = ((FoxEntity)t).func_213475_v(f3);
        this.legBackRight.showModel = true;
        this.legBackLeft.showModel = true;
        this.legFrontRight.showModel = true;
        this.legFrontLeft.showModel = true;
        this.body.setRotationPoint(0.0f, 16.0f, -6.0f);
        this.body.rotateAngleZ = 0.0f;
        this.legBackRight.setRotationPoint(-5.0f, 17.5f, 7.0f);
        this.legBackLeft.setRotationPoint(-1.0f, 17.5f, 7.0f);
        if (((FoxEntity)t).isCrouching()) {
            this.body.rotateAngleX = 1.6755161f;
            float f4 = ((FoxEntity)t).func_213503_w(f3);
            this.body.setRotationPoint(0.0f, 16.0f + ((FoxEntity)t).func_213503_w(f3), -6.0f);
            this.head.setRotationPoint(-1.0f, 16.5f + f4, -3.0f);
            this.head.rotateAngleY = 0.0f;
        } else if (((FoxEntity)t).isSleeping()) {
            this.body.rotateAngleZ = -1.5707964f;
            this.body.setRotationPoint(0.0f, 21.0f, -6.0f);
            this.tail.rotateAngleX = -2.6179938f;
            if (this.isChild) {
                this.tail.rotateAngleX = -2.1816616f;
                this.body.setRotationPoint(0.0f, 21.0f, -2.0f);
            }
            this.head.setRotationPoint(1.0f, 19.49f, -3.0f);
            this.head.rotateAngleX = 0.0f;
            this.head.rotateAngleY = -2.0943952f;
            this.head.rotateAngleZ = 0.0f;
            this.legBackRight.showModel = false;
            this.legBackLeft.showModel = false;
            this.legFrontRight.showModel = false;
            this.legFrontLeft.showModel = false;
        } else if (((FoxEntity)t).isSitting()) {
            this.body.rotateAngleX = 0.5235988f;
            this.body.setRotationPoint(0.0f, 9.0f, -3.0f);
            this.tail.rotateAngleX = 0.7853982f;
            this.tail.setRotationPoint(-4.0f, 15.0f, -2.0f);
            this.head.setRotationPoint(-1.0f, 10.0f, -0.25f);
            this.head.rotateAngleX = 0.0f;
            this.head.rotateAngleY = 0.0f;
            if (this.isChild) {
                this.head.setRotationPoint(-1.0f, 13.0f, -3.75f);
            }
            this.legBackRight.rotateAngleX = -1.3089969f;
            this.legBackRight.setRotationPoint(-5.0f, 21.5f, 6.75f);
            this.legBackLeft.rotateAngleX = -1.3089969f;
            this.legBackLeft.setRotationPoint(-1.0f, 21.5f, 6.75f);
            this.legFrontRight.rotateAngleX = -0.2617994f;
            this.legFrontLeft.rotateAngleX = -0.2617994f;
        }
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(this.body, this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        float f6;
        if (!(((FoxEntity)t).isSleeping() || ((FoxEntity)t).isStuck() || ((FoxEntity)t).isCrouching())) {
            this.head.rotateAngleX = f5 * ((float)Math.PI / 180);
            this.head.rotateAngleY = f4 * ((float)Math.PI / 180);
        }
        if (((FoxEntity)t).isSleeping()) {
            this.head.rotateAngleX = 0.0f;
            this.head.rotateAngleY = -2.0943952f;
            this.head.rotateAngleZ = MathHelper.cos(f3 * 0.027f) / 22.0f;
        }
        if (((FoxEntity)t).isCrouching()) {
            this.body.rotateAngleY = f6 = MathHelper.cos(f3) * 0.01f;
            this.legBackRight.rotateAngleZ = f6;
            this.legBackLeft.rotateAngleZ = f6;
            this.legFrontRight.rotateAngleZ = f6 / 2.0f;
            this.legFrontLeft.rotateAngleZ = f6 / 2.0f;
        }
        if (((FoxEntity)t).isStuck()) {
            f6 = 0.1f;
            this.field_217125_n += 0.67f;
            this.legBackRight.rotateAngleX = MathHelper.cos(this.field_217125_n * 0.4662f) * 0.1f;
            this.legBackLeft.rotateAngleX = MathHelper.cos(this.field_217125_n * 0.4662f + (float)Math.PI) * 0.1f;
            this.legFrontRight.rotateAngleX = MathHelper.cos(this.field_217125_n * 0.4662f + (float)Math.PI) * 0.1f;
            this.legFrontLeft.rotateAngleX = MathHelper.cos(this.field_217125_n * 0.4662f) * 0.1f;
        }
    }

    @Override
    public void setLivingAnimations(Entity entity2, float f, float f2, float f3) {
        this.setLivingAnimations((T)((FoxEntity)entity2), f, f2, f3);
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((FoxEntity)entity2), f, f2, f3, f4, f5);
    }
}

