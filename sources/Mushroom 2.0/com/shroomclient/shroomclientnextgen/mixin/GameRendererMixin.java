package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.events.Bus;
import com.shroomclient.shroomclientnextgen.events.impl.Render3dEvent;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.render.AntiDamageCam;
import com.shroomclient.shroomclientnextgen.modules.impl.render.Zoom;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z",
            opcode = 180
        ),
        method = "renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V"
    )
    private void onRenderWorldFinished(
        float tickDelta,
        long limitTime,
        MatrixStack matrixStack,
        CallbackInfo ci
    ) {
        Bus.post(new Render3dEvent(matrixStack, tickDelta));
    }

    @Inject(at = @At("HEAD"), method = "getFov", cancellable = true)
    private void updateFovMultiplier(
        Camera camera,
        float tickDelta,
        boolean changingFov,
        CallbackInfoReturnable<Double> cir
    ) {
        if (ModuleManager.isEnabled(Zoom.class)) cir.setReturnValue(10d);
    }

    @Inject(
        at = @At("HEAD"),
        method = "tiltViewWhenHurt(Lnet/minecraft/client/util/math/MatrixStack;F)V",
        cancellable = true
    )
    private void onTiltViewWhenHurt(
        MatrixStack matrices,
        float tickDelta,
        CallbackInfo ci
    ) {
        if (ModuleManager.isEnabled(AntiDamageCam.class)) ci.cancel();
    }
}
