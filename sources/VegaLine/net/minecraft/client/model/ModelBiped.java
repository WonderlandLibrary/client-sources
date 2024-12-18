/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import ru.govno.client.utils.Command.impl.Panic;

public class ModelBiped
extends ModelBase {
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

    public ModelBiped() {
        this(0.0f);
    }

    public ModelBiped(float modelSize) {
        this(modelSize, 0.0f, 64, 32);
    }

    public ModelBiped(float modelSize, float p_i1149_2_, int textureWidthIn, int textureHeightIn) {
        this.textureWidth = textureWidthIn;
        this.textureHeight = textureHeightIn;
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, modelSize);
        this.bipedHead.setRotationPoint(0.0f, 0.0f + p_i1149_2_, 0.0f);
        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        this.bipedHeadwear.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, modelSize + 0.5f);
        this.bipedHeadwear.setRotationPoint(0.0f, 0.0f + p_i1149_2_, 0.0f);
        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, modelSize);
        this.bipedBody.setRotationPoint(0.0f, 0.0f + p_i1149_2_, 0.0f);
        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4, modelSize);
        this.bipedRightArm.setRotationPoint(-5.0f, 2.0f + p_i1149_2_, 0.0f);
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, modelSize);
        this.bipedLeftArm.setRotationPoint(5.0f, 2.0f + p_i1149_2_, 0.0f);
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, modelSize);
        this.bipedRightLeg.setRotationPoint(-1.9f, 12.0f + p_i1149_2_, 0.0f);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, modelSize);
        this.bipedLeftLeg.setRotationPoint(1.9f, 12.0f + p_i1149_2_, 0.0f);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        GlStateManager.pushMatrix();
        if (this.isChild) {
            float f = 2.0f;
            GlStateManager.scale(0.75f, 0.75f, 0.75f);
            GlStateManager.translate(0.0f, 16.0f * scale, 0.0f);
            this.bipedHead.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            GlStateManager.translate(0.0f, 24.0f * scale, 0.0f);
            this.bipedBody.render(scale);
            this.bipedRightArm.render(scale);
            this.bipedLeftArm.render(scale);
            this.bipedRightLeg.render(scale);
            this.bipedLeftLeg.render(scale);
            this.bipedHeadwear.render(scale);
        } else {
            EntityLivingBase base;
            if (entityIn.isSneaking()) {
                GlStateManager.translate(0.0f, 0.2f, 0.0f);
            }
            this.bipedHead.render(scale);
            this.bipedBody.render(scale);
            if (!Panic.stop && entityIn instanceof EntityLivingBase && (base = (EntityLivingBase)entityIn).isEating() && base.getActiveHand() == EnumHand.MAIN_HAND) {
                GlStateManager.rotate(-45.0f, 1.0f, 1.0f, 0.0f);
            }
            this.bipedRightArm.render(scale);
            if (!Panic.stop && entityIn instanceof EntityLivingBase && (base = (EntityLivingBase)entityIn).isEating() && base.getActiveHand() == EnumHand.MAIN_HAND) {
                GlStateManager.rotate(45.0f, 1.0f, 1.0f, 0.0f);
            }
            if (!Panic.stop && entityIn instanceof EntityLivingBase && (base = (EntityLivingBase)entityIn).isEating() && base.getActiveHand() == EnumHand.OFF_HAND) {
                GlStateManager.rotate(-45.0f, 1.0f, -1.0f, 0.0f);
            }
            this.bipedLeftArm.render(scale);
            if (!Panic.stop && entityIn instanceof EntityLivingBase && (base = (EntityLivingBase)entityIn).isEating() && base.getActiveHand() == EnumHand.OFF_HAND) {
                GlStateManager.rotate(45.0f, 1.0f, -1.0f, 0.0f);
            }
            this.bipedRightLeg.render(scale);
            this.bipedLeftLeg.render(scale);
            this.bipedHeadwear.render(scale);
        }
        GlStateManager.popMatrix();
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        block25: {
            block24: {
                flag = entityIn instanceof EntityLivingBase != false && ((EntityLivingBase)entityIn).getTicksElytraFlying() > 4;
                this.bipedHead.rotateAngleY = netHeadYaw * 0.017453292f;
                if (!flag) break block24;
                this.bipedHead.rotateAngleX = -0.7853982f;
                break block25;
            }
            if (!(entityIn instanceof EntityLivingBase)) ** GOTO lbl-1000
            base = (EntityLivingBase)entityIn;
            if (base.isLay) {
                v0 = 60;
            } else lbl-1000:
            // 2 sources

            {
                v0 = 0;
            }
            this.bipedHead.rotateAngleX = (headPitch - (float)v0) * 0.017453292f;
        }
        this.bipedBody.rotateAngleY = 0.0f;
        this.bipedRightArm.rotationPointZ = 0.0f;
        this.bipedRightArm.rotationPointX = -5.0f;
        this.bipedLeftArm.rotationPointZ = 0.0f;
        this.bipedLeftArm.rotationPointX = 5.0f;
        f = 1.0f;
        if (flag) {
            f = (float)(entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
            f /= 0.2f;
            f = f * f * f;
        }
        if (f < 1.0f) {
            f = 1.0f;
        }
        this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + 3.1415927f) * 2.0f * limbSwingAmount * 0.5f / f;
        this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 2.0f * limbSwingAmount * 0.5f / f;
        if (entityIn instanceof EntityLivingBase) {
            base = (EntityLivingBase)entityIn;
            if (base.isLay) {
                this.bipedRightArm.rotateAngleX = (float)((double)this.bipedRightArm.rotateAngleX - Math.toRadians(base.getHeldItemMainhand().getItem() == Items.air ? 170.0 : 320.0));
                this.bipedLeftArm.rotateAngleX = (float)((double)this.bipedLeftArm.rotateAngleX - Math.toRadians(base.getHeldItemOffhand().getItem() == Items.air ? 170.0 : 320.0));
            }
        }
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;
        this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount / f;
        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + 3.1415927f) * 1.4f * limbSwingAmount / f;
        this.bipedRightLeg.rotateAngleY = 0.0f;
        this.bipedLeftLeg.rotateAngleY = 0.0f;
        this.bipedRightLeg.rotateAngleZ = 0.0f;
        this.bipedLeftLeg.rotateAngleZ = 0.0f;
        if (this.isRiding) {
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
        this.bipedRightArm.rotateAngleZ = 0.0f;
        switch (1.$SwitchMap$net$minecraft$client$model$ModelBiped$ArmPose[this.leftArmPose.ordinal()]) {
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
            }
        }
        switch (1.$SwitchMap$net$minecraft$client$model$ModelBiped$ArmPose[this.rightArmPose.ordinal()]) {
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
            }
        }
        if (this.swingProgress > 0.0f) {
            enumhandside = this.getMainHand(entityIn);
            modelrenderer = this.getArmForSide(enumhandside);
            f1 = this.swingProgress;
            this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * 6.2831855f) * 0.2f;
            if (enumhandside == EnumHandSide.LEFT) {
                this.bipedBody.rotateAngleY *= -1.0f;
            }
            this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
            f1 = 1.0f - this.swingProgress;
            f1 *= f1;
            f1 *= f1;
            f1 = 1.0f - f1;
            f2 = MathHelper.sin(f1 * 3.1415927f);
            f3 = MathHelper.sin(this.swingProgress * 3.1415927f) * -(this.bipedHead.rotateAngleX - 0.7f) * 0.75f;
            modelrenderer.rotateAngleX = (float)((double)modelrenderer.rotateAngleX - ((double)f2 * 1.2 + (double)f3));
            modelrenderer.rotateAngleY += this.bipedBody.rotateAngleY * 2.0f;
            modelrenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * 3.1415927f) * -0.4f;
        }
        if (!this.isSneak) ** GOTO lbl-1000
        if (!(entityIn instanceof EntityLivingBase)) ** GOTO lbl-1000
        base = (EntityLivingBase)entityIn;
        if (!base.isLay) lbl-1000:
        // 2 sources

        {
            this.bipedBody.rotateAngleX = 0.5f;
            this.bipedRightArm.rotateAngleX += 0.4f;
            this.bipedLeftArm.rotateAngleX += 0.4f;
            this.bipedRightLeg.rotationPointZ = 4.0f;
            this.bipedLeftLeg.rotationPointZ = 4.0f;
            this.bipedRightLeg.rotationPointY = 9.0f;
            this.bipedLeftLeg.rotationPointY = 9.0f;
            this.bipedHead.rotationPointY = 1.0f;
        } else lbl-1000:
        // 2 sources

        {
            this.bipedBody.rotateAngleX = 0.0f;
            this.bipedRightLeg.rotationPointZ = 0.1f;
            this.bipedLeftLeg.rotationPointZ = 0.1f;
            this.bipedRightLeg.rotationPointY = 12.0f;
            this.bipedLeftLeg.rotationPointY = 12.0f;
            this.bipedHead.rotationPointY = 0.0f;
        }
        this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09f) * 0.05f + 0.05f;
        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09f) * 0.05f + 0.05f;
        this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067f) * 0.05f;
        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067f) * 0.05f;
        if (this.rightArmPose == ArmPose.BOW_AND_ARROW) {
            this.bipedRightArm.rotateAngleY = -0.1f + this.bipedHead.rotateAngleY;
            this.bipedLeftArm.rotateAngleY = 0.1f + this.bipedHead.rotateAngleY + 0.4f;
            this.bipedRightArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
            this.bipedLeftArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
        } else if (this.leftArmPose == ArmPose.BOW_AND_ARROW) {
            this.bipedRightArm.rotateAngleY = -0.1f + this.bipedHead.rotateAngleY - 0.4f;
            this.bipedLeftArm.rotateAngleY = 0.1f + this.bipedHead.rotateAngleY;
            this.bipedRightArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
            this.bipedLeftArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
        }
        ModelBiped.copyModelAngles(this.bipedHead, this.bipedHeadwear);
    }

    @Override
    public void setModelAttributes(ModelBase model) {
        super.setModelAttributes(model);
        if (model instanceof ModelBiped) {
            ModelBiped modelbiped = (ModelBiped)model;
            this.leftArmPose = modelbiped.leftArmPose;
            this.rightArmPose = modelbiped.rightArmPose;
            this.isSneak = modelbiped.isSneak;
        }
    }

    public void setInvisible(boolean invisible) {
        this.bipedHead.showModel = invisible;
        this.bipedHeadwear.showModel = invisible;
        this.bipedBody.showModel = invisible;
        this.bipedRightArm.showModel = invisible;
        this.bipedLeftArm.showModel = invisible;
        this.bipedRightLeg.showModel = invisible;
        this.bipedLeftLeg.showModel = invisible;
    }

    public void postRenderArm(float scale, EnumHandSide side) {
        this.getArmForSide(side).postRender(scale);
    }

    protected ModelRenderer getArmForSide(EnumHandSide side) {
        return side == EnumHandSide.LEFT ? this.bipedLeftArm : this.bipedRightArm;
    }

    protected EnumHandSide getMainHand(Entity entityIn) {
        if (entityIn instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
            EnumHandSide enumhandside = entitylivingbase.getPrimaryHand();
            return entitylivingbase.swingingHand == EnumHand.MAIN_HAND ? enumhandside : enumhandside.opposite();
        }
        return EnumHandSide.RIGHT;
    }

    public static enum ArmPose {
        EMPTY,
        ITEM,
        BLOCK,
        BOW_AND_ARROW;

    }
}

