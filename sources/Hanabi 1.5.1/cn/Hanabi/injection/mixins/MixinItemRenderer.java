package cn.Hanabi.injection.mixins;

import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.entity.*;
import org.spongepowered.asm.mixin.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.modules.Render.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

@Mixin({ ItemRenderer.class })
public abstract class MixinItemRenderer
{
    @Shadow
    private Minecraft mc;
    @Shadow
    private float equippedProgress;
    @Shadow
    private float prevEquippedProgress;
    @Shadow
    private ItemStack itemToRender;
    @Shadow
    private RenderManager renderManager;
    
    @Overwrite
    public void renderItemInFirstPerson(final float partialTicks) {
        final float f = 1.0f - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks);
        final AbstractClientPlayer entityplayersp = (AbstractClientPlayer)this.mc.thePlayer;
        final float f2 = entityplayersp.getSwingProgress(partialTicks);
        final float f3 = entityplayersp.prevRotationPitch + (entityplayersp.rotationPitch - entityplayersp.prevRotationPitch) * partialTicks;
        final float f4 = entityplayersp.prevRotationYaw + (entityplayersp.rotationYaw - entityplayersp.prevRotationYaw) * partialTicks;
        this.rotateArroundXAndY(f3, f4);
        this.setLightMapFromPlayer(entityplayersp);
        this.rotateWithPlayerRotations((EntityPlayerSP)entityplayersp, partialTicks);
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        if (this.itemToRender != null) {
            if (this.itemToRender.getItem() == Items.filled_map) {
                this.renderItemMap(entityplayersp, f3, f, f2);
            }
            else if (entityplayersp.getItemInUseCount() > 0) {
                final EnumAction enumaction = this.itemToRender.getItemUseAction();
                switch (enumaction) {
                    case NONE: {
                        this.transformFirstPersonItem(f, 0.0f);
                        break;
                    }
                    case EAT:
                    case DRINK: {
                        this.performDrinking(entityplayersp, partialTicks);
                        this.transformFirstPersonItem(f, 0.0f);
                        break;
                    }
                    case BLOCK: {
                        this.renderblock(f, f2);
                        break;
                    }
                    case BOW: {
                        this.transformFirstPersonItem(f, 0.0f);
                        this.doBowTransformations(partialTicks, entityplayersp);
                        break;
                    }
                }
            }
            else {
                this.doItemUsedTransformations(f2);
                this.transformFirstPersonItem(f, f2);
            }
            this.renderItem((EntityLivingBase)entityplayersp, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
        }
        else if (!entityplayersp.isInvisible()) {
            this.renderPlayerArm(entityplayersp, f, f2);
        }
        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
    }
    
    private void renderblock(final float f, final float f1) {
        if (!ModManager.getModule("HitAnimation").isEnabled()) {
            this.transformFirstPersonItem(f, 0.0f);
            this.doBlockTransformations();
            return;
        }
        if (HitAnimation.mode.isCurrentMode("Sigma")) {
            this.transformFirstPersonItem(f, 0.0f);
            this.doBlockTransformations();
            final float var14 = MathHelper.sin(f1 * f1 * 3.1415927f);
            final float var15 = MathHelper.sin(MathHelper.sqrt_float(f1) * 3.1415927f);
            GlStateManager.translate(-0.0f, 0.4f, 1.0f);
            GlStateManager.rotate(-var15 * 22.5f, -9.0f, -0.0f, 9.0f);
            GlStateManager.rotate(-var15 * 10.0f, 1.0f, -0.4f, -0.5f);
        }
        else if (HitAnimation.mode.isCurrentMode("Debug")) {
            this.transformFirstPersonItem(0.2f, f1);
            this.doBlockTransformations();
            GlStateManager.translate(-0.5, 0.2, 0.0);
        }
        else if (HitAnimation.mode.isCurrentMode("Vanilla")) {
            this.transformFirstPersonItem(f, 0.0f);
            this.doBlockTransformations();
        }
        else if (HitAnimation.mode.isCurrentMode("Luna")) {
            this.transformFirstPersonItem(f, 0.0f);
            this.doBlockTransformations();
            final float sin = MathHelper.sin(f1 * f1 * 3.1415927f);
            final float sin2 = MathHelper.sin(MathHelper.sqrt_float(f1) * 3.1415927f);
            GlStateManager.scale(1.0f, 1.0f, 1.0f);
            GlStateManager.translate(-0.2f, 0.45f, 0.25f);
            GlStateManager.rotate(-sin2 * 20.0f, -5.0f, -5.0f, 9.0f);
        }
        else if (HitAnimation.mode.isCurrentMode("1.7")) {
            this.genCustom(f, f1);
            this.doBlockTransformations();
        }
        else if (HitAnimation.mode.isCurrentMode("ExhibitionSwang")) {
            this.transformFirstPersonItem(f / 2.0f, f1);
            final float var16 = MathHelper.sin(MathHelper.sqrt_float(f1) * 3.1415927f);
            GlStateManager.rotate(var16 * 30.0f / 2.0f, -var16, -0.0f, 9.0f);
            GlStateManager.rotate(var16 * 40.0f, 1.0f, -var16 / 2.0f, -0.0f);
            this.doBlockTransformations();
        }
        else if (HitAnimation.mode.isCurrentMode("ExhibitionSwank")) {
            this.transformFirstPersonItem(f / 2.0f, f1);
            final float var16 = MathHelper.sin(MathHelper.sqrt_float(f) * 3.1415927f);
            GlStateManager.rotate(var16 * 30.0f, -var16, -0.0f, 9.0f);
            GlStateManager.rotate(var16 * 40.0f, 1.0f, -var16, -0.0f);
            this.doBlockTransformations();
        }
        else if (HitAnimation.mode.isCurrentMode("ExhibitionSwong")) {
            this.transformFirstPersonItem(f / 2.0f, 0.0f);
            final float var16 = MathHelper.sin(f1 * f1 * 3.1415927f);
            final float var17 = MathHelper.sin(MathHelper.sqrt_float(f1) * 3.1415927f);
            GlStateManager.rotate(-var17 * 40.0f / 2.0f, var17 / 2.0f, -0.0f, 9.0f);
            GlStateManager.rotate(-var17 * 30.0f, 1.0f, var17 / 2.0f, -0.0f);
            this.doBlockTransformations();
        }
        else if (HitAnimation.mode.isCurrentMode("Jigsaw")) {
            this.transformFirstPersonItem(0.1f, f1);
            this.doBlockTransformations();
            GlStateManager.translate(-0.5, 0.0, 0.0);
        }
        else if (HitAnimation.mode.isCurrentMode("Hanabi")) {
            final float var16 = MathHelper.sin(MathHelper.sqrt_float(f1) * 3.1415927f);
            GlStateManager.translate(-0.0f, -0.3f, 0.4f);
            GlStateManager.rotate(-var16 * 22.5f, -9.0f, -0.0f, 9.0f);
            GlStateManager.rotate(-var16 * 10.0f, 1.0f, -0.4f, -0.5f);
        }
    }
    
    private void genCustom(final float equipProgress, final float swingProgress) {
        GlStateManager.translate(0.56f, -0.52f, -0.71999997f);
        GlStateManager.translate(0.0f, equipProgress * -0.6f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        final float var3 = MathHelper.sin(swingProgress * swingProgress * 3.1415927f);
        final float var4 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927f);
        GlStateManager.rotate(var3 * -34.0f, 0.0f, 1.0f, 0.2f);
        GlStateManager.rotate(var4 * -20.7f, 0.2f, 0.1f, 1.0f);
        GlStateManager.rotate(var4 * -68.6f, 1.3f, 0.1f, 0.2f);
        GlStateManager.scale(0.4f, 0.4f, 0.4f);
    }
    
    private boolean canBlockItem() {
        try {
            return this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Shadow
    protected abstract void doBlockTransformations();
    
    @Shadow
    protected abstract void renderPlayerArm(final AbstractClientPlayer p0, final float p1, final float p2);
    
    @Shadow
    public abstract void renderItem(final EntityLivingBase p0, final ItemStack p1, final ItemCameraTransforms.TransformType p2);
    
    @Shadow
    protected abstract void rotateArroundXAndY(final float p0, final float p1);
    
    @Shadow
    public abstract void renderItemMap(final AbstractClientPlayer p0, final float p1, final float p2, final float p3);
    
    @Shadow
    protected abstract void transformFirstPersonItem(final float p0, final float p1);
    
    @Shadow
    protected abstract void setLightMapFromPlayer(final AbstractClientPlayer p0);
    
    @Shadow
    protected abstract void doItemUsedTransformations(final float p0);
    
    @Shadow
    protected abstract void doBowTransformations(final float p0, final AbstractClientPlayer p1);
    
    @Shadow
    protected abstract void rotateWithPlayerRotations(final EntityPlayerSP p0, final float p1);
    
    @Shadow
    protected abstract void performDrinking(final AbstractClientPlayer p0, final float p1);
}
