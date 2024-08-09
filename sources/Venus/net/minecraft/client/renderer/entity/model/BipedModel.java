/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class BipedModel<T extends LivingEntity>
extends AgeableModel<T>
implements IHasArm,
IHasHead {
    public ModelRenderer bipedHead;
    public ModelRenderer bipedHeadwear;
    public ModelRenderer bipedBody;
    public ModelRenderer bipedRightArm;
    public ModelRenderer bipedLeftArm;
    public ModelRenderer bipedRightLeg;
    public ModelRenderer bipedLeftLeg;
    public ArmPose leftArmPose = ArmPose.EMPTY;
    public ArmPose rightArmPose = ArmPose.EMPTY;
    public boolean isSneak;
    public float swimAnimation;

    public BipedModel(float f) {
        this(RenderType::getEntityCutoutNoCull, f, 0.0f, 64, 32);
    }

    protected BipedModel(float f, float f2, int n, int n2) {
        this(RenderType::getEntityCutoutNoCull, f, f2, n, n2);
    }

    public BipedModel(Function<ResourceLocation, RenderType> function, float f, float f2, int n, int n2) {
        super(function, true, 16.0f, 0.0f, 2.0f, 2.0f, 24.0f);
        this.textureWidth = n;
        this.textureHeight = n2;
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, f);
        this.bipedHead.setRotationPoint(0.0f, 0.0f + f2, 0.0f);
        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        this.bipedHeadwear.addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, f + 0.5f);
        this.bipedHeadwear.setRotationPoint(0.0f, 0.0f + f2, 0.0f);
        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, f);
        this.bipedBody.setRotationPoint(0.0f, 0.0f + f2, 0.0f);
        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.addBox(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
        this.bipedRightArm.setRotationPoint(-5.0f, 2.0f + f2, 0.0f);
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
        this.bipedLeftArm.setRotationPoint(5.0f, 2.0f + f2, 0.0f);
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
        this.bipedRightLeg.setRotationPoint(-1.9f, 12.0f + f2, 0.0f);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
        this.bipedLeftLeg.setRotationPoint(1.9f, 12.0f + f2, 0.0f);
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(this.bipedHead);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(this.bipedBody, this.bipedRightArm, this.bipedLeftArm, this.bipedRightLeg, this.bipedLeftLeg, this.bipedHeadwear);
    }

    @Override
    public void setLivingAnimations(T t, float f, float f2, float f3) {
        this.swimAnimation = ((LivingEntity)t).getSwimAnimation(f3);
        super.setLivingAnimations(t, f, f2, f3);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        boolean bl;
        boolean bl2 = ((LivingEntity)t).getTicksElytraFlying() > 4;
        boolean bl3 = ((LivingEntity)t).isActualySwimming();
        this.bipedHead.rotateAngleY = f4 * ((float)Math.PI / 180);
        this.bipedHead.rotateAngleX = bl2 ? -0.7853982f : (this.swimAnimation > 0.0f ? (bl3 ? this.rotLerpRad(this.swimAnimation, this.bipedHead.rotateAngleX, -0.7853982f) : this.rotLerpRad(this.swimAnimation, this.bipedHead.rotateAngleX, f5 * ((float)Math.PI / 180))) : f5 * ((float)Math.PI / 180));
        this.bipedBody.rotateAngleY = 0.0f;
        this.bipedRightArm.rotationPointZ = 0.0f;
        this.bipedRightArm.rotationPointX = -5.0f;
        this.bipedLeftArm.rotationPointZ = 0.0f;
        this.bipedLeftArm.rotationPointX = 5.0f;
        float f6 = 1.0f;
        if (bl2) {
            f6 = (float)((Entity)t).getMotion().lengthSquared();
            f6 /= 0.2f;
            f6 = f6 * f6 * f6;
        }
        if (f6 < 1.0f) {
            f6 = 1.0f;
        }
        this.bipedRightArm.rotateAngleX = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 2.0f * f2 * 0.5f / f6;
        this.bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662f) * 2.0f * f2 * 0.5f / f6;
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;
        this.bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f2 / f6;
        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * f2 / f6;
        this.bipedRightLeg.rotateAngleY = 0.0f;
        this.bipedLeftLeg.rotateAngleY = 0.0f;
        this.bipedRightLeg.rotateAngleZ = 0.0f;
        this.bipedLeftLeg.rotateAngleZ = 0.0f;
        if (this.isSitting) {
            this.bipedRightArm.rotateAngleX += -0.62831855f;
            this.bipedLeftArm.rotateAngleX += -0.62831855f;
            this.bipedRightLeg.rotateAngleX = -1.4137167f;
            this.bipedRightLeg.rotateAngleY = 0.31415927f;
            this.bipedRightLeg.rotateAngleZ = 0.07853982f;
            this.bipedLeftLeg.rotateAngleX = -1.4137167f;
            this.bipedLeftLeg.rotateAngleY = -0.31415927f;
            this.bipedLeftLeg.rotateAngleZ = -0.07853982f;
        }
        this.bipedRightArm.rotateAngleY = 0.0f;
        this.bipedLeftArm.rotateAngleY = 0.0f;
        boolean bl4 = ((LivingEntity)t).getPrimaryHand() == HandSide.RIGHT;
        boolean bl5 = bl = bl4 ? this.leftArmPose.func_241657_a_() : this.rightArmPose.func_241657_a_();
        if (bl4 != bl) {
            this.func_241655_c_(t);
            this.func_241654_b_(t);
        } else {
            this.func_241654_b_(t);
            this.func_241655_c_(t);
        }
        this.func_230486_a_(t, f3);
        if (this.isSneak) {
            this.bipedBody.rotateAngleX = 0.5f;
            this.bipedRightArm.rotateAngleX += 0.4f;
            this.bipedLeftArm.rotateAngleX += 0.4f;
            this.bipedRightLeg.rotationPointZ = 4.0f;
            this.bipedLeftLeg.rotationPointZ = 4.0f;
            this.bipedRightLeg.rotationPointY = 12.2f;
            this.bipedLeftLeg.rotationPointY = 12.2f;
            this.bipedHead.rotationPointY = 4.2f;
            this.bipedBody.rotationPointY = 3.2f;
            this.bipedLeftArm.rotationPointY = 5.2f;
            this.bipedRightArm.rotationPointY = 5.2f;
        } else {
            this.bipedBody.rotateAngleX = 0.0f;
            this.bipedRightLeg.rotationPointZ = 0.1f;
            this.bipedLeftLeg.rotationPointZ = 0.1f;
            this.bipedRightLeg.rotationPointY = 12.0f;
            this.bipedLeftLeg.rotationPointY = 12.0f;
            this.bipedHead.rotationPointY = 0.0f;
            this.bipedBody.rotationPointY = 0.0f;
            this.bipedLeftArm.rotationPointY = 2.0f;
            this.bipedRightArm.rotationPointY = 2.0f;
        }
        ModelHelper.func_239101_a_(this.bipedRightArm, this.bipedLeftArm, f3);
        if (this.swimAnimation > 0.0f) {
            float f7;
            float f8;
            float f9 = f % 26.0f;
            HandSide handSide = this.getMainHand(t);
            float f10 = handSide == HandSide.RIGHT && this.swingProgress > 0.0f ? 0.0f : this.swimAnimation;
            float f11 = f8 = handSide == HandSide.LEFT && this.swingProgress > 0.0f ? 0.0f : this.swimAnimation;
            if (f9 < 14.0f) {
                this.bipedLeftArm.rotateAngleX = this.rotLerpRad(f8, this.bipedLeftArm.rotateAngleX, 0.0f);
                this.bipedRightArm.rotateAngleX = MathHelper.lerp(f10, this.bipedRightArm.rotateAngleX, 0.0f);
                this.bipedLeftArm.rotateAngleY = this.rotLerpRad(f8, this.bipedLeftArm.rotateAngleY, (float)Math.PI);
                this.bipedRightArm.rotateAngleY = MathHelper.lerp(f10, this.bipedRightArm.rotateAngleY, (float)Math.PI);
                this.bipedLeftArm.rotateAngleZ = this.rotLerpRad(f8, this.bipedLeftArm.rotateAngleZ, (float)Math.PI + 1.8707964f * this.getArmAngleSq(f9) / this.getArmAngleSq(14.0f));
                this.bipedRightArm.rotateAngleZ = MathHelper.lerp(f10, this.bipedRightArm.rotateAngleZ, (float)Math.PI - 1.8707964f * this.getArmAngleSq(f9) / this.getArmAngleSq(14.0f));
            } else if (f9 >= 14.0f && f9 < 22.0f) {
                f7 = (f9 - 14.0f) / 8.0f;
                this.bipedLeftArm.rotateAngleX = this.rotLerpRad(f8, this.bipedLeftArm.rotateAngleX, 1.5707964f * f7);
                this.bipedRightArm.rotateAngleX = MathHelper.lerp(f10, this.bipedRightArm.rotateAngleX, 1.5707964f * f7);
                this.bipedLeftArm.rotateAngleY = this.rotLerpRad(f8, this.bipedLeftArm.rotateAngleY, (float)Math.PI);
                this.bipedRightArm.rotateAngleY = MathHelper.lerp(f10, this.bipedRightArm.rotateAngleY, (float)Math.PI);
                this.bipedLeftArm.rotateAngleZ = this.rotLerpRad(f8, this.bipedLeftArm.rotateAngleZ, 5.012389f - 1.8707964f * f7);
                this.bipedRightArm.rotateAngleZ = MathHelper.lerp(f10, this.bipedRightArm.rotateAngleZ, 1.2707963f + 1.8707964f * f7);
            } else if (f9 >= 22.0f && f9 < 26.0f) {
                f7 = (f9 - 22.0f) / 4.0f;
                this.bipedLeftArm.rotateAngleX = this.rotLerpRad(f8, this.bipedLeftArm.rotateAngleX, 1.5707964f - 1.5707964f * f7);
                this.bipedRightArm.rotateAngleX = MathHelper.lerp(f10, this.bipedRightArm.rotateAngleX, 1.5707964f - 1.5707964f * f7);
                this.bipedLeftArm.rotateAngleY = this.rotLerpRad(f8, this.bipedLeftArm.rotateAngleY, (float)Math.PI);
                this.bipedRightArm.rotateAngleY = MathHelper.lerp(f10, this.bipedRightArm.rotateAngleY, (float)Math.PI);
                this.bipedLeftArm.rotateAngleZ = this.rotLerpRad(f8, this.bipedLeftArm.rotateAngleZ, (float)Math.PI);
                this.bipedRightArm.rotateAngleZ = MathHelper.lerp(f10, this.bipedRightArm.rotateAngleZ, (float)Math.PI);
            }
            f7 = 0.3f;
            float f12 = 0.33333334f;
            this.bipedLeftLeg.rotateAngleX = MathHelper.lerp(this.swimAnimation, this.bipedLeftLeg.rotateAngleX, 0.3f * MathHelper.cos(f * 0.33333334f + (float)Math.PI));
            this.bipedRightLeg.rotateAngleX = MathHelper.lerp(this.swimAnimation, this.bipedRightLeg.rotateAngleX, 0.3f * MathHelper.cos(f * 0.33333334f));
        }
        this.bipedHeadwear.copyModelAngles(this.bipedHead);
    }

    private void func_241654_b_(T t) {
        switch (1.$SwitchMap$net$minecraft$client$renderer$entity$model$BipedModel$ArmPose[this.rightArmPose.ordinal()]) {
            case 1: {
                this.bipedRightArm.rotateAngleY = 0.0f;
                break;
            }
            case 2: {
                this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5f - 0.9424779f;
                this.bipedRightArm.rotateAngleY = -0.5235988f;
                break;
            }
            case 3: {
                this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5f - 0.31415927f;
                this.bipedRightArm.rotateAngleY = 0.0f;
                break;
            }
            case 4: {
                this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5f - (float)Math.PI;
                this.bipedRightArm.rotateAngleY = 0.0f;
                break;
            }
            case 5: {
                this.bipedRightArm.rotateAngleY = -0.1f + this.bipedHead.rotateAngleY;
                this.bipedLeftArm.rotateAngleY = 0.1f + this.bipedHead.rotateAngleY + 0.4f;
                this.bipedRightArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
                this.bipedLeftArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
                break;
            }
            case 6: {
                ModelHelper.func_239102_a_(this.bipedRightArm, this.bipedLeftArm, t, true);
                break;
            }
            case 7: {
                ModelHelper.func_239104_a_(this.bipedRightArm, this.bipedLeftArm, this.bipedHead, true);
            }
        }
    }

    private void func_241655_c_(T t) {
        switch (1.$SwitchMap$net$minecraft$client$renderer$entity$model$BipedModel$ArmPose[this.leftArmPose.ordinal()]) {
            case 1: {
                this.bipedLeftArm.rotateAngleY = 0.0f;
                break;
            }
            case 2: {
                this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5f - 0.9424779f;
                this.bipedLeftArm.rotateAngleY = 0.5235988f;
                break;
            }
            case 3: {
                this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5f - 0.31415927f;
                this.bipedLeftArm.rotateAngleY = 0.0f;
                break;
            }
            case 4: {
                this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5f - (float)Math.PI;
                this.bipedLeftArm.rotateAngleY = 0.0f;
                break;
            }
            case 5: {
                this.bipedRightArm.rotateAngleY = -0.1f + this.bipedHead.rotateAngleY - 0.4f;
                this.bipedLeftArm.rotateAngleY = 0.1f + this.bipedHead.rotateAngleY;
                this.bipedRightArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
                this.bipedLeftArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
                break;
            }
            case 6: {
                ModelHelper.func_239102_a_(this.bipedRightArm, this.bipedLeftArm, t, false);
                break;
            }
            case 7: {
                ModelHelper.func_239104_a_(this.bipedRightArm, this.bipedLeftArm, this.bipedHead, false);
            }
        }
    }

    protected void func_230486_a_(T t, float f) {
        if (!(this.swingProgress <= 0.0f)) {
            HandSide handSide = this.getMainHand(t);
            ModelRenderer modelRenderer = this.getArmForSide(handSide);
            float f2 = this.swingProgress;
            this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f2) * ((float)Math.PI * 2)) * 0.2f;
            if (handSide == HandSide.LEFT) {
                this.bipedBody.rotateAngleY *= -1.0f;
            }
            this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
            f2 = 1.0f - this.swingProgress;
            f2 *= f2;
            f2 *= f2;
            f2 = 1.0f - f2;
            float f3 = MathHelper.sin(f2 * (float)Math.PI);
            float f4 = MathHelper.sin(this.swingProgress * (float)Math.PI) * -(this.bipedHead.rotateAngleX - 0.7f) * 0.75f;
            modelRenderer.rotateAngleX = (float)((double)modelRenderer.rotateAngleX - ((double)f3 * 1.2 + (double)f4));
            modelRenderer.rotateAngleY += this.bipedBody.rotateAngleY * 2.0f;
            modelRenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * (float)Math.PI) * -0.4f;
        }
    }

    protected float rotLerpRad(float f, float f2, float f3) {
        float f4 = (f3 - f2) % ((float)Math.PI * 2);
        if (f4 < (float)(-Math.PI)) {
            f4 += (float)Math.PI * 2;
        }
        if (f4 >= (float)Math.PI) {
            f4 -= (float)Math.PI * 2;
        }
        return f2 + f * f4;
    }

    private float getArmAngleSq(float f) {
        return -65.0f * f + f * f;
    }

    public void setModelAttributes(BipedModel<T> bipedModel) {
        super.copyModelAttributesTo(bipedModel);
        bipedModel.leftArmPose = this.leftArmPose;
        bipedModel.rightArmPose = this.rightArmPose;
        bipedModel.isSneak = this.isSneak;
        bipedModel.bipedHead.copyModelAngles(this.bipedHead);
        bipedModel.bipedHeadwear.copyModelAngles(this.bipedHeadwear);
        bipedModel.bipedBody.copyModelAngles(this.bipedBody);
        bipedModel.bipedRightArm.copyModelAngles(this.bipedRightArm);
        bipedModel.bipedLeftArm.copyModelAngles(this.bipedLeftArm);
        bipedModel.bipedRightLeg.copyModelAngles(this.bipedRightLeg);
        bipedModel.bipedLeftLeg.copyModelAngles(this.bipedLeftLeg);
    }

    public void setVisible(boolean bl) {
        this.bipedHead.showModel = bl;
        this.bipedHeadwear.showModel = bl;
        this.bipedBody.showModel = bl;
        this.bipedRightArm.showModel = bl;
        this.bipedLeftArm.showModel = bl;
        this.bipedRightLeg.showModel = bl;
        this.bipedLeftLeg.showModel = bl;
    }

    @Override
    public void translateHand(HandSide handSide, MatrixStack matrixStack) {
        this.getArmForSide(handSide).translateRotate(matrixStack);
    }

    protected ModelRenderer getArmForSide(HandSide handSide) {
        return handSide == HandSide.LEFT ? this.bipedLeftArm : this.bipedRightArm;
    }

    @Override
    public ModelRenderer getModelHead() {
        return this.bipedHead;
    }

    protected HandSide getMainHand(T t) {
        HandSide handSide = ((LivingEntity)t).getPrimaryHand();
        return ((LivingEntity)t).swingingHand == Hand.MAIN_HAND ? handSide : handSide.opposite();
    }

    @Override
    public void setLivingAnimations(Entity entity2, float f, float f2, float f3) {
        this.setLivingAnimations((T)((LivingEntity)entity2), f, f2, f3);
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((LivingEntity)entity2), f, f2, f3, f4, f5);
    }

    public static enum ArmPose {
        EMPTY(false),
        ITEM(false),
        BLOCK(false),
        BOW_AND_ARROW(true),
        THROW_SPEAR(false),
        CROSSBOW_CHARGE(true),
        CROSSBOW_HOLD(true);

        private final boolean field_241656_h_;

        private ArmPose(boolean bl) {
            this.field_241656_h_ = bl;
        }

        public boolean func_241657_a_() {
            return this.field_241656_h_;
        }
    }
}

