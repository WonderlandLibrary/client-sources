package net.silentclient.client.mixin.mixins;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.tileentity.TileEntitySkull;
import net.silentclient.client.Client;
import net.silentclient.client.mods.settings.FPSBoostMod;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntitySkullRenderer.class)
public class TileEntitySkullRendererMixin {
    @Inject(method = "renderTileEntityAt(Lnet/minecraft/tileentity/TileEntitySkull;DDDFI)V", at = @At("HEAD"), cancellable = true)
    public void cancelRendering(TileEntitySkull te, double x, double y, double z, float partialTicks, int destroyStage, CallbackInfo ci) {
        if (Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Hide Skulls").getValBoolean()) {
            ci.cancel();
        }
    }

    //#if MC==10809
    @Inject(method = "renderSkull", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void silent$enableBlending(CallbackInfo ci) {
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
    }
    //#endif
}
