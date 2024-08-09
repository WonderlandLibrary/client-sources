/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class DrownedModel<T extends ZombieEntity>
extends ZombieModel<T> {
    public DrownedModel(float f, float f2, int n, int n2) {
        super(f, f2, n, n2);
        this.bipedRightArm = new ModelRenderer(this, 32, 48);
        this.bipedRightArm.addBox(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
        this.bipedRightArm.setRotationPoint(-5.0f, 2.0f + f2, 0.0f);
        this.bipedRightLeg = new ModelRenderer(this, 16, 48);
        this.bipedRightLeg.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
        this.bipedRightLeg.setRotationPoint(-1.9f, 12.0f + f2, 0.0f);
    }

    public DrownedModel(float f, boolean bl) {
        super(f, 0.0f, 64, bl ? 32 : 64);
    }

    @Override
    public void setLivingAnimations(T t, float f, float f2, float f3) {
        this.rightArmPose = BipedModel.ArmPose.EMPTY;
        this.leftArmPose = BipedModel.ArmPose.EMPTY;
        ItemStack itemStack = ((LivingEntity)t).getHeldItem(Hand.MAIN_HAND);
        if (itemStack.getItem() == Items.TRIDENT && ((MobEntity)t).isAggressive()) {
            if (((MobEntity)t).getPrimaryHand() == HandSide.RIGHT) {
                this.rightArmPose = BipedModel.ArmPose.THROW_SPEAR;
            } else {
                this.leftArmPose = BipedModel.ArmPose.THROW_SPEAR;
            }
        }
        super.setLivingAnimations(t, f, f2, f3);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        super.setRotationAngles(t, f, f2, f3, f4, f5);
        if (this.leftArmPose == BipedModel.ArmPose.THROW_SPEAR) {
            this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5f - (float)Math.PI;
            this.bipedLeftArm.rotateAngleY = 0.0f;
        }
        if (this.rightArmPose == BipedModel.ArmPose.THROW_SPEAR) {
            this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5f - (float)Math.PI;
            this.bipedRightArm.rotateAngleY = 0.0f;
        }
        if (this.swimAnimation > 0.0f) {
            this.bipedRightArm.rotateAngleX = this.rotLerpRad(this.swimAnimation, this.bipedRightArm.rotateAngleX, -2.5132742f) + this.swimAnimation * 0.35f * MathHelper.sin(0.1f * f3);
            this.bipedLeftArm.rotateAngleX = this.rotLerpRad(this.swimAnimation, this.bipedLeftArm.rotateAngleX, -2.5132742f) - this.swimAnimation * 0.35f * MathHelper.sin(0.1f * f3);
            this.bipedRightArm.rotateAngleZ = this.rotLerpRad(this.swimAnimation, this.bipedRightArm.rotateAngleZ, -0.15f);
            this.bipedLeftArm.rotateAngleZ = this.rotLerpRad(this.swimAnimation, this.bipedLeftArm.rotateAngleZ, 0.15f);
            this.bipedLeftLeg.rotateAngleX -= this.swimAnimation * 0.55f * MathHelper.sin(0.1f * f3);
            this.bipedRightLeg.rotateAngleX += this.swimAnimation * 0.55f * MathHelper.sin(0.1f * f3);
            this.bipedHead.rotateAngleX = 0.0f;
        }
    }

    @Override
    public void setRotationAngles(MonsterEntity monsterEntity, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((ZombieEntity)monsterEntity), f, f2, f3, f4, f5);
    }

    @Override
    public void setRotationAngles(LivingEntity livingEntity, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((ZombieEntity)livingEntity), f, f2, f3, f4, f5);
    }

    @Override
    public void setLivingAnimations(LivingEntity livingEntity, float f, float f2, float f3) {
        this.setLivingAnimations((T)((ZombieEntity)livingEntity), f, f2, f3);
    }

    @Override
    public void setLivingAnimations(Entity entity2, float f, float f2, float f3) {
        this.setLivingAnimations((T)((ZombieEntity)entity2), f, f2, f3);
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((ZombieEntity)entity2), f, f2, f3, f4, f5);
    }
}

