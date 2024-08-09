/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class IllagerModel<T extends AbstractIllagerEntity>
extends SegmentedModel<T>
implements IHasArm,
IHasHead {
    private final ModelRenderer head;
    private final ModelRenderer hat;
    private final ModelRenderer body;
    private final ModelRenderer arms;
    private final ModelRenderer field_217143_g;
    private final ModelRenderer field_217144_h;
    private final ModelRenderer rightArm;
    private final ModelRenderer leftArm;

    public IllagerModel(float f, float f2, int n, int n2) {
        this.head = new ModelRenderer(this).setTextureSize(n, n2);
        this.head.setRotationPoint(0.0f, 0.0f + f2, 0.0f);
        this.head.setTextureOffset(0, 0).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f, f);
        this.hat = new ModelRenderer(this, 32, 0).setTextureSize(n, n2);
        this.hat.addBox(-4.0f, -10.0f, -4.0f, 8.0f, 12.0f, 8.0f, f + 0.45f);
        this.head.addChild(this.hat);
        this.hat.showModel = false;
        ModelRenderer modelRenderer = new ModelRenderer(this).setTextureSize(n, n2);
        modelRenderer.setRotationPoint(0.0f, f2 - 2.0f, 0.0f);
        modelRenderer.setTextureOffset(24, 0).addBox(-1.0f, -1.0f, -6.0f, 2.0f, 4.0f, 2.0f, f);
        this.head.addChild(modelRenderer);
        this.body = new ModelRenderer(this).setTextureSize(n, n2);
        this.body.setRotationPoint(0.0f, 0.0f + f2, 0.0f);
        this.body.setTextureOffset(16, 20).addBox(-4.0f, 0.0f, -3.0f, 8.0f, 12.0f, 6.0f, f);
        this.body.setTextureOffset(0, 38).addBox(-4.0f, 0.0f, -3.0f, 8.0f, 18.0f, 6.0f, f + 0.5f);
        this.arms = new ModelRenderer(this).setTextureSize(n, n2);
        this.arms.setRotationPoint(0.0f, 0.0f + f2 + 2.0f, 0.0f);
        this.arms.setTextureOffset(44, 22).addBox(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, f);
        ModelRenderer modelRenderer2 = new ModelRenderer(this, 44, 22).setTextureSize(n, n2);
        modelRenderer2.mirror = true;
        modelRenderer2.addBox(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, f);
        this.arms.addChild(modelRenderer2);
        this.arms.setTextureOffset(40, 38).addBox(-4.0f, 2.0f, -2.0f, 8.0f, 4.0f, 4.0f, f);
        this.field_217143_g = new ModelRenderer(this, 0, 22).setTextureSize(n, n2);
        this.field_217143_g.setRotationPoint(-2.0f, 12.0f + f2, 0.0f);
        this.field_217143_g.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
        this.field_217144_h = new ModelRenderer(this, 0, 22).setTextureSize(n, n2);
        this.field_217144_h.mirror = true;
        this.field_217144_h.setRotationPoint(2.0f, 12.0f + f2, 0.0f);
        this.field_217144_h.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
        this.rightArm = new ModelRenderer(this, 40, 46).setTextureSize(n, n2);
        this.rightArm.addBox(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
        this.rightArm.setRotationPoint(-5.0f, 2.0f + f2, 0.0f);
        this.leftArm = new ModelRenderer(this, 40, 46).setTextureSize(n, n2);
        this.leftArm.mirror = true;
        this.leftArm.addBox(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
        this.leftArm.setRotationPoint(5.0f, 2.0f + f2, 0.0f);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.head, this.body, this.field_217143_g, this.field_217144_h, this.arms, this.rightArm, this.leftArm);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        boolean bl;
        this.head.rotateAngleY = f4 * ((float)Math.PI / 180);
        this.head.rotateAngleX = f5 * ((float)Math.PI / 180);
        this.arms.rotationPointY = 3.0f;
        this.arms.rotationPointZ = -1.0f;
        this.arms.rotateAngleX = -0.75f;
        if (this.isSitting) {
            this.rightArm.rotateAngleX = -0.62831855f;
            this.rightArm.rotateAngleY = 0.0f;
            this.rightArm.rotateAngleZ = 0.0f;
            this.leftArm.rotateAngleX = -0.62831855f;
            this.leftArm.rotateAngleY = 0.0f;
            this.leftArm.rotateAngleZ = 0.0f;
            this.field_217143_g.rotateAngleX = -1.4137167f;
            this.field_217143_g.rotateAngleY = 0.31415927f;
            this.field_217143_g.rotateAngleZ = 0.07853982f;
            this.field_217144_h.rotateAngleX = -1.4137167f;
            this.field_217144_h.rotateAngleY = -0.31415927f;
            this.field_217144_h.rotateAngleZ = -0.07853982f;
        } else {
            this.rightArm.rotateAngleX = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 2.0f * f2 * 0.5f;
            this.rightArm.rotateAngleY = 0.0f;
            this.rightArm.rotateAngleZ = 0.0f;
            this.leftArm.rotateAngleX = MathHelper.cos(f * 0.6662f) * 2.0f * f2 * 0.5f;
            this.leftArm.rotateAngleY = 0.0f;
            this.leftArm.rotateAngleZ = 0.0f;
            this.field_217143_g.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f2 * 0.5f;
            this.field_217143_g.rotateAngleY = 0.0f;
            this.field_217143_g.rotateAngleZ = 0.0f;
            this.field_217144_h.rotateAngleX = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * f2 * 0.5f;
            this.field_217144_h.rotateAngleY = 0.0f;
            this.field_217144_h.rotateAngleZ = 0.0f;
        }
        AbstractIllagerEntity.ArmPose armPose = ((AbstractIllagerEntity)t).getArmPose();
        if (armPose == AbstractIllagerEntity.ArmPose.ATTACKING) {
            if (((LivingEntity)t).getHeldItemMainhand().isEmpty()) {
                ModelHelper.func_239105_a_(this.leftArm, this.rightArm, true, this.swingProgress, f3);
            } else {
                ModelHelper.func_239103_a_(this.rightArm, this.leftArm, t, this.swingProgress, f3);
            }
        } else if (armPose == AbstractIllagerEntity.ArmPose.SPELLCASTING) {
            this.rightArm.rotationPointZ = 0.0f;
            this.rightArm.rotationPointX = -5.0f;
            this.leftArm.rotationPointZ = 0.0f;
            this.leftArm.rotationPointX = 5.0f;
            this.rightArm.rotateAngleX = MathHelper.cos(f3 * 0.6662f) * 0.25f;
            this.leftArm.rotateAngleX = MathHelper.cos(f3 * 0.6662f) * 0.25f;
            this.rightArm.rotateAngleZ = 2.3561945f;
            this.leftArm.rotateAngleZ = -2.3561945f;
            this.rightArm.rotateAngleY = 0.0f;
            this.leftArm.rotateAngleY = 0.0f;
        } else if (armPose == AbstractIllagerEntity.ArmPose.BOW_AND_ARROW) {
            this.rightArm.rotateAngleY = -0.1f + this.head.rotateAngleY;
            this.rightArm.rotateAngleX = -1.5707964f + this.head.rotateAngleX;
            this.leftArm.rotateAngleX = -0.9424779f + this.head.rotateAngleX;
            this.leftArm.rotateAngleY = this.head.rotateAngleY - 0.4f;
            this.leftArm.rotateAngleZ = 1.5707964f;
        } else if (armPose == AbstractIllagerEntity.ArmPose.CROSSBOW_HOLD) {
            ModelHelper.func_239104_a_(this.rightArm, this.leftArm, this.head, true);
        } else if (armPose == AbstractIllagerEntity.ArmPose.CROSSBOW_CHARGE) {
            ModelHelper.func_239102_a_(this.rightArm, this.leftArm, t, true);
        } else if (armPose == AbstractIllagerEntity.ArmPose.CELEBRATING) {
            this.rightArm.rotationPointZ = 0.0f;
            this.rightArm.rotationPointX = -5.0f;
            this.rightArm.rotateAngleX = MathHelper.cos(f3 * 0.6662f) * 0.05f;
            this.rightArm.rotateAngleZ = 2.670354f;
            this.rightArm.rotateAngleY = 0.0f;
            this.leftArm.rotationPointZ = 0.0f;
            this.leftArm.rotationPointX = 5.0f;
            this.leftArm.rotateAngleX = MathHelper.cos(f3 * 0.6662f) * 0.05f;
            this.leftArm.rotateAngleZ = -2.3561945f;
            this.leftArm.rotateAngleY = 0.0f;
        }
        this.arms.showModel = bl = armPose == AbstractIllagerEntity.ArmPose.CROSSED;
        this.leftArm.showModel = !bl;
        this.rightArm.showModel = !bl;
    }

    private ModelRenderer getArm(HandSide handSide) {
        return handSide == HandSide.LEFT ? this.leftArm : this.rightArm;
    }

    public ModelRenderer func_205062_a() {
        return this.hat;
    }

    @Override
    public ModelRenderer getModelHead() {
        return this.head;
    }

    @Override
    public void translateHand(HandSide handSide, MatrixStack matrixStack) {
        this.getArm(handSide).translateRotate(matrixStack);
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((AbstractIllagerEntity)entity2), f, f2, f3, f4, f5);
    }
}

