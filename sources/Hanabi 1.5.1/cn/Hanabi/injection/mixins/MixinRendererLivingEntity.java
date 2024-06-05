package cn.Hanabi.injection.mixins;

import cn.Hanabi.injection.interfaces.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import cn.Hanabi.modules.*;
import net.minecraft.entity.player.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.util.*;
import cn.Hanabi.events.*;
import com.darkmagician6.eventapi.*;
import com.darkmagician6.eventapi.events.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ RendererLivingEntity.class })
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> extends MixinRender implements IRendererLivingEntity
{
    @Shadow
    protected boolean renderOutlines;
    @Shadow
    protected ModelBase mainModel;
    @Shadow
    @Final
    private static Logger logger;
    
    public MixinRendererLivingEntity() {
        this.renderOutlines = false;
    }
    
    @Inject(method = { "renderName(Lnet/minecraft/entity/EntityLivingBase;DDD)V" }, at = { @At("HEAD") }, cancellable = true)
    public void onChat(final EntityLivingBase entity, final double x, final double y, final double z, final CallbackInfo ci) {
        if (ModManager.getModule("Nametags").isEnabled() && entity instanceof EntityPlayer) {
            ci.cancel();
        }
    }
    
    @Shadow
    protected abstract float interpolateRotation(final float p0, final float p1, final float p2);
    
    @Shadow
    protected abstract float getSwingProgress(final T p0, final float p1);
    
    @Shadow
    protected abstract void renderLivingAt(final T p0, final double p1, final double p2, final double p3);
    
    @Shadow
    protected abstract void rotateCorpse(final T p0, final float p1, final float p2, final float p3);
    
    @Shadow
    protected abstract float handleRotationFloat(final T p0, final float p1);
    
    @Shadow
    protected abstract void preRenderCallback(final T p0, final float p1);
    
    @Shadow
    protected abstract boolean setScoreTeamColor(final EntityLivingBase p0);
    
    @Shadow
    protected abstract void unsetScoreTeamColor();
    
    @Shadow
    protected abstract void renderModel(final T p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6);
    
    @Shadow
    protected abstract void renderLayers(final T p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7);
    
    @Shadow
    protected abstract boolean setDoRenderBrightness(final T p0, final float p1);
    
    @Shadow
    protected abstract void unsetBrightness();
    
    @Override
    public void doRenderModel(final Object entitylivingbaseIn, final float a, final float b, final float c, final float d, final float e, final float scaleFactor) {
        this.renderModel((EntityLivingBase)entitylivingbaseIn, a, b, c, d, e, scaleFactor);
    }
    
    @Override
    public void doRenderLayers(final Object entitylivingbaseIn, final float a, final float b, final float partialTicks, final float d, final float e, final float f, final float g) {
        this.renderLayers((EntityLivingBase)entitylivingbaseIn, a, b, partialTicks, d, e, f, g);
    }
    
    @Overwrite
    public void doRender(final T entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        this.mainModel.swingProgress = this.getSwingProgress(entity, partialTicks);
        this.mainModel.isRiding = entity.isRiding();
        this.mainModel.isChild = entity.isChild();
        try {
            float f = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
            final float f2 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
            float f3 = f2 - f;
            if (this.mainModel.isRiding && entity.ridingEntity instanceof EntityLivingBase) {
                final EntityLivingBase entitylivingbase = (EntityLivingBase)entity.ridingEntity;
                f = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
                f3 = f2 - f;
                float f4 = MathHelper.wrapAngleTo180_float(f3);
                if (f4 < -85.0f) {
                    f4 = -85.0f;
                }
                if (f4 >= 85.0f) {
                    f4 = 85.0f;
                }
                f = f2 - f4;
                if (f4 * f4 > 2500.0f) {
                    f += f4 * 0.2f;
                }
            }
            final float f5 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            this.renderLivingAt(entity, x, y, z);
            final float f6 = this.handleRotationFloat(entity, partialTicks);
            this.rotateCorpse(entity, f6, f, partialTicks);
            GlStateManager.enableRescaleNormal();
            GlStateManager.scale(-1.0f, -1.0f, 1.0f);
            this.preRenderCallback(entity, partialTicks);
            final float f7 = 0.0625f;
            GlStateManager.translate(0.0f, -1.5078125f, 0.0f);
            float f8 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
            float f9 = entity.limbSwing - entity.limbSwingAmount * (1.0f - partialTicks);
            if (entity instanceof EntityPlayer) {
                final EventRenderLivingEntity pre = new EventRenderLivingEntity(entity, true, f9, f8, f6, f3, f5, f, f7);
                EventManager.call(pre);
                if (pre.isCancelled()) {
                    return;
                }
            }
            if (entity.isChild()) {
                f9 *= 3.0f;
            }
            if (f8 > 1.0f) {
                f8 = 1.0f;
            }
            GlStateManager.enableAlpha();
            this.mainModel.setLivingAnimations((EntityLivingBase)entity, f9, f8, partialTicks);
            this.mainModel.setRotationAngles(f9, f8, f6, f3, f5, 0.0625f, (Entity)entity);
            if (this.renderOutlines) {
                final boolean flag1 = this.setScoreTeamColor(entity);
                this.renderModel(entity, f9, f8, f6, f3, f5, 0.0625f);
                if (flag1) {
                    this.unsetScoreTeamColor();
                }
            }
            else {
                final boolean flag2 = this.setDoRenderBrightness(entity, partialTicks);
                this.renderModel(entity, f9, f8, f6, f3, f5, 0.0625f);
                if (flag2) {
                    this.unsetBrightness();
                }
                GlStateManager.depthMask(true);
                if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator()) {
                    this.renderLayers(entity, f9, f8, partialTicks, f6, f3, f5, 0.0625f);
                }
            }
            GlStateManager.disableRescaleNormal();
        }
        catch (Exception exception) {
            MixinRendererLivingEntity.logger.error("Couldn't render entity", (Throwable)exception);
        }
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
        if (!this.renderOutlines) {
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
        final EventRenderLivingEntity post = new EventRenderLivingEntity(entity, false);
        EventManager.call(post);
    }
}
