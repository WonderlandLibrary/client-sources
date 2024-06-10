package maxstats.weave.mixin;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.impl.blatant.Aura;
import me.sleepyfish.smok.rats.impl.other.Animations;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.misc.MathUtils;
import me.sleepyfish.smok.utils.entities.TargetUtils;
import me.sleepyfish.smok.utils.entities.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ItemRenderer.class})
public abstract class MixinItemRenderer {

    @Shadow
    @Final
    private Minecraft mc;

    @Shadow
    private float prevEquippedProgress;

    @Shadow
    private float equippedProgress;

    @Shadow
    private ItemStack itemToRender;

    @Shadow
    protected abstract void rotateArroundXAndY(float var1, float var2);

    @Shadow
    protected abstract void setLightMapFromPlayer(AbstractClientPlayer var1);

    @Shadow
    protected abstract void rotateWithPlayerRotations(EntityPlayerSP var1, float var2);

    @Shadow
    protected abstract void renderItemMap(AbstractClientPlayer var1, float var2, float var3, float var4);

    @Shadow
    protected abstract void transformFirstPersonItem(float var1, float var2);

    @Shadow
    protected abstract void performDrinking(AbstractClientPlayer var1, float var2);

    @Shadow
    protected abstract void doBlockTransformations();

    @Shadow
    protected abstract void doBowTransformations(float var1, AbstractClientPlayer var2);

    @Shadow
    protected abstract void doItemUsedTransformations(float var1);

    @Shadow
    public abstract void renderItem(EntityLivingBase var1, ItemStack var2, TransformType var3);

    @Shadow
    protected abstract void renderPlayerArm(AbstractClientPlayer var1, float var2, float var3);

    @Inject(method = {"renderItemInFirstPerson"}, at = {@At("HEAD")}, cancellable = true)
    public void renderItemInFirstPerson(float var1, CallbackInfo ci) {
        if (Smok.inst.ratManager.getRatByClass(Animations.class).isEnabled()) {
            ci.cancel();

            EntityPlayerSP var3 = mc.thePlayer;

            float var2 = 1.0F - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * var1);
            float var4 = var3.getSwingProgress(var1);
            float var5 = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * var1;
            float var6 = var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * var1;
            this.rotateArroundXAndY(var5, var6);
            this.setLightMapFromPlayer(var3);
            this.rotateWithPlayerRotations(var3, var1);
            GlStateManager.enableRescaleNormal();
            GlStateManager.pushMatrix();

            if (this.itemToRender != null) {
                float var3dd = MathHelper.sin((float) ((double) (var4 * var4) * MathUtils.PI));
                float var4dd = MathHelper.sin((float) ((double) MathHelper.sqrt_float(var4) * MathUtils.PI));

                if (this.itemToRender.getItem() == Items.filled_map) {
                    this.renderItemMap(var3, var5, var2, var4);
                } else if (var3.getItemInUseCount() > 0) {
                    EnumAction var7 = this.itemToRender.getItemUseAction();

                    switch (var7) {
                        case NONE:
                            this.transformFirstPersonItem(var2, 0.0F);
                            break;

                        case EAT:
                        case DRINK:
                            this.performDrinking(var3, var1);
                            this.transformFirstPersonItem(var2, 0.0F);
                            break;

                        case BLOCK:
                            this.transformFirstPersonItem(var2, 0.0F);
                            this.doBlockTransformations();
                            break;

                        case BOW:
                            this.transformFirstPersonItem(var2, 0.0F);
                            this.doBowTransformations(var1, var3);
                    }
                } else if (Smok.inst.ratManager.getRatByClass(Animations.class).isEnabled()) {
                    if (Animations.mode.getMode() == Animations.modes.Swing) {
                        this.transformFirstPersonItem(var2, var4);
                    }

                    if (Animations.mode.getMode() == Animations.modes.HighSwing) {
                        this.transformFirstPersonItem(var2 - 0.2F, var4);
                    }

                    if (Animations.mode.getMode() == Animations.modes.Reversed) {
                        this.transformFirstPersonItem(var2, var4 + 0.2F);
                    }

                    if (Animations.mode.getMode() == Animations.modes.Goober) {
                        this.transformFirstPersonItem(var2 - 0.2F, var4 - 0.2F);
                    }

                    if (Animations.mode.getMode() == Animations.modes.Sided) {
                        GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
                        GlStateManager.translate(0.0F, var2 * -0.6F, 0.0F);
                        GlStateManager.rotate(var4, 0.0F, 0.0F, 1.0F);
                        GlStateManager.rotate(var3dd * -20.0F, 0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate(var4dd * -20.0F, 0.0F, 0.0F, 1.0F);
                        GlStateManager.rotate(var4dd * -80.0F, 1.0F, 0.0F, 0.0F);
                        GlStateManager.scale(0.4F, 0.4F, 0.4F);
                    }

                    if (Animations.mode.getMode() == Animations.modes.Wonky) {
                        GlStateManager.translate(var4, -0.52F, -0.71999997F);
                        GlStateManager.translate(0.0F, var2 * -0.6F, 0.0F);
                        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate(var3dd * -20.0F, 0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate(var4dd * -20.0F, 0.0F, 0.0F, 1.0F);
                        GlStateManager.rotate(var4dd * -80.0F, 1.0F, 0.0F, 0.0F);
                        GlStateManager.scale(0.4F, 0.4F, 0.4F);
                    }

                    if (Animations.mode.getMode() == Animations.modes.Spin) {
                        GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
                        GlStateManager.translate(0.0F, var2 * -0.6F, 0.0F);
                        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate((1.0F - var3.swingProgress) * 360.0F, 1.0F, 0.0F, 0.0F);
                        GlStateManager.rotate(var3dd * -20.0F, 0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate(var4dd * -20.0F, 0.0F, 0.0F, 1.0F);
                        GlStateManager.rotate(var4dd * -80.0F, 1.0F, 0.0F, 0.0F);
                        GlStateManager.scale(0.4F, 0.4F, 0.4F);
                    }
                } else {
                    this.transformFirstPersonItem(var2, var4);
                    this.doItemUsedTransformations(var4);
                }

                if (Smok.inst.ratManager.getRatByClass(Aura.class).isEnabled()) {
                    if (TargetUtils.getTarget() == null && !Smok.inst.rotManager.isRotating()) {
                        if (Aura.renderFakeBlock && Aura.renderFakeBlockTimer.delay(2269L)) {
                            Aura.renderFakeBlock = false;
                            Aura.renderFakeBlockTimer.reset();

                            ClientUtils.addDebug("autoBlock reset");
                        }
                    } else if (Aura.renderFakeBlock && Utils.holdingWeapon()) {
                        // Blocking animation -----------
                        GlStateManager.translate(-0.5F, 0.2F, 0.0F);
                        GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
                        GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
                        // Blocking animation -----------
                    }
                }

                if (Smok.inst.ratManager.getRatByClass(Animations.class).isEnabled() && Animations.removeHand.isEnabled()) {
                    this.renderItem(var3, null, TransformType.FIRST_PERSON);
                } else {
                    this.renderItem(var3, this.itemToRender, TransformType.FIRST_PERSON);
                }
            } else if (!var3.isInvisible()) {
                if (Smok.inst.ratManager.getRatByClass(Animations.class).isEnabled() && Animations.removeHand.isEnabled()) {
                    this.renderPlayerArm(var3, -999999.0F, 9999.0F);
                } else {
                    this.renderPlayerArm(var3, var2, var4);
                }
            }

            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            RenderHelper.disableStandardItemLighting();
        }
    }

}