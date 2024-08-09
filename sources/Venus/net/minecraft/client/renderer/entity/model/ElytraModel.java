/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;

public class ElytraModel<T extends LivingEntity>
extends AgeableModel<T> {
    private final ModelRenderer rightWing;
    private final ModelRenderer leftWing = new ModelRenderer(this, 22, 0);

    public ElytraModel() {
        this.leftWing.addBox(-10.0f, 0.0f, 0.0f, 10.0f, 20.0f, 2.0f, 1.0f);
        this.rightWing = new ModelRenderer(this, 22, 0);
        this.rightWing.mirror = true;
        this.rightWing.addBox(0.0f, 0.0f, 0.0f, 10.0f, 20.0f, 2.0f, 1.0f);
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(this.leftWing, this.rightWing);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        float f6 = 0.2617994f;
        float f7 = -0.2617994f;
        float f8 = 0.0f;
        float f9 = 0.0f;
        if (((LivingEntity)t).isElytraFlying()) {
            float f10 = 1.0f;
            Vector3d vector3d = ((Entity)t).getMotion();
            if (vector3d.y < 0.0) {
                Vector3d vector3d2 = vector3d.normalize();
                f10 = 1.0f - (float)Math.pow(-vector3d2.y, 1.5);
            }
            f6 = f10 * 0.34906584f + (1.0f - f10) * f6;
            f7 = f10 * -1.5707964f + (1.0f - f10) * f7;
        } else if (((Entity)t).isCrouching()) {
            f6 = 0.69813174f;
            f7 = -0.7853982f;
            f8 = 3.0f;
            f9 = 0.08726646f;
        }
        this.leftWing.rotationPointX = 5.0f;
        this.leftWing.rotationPointY = f8;
        if (t instanceof AbstractClientPlayerEntity) {
            AbstractClientPlayerEntity abstractClientPlayerEntity = (AbstractClientPlayerEntity)t;
            abstractClientPlayerEntity.rotateElytraX = (float)((double)abstractClientPlayerEntity.rotateElytraX + (double)(f6 - abstractClientPlayerEntity.rotateElytraX) * 0.1);
            abstractClientPlayerEntity.rotateElytraY = (float)((double)abstractClientPlayerEntity.rotateElytraY + (double)(f9 - abstractClientPlayerEntity.rotateElytraY) * 0.1);
            abstractClientPlayerEntity.rotateElytraZ = (float)((double)abstractClientPlayerEntity.rotateElytraZ + (double)(f7 - abstractClientPlayerEntity.rotateElytraZ) * 0.1);
            this.leftWing.rotateAngleX = abstractClientPlayerEntity.rotateElytraX;
            this.leftWing.rotateAngleY = abstractClientPlayerEntity.rotateElytraY;
            this.leftWing.rotateAngleZ = abstractClientPlayerEntity.rotateElytraZ;
        } else {
            this.leftWing.rotateAngleX = f6;
            this.leftWing.rotateAngleZ = f7;
            this.leftWing.rotateAngleY = f9;
        }
        this.rightWing.rotationPointX = -this.leftWing.rotationPointX;
        this.rightWing.rotateAngleY = -this.leftWing.rotateAngleY;
        this.rightWing.rotationPointY = this.leftWing.rotationPointY;
        this.rightWing.rotateAngleX = this.leftWing.rotateAngleX;
        this.rightWing.rotateAngleZ = -this.leftWing.rotateAngleZ;
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((LivingEntity)entity2), f, f2, f3, f4, f5);
    }
}

