package net.shoreline.client.mixin.render;

import net.shoreline.client.Shoreline;
import net.shoreline.client.impl.event.world.SkyboxEvent;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionEffects.class)
public class MixinDimensionEffects
{
    /**
     *
     * @param skyAngle
     * @param tickDelta
     * @param cir
     */
    @Inject(method = "getFogColorOverride", at = @At(value = "HEAD"), cancellable = true)
    private void hookGetFogColorOverride(float skyAngle, float tickDelta,
                                         CallbackInfoReturnable<float[]> cir)
    {
        SkyboxEvent.Fog skyboxEvent = new SkyboxEvent.Fog(tickDelta);
        Shoreline.EVENT_HANDLER.dispatch(skyboxEvent);
        if (skyboxEvent.isCanceled())
        {
            Vec3d color = skyboxEvent.getColor();
            cir.cancel();
            cir.setReturnValue(new float[]
                    {
                            (float) color.x, (float) color.y,
                            (float) color.z, 1.0f
                    });
        }
    }
}
