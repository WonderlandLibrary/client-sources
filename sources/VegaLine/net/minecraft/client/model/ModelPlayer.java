/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import ru.govno.client.utils.Command.impl.Panic;

public class ModelPlayer
extends ModelBiped {
    public ModelRenderer bipedLeftArmwear;
    public ModelRenderer bipedRightArmwear;
    public ModelRenderer bipedLeftLegwear;
    public ModelRenderer bipedRightLegwear;
    public ModelRenderer bipedBodyWear;
    private final ModelRenderer bipedCape;
    private final boolean smallArms;

    public ModelPlayer(float modelSize, boolean smallArmsIn) {
        super(modelSize, 0.0f, 64, 64);
        this.smallArms = smallArmsIn;
        this.bipedCape = new ModelRenderer(this, 0, 0);
        this.bipedCape.setTextureSize(64, 32);
        this.bipedCape.addBox(-5.0f, 0.0f, -1.0f, 10, 16, 1, modelSize);
        if (smallArmsIn) {
            this.bipedLeftArm = new ModelRenderer(this, 32, 48);
            this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 3, 12, 4, modelSize);
            this.bipedLeftArm.setRotationPoint(5.0f, 2.5f, 0.0f);
            this.bipedRightArm = new ModelRenderer(this, 40, 16);
            this.bipedRightArm.addBox(-2.0f, -2.0f, -2.0f, 3, 12, 4, modelSize);
            this.bipedRightArm.setRotationPoint(-5.0f, 2.5f, 0.0f);
            this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            this.bipedLeftArmwear.addBox(-1.0f, -2.0f, -2.0f, 3, 12, 4, modelSize + 0.25f);
            this.bipedLeftArmwear.setRotationPoint(5.0f, 2.5f, 0.0f);
            this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
            this.bipedRightArmwear.addBox(-2.0f, -2.0f, -2.0f, 3, 12, 4, modelSize + 0.25f);
            this.bipedRightArmwear.setRotationPoint(-5.0f, 2.5f, 10.0f);
        } else {
            this.bipedLeftArm = new ModelRenderer(this, 32, 48);
            this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, modelSize);
            this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
            this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            this.bipedLeftArmwear.addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, modelSize + 0.25f);
            this.bipedLeftArmwear.setRotationPoint(5.0f, 2.0f, 0.0f);
            this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
            this.bipedRightArmwear.addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4, modelSize + 0.25f);
            this.bipedRightArmwear.setRotationPoint(-5.0f, 2.0f, 10.0f);
        }
        this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
        this.bipedLeftLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, modelSize);
        this.bipedLeftLeg.setRotationPoint(1.9f, 12.0f, 0.0f);
        this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
        this.bipedLeftLegwear.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, modelSize + 0.25f);
        this.bipedLeftLegwear.setRotationPoint(1.9f, 12.0f, 0.0f);
        this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
        this.bipedRightLegwear.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, modelSize + 0.25f);
        this.bipedRightLegwear.setRotationPoint(-1.9f, 12.0f, 0.0f);
        this.bipedBodyWear = new ModelRenderer(this, 16, 32);
        this.bipedBodyWear.addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, modelSize + 0.25f);
        this.bipedBodyWear.setRotationPoint(0.0f, 0.0f, 0.0f);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.pushMatrix();
        if (this.isChild) {
            EntityLivingBase base;
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            GlStateManager.translate(0.0f, 24.0f * scale, 0.0f);
            this.bipedLeftLegwear.render(scale);
            this.bipedRightLegwear.render(scale);
            if (!Panic.stop && entityIn instanceof EntityLivingBase && (base = (EntityLivingBase)entityIn).isEating() && base.getActiveHand() == EnumHand.OFF_HAND) {
                GlStateManager.rotate(-45.0f, 1.0f, -1.0f, 0.0f);
            }
            this.bipedLeftArmwear.render(scale);
            if (!Panic.stop && entityIn instanceof EntityLivingBase && (base = (EntityLivingBase)entityIn).isEating() && base.getActiveHand() == EnumHand.OFF_HAND) {
                GlStateManager.rotate(45.0f, 1.0f, -1.0f, 0.0f);
            }
            if (!Panic.stop && entityIn instanceof EntityLivingBase && (base = (EntityLivingBase)entityIn).isEating() && base.getActiveHand() == EnumHand.MAIN_HAND) {
                GlStateManager.rotate(-45.0f, 1.0f, 1.0f, 0.0f);
            }
            this.bipedRightArmwear.render(scale);
            if (!Panic.stop && entityIn instanceof EntityLivingBase && (base = (EntityLivingBase)entityIn).isEating() && base.getActiveHand() == EnumHand.MAIN_HAND) {
                GlStateManager.rotate(45.0f, 1.0f, 1.0f, 0.0f);
            }
            this.bipedBodyWear.render(scale);
        } else {
            EntityLivingBase base;
            if (entityIn.isSneaking()) {
                GlStateManager.translate(0.0f, 0.2f, 0.0f);
            }
            this.bipedLeftLegwear.render(scale);
            this.bipedRightLegwear.render(scale);
            if (!Panic.stop && entityIn instanceof EntityLivingBase && (base = (EntityLivingBase)entityIn).isEating() && base.getActiveHand() == EnumHand.MAIN_HAND) {
                GlStateManager.rotate(-45.0f, 1.0f, 1.0f, 0.0f);
            }
            this.bipedRightArmwear.render(scale);
            if (!Panic.stop && entityIn instanceof EntityLivingBase && (base = (EntityLivingBase)entityIn).isEating() && base.getActiveHand() == EnumHand.MAIN_HAND) {
                GlStateManager.rotate(45.0f, 1.0f, 1.0f, 0.0f);
            }
            if (!Panic.stop && entityIn instanceof EntityLivingBase && (base = (EntityLivingBase)entityIn).isEating() && base.getActiveHand() == EnumHand.OFF_HAND) {
                GlStateManager.rotate(-45.0f, 1.0f, -1.0f, 0.0f);
            }
            this.bipedLeftArmwear.render(scale);
            if (!Panic.stop && entityIn instanceof EntityLivingBase && (base = (EntityLivingBase)entityIn).isEating() && base.getActiveHand() == EnumHand.OFF_HAND) {
                GlStateManager.rotate(45.0f, 1.0f, -1.0f, 0.0f);
            }
            this.bipedBodyWear.render(scale);
        }
        GlStateManager.popMatrix();
    }

    public void renderCape(float scale) {
        this.bipedCape.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        ModelBase.copyModelAngles(this.bipedLeftLeg, this.bipedLeftLegwear);
        ModelBase.copyModelAngles(this.bipedRightLeg, this.bipedRightLegwear);
        ModelBase.copyModelAngles(this.bipedLeftArm, this.bipedLeftArmwear);
        ModelBase.copyModelAngles(this.bipedRightArm, this.bipedRightArmwear);
        ModelBase.copyModelAngles(this.bipedBody, this.bipedBodyWear);
    }

    @Override
    public void setInvisible(boolean invisible) {
        super.setInvisible(invisible);
        this.bipedLeftArmwear.showModel = invisible;
        this.bipedRightArmwear.showModel = invisible;
        this.bipedLeftLegwear.showModel = invisible;
        this.bipedRightLegwear.showModel = invisible;
        this.bipedBodyWear.showModel = invisible;
        this.bipedCape.showModel = invisible;
    }

    @Override
    public void postRenderArm(float scale, EnumHandSide side) {
        ModelRenderer modelrenderer = this.getArmForSide(side);
        if (this.smallArms) {
            float f = 0.5f * (float)(side == EnumHandSide.RIGHT ? 1 : -1);
            ModelRenderer modelRenderer = modelrenderer;
            modelRenderer.rotationPointX += f;
            modelrenderer.postRender(scale);
            ModelRenderer modelRenderer2 = modelrenderer;
            modelRenderer2.rotationPointX -= f;
        } else {
            modelrenderer.postRender(scale);
        }
    }
}

