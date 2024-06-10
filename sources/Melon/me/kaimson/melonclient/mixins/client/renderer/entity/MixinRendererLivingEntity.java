package me.kaimson.melonclient.mixins.client.renderer.entity;

import org.spongepowered.asm.mixin.*;
import me.kaimson.melonclient.config.*;
import me.kaimson.melonclient.features.modules.*;
import me.kaimson.melonclient.features.*;
import org.spongepowered.asm.mixin.injection.*;
import me.kaimson.melonclient.gui.utils.*;

@Mixin({ bjl.class })
public class MixinRendererLivingEntity
{
    @ModifyArg(method = { "renderLayers" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;setBrightness(Lnet/minecraft/entity/EntityLivingBase;FZ)Z"))
    private boolean combineTextures(final boolean shouldCombine) {
        return (ModuleConfig.INSTANCE.isEnabled(OldAnimationsModule.INSTANCE) && OldAnimationsModule.INSTANCE.damage.getBoolean()) || shouldCombine;
    }
    
    @ModifyArg(method = { "setBrightness" }, at = @At(value = "INVOKE", target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;", ordinal = 0))
    private float setRed(final float redIn) {
        float red = (ModuleConfig.INSTANCE.isEnabled(OldAnimationsModule.INSTANCE) && OldAnimationsModule.INSTANCE.damage.getBoolean()) ? 0.7f : redIn;
        red = (ModuleConfig.INSTANCE.isEnabled(OldAnimationsModule.INSTANCE) ? ((OldAnimationsModule.INSTANCE.hitColor.getColor() >> 16 & 0xFF) / 255.0f) : red);
        return red;
    }
    
    @ModifyArg(method = { "setBrightness" }, at = @At(value = "INVOKE", target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;", ordinal = 1))
    private float setGreen(final float greenIn) {
        return ModuleConfig.INSTANCE.isEnabled(OldAnimationsModule.INSTANCE) ? GuiUtils.rgbToGl(OldAnimationsModule.INSTANCE.hitColor.getColor() >> 8 & 0xFF) : greenIn;
    }
    
    @ModifyArg(method = { "setBrightness" }, at = @At(value = "INVOKE", target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;", ordinal = 2))
    private float setBlue(final float blueIn) {
        return ModuleConfig.INSTANCE.isEnabled(OldAnimationsModule.INSTANCE) ? GuiUtils.rgbToGl(OldAnimationsModule.INSTANCE.hitColor.getColor() & 0xFF) : blueIn;
    }
    
    @ModifyArg(method = { "setBrightness" }, at = @At(value = "INVOKE", target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;", ordinal = 3))
    private float setAlpha(final float alphaIn) {
        return ModuleConfig.INSTANCE.isEnabled(OldAnimationsModule.INSTANCE) ? GuiUtils.rgbToGl(OldAnimationsModule.INSTANCE.hitColor.getColor() >> 24 & 0xFF) : alphaIn;
    }
}
