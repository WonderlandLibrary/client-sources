package net.silentclient.client.mixin.mixins;

import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.entity.EntityLivingBase;
import net.silentclient.client.Client;
import net.silentclient.client.mods.settings.FPSBoostMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerArrow.class)
public class LayerArrowMixin {
    @Inject(method = "doRenderLayer", at = @At("HEAD"), cancellable = true)
    public void cancel(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale, CallbackInfo ci) {
        if(Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Hide Stuck Arrows").getValBoolean()) {
            ci.cancel();
        }
    }

    @Redirect(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderHelper;disableStandardItemLighting()V"))
    private void silent$removeDisable() {
        // no-op
    }

    @Redirect(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderHelper;enableStandardItemLighting()V"))
    private void silent$removeEnable() {
        // no-op
    }
}
