package me.kaimson.melonclient.mixins.client.renderer;

import org.spongepowered.asm.mixin.*;
import me.kaimson.melonclient.features.modules.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ auz.class })
public class MixinActiveRenderInfo
{
    @ModifyVariable(method = { "updateRenderInfo" }, ordinal = 2, at = @At("STORE"))
    private static float getPitch(final float pitch) {
        return PerspectiveModule.INSTANCE.isHeld() ? PerspectiveModule.INSTANCE.cameraPitch : pitch;
    }
    
    @ModifyVariable(method = { "updateRenderInfo" }, ordinal = 3, at = @At("STORE"))
    private static float getYaw(final float yaw) {
        return PerspectiveModule.INSTANCE.isHeld() ? PerspectiveModule.INSTANCE.cameraYaw : yaw;
    }
}
