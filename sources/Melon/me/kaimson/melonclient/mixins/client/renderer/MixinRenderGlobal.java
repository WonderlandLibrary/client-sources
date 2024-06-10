package me.kaimson.melonclient.mixins.client.renderer;

import org.spongepowered.asm.mixin.*;
import me.kaimson.melonclient.config.*;
import me.kaimson.melonclient.features.modules.*;
import me.kaimson.melonclient.features.*;
import me.kaimson.melonclient.gui.utils.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin({ bfr.class })
public class MixinRenderGlobal
{
    @Shadow
    private bdb k;
    
    @Inject(method = { "drawSelectionBox" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V", shift = At.Shift.AFTER) })
    private void drawSelectionBox(final wn player, final auh movingObjectPositionIn, final int execute, final float partialTicks, final CallbackInfo ci) {
        if (ModuleConfig.INSTANCE.isEnabled(BlockOverlayModule.INSTANCE)) {
            GuiUtils.setGlColor(BlockOverlayModule.INSTANCE.outlineColor.getColor());
        }
    }
    
    @ModifyArg(method = { "drawSelectionBox" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glLineWidth(F)V", remap = false))
    private float getLineWidth(final float lineWidth) {
        return ModuleConfig.INSTANCE.isEnabled(BlockOverlayModule.INSTANCE) ? BlockOverlayModule.INSTANCE.outlineWidth.getFloat() : lineWidth;
    }
    
    @Inject(method = { "drawSelectionBox" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;depthMask(Z)V", ordinal = 0, shift = At.Shift.AFTER) })
    private void disableDepth(final wn player, final auh movingObjectPositionIn, final int execute, final float partialTicks, final CallbackInfo ci) {
        if (ModuleConfig.INSTANCE.isEnabled(BlockOverlayModule.INSTANCE) && BlockOverlayModule.INSTANCE.ignoreDepth.getBoolean()) {
            bfl.i();
        }
    }
    
    @Inject(method = { "drawSelectionBox" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;depthMask(Z)V", ordinal = 1) })
    private void enableDepth(final wn player, final auh movingObjectPositionIn, final int execute, final float partialTicks, final CallbackInfo ci) {
        if (ModuleConfig.INSTANCE.isEnabled(BlockOverlayModule.INSTANCE) && BlockOverlayModule.INSTANCE.ignoreDepth.getBoolean()) {
            bfl.j();
        }
    }
    
    @Inject(method = { "drawSelectionBox" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;drawSelectionBoundingBox(Lnet/minecraft/util/AxisAlignedBB;)V", shift = At.Shift.AFTER) }, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void drawSelectionBoxFill(final wn player, final auh movingObjectPositionIn, final int execute, final float partialTicks, final CallbackInfo ci, final float f, final cj blockPos, final afh block) {
        if (ModuleConfig.INSTANCE.isEnabled(BlockOverlayModule.INSTANCE) && BlockOverlayModule.INSTANCE.fill.getBoolean()) {
            GuiUtils.setGlColor(BlockOverlayModule.INSTANCE.fillColor.getColor());
            final double d0 = player.P + (player.s - player.P) * partialTicks;
            final double d2 = player.Q + (player.t - player.Q) * partialTicks;
            final double d3 = player.R + (player.u - player.R) * partialTicks;
            BlockOverlayModule.INSTANCE.drawFilledWithGL(block.b((adm)this.k, blockPos).b(0.0020000000949949026, 0.0020000000949949026, 0.0020000000949949026).c(-d0, -d2, -d3));
        }
    }
}
