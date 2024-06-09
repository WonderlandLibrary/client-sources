package com.leafclient.leaf.mixin.render.entity;

import com.leafclient.leaf.event.game.entity.PlayerMotionEvent;
import com.leafclient.leaf.extension.ExtensionEntityPlayerSP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderLivingBase.class)
public abstract class MixinRenderEntityLivingBase {

    private float rotationPitch;
    private float prevRotationPitch;

    @Inject(
            method = "doRender",
            at = @At("HEAD")
    )
    private void inject$rotationPitchRender(EntityLivingBase entity, double x, double y, double z, float entityYaw,
                                            float partialTicks, CallbackInfo info) {
        if(entity instanceof EntityPlayerSP) {
            final ExtensionEntityPlayerSP player = ((ExtensionEntityPlayerSP)entity);
            final PlayerMotionEvent.Pre e = player.getLastMotionEvent();
            if(e != null && e.isHeadRotationInjected() && e.isRotationModified()) {
                rotationPitch = entity.rotationPitch;
                prevRotationPitch = entity.prevRotationPitch;

                entity.rotationPitch = e.getRotationPitch();
                entity.prevRotationPitch = e.getRotationPitch();
            }
        }
    }

    @Inject(
            method = "doRender",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/Render;doRender(Lnet/minecraft/entity/Entity;DDDFF)V",
                    shift = At.Shift.AFTER
            )
    )
    private void inject$rotationPitchPostRender(EntityLivingBase entity, double x, double y, double z, float entityYaw,
                                            float partialTicks, CallbackInfo info) {
        if(entity instanceof EntityPlayerSP) {
            final ExtensionEntityPlayerSP player = ((ExtensionEntityPlayerSP)entity);
            final PlayerMotionEvent.Pre e = player.getLastMotionEvent();
            if(e != null && e.isHeadRotationInjected() && e.isRotationModified()) {
                entity.rotationPitch = rotationPitch;
                entity.prevRotationPitch = prevRotationPitch;
            }
        }
    }

}
