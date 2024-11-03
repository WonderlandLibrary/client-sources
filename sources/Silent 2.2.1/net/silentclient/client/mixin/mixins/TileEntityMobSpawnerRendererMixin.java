package net.silentclient.client.mixin.mixins;

import net.minecraft.client.renderer.tileentity.TileEntityMobSpawnerRenderer;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.silentclient.client.Client;
import net.silentclient.client.mods.settings.FPSBoostMod;
import net.silentclient.client.utils.culling.EntityCulling;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntityMobSpawnerRenderer.class)
public class TileEntityMobSpawnerRendererMixin {
    //#if MC<11200
    private static final String renderEntity = "Lnet/minecraft/client/renderer/entity/RenderManager;renderEntityWithPosYaw(Lnet/minecraft/entity/Entity;DDDFF)Z";
    //#else if MC >=11200
    //$$ private static final String renderEntity = "Lnet/minecraft/client/renderer/entity/RenderManager;doRenderEntity(Lnet/minecraft/entity/Entity;DDDFFZ)V";
    //#endif

    @Inject(method = "renderMob", at = @At(value = "INVOKE", target = renderEntity))
    private static void silent$captureRenderingPre(CallbackInfo ci) {
        EntityCulling.renderingSpawnerEntity = true;
    }

    @Inject(method = "renderMob", at = @At(value = "INVOKE", target = renderEntity, shift = At.Shift.AFTER))
    private static void silent$captureRenderingPost(CallbackInfo ci) {
        EntityCulling.renderingSpawnerEntity = false;
    }

    @Inject(method = "renderMob", at = @At("HEAD"), cancellable = true)
    private static void cancelRender(MobSpawnerBaseLogic mobSpawnerLogic, double posX, double posY, double posZ, float partialTicks, CallbackInfo ci) {
        if(Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Hide Mob in Spawner").getValBoolean()) {
            ci.cancel();
        }
    }
}
