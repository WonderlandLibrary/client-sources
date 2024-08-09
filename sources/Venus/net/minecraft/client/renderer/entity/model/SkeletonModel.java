/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class SkeletonModel<T extends MobEntity>
extends BipedModel<T> {
    public SkeletonModel() {
        this(0.0f, false);
    }

    public SkeletonModel(float f, boolean bl) {
        super(f);
        if (!bl) {
            this.bipedRightArm = new ModelRenderer(this, 40, 16);
            this.bipedRightArm.addBox(-1.0f, -2.0f, -1.0f, 2.0f, 12.0f, 2.0f, f);
            this.bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
            this.bipedLeftArm = new ModelRenderer(this, 40, 16);
            this.bipedLeftArm.mirror = true;
            this.bipedLeftArm.addBox(-1.0f, -2.0f, -1.0f, 2.0f, 12.0f, 2.0f, f);
            this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
            this.bipedRightLeg = new ModelRenderer(this, 0, 16);
            this.bipedRightLeg.addBox(-1.0f, 0.0f, -1.0f, 2.0f, 12.0f, 2.0f, f);
            this.bipedRightLeg.setRotationPoint(-2.0f, 12.0f, 0.0f);
            this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
            this.bipedLeftLeg.mirror = true;
            this.bipedLeftLeg.addBox(-1.0f, 0.0f, -1.0f, 2.0f, 12.0f, 2.0f, f);
            this.bipedLeftLeg.setRotationPoint(2.0f, 12.0f, 0.0f);
        }
    }

    @Override
    public void setLivingAnimations(T t, float f, float f2, float f3) {
        this.rightArmPose = BipedModel.ArmPose.EMPTY;
        this.leftArmPose = BipedModel.ArmPose.EMPTY;
        ItemStack itemStack = ((LivingEntity)t).getHeldItem(Hand.MAIN_HAND);
        if (itemStack.getItem() == Items.BOW && ((MobEntity)t).isAggressive()) {
            if (((MobEntity)t).getPrimaryHand() == HandSide.RIGHT) {
                this.rightArmPose = BipedModel.ArmPose.BOW_AND_ARROW;
            } else {
                this.leftArmPose = BipedModel.ArmPose.BOW_AND_ARROW;
            }
        }
        super.setLivingAnimations(t, f, f2, f3);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        super.setRotationAngles(t, f, f2, f3, f4, f5);
        ItemStack itemStack = ((LivingEntity)t).getHeldItemMainhand();
        if (((MobEntity)t).isAggressive() && (itemStack.isEmpty() || itemStack.getItem() != Items.BOW)) {
            float f6 = MathHelper.sin(this.swingProgress * (float)Math.PI);
            float f7 = MathHelper.sin((1.0f - (1.0f - this.swingProgress) * (1.0f - this.swingProgress)) * (float)Math.PI);
            this.bipedRightArm.rotateAngleZ = 0.0f;
            this.bipedLeftArm.rotateAngleZ = 0.0f;
            this.bipedRightArm.rotateAngleY = -(0.1f - f6 * 0.6f);
            this.bipedLeftArm.rotateAngleY = 0.1f - f6 * 0.6f;
            this.bipedRightArm.rotateAngleX = -1.5707964f;
            this.bipedLeftArm.rotateAngleX = -1.5707964f;
            this.bipedRightArm.rotateAngleX -= f6 * 1.2f - f7 * 0.4f;
            this.bipedLeftArm.rotateAngleX -= f6 * 1.2f - f7 * 0.4f;
            ModelHelper.func_239101_a_(this.bipedRightArm, this.bipedLeftArm, f3);
        }
    }

    @Override
    public void translateHand(HandSide handSide, MatrixStack matrixStack) {
        float f = handSide == HandSide.RIGHT ? 1.0f : -1.0f;
        ModelRenderer modelRenderer = this.getArmForSide(handSide);
        modelRenderer.rotationPointX += f;
        modelRenderer.translateRotate(matrixStack);
        modelRenderer.rotationPointX -= f;
    }

    @Override
    public void setRotationAngles(LivingEntity livingEntity, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((MobEntity)livingEntity), f, f2, f3, f4, f5);
    }

    @Override
    public void setLivingAnimations(LivingEntity livingEntity, float f, float f2, float f3) {
        this.setLivingAnimations((T)((MobEntity)livingEntity), f, f2, f3);
    }

    @Override
    public void setLivingAnimations(Entity entity2, float f, float f2, float f3) {
        this.setLivingAnimations((T)((MobEntity)entity2), f, f2, f3);
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((MobEntity)entity2), f, f2, f3, f4, f5);
    }
}

