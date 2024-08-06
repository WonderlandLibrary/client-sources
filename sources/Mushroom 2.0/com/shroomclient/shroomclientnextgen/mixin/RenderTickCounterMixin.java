package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.util.TimerUtil;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderTickCounter.class)
public class RenderTickCounterMixin {

    @Shadow
    public float lastFrameDuration;

    @Inject(
        method = "beginRenderTick",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/render/RenderTickCounter;lastFrameDuration:F",
            shift = At.Shift.AFTER
        )
    )
    private void hookTimer(CallbackInfoReturnable<Integer> callback) {
        float customTimer = TimerUtil.getMulti();
        if (customTimer > 0) lastFrameDuration *= customTimer;
    }
}
