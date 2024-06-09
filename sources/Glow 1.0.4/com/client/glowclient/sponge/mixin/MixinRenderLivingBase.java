package com.client.glowclient.sponge.mixin;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import com.client.glowclient.sponge.mixinutils.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ RenderLivingBase.class })
public abstract class MixinRenderLivingBase<T extends EntityLivingBase> extends Render<T>
{
    @Shadow
    protected ModelBase field_77045_g;
    @Shadow
    protected boolean field_188323_j;
    
    @Shadow
    protected abstract float getSwingProgress(final T p0, final float p1);
    
    @Shadow
    protected abstract float interpolateRotation(final float p0, final float p1, final float p2);
    
    @Shadow
    protected abstract void renderLivingAt(final T p0, final double p1, final double p2, final double p3);
    
    @Shadow
    protected abstract float handleRotationFloat(final T p0, final float p1);
    
    @Shadow
    public abstract float prepareScale(final T p0, final float p1);
    
    @Shadow
    protected abstract void applyRotations(final T p0, final float p1, final float p2, final float p3);
    
    @Shadow
    protected abstract void renderModel(final T p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6);
    
    @Shadow
    protected abstract boolean setScoreTeamColor(final T p0);
    
    @Shadow
    protected abstract void renderLayers(final T p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7);
    
    @Shadow
    protected abstract void unsetScoreTeamColor();
    
    @Shadow
    protected abstract boolean setDoRenderBrightness(final T p0, final float p1);
    
    @Shadow
    protected abstract void unsetBrightness();
    
    public MixinRenderLivingBase() {
        super((RenderManager)null);
    }
    
    @Overwrite
    public void doRender(final T scoreTeamColor, final double n, final double n2, final double n3, final float n4, final float n5) {
        if (MinecraftForge.EVENT_BUS.post((Event)new RenderLivingEvent.Pre((EntityLivingBase)scoreTeamColor, (RenderLivingBase)RenderLivingBase.class.cast(this), n5, n, n2, n3))) {
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        this.mainModel.swingProgress = this.getSwingProgress(scoreTeamColor, n5);
        final boolean isRiding = scoreTeamColor.isRiding() && scoreTeamColor.getRidingEntity() != null && scoreTeamColor.getRidingEntity().shouldRiderSit();
        this.mainModel.isRiding = isRiding;
        this.mainModel.isChild = scoreTeamColor.isChild();
        try {
            float interpolateRotation = this.interpolateRotation(scoreTeamColor.prevRenderYawOffset, scoreTeamColor.renderYawOffset, n5);
            final float interpolateRotation2 = this.interpolateRotation(scoreTeamColor.prevRotationYawHead, scoreTeamColor.rotationYawHead, n5);
            float n6 = interpolateRotation2 - interpolateRotation;
            if (isRiding && scoreTeamColor.getRidingEntity() instanceof EntityLivingBase) {
                final EntityLivingBase entityLivingBase = (EntityLivingBase)scoreTeamColor.getRidingEntity();
                float wrapDegrees = MathHelper.wrapDegrees(interpolateRotation2 - this.interpolateRotation(entityLivingBase.prevRenderYawOffset, entityLivingBase.renderYawOffset, n5));
                if (wrapDegrees < -85.0f) {
                    wrapDegrees = -85.0f;
                }
                if (wrapDegrees >= 85.0f) {
                    wrapDegrees = 85.0f;
                }
                interpolateRotation = interpolateRotation2 - wrapDegrees;
                if (wrapDegrees * wrapDegrees > 2500.0f) {
                    interpolateRotation += wrapDegrees * 0.2f;
                }
                n6 = interpolateRotation2 - interpolateRotation;
            }
            final float n7 = scoreTeamColor.prevRotationPitch + (scoreTeamColor.rotationPitch - scoreTeamColor.prevRotationPitch) * n5;
            this.renderLivingAt(scoreTeamColor, n, n2, n3);
            final float handleRotationFloat = this.handleRotationFloat(scoreTeamColor, n5);
            this.applyRotations(scoreTeamColor, handleRotationFloat, interpolateRotation, n5);
            final float prepareScale = this.prepareScale(scoreTeamColor, n5);
            float n8 = 0.0f;
            float n9 = 0.0f;
            if (!scoreTeamColor.isRiding()) {
                n8 = scoreTeamColor.prevLimbSwingAmount + (scoreTeamColor.limbSwingAmount - scoreTeamColor.prevLimbSwingAmount) * n5;
                n9 = scoreTeamColor.limbSwing - scoreTeamColor.limbSwingAmount * (1.0f - n5);
                if (scoreTeamColor.isChild()) {
                    n9 *= 3.0f;
                }
                if (n8 > 1.0f) {
                    n8 = 1.0f;
                }
                n6 = interpolateRotation2 - interpolateRotation;
            }
            GlStateManager.enableAlpha();
            this.mainModel.setLivingAnimations((EntityLivingBase)scoreTeamColor, n9, n8, n5);
            this.mainModel.setRotationAngles(n9, n8, handleRotationFloat, n6, n7, prepareScale, (Entity)scoreTeamColor);
            if (HookTranslator.m49()) {
                GlStateManager.depthMask(true);
                if (HookTranslator.m55()) {
                    if (scoreTeamColor instanceof EntityPlayer) {
                        if (scoreTeamColor != HookTranslator.mc.player) {
                            this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                            HookTranslator.m50();
                            this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                            HookTranslator.m51();
                            this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                            HookTranslator.m52();
                            HookTranslator.m53();
                            this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                            HookTranslator.m54();
                        }
                    }
                    else {
                        this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                    }
                }
                if (HookTranslator.m57()) {
                    if (scoreTeamColor instanceof EntityMob || scoreTeamColor instanceof EntitySlime || scoreTeamColor instanceof EntityMagmaCube || scoreTeamColor instanceof EntityGhast) {
                        if (scoreTeamColor != HookTranslator.mc.player) {
                            this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                            HookTranslator.m50();
                            this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                            HookTranslator.m51();
                            this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                            HookTranslator.m52();
                            HookTranslator.m53();
                            this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                            HookTranslator.m54();
                        }
                    }
                    else {
                        this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                    }
                }
                if (HookTranslator.m56()) {
                    if (scoreTeamColor instanceof EntityAnimal || scoreTeamColor instanceof EntityIronGolem || scoreTeamColor instanceof EntityGolem || scoreTeamColor instanceof EntitySquid) {
                        if (scoreTeamColor != HookTranslator.mc.player) {
                            this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                            HookTranslator.m50();
                            this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                            HookTranslator.m51();
                            this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                            HookTranslator.m52();
                            HookTranslator.m53();
                            this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                            HookTranslator.m54();
                        }
                    }
                    else {
                        this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                    }
                }
                if (HookTranslator.m58() && scoreTeamColor != HookTranslator.mc.player) {
                    this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                    HookTranslator.m50();
                    this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                    HookTranslator.m51();
                    this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                    HookTranslator.m52();
                    HookTranslator.m53();
                    this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                    HookTranslator.m54();
                }
            }
            else {
                this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
            }
            if (this.renderOutlines) {
                final boolean setScoreTeamColor = this.setScoreTeamColor(scoreTeamColor);
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(this.getTeamColor((Entity)scoreTeamColor));
                if (!this.renderMarker) {
                    this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                }
                if (!(scoreTeamColor instanceof EntityPlayer) || !((EntityPlayer)scoreTeamColor).isSpectator()) {
                    this.renderLayers(scoreTeamColor, n9, n8, n5, handleRotationFloat, n6, n7, prepareScale);
                }
                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();
                if (setScoreTeamColor) {
                    this.unsetScoreTeamColor();
                }
            }
            else {
                final boolean setDoRenderBrightness = this.setDoRenderBrightness(scoreTeamColor, n5);
                this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, prepareScale);
                if (setDoRenderBrightness) {
                    this.unsetBrightness();
                }
                GlStateManager.depthMask(true);
                if (!(scoreTeamColor instanceof EntityPlayer) || !((EntityPlayer)scoreTeamColor).isSpectator()) {
                    this.renderLayers(scoreTeamColor, n9, n8, n5, handleRotationFloat, n6, n7, prepareScale);
                }
            }
            GlStateManager.disableRescaleNormal();
        }
        catch (Exception ex) {}
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
        super.doRender((Entity)scoreTeamColor, n, n2, n3, n4, n5);
        MinecraftForge.EVENT_BUS.post((Event)new RenderLivingEvent.Post((EntityLivingBase)scoreTeamColor, (RenderLivingBase)RenderLivingBase.class.cast(this), n5, n, n2, n3));
    }
    
    @Overwrite
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityLivingBase)entity, n, n2, n3, n4, n5);
    }
}
