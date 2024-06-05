package net.shoreline.client.mixin.render;

import net.shoreline.client.Shoreline;
import net.shoreline.client.impl.event.gui.hud.RenderOverlayEvent;
import net.shoreline.client.impl.event.render.CameraClipEvent;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
@Mixin(Camera.class)
public class MixinCamera
{
    /**
     *
     * @param cir
     */
    @Inject(method = "getSubmersionType", at = @At(value = "HEAD"),
            cancellable = true)
    private void hookGetSubmersionType(CallbackInfoReturnable<CameraSubmersionType> cir)
    {
        RenderOverlayEvent.Water renderOverlayEvent =
                new RenderOverlayEvent.Water(null);
        Shoreline.EVENT_HANDLER.dispatch(renderOverlayEvent);
        if (renderOverlayEvent.isCanceled())
        {
            cir.setReturnValue(CameraSubmersionType.NONE);
            cir.cancel();
        }
    }

    /**
     *
     * @param desiredCameraDistance
     * @param cir
     */
    @Inject(method = "clipToSpace", at = @At(value = "HEAD"), cancellable = true)
    private void hookClipToSpace(double desiredCameraDistance,
                                 CallbackInfoReturnable<Double> cir)
    {
        CameraClipEvent cameraClipEvent =
                new CameraClipEvent(desiredCameraDistance);
        Shoreline.EVENT_HANDLER.dispatch(cameraClipEvent);
        if (cameraClipEvent.isCanceled())
        {
            cir.setReturnValue(cameraClipEvent.getDistance());
            cir.cancel();
        }
    }
}
