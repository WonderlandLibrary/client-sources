package com.leafclient.leaf.mixin.render;

import com.leafclient.leaf.event.game.render.RenderEntitiesEvent;
import com.leafclient.leaf.management.event.EventManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderGlobal.class)
public abstract class MixinRenderGlobal {

    @Inject(
            method = "renderEntities",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/RenderGlobal;isRenderEntityOutlines()Z",
                    shift = At.Shift.BEFORE
            )
    )
    private void inject$renderEntities(Entity renderViewEntity, ICamera camera, float partialTicks, CallbackInfo info) {
        EventManager.INSTANCE.publish(new RenderEntitiesEvent(partialTicks, camera));
    }

}
