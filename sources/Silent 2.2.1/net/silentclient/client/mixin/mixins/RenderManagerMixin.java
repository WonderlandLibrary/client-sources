package net.silentclient.client.mixin.mixins;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.silentclient.client.event.impl.EventRenderHitbox;
import net.silentclient.client.hooks.RenderArrowHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderManager.class)
public class RenderManagerMixin {
    @Inject(method = "renderDebugBoundingBox", at = @At("HEAD"), cancellable = true)
    public void renderDebugBoundingBox(Entity entityIn, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (entityIn instanceof EntityArrow && RenderArrowHook.cancelRendering((EntityArrow) entityIn)) {
            ci.cancel();
        }
        EventRenderHitbox event = new EventRenderHitbox(entityIn, x, y, z, entityYaw, partialTicks);
        event.call();

        if(event.isCancelable()) {
            ci.cancel();
        }
    }
}
